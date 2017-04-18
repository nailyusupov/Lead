/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contact;

import com.datasourse.ControlPanelPool;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author nail yusupov
 */
public class ContactData {

    private String name, email, detailedPageViews, website, remoteAddress, address, phone, businessName, source, businessCollegues, businessNameHeader, totalPagesVisited, totalClicks, geoLocation, networkOwner, networkOwnerId;
    private List<String> relatedIp;
    private Map<String, PageNamePair> pageViews;

    public ContactData(String id, String contextPath) {
        java.sql.Connection con = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;
        try {
            con = ControlPanelPool.getInstance().getConnection();
            stmt = con.prepareStatement("SELECT * FROM leadContact WHERE Id = ?");
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                name = rs.getString("Name");
                email = rs.getString("Email");
                website = rs.getString("WebSite");
                remoteAddress = rs.getString("RemoteAddress");
                address = rs.getString("Address");
                phone = rs.getString("Phone");
                businessName = rs.getString("BusinessName");
                source = rs.getString("Source");
            }
            stmt = con.prepareStatement("SELECT * FROM leadContact WHERE (BusinessName = ? AND BusinessName != '' AND BusinessName != 'NA' AND BusinessName != 'N/A' AND BusinessName != '-' AND Id != ? AND Email != ?) OR (WebSite = ? AND WebSite != '' AND WebSite != '-' AND WebSite != 'NA' AND WebSite != 'N/A' AND Id != ? AND Email != ?)");
            stmt.setString(1, businessName);
            stmt.setString(2, id);
            stmt.setString(3, email);
            stmt.setString(4, website);
            stmt.setString(5, id);
            stmt.setString(6, email);
            rs = stmt.executeQuery();
            businessCollegues = "";
            while (rs.next()) {
                businessCollegues += "<a href=\"" + contextPath + "/contact?id=" + rs.getString("Id") + "\" class=\"list-group-item\">" + rs.getString("Name") + "</a>\n";
            }
            businessNameHeader = "";
            if (businessName!=null&&!businessName.isEmpty()) {
                businessNameHeader = businessName + " Employees";
            }
            relatedIp = new ArrayList<>();
            if (email!=null&&!email.isEmpty()) {
                stmt = con.prepareStatement("SELECT RemoteAddress FROM LeadContact WHERE Id IN (SELECT Id FROM LeadContact WHERE Email = ?)");
                stmt.setString(1, email);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    relatedIp.add(rs.getString("RemoteAddress"));
                }
            }
            pageViews = new HashMap<>();
            totalPagesVisited = "0";
            totalClicks = "0";
            String relatedIps = "";
            if (relatedIp!=null&&!relatedIp.isEmpty()) {
                for (int i = 0; i < relatedIp.size(); i++) {
                    stmt = con.prepareStatement("SELECT DISTINCT TOP 15 leadSession.pageTitle, leadSession.location, COUNT(*) as pageViews FROM leadSession WHERE remoteAddress = ? GROUP BY leadSession.pageTitle, leadSession.location ORDER BY COUNT(*) DESC");
                    stmt.setString(1, relatedIp.get(i));
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        if (pageViews.containsKey(rs.getString("pageTitle"))) {
                            pageViews.get(rs.getString("pageTitle")).visitCount = (pageViews.get(rs.getString("pageTitle")).visitCount + rs.getInt("pageViews"));
                        } else {
                            pageViews.put(rs.getString("pageTitle"), new PageNamePair(rs.getString("pageTitle"), rs.getString("location"), rs.getInt("pageViews")));
                        }
                    }
                    relatedIps += relatedIp.get(i) + "','";
                }
            }
            if (relatedIps!=null&&!relatedIps.isEmpty()) {
                stmt = con.prepareStatement("SELECT COUNT(*) AS pCount, COUNT(DISTINCT pageTitle) as dpCount FROM leadSession WHERE remoteAddress IN ('"+relatedIps+"')");
                rs = stmt.executeQuery();
                while (rs.next()) {
                    totalPagesVisited = rs.getString("dpCount");
                    totalClicks = rs.getString("pCount");
                }
            }
            stmt = con.prepareStatement("SELECT LeadOrganization.Id, LeadOrganization.Name, LeadOrganization.Address, LeadOrganization.City, LeadOrganization.StateProv, LeadOrganization.PostalCode, LeadOrganization.Country FROM LeadRemoteAddress, LeadOrganization WHERE ip = ? AND prefered = 1 AND LeadRemoteAddress.LeadOrganization_uid = LeadOrganization.Id");
            stmt.setString(1, remoteAddress);
            rs = stmt.executeQuery();
            networkOwner = "";
            geoLocation = "";
            while (rs.next()) {
                networkOwner = rs.getString("Name");
                Locale l = new Locale("", rs.getString("Country"));
                networkOwnerId = rs.getString("Id");
                geoLocation = rs.getString("Address") == null || rs.getString("Address").isEmpty() ? "" : rs.getString("Address") + ", ";
                geoLocation += rs.getString("City") == null || rs.getString("City").isEmpty() ? "" : rs.getString("City") + ", ";
                geoLocation += rs.getString("City") == null || rs.getString("StateProv").isEmpty() ? "" : rs.getString("StateProv") + " ";
                geoLocation += rs.getString("City") == null || rs.getString("PostalCode").isEmpty() ? "" : rs.getString("PostalCode") + ", ";
                geoLocation += l.getDisplayCountry();
            }
            detailedPageViews = "";
            stmt = con.prepareStatement("SELECT location, timeIn FROM leadSession WHERE remoteAddress IN ('"+relatedIps+"') ORDER BY Id DESC");
            rs = stmt.executeQuery();
            while (rs.next()) {
                detailedPageViews += "<a target=\"_blank\" href=\""+rs.getString("location")+"\" class=\"list-group-item\">\n" +
"                                        <i class=\"fa fa-link fa-fw\"></i> "+rs.getString("location")+"\n" +
"                                        <span class=\"pull-right text-muted small\"><em>"+rs.getString("timeIn")+"</em>\n" +
"                                        </span>\n" +
"                                    </a>";
            }
            con.close();
        } catch (SQLException | PropertyVetoException | IOException ex) {
            Logger.getLogger(ContactData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(con, stmt, rs);
        }
    }

    /**
     * @return the businessCollegues
     */
    public String getBusinessCollegues() {
        return businessCollegues;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @return the remoteAddress
     */
    public String getRemoteAddress() {
        return remoteAddress;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @return the businessName
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @return the businessNameHeader
     */
    public String getBusinessNameHeader() {
        return businessNameHeader;
    }

    /**
     * @return the relatedIp
     */
    public List<String> getRelatedIp() {
        return relatedIp;
    }

    /**
     * @return the pageViews
     */
    public Map<String, PageNamePair> getPageViews() {
        return pageViews;
    }

    public String getPageViewsHtml() {
        String result = "";
        /*for (String key : pageViews.keySet()) {
            result += "<div class=\"list-group\">\n"
                    + "    <li class=\"list-group-item\">\n"
                    + "        <span class=\"badge\">Clicks: " + pageViews.get(key).visitCount + "</span>\n"
                    + "        <a href=\"" + pageViews.get(key).link + "/\">" + pageViews.get(key).name + "</a>\n"
                    + "    </li>\n"
                    + "</div>\n";
        }*/

        Object[] a = pageViews.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, PageNamePair>) o2).getValue().visitCount
                        .compareTo(((Map.Entry<String, PageNamePair>) o1).getValue().visitCount);
            }
        });
        for (int i = 0; i < Math.min(12, a.length); i++) {
            //result += "<div class=\"list-group\">\n"
            //       + "    <li class=\"list-group-item\">\n"
            //        + "        <span class=\"badge\">Clicks: " + pageViews.get(String.valueOf(((Map.Entry<String, Integer>) a[i]).getKey())).visitCount + "</span>\n"
            //        + "        <a href=\"" + pageViews.get(String.valueOf(((Map.Entry<String, Integer>) a[i]).getKey())).link + "/\">" + pageViews.get(String.valueOf(((Map.Entry<String, Integer>) a[i]).getKey())).name + "</a>\n"
            //        + "    </li>\n"
            //        + "</div>\n";
            result += "    <a target=\"_blank\" class=\"list-group-item list-group-item-action\" href=\"" + pageViews.get(String.valueOf(((Map.Entry<String, Integer>) a[i]).getKey())).link + "\">\n"
                    + "        <h4 class=\"list-group-item-heading\">" + (pageViews.get(String.valueOf(((Map.Entry<String, Integer>) a[i]).getKey())).name.length() > 66 ? pageViews.get(String.valueOf(((Map.Entry<String, Integer>) a[i]).getKey())).name.substring(0, Math.min(66, pageViews.get(String.valueOf(((Map.Entry<String, Integer>) a[i]).getKey())).name.length())) + "..." : pageViews.get(String.valueOf(((Map.Entry<String, Integer>) a[i]).getKey())).name) + "<i style=\"cursor:help;\" class=\"fa fa-map-o pull-right pages\" data-link=\""+pageViews.get(String.valueOf(((Map.Entry<String, Integer>) a[i]).getKey())).link+"\"></i></h4>\n"
                    + "        <p class=\"list-group-item-text\">Total Clicks: " + pageViews.get(String.valueOf(((Map.Entry<String, Integer>) a[i]).getKey())).visitCount + "</p>\n"
                    + "    </a>";
        }

        return result;
    }

    /**
     * @return the totalPagesVisited
     */
    public String getTotalPagesVisited() {
        return totalPagesVisited;
    }

    /**
     * @return the totalClicks
     */
    public String getTotalClicks() {
        return totalClicks;
    }

    /**
     * @return the geoLocation
     */
    public String getGeoLocation() {
        return geoLocation;
    }

    /**
     * @return the networkOwner
     */
    public String getNetworkOwner() {
        return networkOwner;
    }

    public String getPhoneLabel() {
        return phone == null || phone.isEmpty() ? "" : "Phone: ";
    }

    public String getEmailLabel() {
        return email == null || email.isEmpty() ? "" : "Email: ";
    }

    public String getBusinessLabel() {
        return businessName == null || businessName.isEmpty() ? "" : "Business: ";
    }

    public String getWebsiteLabel() {
        return website == null || website.isEmpty() ? "" : "Website: ";
    }

    public String getAddressLabel() {
        return address == null || address.isEmpty() ? "" : "Address: ";
    }

    /**
     * @return the networkOwnerId
     */
    public String getNetworkOwnerId() {
        return networkOwnerId;
    }

    /**
     * @return the detailedPageViews
     */
    public String getDetailedPageViews() {
        return detailedPageViews;
    }
    
    public String getBusinessColleguesLabel(){
        return businessCollegues==null||businessCollegues.isEmpty()?"":"Business Collegues: ";
    }

    public String getPurchasesByBusinessName() {
        String result = "";
        java.sql.Connection con = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;
        try {
            con = ControlPanelPool.getInstance().getConnection();
            stmt = con.prepareStatement("SELECT tblORDTFILE.PNAME, tblORDTFILE.QTY, tblORDTFILE.PackSize, tblORDTFILE.UM, tblORDTFILE.ShpDate, tblORDTFILE.PackType FROM tblORDTFILE, tblORDHFILE WHERE tblORDHFILE.ID = tblORDTFILE.OrdID AND tblORDHFILE.CNAME = ?");
            stmt.setString(1, businessName);
            rs = stmt.executeQuery();
            while(rs.next()){
                result += "<a class=\"list-group-item list-group-item-action\"><h4 class=\"list-group-item-heading\">"+rs.getString("PNAME")+" - "+rs.getString("QTY")+"x"+rs.getString("PackSize") +" "+rs.getString("UM") +" "+rs.getString("PackType")+"</h4> <p class=\"list-group-item-text\">Shipped on "+rs.getString("ShpDate")+"</p></a>\n";
            }
            con.close();
        } catch (IOException | SQLException | PropertyVetoException ex) {
            Logger.getLogger(ContactData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result.isEmpty()?"":("                            <div class=\"col-md-7 col-sm-12 col-xs-12\">\n" +
"                                <div class=\"panel panel-danger\">\n"
                + "<div class=\"panel-heading\"><h3>Products purchased by " +businessName+"</h3></div>" +
"                                    <div class=\"panel-body\">\n" +
"                                        \n                                    </div>\n<ul class=\"list-group\">" +result+
"                                </ul></div>\n" +
"                            </div>");
    }
    
        public String getPurchasesByUserName() {
        String result = "";
        java.sql.Connection con = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;
        try {
            con = ControlPanelPool.getInstance().getConnection();
            stmt = con.prepareStatement("SELECT tblORDTFILE.PNAME, tblORDTFILE.QTY, tblORDTFILE.PackSize, tblORDTFILE.UM, tblORDTFILE.ShpDate, tblORDTFILE.PackType FROM tblORDTFILE, tblORDHFILE WHERE tblORDHFILE.ID = tblORDTFILE.OrdID AND tblORDHFILE.CContact = ?");
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            while(rs.next()){
                result += "<a class=\"list-group-item list-group-item-action\"><h4 class=\"list-group-item-heading\">"+rs.getString("PNAME")+" - "+rs.getString("QTY")+"x"+rs.getString("PackSize") +" "+rs.getString("UM") +" "+rs.getString("PackType")+"</h4> <p class=\"list-group-item-text\">Shipped on "+rs.getString("ShpDate")+"</p></a>\n";
            }
            con.close();
        } catch (IOException | SQLException | PropertyVetoException ex) {
            Logger.getLogger(ContactData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result.isEmpty()?"":("                            <div class=\"col-md-7 col-sm-12 col-xs-12\">\n" +
"                                <div class=\"panel panel-danger\">\n"
                + "<div class=\"panel-heading\"><h3>Products purchased by " +name+"</h3></div>" +
"                                    <div class=\"panel-body\">\n" +
"                                        \n                                    </div>\n<ul class=\"list-group\">" +result+
"                                </ul></div>\n" +
"                            </div>");
    }
    
}

class PageNamePair {

    String name, link;
    Integer visitCount;

    PageNamePair(String n, String l, Integer count) {
        this.name = n;
        this.link = l;
        this.visitCount = count;
    }

}

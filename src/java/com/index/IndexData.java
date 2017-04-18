/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.index;

import com.datasourse.ControlPanelPool;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author nail yusupov
 */
class IndexData {

    private String companyLinks, totalAddressCount, totalSessionCount, contacts, mostlyVisited;

    public IndexData(String startdate, String enddate, String table, String contextPath) {
        java.sql.Connection con = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;
        try {
            con = ControlPanelPool.getInstance().getConnection();
            stmt = con.prepareStatement("SELECT COUNT(DISTINCT remoteAddress) as remoteAdd, COUNT(DISTINCT sessionId) as sess FROM "+table+" WHERE timeIn > CONVERT(date, ?) AND timeIn < CONVERT(date, ?)");
            stmt.setString(1, startdate);
            stmt.setString(2, enddate);
            rs = stmt.executeQuery();
            totalAddressCount = "";
            totalSessionCount = "";
            while (rs.next()) {
                totalAddressCount = rs.getString("remoteAdd");
                totalSessionCount = rs.getString("sess");
            }
            stmt = con.prepareStatement("SELECT TOP 12 LeadOrganization.Id, LeadOrganization.Name, COUNT(DISTINCT "+table+".remoteAddress) as c, COUNT("+table+".remoteAddress) as p FROM LeadOrganization, "+table+", LeadRemoteAddress WHERE "+table+".remoteAddress = LeadRemoteAddress.ip AND LeadRemoteAddress.LeadOrganization_uid  = LeadOrganization.id AND LeadRemoteAddress.prefered = 1 AND LeadOrganization.Address != '' AND "+table+".timeIn > CONVERT(date, ?) AND "+table+".timeIn < CONVERT(date, ?) GROUP BY LeadOrganization.Name, LeadOrganization.Id HAVING COUNT("+table+".remoteAddress) > 0");
            stmt.setString(1, startdate);
            stmt.setString(2, enddate);
            rs = stmt.executeQuery();
            companyLinks = "";
            while (rs.next()) {
                //companyLinks += "<a style=\"display: block; font-size: 12pt; padding-bottom: 5px;\" href=\"" + contextPath + "/org?id=" + rs.getString("Id") + "&startdate=" + startdate + "&enddate=" + enddate + "\">" + rs.getString("Name") + " <span style=\"color: black;\">(<small>visitors:</small> " + rs.getString("c") + ", <small>page views:</small> " + rs.getString("p") + ")</span>" + "</a>";
                companyLinks += "<a href=\"" + contextPath + "/org?id=" + rs.getString("id") + "&startdate=" + startdate + "&enddate=" + enddate + "\" class=\"list-group-item list-group-item-action\"><h5 class=\"list-group-item-heading\"><b><i class=\"fa fa-address-card fa-fw\"></i> " + rs.getString("Name") + "</b></h5><p class=\"list-group-item-text\">visitors: " + rs.getString("c") + "  page views: " + rs.getString("p") + "</p></a>\n";
            }
            stmt = con.prepareStatement("SELECT TOP 18 * FROM LeadContact WHERE RemoteAddress IN (SELECT "+table+".remoteAddress FROM "+table+" WHERE "+table+".timeIn > CONVERT(date, ?) AND "+table+".timeIn < CONVERT(date, ?)) ORDER BY id DESC");
            stmt.setString(1, startdate);
            stmt.setString(2, enddate);
            rs = stmt.executeQuery();
            contacts = "";
            while (rs.next()) {
                //contacts += "<a href=\""+contextPath+"/contact?id="+rs.getString("id")+"\" class=\"list-group-item list-group-item-action\"><h5 class=\"list-group-item-heading\"><b>"+rs.getString("Name")+"</b></h5><p class=\"list-group-item-text\"><a href=\"mailto:"+rs.getString("Email")+"\">"+rs.getString("Email")+"</a> <a href=\"tel:"+rs.getString("Phone")+"\">"+rs.getString("Phone")+"</a><span style=\"display: block;\"><a href=\""+rs.getString("WebSite")+"\">"+rs.getString("BusinessName")+"</a></span><span style=\"display: block;\">"+rs.getString("Address")+"</span></p></a>\n";
                contacts += "<a class=\"list-group-item list-group-item-action\" href=\"" + contextPath + "/contact?id=" + rs.getString("id") + "\">"
                        + "<h5 class=\"list-group-item-heading\"><b style=\"font-size: 12pt;\"><i class=\"fa fa-address-card-o fa-fw\"></i> " + (rs.getString("Name")==null?"":rs.getString("Name"))+"</b>"+"<span style=\"padding-left: 20px; font-size: 12pt;\">"+(rs.getString("Phone")==null||rs.getString("Phone").isEmpty()?"":"tel.: " +rs.getString("Phone")) + "</span></h5>"
                        + "<p class=\"list-group-item-text\">"
                        + "<span style=\"display: block; padding-bottom: 5px;\">"+(rs.getString("Email")==null?"":rs.getString("Email"))+"</span>" + "<span style=\"display: block;\">"+ (rs.getString("BusinessName")==null?"":rs.getString("BusinessName")) + "<span style=\"padding-left: 20px;\">"+(rs.getString("WebSite")==null?"":rs.getString("WebSite"))+"</span></span><span style=\"display: block; font-size: 10pt; padding-top: 8px;\"><i>" + (rs.getString("Address")==null?"":rs.getString("Address")) + "</i></span></p></a>";
            }
            stmt = con.prepareStatement("SELECT TOP 8 COUNT(pageTitle) as c, pageTitle, location FROM "+table+" WHERE "+table+".timeIn > CONVERT(date, ?) AND "+table+".timeIn < CONVERT(date, ?) GROUP BY pageTitle, location ORDER BY COUNT(pageTitle) DESC");
            stmt.setString(1, startdate);
            stmt.setString(2, enddate);
            rs = stmt.executeQuery();
            mostlyVisited = "";
            while(rs.next()){
                mostlyVisited += "                                    <a href=\""+rs.getString("location")+"\" class=\"list-group-item\">\n" +
"                                        <i class=\"fa fa-link fa-fw\"></i> "+(rs.getString("pageTitle").length()>70?rs.getString("pageTitle").substring(0,70)+"...":rs.getString("pageTitle"))+"\n" +
"                                        <span class=\"pull-right text-muted small\"><em>"+rs.getString("c")+" visits</em>\n" +
"                                        </span>\n" +
"                                    </a>";
            }
            con.close();
        } catch (SQLException | PropertyVetoException | IOException ex) {
            Logger.getLogger(IndexData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(con, stmt, rs);
        }
    }

    /**
     * @return the companyLinks
     */
    public String getCompanyLinks() {
        return companyLinks;
    }

    /**
     * @return the totalAddressCount
     */
    public String getTotalAddressCount() {
        return totalAddressCount;
    }

    /**
     * @return the totalSessionCount
     */
    public String getTotalSessionCount() {
        return totalSessionCount;
    }

    public String getContacts() {
        return contacts;
    }

    /**
     * @return the mostlyVisited
     */
    public String getMostlyVisited() {
        return mostlyVisited;
    }

}

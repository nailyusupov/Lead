/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization;

import com.datasourse.ControlPanelPool;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author nail yusupov
 */
class OrganizationData {

    private String pagesVisited, remoteUsers, contactInformation, referers, orgName, address, city, state, zip, country, totalClicks;

    public OrganizationData(String startdate, String enddate, String id, String contextPath) {
        java.sql.Connection con = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;
        try {
            con = ControlPanelPool.getInstance().getConnection();
            stmt = con.prepareStatement("SELECT * FROM LeadOrganization WHERE Id = ?");
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                orgName = rs.getString("Name");
                address = rs.getString("Address");
                city = rs.getString("City");
                state = rs.getString("StateProv");
                zip = rs.getString("PostalCode");
                country = rs.getString("Country");
            }
            stmt = con.prepareStatement("SELECT DISTINCT TOP 30 leadSession.pageTitle, leadSession.location, COUNT(leadSession.pageTitle) as c FROM leadSession, LeadRemoteAddress, LeadOrganization WHERE leadSession.timeIn > CONVERT(date, ?) AND leadSession.timeIn < CONVERT(date, ?) AND LeadRemoteAddress.ip = leadSession.remoteAddress AND LeadRemoteAddress.LeadOrganization_uid = LeadOrganization.id AND LeadOrganization.Id = ? GROUP BY leadSession.pageTitle, leadSession.location HAVING COUNT(leadSession.pageTitle) > 0 ORDER BY c DESC");
            stmt.setString(1, startdate);
            stmt.setString(2, enddate);
            stmt.setString(3, id);
            rs = stmt.executeQuery();
            pagesVisited = "";
            while (rs.next()) {
                pagesVisited += "<a style=\"display: block; padding-bottom: 5px;\" href=\"" + rs.getString("location") + "\" target=\"_blank\">" + ((rs.getString("pageTitle").length() < 70) ? rs.getString("pageTitle") : (rs.getString("pageTitle").substring(0, 70) + "...")) + " <span style=\"color: black; font-size: 14pt;\"> (" + rs.getString("c") + ")</span>" + "</a>";
            }
            stmt = con.prepareStatement("SELECT DISTINCT TOP 30 leadSession.remoteAddress FROM leadSession, LeadRemoteAddress, LeadOrganization, LeadContact WHERE leadSession.timeIn > CONVERT(date, ?) AND leadSession.timeIn < CONVERT(date, ?) AND LeadRemoteAddress.ip = leadSession.remoteAddress AND LeadRemoteAddress.LeadOrganization_uid = LeadOrganization.id AND LeadOrganization.Id = ?");
            stmt.setString(1, startdate);
            stmt.setString(2, enddate);
            stmt.setString(3, id);
            rs = stmt.executeQuery();
            remoteUsers = "";
            while (rs.next()) {
                remoteUsers += "<span style=\"display: block;\"><a href=\"" + contextPath + "/rem?startdate=" + startdate + "&enddate=" + enddate + "&ip=" + rs.getString("remoteAddress") + "\">" + rs.getString("remoteAddress") + "</a></span>";
            }
            stmt = con.prepareStatement("SELECT DISTINCT TOP 5 LeadContact.Name, LeadContact.Email, LeadContact.WebSite, LeadContact.RemoteAddress, LeadContact.Address, LeadContact.Phone, LeadContact.BusinessName FROM leadSession, LeadRemoteAddress, LeadOrganization, LeadContact WHERE leadSession.timeIn > CONVERT(date, ?) AND leadSession.timeIn < CONVERT(date, ?) AND LeadRemoteAddress.ip = leadSession.remoteAddress AND LeadRemoteAddress.LeadOrganization_uid = LeadOrganization.id AND LeadOrganization.Id = ? AND LeadContact.RemoteAddress = leadSession.remoteAddress");
            stmt.setString(1, startdate);
            stmt.setString(2, enddate);
            stmt.setString(3, id);
            rs = stmt.executeQuery();
            contactInformation = "";
            while (rs.next()) {
                contactInformation += "<div style=\"display: block;\"><span>" + rs.getString("Name") + " </span><span>" + rs.getString("Email") + "</span><span style=\"display: block;\">" + rs.getString("Phone") + "</span><span style=\"display: block;\">" + rs.getString("WebSite") + "</span><span style=\"display: block;\">" + rs.getString("BusinessName") + "</span><span style=\"display: block;\">" + rs.getString("Address") + "</span><span style=\"display: block;\">" + rs.getString("RemoteAddress") + "</span></div>";
            }
            if (contactInformation.isEmpty()) {
                contactInformation = "none";
            }
            stmt = con.prepareStatement("SELECT DISTINCT TOP 30 leadSession.referer, COUNT(leadSession.referer) as c  FROM leadSession, LeadRemoteAddress, LeadOrganization WHERE leadSession.timeIn > CONVERT(date, ?) AND leadSession.timeIn < CONVERT(date, ?) AND LeadRemoteAddress.ip = leadSession.remoteAddress AND LeadRemoteAddress.LeadOrganization_uid = LeadOrganization.id AND LeadOrganization.Id = ? AND leadSession.referer != '' GROUP BY leadSession.referer HAVING COUNT(leadSession.referer) > 0 ORDER BY c DESC");
            stmt.setString(1, startdate);
            stmt.setString(2, enddate);
            stmt.setString(3, id);
            rs = stmt.executeQuery();
            referers = "";
            Map<String, Integer> refs = new HashMap<>();
            while (rs.next()) {
                if (refs.containsKey(rs.getString("referer").split("/")[2])) {
                    refs.put(rs.getString("referer").split("/")[2], refs.get(rs.getString("referer").split("/")[2]) + rs.getInt("c"));
                } else {
                    refs.put(rs.getString("referer").split("/")[2], rs.getInt("c"));
                }
                //referers += "<span style=\"display: block; padding-bottom: 5px;\">"+rs.getString("referer").split("/")[2]+" ("+rs.getString("c")+")"+"</span>";
            }
            for (String key : refs.keySet()) {
                referers += "<span style=\"display: block; padding-bottom: 5px;\">" + key + " (" + refs.get(key) + ")" + "</span>";
            }
            con.close();
        } catch (SQLException | PropertyVetoException | IOException ex) {
            Logger.getLogger(OrganizationData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(con, stmt, rs);
        }
    }

    public OrganizationData(String id, String contextPath, String tableName) {
        java.sql.Connection con = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;
        try {
            con = ControlPanelPool.getInstance().getConnection();
            stmt = con.prepareStatement("SELECT * FROM LeadOrganization WHERE Id = ?");
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                orgName = rs.getString("Name");
                address = rs.getString("Address");
                city = rs.getString("City");
                state = rs.getString("StateProv");
                zip = rs.getString("PostalCode");
                country = rs.getString("Country");
            }
            stmt = con.prepareStatement("SELECT COUNT(" + tableName + ".pageTitle) as c FROM " + tableName + ", LeadRemoteAddress, LeadOrganization WHERE LeadRemoteAddress.ip = " + tableName + ".remoteAddress AND LeadRemoteAddress.LeadOrganization_uid = LeadOrganization.id AND LeadOrganization.Id = ?");
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            //pagesVisited = "";
            totalClicks = "";
            while (rs.next()) {
                totalClicks = rs.getString("c");
                //pagesVisited += "<a style=\"display: block; padding-bottom: 5px;\" href=\"" + rs.getString("location") + "\" target=\"_blank\">" + ((rs.getString("pageTitle").length()<70)?rs.getString("pageTitle"):(rs.getString("pageTitle").substring(0, 70)+"...")) + " <span style=\"color: black; font-size: 14pt;\"> (" + rs.getString("c") + ")</span>" + "</a>";
            }
            stmt = con.prepareStatement("SELECT DISTINCT TOP 30 " + tableName + ".remoteAddress FROM " + tableName + ", LeadRemoteAddress, LeadOrganization, LeadContact WHERE LeadRemoteAddress.ip = " + tableName + ".remoteAddress AND LeadRemoteAddress.LeadOrganization_uid = LeadOrganization.id AND LeadOrganization.Id = ?");
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            remoteUsers = "";
            while (rs.next()) {
                remoteUsers += "<span style=\"display: block;\"><a href=\"" + contextPath + "/rem?ip=" + rs.getString("remoteAddress") + "\">" + rs.getString("remoteAddress") + "</a></span>";
            }
            stmt = con.prepareStatement("SELECT DISTINCT TOP 5 LeadContact.Name, LeadContact.Email, LeadContact.WebSite, LeadContact.RemoteAddress, LeadContact.Address, LeadContact.Phone, LeadContact.BusinessName FROM " + tableName + ", LeadRemoteAddress, LeadOrganization, LeadContact WHERE LeadRemoteAddress.ip = " + tableName + ".remoteAddress AND LeadRemoteAddress.LeadOrganization_uid = LeadOrganization.id AND LeadOrganization.Id = ? AND LeadContact.RemoteAddress = " + tableName + ".remoteAddress");
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            contactInformation = "";
            while (rs.next()) {
                contactInformation += "<div style=\"display: block;\"><span>" + rs.getString("Name") + " </span><span>" + rs.getString("Email") + "</span><span style=\"display: block;\">" + rs.getString("Phone") + "</span><span style=\"display: block;\">" + rs.getString("WebSite") + "</span><span style=\"display: block;\">" + rs.getString("BusinessName") + "</span><span style=\"display: block;\">" + rs.getString("Address") + "</span><span style=\"display: block;\">" + rs.getString("RemoteAddress") + "</span></div>";
            }
            if (contactInformation.isEmpty()) {
                contactInformation = "none";
            }
            stmt = con.prepareStatement("SELECT DISTINCT TOP 30 " + tableName + ".referer, COUNT(" + tableName + ".referer) as c  FROM " + tableName + ", LeadRemoteAddress, LeadOrganization WHERE LeadRemoteAddress.ip = " + tableName + ".remoteAddress AND LeadRemoteAddress.LeadOrganization_uid = LeadOrganization.id AND LeadOrganization.Id = ? AND " + tableName + ".referer != '' GROUP BY " + tableName + ".referer HAVING COUNT(" + tableName + ".referer) > 0 ORDER BY c DESC");
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            referers = "";
            Map<String, Integer> refs = new HashMap<>();
            while (rs.next()) {
                if (refs.containsKey(rs.getString("referer").split("/")[2])) {
                    refs.put(rs.getString("referer").split("/")[2], refs.get(rs.getString("referer").split("/")[2]) + rs.getInt("c"));
                } else {
                    refs.put(rs.getString("referer").split("/")[2], rs.getInt("c"));
                }
                //referers += "<span style=\"display: block; padding-bottom: 5px;\">"+rs.getString("referer").split("/")[2]+" ("+rs.getString("c")+")"+"</span>";
            }
            for (String key : refs.keySet()) {
                referers += "<span style=\"display: block; padding-bottom: 5px;\">" + key + " (" + refs.get(key) + ")" + "</span>";
            }
            con.close();
        } catch (SQLException | PropertyVetoException | IOException ex) {
            Logger.getLogger(OrganizationData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(con, stmt, rs);
        }
    }

    /**
     * @return the pagesVisited
     */
    public String getPagesVisited() {
        return pagesVisited;
    }

    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the remoteUsers
     */
    public String getRemoteUsers() {
        return remoteUsers;
    }

    /**
     * @return the contactInformation
     */
    public String getContactInformation() {
        return contactInformation;
    }

    /**
     * @return the referers
     */
    public String getReferers() {
        return referers;
    }

}

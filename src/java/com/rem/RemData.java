/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rem;

import com.datasourse.ControlPanelPool;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author nail yusupov
 */
public class RemData {

    private String pagesVisited, networkId, networkName, networkCountry;
    private List<RemContact> contacts;

    public RemData(String ip, String contextPath) {
        java.sql.Connection con = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;
        contacts = new ArrayList<>();
        try {
            con = ControlPanelPool.getInstance().getConnection();
            stmt = con.prepareStatement("SELECT DISTINCT TOP 30 leadSession.pageTitle, leadSession.location, COUNT(leadSession.pageTitle) as c FROM leadSession WHERE leadSession.remoteAddress = ? GROUP BY leadSession.pageTitle, leadSession.location HAVING COUNT(leadSession.pageTitle) > 0 ORDER BY c DESC"); //insert after where for timed search ->> leadSession.timeIn > CONVERT(date, ?) AND leadSession.timeIn < CONVERT(date, ?) AND
            //stmt.setString(1, startdate);
            //stmt.setString(2, enddate);
            stmt.setString(1, ip);
            rs = stmt.executeQuery();
            pagesVisited = "";
            while (rs.next()) {
                pagesVisited += "<a class=\"list-group-item\" target=\"_blank\" style=\"display: block; margin: 5px; font-size: 12pt;\" href=\"" + rs.getString("location") + "\" target=\"_blank\"><i class=\"fa fa-link fa-fw\"></i> " + ((rs.getString("pageTitle").length() < 70) ? rs.getString("pageTitle") : (rs.getString("pageTitle").substring(0, 70) + "...")) + " <span class=\"pull-right text-muted small\"><em>" + rs.getString("c") + " visits</em></span></a>";
            }
            stmt = con.prepareStatement("SELECT LeadOrganization.Id as networkId, LeadOrganization.Name as networkName, LeadOrganization.Country as networkCountry, LeadContact.Name as contactName, LeadContact.BusinessName as businessName, LeadContact.Email as contactEmail, LeadContact.Address as contactAddress, LeadContact.Id as contactId, LeadContact.Phone as contactPhone FROM LeadRemoteAddress, LeadOrganization, LeadContact WHERE ip = ? AND LeadRemoteAddress.prefered = 1 AND LeadRemoteAddress.LeadOrganization_uid = LeadOrganization.Id AND LeadRemoteAddress.ip = LeadContact.RemoteAddress");
            stmt.setString(1, ip);
            rs = stmt.executeQuery();
            while (rs.next()) {
                RemContact c = new RemContact();
                c.name = rs.getString("contactName");
                c.company = rs.getString("businessName");
                c.email = rs.getString("contactEmail");
                c.address = rs.getString("contactAddress");
                c.id = rs.getString("contactId");
                c.phone = rs.getString("contactPhone");
                networkId = rs.getString("networkId");
                networkName = rs.getString("networkName");
                networkCountry = rs.getString("networkCountry");
                contacts.add(c);
            }
            con.close();
        } catch (SQLException | PropertyVetoException | IOException ex) {
            Logger.getLogger(RemData.class.getName()).log(Level.SEVERE, null, ex);
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
     * @param pagesVisited the pagesVisited to set
     */
    public void setPagesVisited(String pagesVisited) {
        this.pagesVisited = pagesVisited;
    }

    /**
     * @return the networkId
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * @param networkId the networkId to set
     */
    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    /**
     * @return the networkName
     */
    public String getNetworkName() {
        return networkName;
    }

    /**
     * @param networkName the networkName to set
     */
    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    /**
     * @return the networkCountry
     */
    public String getNetworkCountry() {
        return networkCountry;
    }

    /**
     * @param networkCountry the networkCountry to set
     */
    public void setNetworkCountry(String networkCountry) {
        this.networkCountry = networkCountry;
    }

    /**
     * @return the contacts
     */
    public String getContactsHTML(String contextPath) {
        String results = "";
        for(int i = 0; i < contacts.size(); i++){
           results += "<a href=\""+contextPath+"/contact?id="+contacts.get(i).id+"\" class=\"list-group-item list-group-item-action\">\n"
                   + "<h4 style=\"padding-bottom: 10px; font-size: 14pt;\" class=\"list-group-item-heading\"><b><i class=\"fa fa-address-card-o fa-fw\"></i> "+contacts.get(i).name+"</b></h4>\n"
                   + "<p class=\"list-group-item-text\">"
                   + ((contacts.get(i).phone!=null&&!contacts.get(i).phone.isEmpty())?("<span style=\"font-size: 12pt; display: block; padding-bottom: 6px;\">Tel.: "+contacts.get(i).phone+"</span>"):"")
                   + ((contacts.get(i).email!=null&&!contacts.get(i).email.isEmpty())?("<span style=\"font-size: 12pt; display: block; padding-bottom: 6px;\">Email: "+contacts.get(i).email+"</span>"):"")
                   + ((contacts.get(i).address!=null&&!contacts.get(i).address.isEmpty())?("<span style=\"font-size: 12pt; display: block; padding-bottom: 6px;\">Address: "+contacts.get(i).address+"</span>"):"")
                   + "</p></a>";
       }
        return results;
    }

    /**
     * @param contacts the contacts to set
     */
    public void setContacts(List<RemContact> contacts) {
        this.contacts = contacts;
    }

}

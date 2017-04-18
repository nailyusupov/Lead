/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graph.pie;

import com.datasourse.ControlPanelPool;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.pieroxy.ua.detection.UserAgentDetectionResult;
import net.pieroxy.ua.detection.UserAgentDetector;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author nail yusupov
 */
public class PieGraphData {

    private Map<String, Integer> languages;

    public PieGraphData(String startDate, String endDate) {
        java.sql.Connection con = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;
        try {
            con = ControlPanelPool.getInstance().getConnection();
            stmt = con.prepareStatement("SELECT DISTINCT TOP 15 LeadOrganization.Country, COUNT(LeadOrganization.Country) as c FROM LeadOrganization, LeadRemoteAddress, leadSession WHERE leadSession.remoteAddress = LeadRemoteAddress.ip AND LeadRemoteAddress.LeadOrganization_uid = LeadOrganization.Id AND LeadRemoteAddress.prefered = 1 AND LeadOrganization.Country != '' AND leadSession.timeIn > CONVERT(date, ?) AND leadSession.timeIn < CONVERT(date, ?) GROUP BY LeadOrganization.Country HAVING COUNT(LeadOrganization.Country) > 0 ORDER BY c DESC");
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            rs = stmt.executeQuery();
            languages = new HashMap<>();
            while (rs.next()) {
                /*
                if (rs.getString("userAgent") != null) {
                    String lang = new UserAgentDetector().parseUserAgent(rs.getString("userAgent")).getLocale().country.getLabel();
                    if (languages.containsKey(lang)) {
                        languages.put(lang, languages.get(lang) + 1);
                    } else {
                        languages.put(lang, 1);
                    }
                }
                */
                Locale l = new Locale("",rs.getString("Country"));
                languages.put(l.getDisplayCountry(), rs.getInt("c"));
            }
            //languages.remove("Unknown");
            con.close();
        } catch (IOException | SQLException | PropertyVetoException ex) {
            Logger.getLogger(PieGraphData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(con, stmt, rs);
        }
    }

    /**
     * @return the languages
     */
    public Map<String, Integer> getLanguages() {
        return languages;
    }

}

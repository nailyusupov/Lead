/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graph.bar;

import com.datasourse.ControlPanelPool;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author nail yusupov
 */
public class BarGraphData {

    private Map<String, Integer> refs;

    public BarGraphData(String startDate, String endDate, String domain) {
        java.sql.Connection con = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;
        try {
            con = ControlPanelPool.getInstance().getConnection();
            stmt = con.prepareStatement("");
            stmt = con.prepareStatement("SELECT DISTINCT leadSession.referer, COUNT(leadSession.referer) as c  FROM leadSession WHERE leadSession.timeIn > CONVERT(date, ?) AND leadSession.timeIn < CONVERT(date, ?) AND leadSession.referer != '' AND leadSession.referer is not null GROUP BY leadSession.referer HAVING COUNT(leadSession.referer) > 0");
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            rs = stmt.executeQuery();
            refs = new ConcurrentHashMap<>();
            while (rs.next()) {
                if (refs.containsKey(rs.getString("referer").split("/")[2])) {
                    refs.put(rs.getString("referer").split("/")[2], refs.get(rs.getString("referer").split("/")[2]) + rs.getInt("c"));
                } else {
                    refs.put(rs.getString("referer").split("/")[2], rs.getInt("c"));
                }
            }
            refs.put("Google", 0);
            refs.put("Yahoo", 0);
            refs.put("Bing", 0);
            refs.put("Other", 0);

            for (String key : refs.keySet()) {
                try {
                    if (refs.get(key) != null && key.contains("google")) {
                        refs.put("Google", refs.get("Google") + refs.get(key));
                        refs.remove(key);
                    }
                    if (refs.get(key) != null && key.contains("yahoo")) {
                        refs.put("Yahoo", refs.get("Yahoo") + refs.get(key));
                        refs.remove(key);
                    }
                    if (refs.get(key) != null && key.contains("bing")) {
                        refs.put("Bing", refs.get("Bing") + refs.get(key));
                        refs.remove(key);
                    }
                    //if (refs.get(key) != null && refs.get(key) < 6) {
                    //    refs.put("Other", refs.get("Other") + refs.get(key));
                    //    refs.remove(key);
                    //}
                    if(refs.get(key)!=null && key.contains(domain)){
                        //remove the self referred links
                        refs.remove(key);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            con.close();
        } catch (IOException | SQLException | PropertyVetoException ex) {
            Logger.getLogger(BarGraphData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(con, stmt, rs);
        }
    }

    /**
     * @return the refs
     */
    public Map<String, Integer> getRefs() {
        return refs;
    }

}

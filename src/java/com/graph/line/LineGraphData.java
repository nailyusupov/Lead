/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graph.line;

import com.datasourse.ControlPanelPool;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.DbUtils;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author nail yusupov
 */
public class LineGraphData {

    private List<String> sessionCount;
    private List<String> addressCount;
    private List<String> dateCount;

    public LineGraphData(String timeIn, String timeOut) {
        java.sql.Connection con = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;
        try {
            con = ControlPanelPool.getInstance().getConnection();
            DateTimeFormatter format = org.joda.time.format.DateTimeFormat.forPattern("MM/dd/yyyy");
            LocalDate startDate = LocalDate.parse(timeIn, format);
            LocalDate endDate = LocalDate.parse(timeOut, format);
            int days = Days.daysBetween(startDate, endDate).getDays();
            //List<LocalDate> dates = new ArrayList<>(days);
            sessionCount = new ArrayList<>();
            addressCount = new ArrayList<>();
            dateCount = new ArrayList<>();
            for (int i = 0; i < days; i++) {
                LocalDate d2 = startDate.withFieldAdded(DurationFieldType.days(), i+1);
                //dates.add(d);
                stmt = con.prepareStatement("SELECT COUNT(DISTINCT remoteAddress) as remoteAdd, COUNT(DISTINCT sessionId) as sess FROM leadSession WHERE timeIn > CONVERT(date, ?) AND timeIn < CONVERT(date, ?)");
                stmt.setString(1, startDate.withFieldAdded(DurationFieldType.days(), i).toString());
                stmt.setString(2, startDate.withFieldAdded(DurationFieldType.days(), i+1).toString());
                rs = stmt.executeQuery();
                while(rs.next()){
                    sessionCount.add(rs.getString("sess"));
                    addressCount.add(rs.getString("remoteAdd"));
                }
                dateCount.add(startDate.withFieldAdded(DurationFieldType.days(), i).toString());
            }
            con.close();
        } catch (IOException | SQLException | PropertyVetoException ex) {
            Logger.getLogger(LineGraphData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(con, stmt, rs);
        }
    }

    /**
     * @return the sessionCount
     */
    public List<String> getSessionCount() {
        return sessionCount;
    }

    /**
     * @return the addressCount
     */
    public List<String> getAddressCount() {
        return addressCount;
    }

    /**
     * @return the dateCount
     */
    public List<String> getDateCount() {
        return dateCount;
    }

}

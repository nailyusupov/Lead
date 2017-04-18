/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.settings;

import com.datasourse.ControlPanelPool;
import com.google.gson.Gson;
import com.page.PageServlet;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author nail yusupov
 */
public class SettingsServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        if (request.getParameter("get") != null) {
            java.sql.Connection con = null;
            java.sql.PreparedStatement stmt = null;
            java.sql.ResultSet rs = null;
            try {
                con = ControlPanelPool.getInstance().getConnection();
                stmt = con.prepareStatement("SELECT * FROM leadCustomSearch ORDER BY Id DESC");
                rs = stmt.executeQuery();
                String data = "";
                while (rs.next()) {
                    data += "<a class=\"list-group-item\" href=\"#\"><h4 class=\"list-group-item-heading\">" + rs.getString("pageTitle") + " <i class=\"fa fa-minus pull-right delete\" id=\"" + rs.getString("Id") + "\"></i></h4><p class=\"list-group-item-text\">Type: " + rs.getString("type") + "</p></a>";
                }
                con.close();
                response.getWriter().write(new Gson().toJson(data));
            } catch (SQLException | PropertyVetoException ex) {
                Logger.getLogger(PageServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                DbUtils.closeQuietly(con, stmt, rs);
            }
        }
        if (request.getParameter("autocomplete") != null) {
            java.sql.Connection con = null;
            java.sql.PreparedStatement stmt = null;
            java.sql.ResultSet rs = null;
            try {
                con = ControlPanelPool.getInstance().getConnection();
                stmt = con.prepareStatement("SELECT DISTINCT pageTitle FROM leadSession");
                rs = stmt.executeQuery();
                List<String> data = new ArrayList<>();
                while (rs.next()) {
                    if (rs.getString("pageTitle") != null) {
                        data.add(rs.getString("pageTitle"));
                    }
                }
                con.close();
                response.getWriter().write(new Gson().toJson(data));
            } catch (SQLException | PropertyVetoException ex) {
                Logger.getLogger(PageServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                DbUtils.closeQuietly(con, stmt, rs);
            }
        }
        if (request.getParameter("add") != null) {
            java.sql.Connection con = null;
            java.sql.PreparedStatement stmt = null;
            java.sql.ResultSet rs = null;
            try {
                con = ControlPanelPool.getInstance().getConnection();
                stmt = con.prepareStatement("INSERT INTO leadCustomSearch ([pageTitle],[type]) VALUES (?,?)");
                stmt.setString(1, request.getParameter("add"));
                stmt.setString(2, "exclude");
                stmt.executeUpdate();
                con.close();
                response.getWriter().write(new Gson().toJson("success"));
            } catch (SQLException | PropertyVetoException ex) {
                Logger.getLogger(PageServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                DbUtils.closeQuietly(con, stmt, rs);
            }
        }
        if (request.getParameter("delete") != null) {
            java.sql.Connection con = null;
            java.sql.PreparedStatement stmt = null;
            java.sql.ResultSet rs = null;
            try {
                con = ControlPanelPool.getInstance().getConnection();
                stmt = con.prepareStatement("DELETE leadCustomSearch WHERE Id = ?");
                stmt.setString(1, request.getParameter("delete"));
                stmt.executeUpdate();
                con.close();
                response.getWriter().write(new Gson().toJson("success"));
            } catch (SQLException | PropertyVetoException ex) {
                Logger.getLogger(PageServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                DbUtils.closeQuietly(con, stmt, rs);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

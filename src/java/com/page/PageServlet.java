/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.page;

import com.datasourse.ControlPanelPool;
import com.google.gson.Gson;
import com.search.SearchServlet;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
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
public class PageServlet extends HttpServlet {

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
        if (request.getParameter("url") != null) {
            java.sql.Connection con = null;
            java.sql.PreparedStatement stmt = null;
            java.sql.ResultSet rs = null;
            try {
                con = ControlPanelPool.getInstance().getConnection();
                stmt = con.prepareStatement("SELECT TOP 5 remoteAddress, pageTitle, COUNT(pageTitle) AS c FROM leadSession WHERE location = ? GROUP BY remoteAddress, pageTitle ORDER BY c DESC");
                stmt.setString(1, request.getParameter("url"));
                rs = stmt.executeQuery();
                Map<String, String> data = new HashMap<>();
                String left = "";
                String right = "";
                String title = "";
                int counter = 0;
                while (rs.next()) {
                    if (counter == 0) {
                        title = rs.getString("pageTitle");
                    }
                    if (counter < 5) {
                        left += "<a class=\"list-group-item\" href=\"" + request.getContextPath() + "/rem?ip=" + rs.getString("remoteAddress") + "\"><h4 class=\"list-group-item-heading\">" + rs.getString("remoteAddress") + "</h4><p class=\"list-group-item-text\">Total clicks: " + rs.getString("c") + "</p></a>";
                    } 
                    counter++;
                }
                stmt = con.prepareStatement("SELECT TOP 10 pageTitle, COUNT(pageTitle) AS c FROM leadSession WHERE remoteAddress IN (SELECT DISTINCT remoteAddress FROM leadSession WHERE location = ?) AND pageTitle NOT IN (SELECT pageTitle FROM leadCustomSearch WHERE type = 'exclude' AND pageTitle IS NOT NULL) AND pageTitle != ? GROUP BY pageTitle ORDER BY c DESC");
                stmt.setString(1, request.getParameter("url"));
                stmt.setString(2, title);
                rs = stmt.executeQuery();
                while(rs.next()){
                    right += "<a data-title=\""+rs.getString("pageTitle")+"\" class=\"list-group-item\" href=\"#\"><h4 class=\"list-group-item-heading\">" + rs.getString("pageTitle") + "</h4><p class=\"list-group-item-text\">Total clicks: " + rs.getString("c") + " <i class=\"pull-right fa fa-trash trash\"></i></p></a>";
                }
                con.close();
                data.put("left", "<a data-toggle=\"collapse\" href=\"#collapse1\">Show users who are most interested in '"+title+"' <i class=\"fa fa-sort-down\"></i></a><div id=\"collapse1\" class=\"panel-collapse collapse\"><ul class=\"list-group\">"+left+"</ul></div>");
                data.put("right", right);
                data.put("lefttitle", "");
                data.put("righttitle", "Users who are intrested in '"+title+"' are also most interested in");
                response.getWriter().write(new Gson().toJson(data));
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

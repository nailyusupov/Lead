/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.search;

import com.datasourse.ControlPanelPool;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
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
public class SearchServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        if (request.getParameter("type") != null && request.getParameter("sub") != null && request.getParameter("val") != null) {
            java.sql.Connection con = null;
            java.sql.PreparedStatement stmt = null;
            java.sql.ResultSet rs = null;
            try {
                if (request.getParameter("type").equals("people")) {
                    if (request.getParameter("sub").equals("name")) {
                        con = ControlPanelPool.getInstance().getConnection();
                        stmt = con.prepareStatement("SELECT * FROM LeadContact WHERE Name like ?");
                        stmt.setString(1, ("%" + request.getParameter("val").trim().replaceAll("[^A-Za-z0-9 ]", "%") + "%"));
                        rs = stmt.executeQuery();
                        String results = "";
                        while (rs.next()) {
                            results += "  <a href=\"" + request.getContextPath() + "/contact?id=" + rs.getString("Id") + "\" class=\"list-group-item\">\n"
                                    + "    <h4 class=\"list-group-item-heading\">" + rs.getString("Name") + "</h4>\n"
                                    + "    <p class=\"list-group-item-text\">" + "Email/Phone: " + ((rs.getString("Email") != null && !rs.getString("Email").isEmpty()) ? (rs.getString("Email") + "/") : "") + ((rs.getString("Phone") != null && !rs.getString("Phone").isEmpty()) ? (rs.getString("Phone") + " ") : "") + "</p>\n"
                                    + "  </a>";
                        }
                        con.close();
                        request.setAttribute("results", results);
                        request.getRequestDispatcher("results.jsp").forward(request, response);
                    }
                    if (request.getParameter("sub").equals("email")) {
                        con = ControlPanelPool.getInstance().getConnection();
                        stmt = con.prepareStatement("SELECT * FROM LeadContact WHERE Email like ?");
                        stmt.setString(1, ("%" + request.getParameter("val").trim().replaceAll("[^A-Za-z0-9 ]", "%") + "%"));
                        rs = stmt.executeQuery();
                        String results = "";
                        while (rs.next()) {
                            results += "  <a href=\"" + request.getContextPath() + "/contact?id=" + rs.getString("Id") + "\" class=\"list-group-item\">\n"
                                    + "    <h4 class=\"list-group-item-heading\">" + rs.getString("Name") + "</h4>\n"
                                    + "    <p class=\"list-group-item-text\">" + "Email/Phone: " + ((rs.getString("Email") != null && !rs.getString("Email").isEmpty()) ? (rs.getString("Email") + "/") : "") + ((rs.getString("Phone") != null && !rs.getString("Phone").isEmpty()) ? (rs.getString("Phone") + " ") : "") + "</p>\n"
                                    + "  </a>";
                        }
                        con.close();
                        request.setAttribute("results", results);
                        request.getRequestDispatcher("results.jsp").forward(request, response);
                    }
                    if (request.getParameter("sub").equals("ip")) {
                        con = ControlPanelPool.getInstance().getConnection();
                        stmt = con.prepareStatement("SELECT * FROM LeadContact WHERE RemoteAddress like ?");
                        stmt.setString(1, ("%" + request.getParameter("val").trim().replaceAll("[^A-Za-z0-9 ]", "%") + "%"));
                        rs = stmt.executeQuery();
                        String results = "";
                        while (rs.next()) {
                            results += "  <a href=\"" + request.getContextPath() + "/contact?id=" + rs.getString("Id") + "\" class=\"list-group-item\">\n"
                                    + "    <h4 class=\"list-group-item-heading\">" + rs.getString("Name") + " <small>" + rs.getString("RemoteAddress") + "</small></h4>\n"
                                    + "    <p class=\"list-group-item-text\">" + "Email/Phone: " + ((rs.getString("Email") != null && !rs.getString("Email").isEmpty()) ? (rs.getString("Email") + "/") : "") + ((rs.getString("Phone") != null && !rs.getString("Phone").isEmpty()) ? (rs.getString("Phone") + " ") : "") + "</p>\n"
                                    + "  </a>";
                        }
                        con.close();
                        request.setAttribute("results", results);
                        request.getRequestDispatcher("results.jsp").forward(request, response);
                    }
                }
                if (request.getParameter("type").equals("business")) {
                    if (request.getParameter("sub").equals("name")) {
                        con = ControlPanelPool.getInstance().getConnection();
                        stmt = con.prepareStatement("SELECT * FROM LeadContact WHERE BusinessName like ?");
                        stmt.setString(1, ("%" + request.getParameter("val").trim().replaceAll("[^A-Za-z0-9 ]", "%") + "%"));
                        rs = stmt.executeQuery();
                        String results = "";
                        while (rs.next()) {
                            results += "  <a href=\"" + request.getContextPath() + "/business?id=" + rs.getString("Id") + "\" class=\"list-group-item\">\n"
                                    + "    <h4 class=\"list-group-item-heading\">" + rs.getString("BusinessName") + "</h4>\n"
                                    + "    <p class=\"list-group-item-text\">" + "Email/Phone: " + ((rs.getString("Email") != null && !rs.getString("Email").isEmpty()) ? (rs.getString("Email") + "/") : "") + ((rs.getString("Phone") != null && !rs.getString("Phone").isEmpty()) ? (rs.getString("Phone") + " ") : "") + "</p>\n"
                                    + "  </a>";
                        }
                        con.close();
                        request.setAttribute("results", results);
                        request.getRequestDispatcher("results.jsp").forward(request, response);
                    }
                    if (request.getParameter("sub").equals("address")) {
                        con = ControlPanelPool.getInstance().getConnection();
                        stmt = con.prepareStatement("SELECT * FROM LeadContact WHERE Address like ?");
                        stmt.setString(1, ("%" + request.getParameter("val").trim().replaceAll("[^A-Za-z0-9 ]", "%") + "%"));
                        rs = stmt.executeQuery();
                        String results = "";
                        while (rs.next()) {
                            results += "  <a href=\"" + request.getContextPath() + "/business?id=" + rs.getString("Id") + "\" class=\"list-group-item\">\n"
                                    + "    <h4 class=\"list-group-item-heading\">" + rs.getString("BusinessName") + "</h4>\n"
                                    + "    <p class=\"list-group-item-text\">" + "Email/Phone: " + ((rs.getString("Email") != null && !rs.getString("Email").isEmpty()) ? (rs.getString("Email") + "/") : "") + ((rs.getString("Phone") != null && !rs.getString("Phone").isEmpty()) ? (rs.getString("Phone") + " ") : "") + "</p>\n"
                                    + "  </a>";
                        }
                        con.close();
                        request.setAttribute("results", results);
                        request.getRequestDispatcher("results.jsp").forward(request, response);
                    }
                    if (request.getParameter("sub").equals("ip")) {
                        con = ControlPanelPool.getInstance().getConnection();
                        stmt = con.prepareStatement("SELECT * FROM LeadContact WHERE RemoteAddress like ?");
                        stmt.setString(1, ("%" + request.getParameter("val").trim().replaceAll("[^A-Za-z0-9 ]", "%") + "%"));
                        rs = stmt.executeQuery();
                        String results = "";
                        while (rs.next()) {
                            results += "  <a href=\"" + request.getContextPath() + "/business?id=" + rs.getString("Id") + "\" class=\"list-group-item\">\n"
                                    + "    <h4 class=\"list-group-item-heading\">" + rs.getString("BusinessName") + " <small>" + rs.getString("RemoteAddress") + "</small></h4>\n"
                                    + "    <p class=\"list-group-item-text\">" + "Email/Phone: " + ((rs.getString("Email") != null && !rs.getString("Email").isEmpty()) ? (rs.getString("Email") + "/") : "") + ((rs.getString("Phone") != null && !rs.getString("Phone").isEmpty()) ? (rs.getString("Phone") + " ") : "") + "</p>\n"
                                    + "  </a>";
                        }
                        con.close();
                        request.setAttribute("results", results);
                        request.getRequestDispatcher("results.jsp").forward(request, response);
                    }
                }
                if(request.getParameter("type").equals("page")){
                con = ControlPanelPool.getInstance().getConnection();
                
                }
            } catch (SQLException | PropertyVetoException ex) {
                Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
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

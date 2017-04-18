/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.business;

import com.contact.ContactData;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nail yusupov
 */
public class BusinessServlet extends HttpServlet {

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
        if (request.getParameter("id") != null) {
            ContactData data = new ContactData(request.getParameter("id"), request.getContextPath());
            String relatedIps = "";
            for(int i = 0; i < data.getRelatedIp().size(); i++){
                relatedIps += "<a href=\""+request.getContextPath()+"/rem?ip="+data.getRelatedIp().get(i)+"\">"+data.getRelatedIp().get(i)+"</a>";
            }
            System.out.println("here");
            request.setAttribute("data", data);
            request.setAttribute("relatedIps", relatedIps);
            request.setAttribute("purchases", data.getPurchasesByBusinessName());
            request.setAttribute("businessName", data.getBusinessName()==null||data.getBusinessName().isEmpty()?"":"Business Owner: "+data.getBusinessName());
            request.setAttribute("geoLocation", data.getGeoLocation()==null||data.getGeoLocation().isEmpty()?"":"<i class=\"fa fa-globe\"></i> Geolocation: "+data.getGeoLocation());
            request.setAttribute("networkOwner", "<a href=\""+request.getContextPath()+"/org?id="+data.getNetworkOwnerId()+"\">"+data.getNetworkOwner()+"</a>");
            request.getRequestDispatcher("/business.jsp").forward(request, response);
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

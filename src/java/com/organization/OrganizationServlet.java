/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organization;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nail yusupov
 */
public class OrganizationServlet extends HttpServlet {

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
        /*
        if (request.getParameter("ip") == null && request.getParameter("id") != null && request.getParameter("startdate") != null && request.getParameter("enddate") != null) {
            OrganizationData data = new OrganizationData(request.getParameter("startdate"), request.getParameter("enddate"), request.getParameter("id"), request.getContextPath());
            String fullAddress = (data.getAddress()!=null&&!data.getAddress().isEmpty())?("<span style=\"display: block;\">"+data.getAddress()+"</span>"):"";
            fullAddress += (data.getCity()!=null&&!data.getCity().isEmpty())?("<span style=\"display: block;\">"+data.getCity()+", "):"";
            fullAddress += (data.getState()!=null&&!data.getState().isEmpty())?(data.getState()+" "):"";
            fullAddress += (data.getZip()!=null&&!data.getZip().isEmpty())?(data.getZip()+"</span>"):"";
            fullAddress += (data.getCountry()!=null&&!data.getCountry().isEmpty())?("<span style=\"display: block;\">"+data.getCountry()+"</span>"):"";
            request.setAttribute("orgName", data.getOrgName());
            request.setAttribute("orgCity", data.getCity());
            request.setAttribute("orgState", data.getState());
            request.setAttribute("orgAddress", data.getAddress());
            request.setAttribute("orgZip", data.getZip());
            request.setAttribute("orgCountry", data.getCountry());
            request.setAttribute("remoteUsers", data.getRemoteUsers());
            request.setAttribute("referers", data.getReferers());
            request.setAttribute("contactInfo", data.getContactInformation());
            request.setAttribute("mostInterested", data.getPagesVisited());
            request.setAttribute("fullAddress", fullAddress);
            request.getRequestDispatcher("/org.jsp").forward(request, response);
        }*/
        if (request.getParameter("ip") == null && request.getParameter("id") != null && request.getParameter("startdate") == null && request.getParameter("enddate") == null) {
            OrganizationData data = new OrganizationData(request.getParameter("id"), request.getContextPath(), "leadSession");
            String fullAddress = (data.getAddress() != null && !data.getAddress().isEmpty()) ? ("<span style=\"display: block;\">" + data.getAddress() + "</span>") : "";
            fullAddress += (data.getCity() != null && !data.getCity().isEmpty()) ? ("<span style=\"display: block;\">" + data.getCity() + ", ") : "";
            fullAddress += (data.getState() != null && !data.getState().isEmpty()) ? (data.getState() + " ") : "";
            fullAddress += (data.getZip() != null && !data.getZip().isEmpty()) ? (data.getZip() + "</span>") : "";
            fullAddress += (data.getCountry() != null && !data.getCountry().isEmpty()) ? ("<span style=\"display: block;\">" + data.getCountry() + "</span>") : "";
            request.setAttribute("orgName", data.getOrgName());
            request.setAttribute("orgCity", data.getCity());
            request.setAttribute("orgState", data.getState());
            request.setAttribute("orgAddress", data.getAddress());
            request.setAttribute("orgZip", data.getZip());
            request.setAttribute("orgCountry", data.getCountry());
            request.setAttribute("remoteUsers", data.getRemoteUsers());
            request.setAttribute("referers", data.getReferers());
            request.setAttribute("contactInfo", data.getContactInformation());
            request.setAttribute("mostInterested", data.getPagesVisited());
            request.setAttribute("fullAddress", fullAddress);
            request.getRequestDispatcher("/org.jsp").forward(request, response);
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

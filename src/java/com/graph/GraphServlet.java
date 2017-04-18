/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graph;

import com.google.gson.Gson;
import com.graph.bar.BarGraph;
import com.graph.bar.BarGraphData;
import com.graph.line.LineGraph;
import com.graph.line.LineGraphData;
import com.graph.line.LineItem;
import com.graph.pie.PieGraph;
import com.graph.pie.PieGraphData;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nail yusupov
 */
public class GraphServlet extends HttpServlet {

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
        if (request.getParameter("linegraph") != null && request.getParameter("startDate") != null && request.getParameter("endDate") != null) {
            LineGraphData data = new LineGraphData(request.getParameter("startDate"), request.getParameter("endDate"));
            LineGraph graph = new LineGraph();
            LineItem sessionCount = new LineItem("Unique Sessions");
            LineItem addressCount = new LineItem("Unique Visitors");
            for (int i = 0; i < data.getDateCount().size(); i++) {
                graph.addLineBreak(data.getDateCount().get(i));
                sessionCount.addData(data.getSessionCount().get(i));
                addressCount.addData(data.getAddressCount().get(i));
            }
            graph.addLine(addressCount);
            graph.addLine(sessionCount);
            /*
            data.addLineBreak("date1");
            data.addLineBreak("date2");
            data.addLineBreak("today");
            LineItem line = new LineItem("Line 1");
            line.addData("838");
            line.addData("562");
            line.addData("4");
            data.addLine(line);
             */
            response.getWriter().write(new Gson().toJson(graph));
        }
        if (request.getParameter("piegraph") != null && request.getParameter("startDate") != null && request.getParameter("endDate") != null) {
            PieGraph graph = new PieGraph();
            PieGraphData data = new PieGraphData(request.getParameter("startDate"), request.getParameter("endDate"));
            for (String key : data.getLanguages().keySet()) {
                graph.addLabel(key);
                graph.addSlice(String.valueOf(data.getLanguages().get(key)));
            }
            response.getWriter().write(new Gson().toJson(graph));
        }
        if (request.getParameter("bargraph") != null && request.getParameter("startDate") != null && request.getParameter("endDate") != null) {
            BarGraph graph = new BarGraph();
            BarGraphData data = new BarGraphData(request.getParameter("startDate"), request.getParameter("endDate"), "www.parchem.com");
            Map<String, Integer> map = data.getRefs();
            Object[] a = map.entrySet().toArray();
            Arrays.sort(a, new Comparator() {
                public int compare(Object o1, Object o2) {
                    return ((Map.Entry<String, Integer>) o2).getValue()
                            .compareTo(((Map.Entry<String, Integer>) o1).getValue());
                }
            });
            for (int i = 0; i < Math.min(12, a.length); i++) {
                graph.addLabel(String.valueOf(((Map.Entry<String, Integer>) a[i]).getKey()));
                graph.addBar(String.valueOf(((Map.Entry<String, Integer>) a[i]).getValue()));
            }
            response.getWriter().write(new Gson().toJson(graph));
        }
        if (request.getParameter("polargraph") != null && request.getParameter("startDate") != null && request.getParameter("endDate") != null) {
            //PolarGraph graph = new PolarGraph();
            //BarGraphData data = new BarGraphData(request.getParameter("startDate"), request.getParameter("endDate"));
            //for(String key : data.getRefs().keySet()){
            //    graph.addLabel(key);
            //    graph.addSlice(String.valueOf(data.getRefs().get(key)));
            //}
            //response.getWriter().write(new Gson().toJson(graph));
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

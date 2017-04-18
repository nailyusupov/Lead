<%-- 
    Document   : results
    Created on : Apr 3, 2017, 11:36:54 AM
    Author     : nail yusupov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="head.jsp"%>
        <title>Dashboard</title>
    </head>
    <body>
        <div id="wrapper">
            <%@include file="header.jsp"%>
            <div id="page-wrapper" class="row">
                <h1>Search Results</h1>
                <div class="list-group">
                    ${results}
                </div>
            </div>            
        </div>
        <%@include file="footer.jsp"%>
    </body>
</html>


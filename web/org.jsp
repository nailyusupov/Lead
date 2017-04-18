<%-- 
    Document   : organization
    Created on : Dec 16, 2016, 3:11:16 PM
    Author     : nail yusupov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="head.jsp"%>
        <title>Network Organization</title>
        <style>
            .custom-container{             
                padding-bottom: 8px;      
                font-size: 12pt;
            }
        </style>
    </head>
    <body>
        <div id="wrapper">
            <%@include file="header.jsp"%>
            <div id="page-wrapper">
                <div class="content">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="col-md-3 col-sm-12 col-xs-12 custom-container" style="display: block;">
                            <h3>${orgName}</h3>
                            <span>${fullAddress}</span>
                        </div>
                        <div class="col-md-4 col-sm-12 col-xs-12 custom-container">
                            <h3>${orgName} Most Interested In</h3>
                            ${mostInterested}
                        </div>
                        <div class="col-md-4 col-sm-12 col-xs-12 custom-container">
                            <h3>Remote Users</h3>
                            ${remoteUsers}
                            <h3>Available Contact Info</h3>
                            ${contactInfo}
                        </div>
                        <div class="col-md-4 col-sm-12 col-xs-12 custom-container">
                            <h3>Top Referrers</h3>
                            ${referers}
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="footer.jsp"%>
        <script>
            $(document).ready(function () {
                $("#startDate").datepicker();
                $("#endDate").datepicker();
                $("#updateDates").on("click", function () {
                    window.location = "org?startdate=" + $("#startDate").datepicker({dateFormat: 'dd,MM,yyyy'}).val() + "&enddate=" + $("#endDate").datepicker({dateFormat: 'dd,MM,yyyy'}).val();
                });
            });
        </script>
    </body>
</html>


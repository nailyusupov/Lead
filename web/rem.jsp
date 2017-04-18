<%-- 
    Document   : rem
    Created on : Dec 21, 2016, 1:19:43 PM
    Author     : nail yusupov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="head.jsp"%>
        <title>Default Page</title>
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
                    <div class="col-md-12 col-sm-12 col-xs-12" style="margin-top: 25px;">
                        <div class="col-md-5 col-sm-12 col-xs-12">
                            <div class="list-group">
                                ${contacts}
                            </div>
                        </div>
                        <div class="col-md-6 col-sm-12 col-xs-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    Pages Visited
                                </div>
                                <div class="panel-body">
                                    <div class="list-group">
                                        ${pagesVisited}
                                    </div>
                                </div>
                            </div>
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

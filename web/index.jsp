<%-- 
    Document   : index
    Created on : Dec 14, 2016, 11:20:31 AM
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

                <!--add fa fa icons to every list item link the people get the user icon or something similar-->
                <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 20px;">
                    <h2>Displaying data from <div style="white-space: nowrap; display: inline-block;"><input id="startDate" type="text" style="width: 180px;"> to <input id="endDate" type="text" style="width: 180px;"></div><a id="updateDates" style="cursor: pointer; font-size: 13pt; padding-left: 7px;">Update</a></h2>
                    <h3 style="padding-bottom: 10px;">Visitors: <span class="hidden-sm hidden-xs" style="padding-left: 10px; padding-right: 10px; background-color: rgba(255, 99, 132, 0.2);">${totalAddressCount}</span><span class="hidden-md hidden-lg" style="margin-left: 18px; padding-left: 10px; padding-right: 10px; background-color: rgba(255, 99, 132, 0.2);">${totalAddressCount}</span><span class="hidden-sm hidden-xs" style="padding-left: 12px;">Sessions:</span> <span class="hidden-sm hidden-xs" style="padding-left: 10px; padding-right: 10px; background-color: rgba(255, 99, 132, 0.2);">${totalSessionCount}</span></h3>
                    <h3 class="hidden-md hidden-lg"><span>Sessions:</span> <span style="padding-left: 10px; padding-right: 10px; background-color: rgba(255, 99, 132, 0.2);">${totalSessionCount}</span></h3>
                </div>

                <div class="col-md-8 col-sm-12 col-xs-12" style="padding-bottom: 10px; padding-top: 40px;">

                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <i class="fa fa-line-chart fa-fw"></i> Daily Users & Sessions
                            </div>
                            <div class="panel-body">
                                <canvas id="lineChart"></canvas>
                                <!--<a class="btn btn-default btn-block" style="margin-top: 10px;" href="#">View Details</a>-->
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4 col-sm-12 col-xs-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <i class="fa fa-handshake-o fa-fw"></i> Recent Internet Service Provider
                            </div>
                            <div class="panel-body">
                                <div class="list-group">
                                    ${companyLinks}
                                </div>
                                <!--<a class="btn btn-default btn-block" href="#">View Companies</a>-->
                            </div>
                        </div>
                    </div>

                    <div class="col-md-8 col-sm-12 col-xs-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <i class="fa fa-pie-chart fa-fw"></i> Most Active Locations
                            </div>
                            <div class="panel-body">
                                <canvas id="pieChart"></canvas>
                                <!--<a class="btn btn-default btn-block" href="#" style="margin-top: 15px;">View Details</a>-->
                            </div>
                        </div>
                    </div>

                    <div class="col-md-8 col-sm-12 col-xs-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <i class="fa fa-globe fa-fw"></i> Pages Mostly Visited
                            </div>
                            <div class="panel-body">
                                <div class="list-group">
                                    ${mostlyVisited}
                                </div>
                                <!--<a class="btn btn-default btn-block" href="#">View More</a>-->
                            </div>
                        </div>
                    </div>

                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <i class="fa fa-bar-chart-o fa-fw"></i> Most Active Referrers
                            </div>
                            <div class="panel-body">
                                <canvas id="barChart"></canvas>
                                <!--<a class="btn btn-default btn-block" href="#" style="margin-top: 15px;">View Referrers</a>-->
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 col-sm-12 col-xs-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-group fa-fw"></i> Recent Visitors
                        </div>
                        <div class="panel-body">
                            <div class="list-group">
                                ${contacts}
                            </div>
                            <!--<a class="btn btn-default btn-block" href="#">View Visitors</a>-->
                        </div>
                    </div>
                </div>
                <!--
                <div class="col-md-4 col-sm-12 col-xs-12">
                    <canvas id="polarAreaChart"></canvas>
                </div>
                -->
            </div>            
        </div>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.3.0/Chart.js"></script>
        <%@include file="footer.jsp"%>
        <script type="text/javascript">
            $(document).ready(function () {
                /*
                 $('.search-panel .dropdown-menu').find('a').click(function (e) {
                 e.preventDefault();
                 var param = $(this).attr("href").replace("#", "");
                 var concept = $(this).text();
                 $('.search-panel span#search_concept').text(concept);
                 $('.input-group #search_param').val(param);
                 });
                 */
                $("#startDate").datepicker();
                $("#endDate").datepicker();
                if (getUrlParameter("startdate") === undefined && getUrlParameter("enddate") === undefined) {
                    $("#startDate").datepicker("setDate", "-7");
                    $("#endDate").datepicker("setDate", "+1");
                } else {
                    $("#startDate").datepicker("setDate", getUrlParameter("startdate"));
                    $("#endDate").datepicker("setDate", getUrlParameter("enddate"));
                }
                $("#updateDates").on("click", function () {
                    window.location = "${pageContext.request.contextPath}/dashboard?startdate=" + $("#startDate").datepicker({dateFormat: 'dd,MM,yyyy'}).val() + "&enddate=" + $("#endDate").datepicker({dateFormat: 'dd,MM,yyyy'}).val();
                });
                /*Line Chart Ajax Call*/
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/graph",
                    data: {linegraph: "data", startDate: $("#startDate").datepicker({dateFormat: 'dd,MM,yyyy'}).val(), endDate: $("#endDate").datepicker({dateFormat: 'dd,MM,yyyy'}).val()},
                    dataType: "json",
                    success: function (rdata) {
                        new Chart($("#lineChart"), {
                            type: 'line',
                            data: rdata,
                            options: {
                                scales: {
                                    yAxes: [{
                                            ticks: {
                                                beginAtZero: true
                                            }
                                        }]
                                },
                                responsive: true,
                                title: {
                                    display: false,
                                    text: 'Chart Title'
                                }
                            }
                        });
                    }
                });
                /*End Line Chart Ajax Call*/
                /*Pie Chart Ajax Call*/
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/graph",
                    data: {piegraph: "data", startDate: $("#startDate").datepicker({dateFormat: 'dd,MM,yyyy'}).val(), endDate: $("#endDate").datepicker({dateFormat: 'dd,MM,yyyy'}).val()},
                    dataType: "json",
                    success: function (pdata) {
                        new Chart($("#pieChart"), {
                            type: 'pie',
                            data: pdata,
                            options: {
                                elements: {
                                    arc: {
                                        borderWidth: 1,
                                        borderColor: "#000000"
                                    }
                                }
                            }

                        });
                    }
                });
                /*End Pie Chart Ajax Call*/
                /*Bar Chart Ajax Call*/
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/graph",
                    data: {bargraph: "data", startDate: $("#startDate").datepicker({dateFormat: 'dd,MM,yyyy'}).val(), endDate: $("#endDate").datepicker({dateFormat: 'dd,MM,yyyy'}).val()},
                    dataType: "json",
                    success: function (bdata) {
                        new Chart($("#barChart"), {
                            type: "bar",
                            data: bdata,
                            options: {
                                scales: {
                                    yAxes: [{
                                            ticks: {
                                                beginAtZero: true
                                            }
                                        }]
                                },
                            }
                        });
                    }
                });
                /*End Bar Chart Ajax Call*/
                /*Polar Area Chart Ajax Call
                 $.ajax({
                 type: "POST",
                 url: "${pageContext.request.contextPath}/graph",
                 data: {bargraph: "data", startDate: $("#startDate").datepicker({dateFormat: 'dd,MM,yyyy'}).val(), endDate: $("#endDate").datepicker({dateFormat: 'dd,MM,yyyy'}).val()},
                 dataType: "json",
                 success: function (pdata) {
                 new Chart($("#polarAreaChart"), {
                 data: pdata,
                 type: 'polarArea',
                 options: {
                 elements: {
                 arc: {
                 borderWidth: 1,
                 borderColor: "#000000"
                 }
                 }
                 }
                 });
                 }
                 });
                 End Polar Area Chart Ajax Call*/
            });
        </script>
    </body>
</html>

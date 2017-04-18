<%-- 
    Document   : contact
    Created on : Dec 29, 2016, 11:20:03 AM
    Author     : nail yusupov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="head.jsp"%>
        <title>Visitor Contact Information</title>
    </head>
    <body>
        <div id="wrapper">
            <%@include file="header.jsp"%>
            <div id="page-wrapper">
                <div class="content">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="row">
                            <div class="col-md-5 col-sm-12 col-xs-12" style="margin-top: 25px;">
                                <div class="panel panel-info">
                                    <div class="panel-body" style="background-color: #fcf8e3;">
                                        <h1 style="margin-bottom: 25px;"><i class="fa fa-address-card-o"></i> ${data.getBusinessName()}</h1>
                                        <div class="row" style="padding-bottom: 10px;">
                                            <label class="col-sm-3 control-label">${data.getPhoneLabel()}</label>
                                            <div class="col-sm-9">
                                                <span style="font-size: 14pt;">${data.getPhone()}</span>
                                            </div>
                                        </div>
                                        <div class="row" style="padding-bottom: 10px;">
                                            <label class="col-sm-3 control-label">${data.getEmailLabel()}</label>
                                            <div class="col-sm-9">
                                                <span style="font-size: 14pt;">${data.getEmail()}</span>
                                            </div>
                                        </div>
                                        <div class="row" style="padding-bottom: 10px;">
                                            <label class="col-sm-3 control-label">Point of Contact</label>
                                            <div class="col-sm-9">
                                                <span style="font-size: 14pt;">${data.getName()}</span>
                                            </div>
                                        </div>
                                        <div class="row" style="padding-bottom: 10px;">
                                            <label class="col-sm-3 control-label">${data.getWebsiteLabel()}</label>
                                            <div class="col-sm-9">
                                                <span style="font-size: 14pt;">${data.getWebsite()}</span>
                                            </div>
                                        </div>
                                        <div class="row" style="padding-bottom: 10px;">
                                            <label class="col-sm-3 control-label">${data.getAddressLabel()}</label>
                                            <div class="col-sm-9">
                                                <span style="font-size: 14pt;">${data.getAddress()}</span>
                                            </div>
                                        </div>
                                        <div class="row" style="padding-bottom: 10px;">
                                            <label class="col-sm-3 control-label">${data.getBusinessColleguesLabel()}</label>
                                            <div class="col-sm-9">
                                                <span style="font-size: 14pt;">${data.getBusinessCollegues()}</span>
                                            </div>
                                        </div>
                                        <div class="row" style="padding-bottom: 10px;">
                                            <label class="col-sm-3 control-label">Related IP Addresses:</label>
                                            <div class="col-sm-9">
                                                <span style="font-size: 14pt;">${relatedIps}</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-7 col-sm-12 col-xs-12">
                                <h3 style="padding-top: 20px; padding-bottom: 22px; border-bottom: 2px solid #bce8f1;">${geoLocation}</h3>
                                <h3 style="padding-bottom: 35px;">Internet Service Provider: ${networkOwner}</h3>
                                <div class="col-md-12 col-sm-12 col-xs-12" style="border-top: 2px solid rgba(255, 99, 132, 0.2); padding-left: 0px; margin-top: 35px;">
                                    <div class="col-md-6 col-sm-6 col-xs-6" style="padding-left: 0px;">
                                        <h3>Total Pages Visited: <span style="margin-left: 3px; padding-left: 25px; padding-right: 25px; background-color: rgba(255, 99, 132, 0.2);">${data.getTotalPagesVisited()}</span></h3>
                                    </div>
                                    <div class="col-md-6 col-sm-6 col-xs-6">
                                        <h3>Total Clicks: <span style="margin-left: 15px; padding-left: 25px; padding-right: 25px; background-color: rgba(255, 99, 132, 0.2);">${data.getTotalClicks()}</span></h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" style="margin-top: 25px;">
                            ${purchases}
                            <div class="col-md-5 col-sm-12 col-xs-12">
                                <div class="panel panel-info">
                                    <div class="panel-heading">
                                        <h3>${data.getName()} Most Interested In</h3>
                                    </div>
                                    <div class="panel-body">
                                        <ul class="list-group">
                                            ${data.getPageViewsHtml()}
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-7 col-sm-12 col-xs-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        Complete Navigation History
                                    </div>
                                    <div class="panel-body">
                                        <ul class="list-group">
                                            ${data.getDetailedPageViews()}
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="footer.jsp"%>
    </body>
</html>

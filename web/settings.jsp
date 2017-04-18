<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="head.jsp"%>
        <title>Settings</title>
    </head>
    <body>
        <div id="wrapper">
            <%@include file="header.jsp"%>
            <div id="page-wrapper" class="row">
                <div class="container" style="margin-top: 30px;">
                    <div class="col-md-12">
                        <div class="col-md-1">
                            <label for="addNew">Add new</label>
                        </div>
                        <div class="col-md-10">
                            <input type="text" id="addNew" class="form-control" />
                        </div>
                        <div class="col-md-1">
                            <button type="button" class="btn btn-default" id="add">Add</button>
                        </div>
                    </div>
                </div>
                <div class="container">
                    <h3>Existing terms</h3>
                    <div class="list-group" id="list">

                    </div>
                </div>
            </div>            
        </div>
        <%@include file="footer.jsp"%>
        <script type="text/javascript">
            $(document).ready(function () {
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/settingsServlet",
                    data: {get: "list"},
                    dataType: "json",
                    success: function (data) {
                        $("#list").html(data);
                    }
                });
                /*$.ajax({
                 type: "POST",
                 url: "${pageContext.request.contextPath}/settingsServlet",
                 data: {autocomplete: "list"},
                 dataType: "json",
                 success: function (data) {
                 $("#addNew").autocomplete({
                 source: data,
                 minLength: 15
                 });
                 }
                 });*/
                $("#add").on('click', function () {
                    $.ajax({
                        type: "POST",
                        url: "${pageContext.request.contextPath}/settingsServlet",
                        data: {add: $("#addNew").val()},
                        dataType: "json",
                        success: function (data) {
                            $("#addNew").val("");
                            window.location.reload();
                        }
                    });
                });
                $("#list").on('click', '.delete', function () {
                    $.ajax({
                        type: "POST",
                        url: "${pageContext.request.contextPath}/settingsServlet",
                        data: {delete: $(this).attr('id')},
                        dataType: "json",
                        success: function (data) {
                            window.location.reload();
                        }
                    });
                });
            });
        </script>
    </body>
</html>

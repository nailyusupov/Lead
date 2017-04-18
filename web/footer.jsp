<!--<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>-->
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.12.1/jquery-ui.min.js" integrity="sha256-VazP97ZCwtekAsvgPBSUwPFKdrwD3unUfSGVYrahUqU=" crossorigin="anonymous"></script>
<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript">
    /*dialog search bar functionality*/
    /*$("#search_dialog").dialog({
     autoOpen: false,
     dialogClass: "dia",
     modal: true,
     maxWidth: 1100,
     maxHeight: 300,
     width: 1100,
     height: 300
     });*/
    $("#extra-dialog-content-right").on('click', '.trash', function () {
        $(this).parent().parent().hide();
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/settingsServlet",
            data: {add: $(this).parent().parent().data("title")},
            dataType: "json",
            success: function (data) {
            }
        });
    });
    $("#search_dialog").dialog({
        autoOpen: false,
        dialogClass: "dia",
        width: 'auto',
        maxWidth: 880,
        height: 'auto',
        modal: true,
        fluid: true,
        resizable: false
    });
    $("#extra-dialog").dialog({
        autoOpen: false,
        dialogClass: "dia",
        width: 'auto',
        maxWidth: 880,
        height: 'auto',
        modal: true,
        fluid: true,
        resizable: false
    });
    $("#loading-dialog").dialog({
        autoOpen: false,
        dialogClass: "dia",
        width: 'auto',
        maxWidth: 880,
        height: 'auto',
        modal: true,
        fluid: true,
        resizable: false
    });
    $(".dia .ui-dialog-titlebar").css("display", "none");
    $(".close-dia").click(function () {
        $("#search_dialog").dialog("close");
        $("#extra-dialog").dialog("close");
        $("#loading-dialog").dialog("close");
    });

    $(window).resize(function () {
        fluidDialog();
    });

    /*
     $(document).on("dialogopen", ".ui-dialog", function (event, ui) {
     fluidDialog();
     });
     */

    function fluidDialog() {
        var $visible = $(".ui-dialog:visible");
        $visible.each(function () {
            var $this = $(this);
            var dialog = $this.find(".ui-dialog-content").data("ui-dialog");
            if (dialog.options.fluid) {
                var wWidth = $(window).width();
                if (wWidth < (parseInt(dialog.options.maxWidth) + 50)) {
                    $this.css("max-width", "90%");
                } else {
                    $this.css("max-width", dialog.options.maxWidth + "px");
                }
                dialog.option("position", dialog.options.position);
                $("#search_dia_txt").css("width", "200px");
                $("#search_dia_txt").css("height", "30px");
                $("#search_dia_txt").css("font-size", "12pt");
                $("#search_dia_ico").css("font-size", "12pt");
                $("#search_dia_txt").css("border-radius", "0px");
            }
        });
    }
    $("#search_people_by_name").on("click", function () {
        $("#search_dialog").dialog("open");
        $("#search_dia_txt").attr("placeholder", "Search individual names...");
        $("#search_dia_ico").on("click", function () {
            window.location = ("${pageContext.request.contextPath}/search?type=people&sub=name&val=" + $("#search_dia_txt").val());
        });
    });
    $("#search_people_by_email").on("click", function () {
        $("#search_dialog").dialog("open");
        $("#search_dia_txt").attr("placeholder", "Search individual emails...");
        $("#search_dia_ico").on("click", function () {
            window.location = ("${pageContext.request.contextPath}/search?type=people&sub=email&val=" + $("#search_dia_txt").val());
        });
    });
    $("#search_people_by_ip").on("click", function () {
        $("#search_dialog").dialog("open");
        $("#search_dia_txt").attr("placeholder", "Search individual ip addresses...");
        $("#search_dia_ico").on("click", function () {
            window.location = ("${pageContext.request.contextPath}/search?type=people&sub=ip&val=" + $("#search_dia_txt").val());
        });
    });
    $("#search_business_by_name").on("click", function () {
        $("#search_dialog").dialog("open");
        $("#search_dia_txt").attr("placeholder", "Search business by name...");
        $("#search_dia_ico").on("click", function () {
            window.location = ("${pageContext.request.contextPath}/search?type=business&sub=name&val=" + $("#search_dia_txt").val());
        });
    });
    $("#search_business_by_address").on("click", function () {
        $("#search_dialog").dialog("open");
        $("#search_dia_txt").attr("placeholder", "Search business addresses...");
        $("#search_dia_ico").on("click", function () {
            window.location = ("${pageContext.request.contextPath}/search?type=business&sub=address&val=" + $("#search_dia_txt").val());
        });
    });
    $("#search_business_by_ip").on("click", function () {
        $("#search_dialog").dialog("open");
        $("#search_dia_txt").attr("placeholder", "Search business ip addresses...");
        $("#search_dia_ico").on("click", function () {
            window.location = ("${pageContext.request.contextPath}/search?type=business&sub=ip&val=" + $("#search_dia_txt").val());
        });
    });
    $("#search_page_by_title").on("click", function () {
        $("#search_dialog").dialog("open");
        $("#search_dia_txt").attr("placeholder", "Search page by title...");
        $("#search_dia_ico").on("click", function () {
            window.location = ("${pageContext.request.contextPath}/search?type=page&sub=title&val=" + $("#search_dia_txt").val());
        });
    });
    $("#search_page_by_url").on("click", function () {
        $("#search_dialog").dialog("open");
        $("#search_dia_txt").attr("placeholder", "Search page by url...");
        $("#search_dia_ico").on("click", function () {
            window.location = ("${pageContext.request.contextPath}/search?type=page&sub=url&val=" + $("#search_dia_txt").val());
        });
    });
    $(".pages").on("click", function (e) {
        e.preventDefault();
        $('html, body').css("cursor", "wait");
        $('#loading-dialog').dialog('open');
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/page",
            data: {url: $(this).data('link')},
            dataType: "json",
            success: function (data) {
                $('#loading-dialog').dialog('close');
                $("#extra-dialog-content-left").html(data.left);
                $("#extra-dialog-content-right").html(data.right);
                $("#extra-dialog-content-lefttitle").html(data.lefttitle);
                $("#extra-dialog-content-righttitle").html(data.righttitle);
                $("#extra-dialog").dialog("open");
                $('html, body').css("cursor", "auto");
            }
        });
    });
    /*end dialog search bar functionality*/

    function getUrlParameter(sParam) {
        var sPageURL = decodeURIComponent(window.location.search.substring(1)),
                sURLVariables = sPageURL.split('&'),
                sParameterName,
                i;

        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : sParameterName[1];
            }
        }
    }
    ;
    /*the feature that allows expansion of the search bar once the user starts typing
     $("#search_term").on("keyup", function () {
     if ($("#search_term").val().length > 5) {
     $("#search_term").width(($("#search_term").val().length * 10) + 50);
     }
     });*/
</script>
<script type="text/javascript">
    /*!
     * Start Bootstrap - SB Admin 2 v3.3.7+1 (http://startbootstrap.com/template-overviews/sb-admin-2)
     * Copyright 2013-2016 Start Bootstrap
     * Licensed under MIT (https://github.com/BlackrockDigital/startbootstrap/blob/gh-pages/LICENSE)
     */
    $(function () {
        $("#side-menu").metisMenu()
    }), $(function () {
        $(window).bind("load resize", function () {
            var i = 50, n = this.window.innerWidth > 0 ? this.window.innerWidth : this.screen.width;
            n < 768 ? ($("div.navbar-collapse").addClass("collapse"), i = 100) : $("div.navbar-collapse").removeClass("collapse");
            var e = (this.window.innerHeight > 0 ? this.window.innerHeight : this.screen.height) - 1;
            e -= i, e < 1 && (e = 1), e > i && $("#page-wrapper").css("min-height", e + "px")
        });
        for (var i = window.location, n = $("ul.nav a").filter(function () {
            return this.href == i
        }).addClass("active").parent(); ; ) {
            if (!n.is("li"))
                break;
            n = n.parent().addClass("in").parent()
        }
    });
    /*
     * metismenu - v1.1.3
     * Easy menu jQuery plugin for Twitter Bootstrap 3
     * https://github.com/onokumus/metisMenu
     *
     * Made by Osman Nuri Okumus
     * Under MIT License
     */
    !function (a, b, c) {
        function d(b, c) {
            this.element = a(b), this.settings = a.extend({}, f, c), this._defaults = f, this._name = e, this.init()
        }
        var e = "metisMenu", f = {toggle: !0, doubleTapToGo: !1};
        d.prototype = {init: function () {
                var b = this.element, d = this.settings.toggle, f = this;
                this.isIE() <= 9 ? (b.find("li.active").has("ul").children("ul").collapse("show"), b.find("li").not(".active").has("ul").children("ul").collapse("hide")) : (b.find("li.active").has("ul").children("ul").addClass("collapse in"), b.find("li").not(".active").has("ul").children("ul").addClass("collapse")), f.settings.doubleTapToGo && b.find("li.active").has("ul").children("a").addClass("doubleTapToGo"), b.find("li").has("ul").children("a").on("click." + e, function (b) {
                    return b.preventDefault(), f.settings.doubleTapToGo && f.doubleTapToGo(a(this)) && "#" !== a(this).attr("href") && "" !== a(this).attr("href") ? (b.stopPropagation(), void(c.location = a(this).attr("href"))) : (a(this).parent("li").toggleClass("active").children("ul").collapse("toggle"), void(d && a(this).parent("li").siblings().removeClass("active").children("ul.in").collapse("hide")))
                })
            }, isIE: function () {
                for (var a, b = 3, d = c.createElement("div"), e = d.getElementsByTagName("i"); d.innerHTML = "<!--[if gt IE " + ++b + "]><i></i><![endif]-->", e[0]; )
                    return b > 4 ? b : a
            }, doubleTapToGo: function (a) {
                var b = this.element;
                return a.hasClass("doubleTapToGo") ? (a.removeClass("doubleTapToGo"), !0) : a.parent().children("ul").length ? (b.find(".doubleTapToGo").removeClass("doubleTapToGo"), a.addClass("doubleTapToGo"), !1) : void 0
            }, remove: function () {
                this.element.off("." + e), this.element.removeData(e)
            }}, a.fn[e] = function (b) {
            return this.each(function () {
                var c = a(this);
                c.data(e) && c.data(e).remove(), c.data(e, new d(this, b))
            }), this
        }
    }(jQuery, window, document);
</script>
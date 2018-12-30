<%-- 

this page comtains the first porition of each page that is rendered, nav bar, etc
for every page accept for index (language selector), login and logout pages
--%>
<%@page import="java.util.Locale"%>
<%@page session="true"%>
<%@page trimDirectiveWhitespaces="true" %>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%        //this is to catch someone that bookmarked a page after selecting a language
    Cookie[] cookies2 = request.getCookies();
    if (cookies2 != null && cookies2.length > 0) {
        for (int i = 0; i < cookies2.length; i++) {
            if (cookies2[i] != null && cookies2[i].getName() != null && cookies2[i].getName().equalsIgnoreCase("locale")) {
                if (cookies2[i].getValue() != null) {
                    ResourceLoaderWeb.setLocaleFromCookie(session, cookies2[i].getValue());
                }
            }
        }
    }
    if (!ResourceLoaderWeb.isLocaleSet(session)) {
        //last chance, default to english
        Locale locale = ResourceLoaderWeb.detectLocale(request);
        ResourceLoaderWeb.setLocale(session, locale);
    }

%>
<html lang="<%=((Locale) session.getAttribute("locale")).getLanguage()%>" dir="<%=ResourceLoaderWeb.getResource(session, "direction")%>">
    <head>
        <meta charset="utf-8">
        <title><%=ResourceLoaderWeb.getResource(session, "index.title")%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="<%=ResourceLoaderWeb.getResource(session, "title.description")%>">
        <meta name="author" content="Apache Software Foundation">

        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="css/ui-lightness/jquery-ui-1.10.2.custom.min.css">
        <!--[if IE 7]>
        <link rel="stylesheet" href="css/font-awesome-ie7.min.css">
        <![endif]-->

        <link rel="shortcut icon" href="favicon.ico" />
        <style type="text/css">
            body {
                padding-top: 60px;
                padding-bottom: 40px;
            }
            .tab-content {
                overflow: visible;
            }
        </style>
        <link href="css/bootstrap-responsive.min.css" rel="stylesheet">
        <link href="css/bootstrap-modal.css" rel="stylesheet">

        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
          <script src="js/html5shiv.js"></script>
        <![endif]-->

        <!-- Fav and touch icons -->

        <link rel="shortcut icon" href="ico/favicon.png">
        <script src="js/jquery-1.9.1.js"></script>
        <%-- 
            This is our hook to inject i18n'd strings/resources to make available via javascript
        --%>
        <script src="js/i18n.js.jsp"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/main.js"></script>
    </head>

    <body>


        <form id="uddiform">
            <%@include  file="csrf.jsp" %>
            <input type="hidden" name="nonce" id="nonce" value="<%=StringEscapeUtils.escapeHtml4((String) session.getAttribute("nonce"))%>" />


            <div class="modal hide fade container" id="alert">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3><%=ResourceLoaderWeb.getResource(session, "actions.result")%></h3>
                </div>
                <div class="modal-body">
                    <div id="alert_results"><i class="icon-2x icon-thumbs-up"></i></div>
                </div>
                <div class="modal-footer">

                    <button type="button" class="close" data-dismiss="modal"><%=ResourceLoaderWeb.getResource(session, "modal.close")%></button>
                </div>
            </div>



            <div class="navbar navbar-inverse navbar-fixed-top">
                <div class="navbar-inner">
                    <div class="container">
                        <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="brand" href="home.jsp" style="padding-left:19px; padding-top:0px; padding-bottom:0px">LOGO HERE</a>
                        <div class="nav-collapse collapse">
                            <ul class="nav">
                                <li class="dropdown"><a href="home.jsp" data-toggle="dropdown" class="dropdowb-town"><i class="icon-home icon-large"></i><%=ResourceLoaderWeb.getResource(session, "navbar.home")%><b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="#" title="<%=ResourceLoaderWeb.getResource(session, "navbar.create.mybiz.tooltip")%>"><%=ResourceLoaderWeb.getResource(session, "navbar.create.mybiz")%></a></li>
                                        <li class="divider"></li>
                                        <li><a href="#" title="<%=ResourceLoaderWeb.getResource(session, "navbar.publisherassertions.tooltip")%>"><%=ResourceLoaderWeb.getResource(session, "navbar.publisherassertions")%></a></li>
                                    </ul>
                                </li>
                                <li class="dropdown" ><a href="#" data-toggle="dropdown" class="dropdowb-town"><i class="icon-search icon-large"></i><%=ResourceLoaderWeb.getResource(session, "navbar.discover")%><b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="#" title="<%=ResourceLoaderWeb.getResource(session, "navbar.businesses.tooltip")%>"><%=ResourceLoaderWeb.getResource(session, "navbar.businesses")%></a></li>
                                        <li class="divider"> </li>
                                        <li><a href="#" title="<%=ResourceLoaderWeb.getResource(session, "navbar.search.tooltip")%>"><%=ResourceLoaderWeb.getResource(session, "navbar.search")%></a></li>
                                    </ul>
                                </li>
                                <li class="dropdown"><a href="#" data-toggle="dropdown" class="dropdowb-town"><i class="icon-pencil icon-large"></i><%=ResourceLoaderWeb.getResource(session, "navbar.create")%><b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="#" title="<%=ResourceLoaderWeb.getResource(session, "navbar.create.business.tooltip")%>"><%=ResourceLoaderWeb.getResource(session, "navbar.create.business")%></a></li>
                                    </ul>
                                </li>


                                <li class="dropdown"><a href="#" data-toggle="dropdown" class="dropdowb-town"><i class="icon-question-sign icon-large"></i><%=ResourceLoaderWeb.getResource(session, "navbar.help")%> <b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="about.jsp" title="<%=ResourceLoaderWeb.getResource(session, "navbar.help.about.tooltip")%>"><%=ResourceLoaderWeb.getResource(session, "navbar.help.about")%></a></li>

                                    </ul>
                                </li>
                            </ul>
                            <div id="loginfield">
                                <%@include file="login.jsp" %>
                            </div>
                        </div><!--/.nav-collapse -->
                    </div>
                </div>
            </div>



            <div class="modal hide fade container" id="nodeswitcher">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3><%=ResourceLoaderWeb.getResource(session, "items.switchnode")%></h3>
                </div>
                <div class="modal-body">
                    <div id="nodeswitcherdetails"></div>
                </div>
                <div class="modal-footer">

                    <a href="index.jsp" class="btn btn-primary" ><%=ResourceLoaderWeb.getResource(session, "actions.continue")%></a>
                </div>
            </div>
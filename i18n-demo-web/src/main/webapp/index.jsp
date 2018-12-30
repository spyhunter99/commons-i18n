<%-- 
/*
 * Copyright 2001-2013 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
    Document   : index, basically a language and timezone selector
                based on previous work on Apache jUDDI
    Created on : Mar 30, 2013, 10:05:37 PM
    Author     : Alex O'Ree

--%>

<%@page import="java.util.Comparator"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Set"%>
<%@page import="com.github.spyhunter99.i18nl10n.ResourceLoader"%>
<%@page import="java.util.Locale"%>
<%@page import="com.github.spyhunter99.i18nl10n.ResourceLoaderWeb"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Cookie[] cookies = request.getCookies();
    Locale locale=Locale.getDefault();
    if (cookies != null && cookies.length > 0) {
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i] != null && cookies[i].getName() != null && cookies[i].getName().equalsIgnoreCase("locale")) {
                if (cookies[i].getValue() != null) {
                    ResourceLoaderWeb.setLocaleFromCookie(session, cookies[i].getValue());
                    //FIXME session.setAttribute("locale", cookies[i].getValue());
                    //response.sendRedirect("home.jsp");
                    //return;
                }
            }
        }
    }
    if (request.getMethod().equalsIgnoreCase("post")) {
        String lang = request.getParameter("language");
        String checked=request.getParameter("setcookie");
        if (lang != null) {
            locale=ResourceLoaderWeb.setLocaleFromCookie(session, lang);
            
            if (checked != null && checked.equalsIgnoreCase("on")) {
                Cookie cookie = new Cookie("locale", locale.toString());
                cookie.setMaxAge(Integer.MAX_VALUE);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            response.sendRedirect("home.jsp");
            return;
        }
    }
%>

<html lang="<%=((Locale) session.getAttribute("locale")).getLanguage()%>" dir="<%=ResourceLoaderWeb.getResource(session, "direction")%>">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%=ResourceLoaderWeb.getResource(session, "index.title")%></title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="<%=ResourceLoaderWeb.getResource(session, "index.description")%>">
        <meta name="author" content="Apache Software Foundation">

        <!-- Le styles -->
        <link href="css/bootstrap.css" rel="stylesheet">
        <link rel="shortcut icon" href="favicon.ico" />
        <link href="css/bootstrap-responsive.css" rel="stylesheet">

        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
          <script src="js/html5shiv.js"></script>
        <![endif]-->

        <!-- Fav and touch icons -->

        <link rel="shortcut icon" href="ico/favicon.png">
        <style type="text/css">
            body {
                padding: 0px 0px 0px 0px;
                margin: 0px 0px 0px 0px;
            }
        </style>

    </head>
    <body>
        <div style="width:100%; height: 100%; position:absolute; text-align: center; vertical-align: middle; padding: 0px; margin: 0px; 
             background-image: url('img/bluemarble2.jpg'); background-repeat: no-repeat; background-position-x: center;
             background-position-y: center; background-size: cover">
            <div style="color: black; background-color: whitesmoke; 
                 background: rgb(235, 235, 235); /* Fall-back for browsers that don't
                                    support rgba */
                 background: rgba(235, 235, 235, .7);width:60%; position: relative; left:20%; top:25%; height:50%; vertical-align: middle">
                <br><br>
                <h1><%=ResourceLoaderWeb.getResource(session, "i18npage.welcome")%></h1>
                <form method="POST">
                    <%=ResourceLoaderWeb.getResource(session, "i18npage.language")%>
                    <select id="language" name="language" >
                    <%
                        Set<Locale> locales = ResourceLoader.getSupportedLocales();
                        for (Locale l:locales) {
                            out.write("<option value=\"" + l.toString() + "\" ");
                            if (l.equals(locale))
                                out.write(" selected ");
                            out.write(">" + l.getDisplayName(locale) + "</option>\n");
                        }
                        
                        %>
                      
                    </select>
                    <br>
                    <%=ResourceLoaderWeb.getResource(session, "i18npage.timezone")%>
                    <select id="timezone" name="timezone" >
                        <%
                            String[] tz=TimeZone.getAvailableIDs();
                            Arrays.sort(tz, new Comparator<String>() {
                                @Override
                                public int compare(String o1, String o2) {
                                    return Integer.compare(TimeZone.getTimeZone(o1).getOffset(System.currentTimeMillis()),TimeZone.getTimeZone(o2).getOffset(System.currentTimeMillis()));

                                }
                            }
                            );
                            TimeZone defaultTimeZone = TimeZone.getDefault();
                            for (String zone: tz) {
                                TimeZone z=TimeZone.getTimeZone(zone);
                                int offset = z.getOffset(System.currentTimeMillis());
                                out.write("<option value=\"" + zone + "\" ");
                                if (defaultTimeZone.getID().equals(z.getID()))
                                    out.write(" selected ");
                                out.write(">"  + z.getID() +  "/" + z.getDisplayName(locale) + " (" + (double)offset/(60d*60d*1000d) + ")</option>\n");
                            }
                            
                            
                            %>
                    </select><br>
                    <input type="checkbox" name="setcookie" checked> Remember my decision<br>
                    <button type="submit" value="Go" class="btn btn-primary">Go</button>
                </form>
                <b>We welcome help internationalizing jUDDI!</b>
                <noscript>Your browser does not support JavaScript! Functionality will be so severely reduced, that you might as well give up, sorry!</noscript>
            </div>
        </div>
    </body>
</html>

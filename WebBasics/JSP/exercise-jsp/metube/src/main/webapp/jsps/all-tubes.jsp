<%--
  Created by IntelliJ IDEA.
  User: hristocr
  Date: 02.02.19
  Time: 23:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="metube.domain.models.views.AllTubesViewModel" %>
<%@ page import="java.util.List" %>
<% List<AllTubesViewModel> allTubesViewModel = (List<AllTubesViewModel>) request.getAttribute("allTubes");%>
<html>
<html>
<c:import url="templates/head.jsp"/>
<body>
    <div class="container">
        <div class="jumbotron">
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
                    <h1>All Tubes</h1>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
                    <h4>Check our tubes below.</h4>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
                    <ul>
                        <% for (AllTubesViewModel tubesViewModel : allTubesViewModel) { %>
                            <li><a href="/tubes/details?title=<%=tubesViewModel.getName()%>"><%=tubesViewModel.getName()%></a>
                            </li>
                        <% } %>
                    </ul>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
                    <a href="/">Back to Home page</a>
                </div>
            </div>

        </div>
    </div>
</body>
</html>

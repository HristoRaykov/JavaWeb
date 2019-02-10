<%--
  Created by IntelliJ IDEA.
  User: hristocr
  Date: 02.02.19
  Time: 23:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="metube.domain.models.views.TubeDetailsViewModel" %>
<% TubeDetailsViewModel tubeDetails = (TubeDetailsViewModel) request.getAttribute("tubeDetailsModel");%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<html>
<c:import url="templates/head.jsp"/>
<body>
    <div class="container">
        <div class="jumbotron">

            <div class="row">
            <div class="col col-md-12 d-flex justify-content-center">
            <h1><%= tubeDetails.getName() %>
            </h1>
            </div>
            </div>
            <hr>
            <div class="row">
            <div class="col col-md-12 d-flex justify-content-center">
            <h3><%= tubeDetails.getDescription()%></h3>
            </div>
            </div>
            <hr>
            <div class="row">
            <div class="col col-md-6 d-flex justify-content-center">
            <a href="<%=tubeDetails.getYouTubeLink()%>">Link to Video</a>
            </div>
            <div class="col col-md-6 d-flex justify-content-center">
            <p><%=tubeDetails.getUploader()%>
            </p>
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

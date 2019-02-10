<%--
  Created by IntelliJ IDEA.
  User: hristocr
  Date: 02.02.19
  Time: 22:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<c:import url="templates/head.jsp"/>
<body>
    <div class="container">
        <div class="jumbotron">
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
                    <h1>Welcome to MeTube!</h1>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
                    <h4>Cool app in beta version</h4>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col col-md-4">
                    <div class="row justify-content-end">
                        <a class="btn btn-primary" href="/tubes/create">Create Tube</a>
                    </div>
                </div>
                <div class="col col-md-4">
                </div>
                <div class="col col-md-4">
                    <div class="row justify-content-start">
                        <a class="btn btn-primary" href="/tubes/all">All Tubes</a>
                    </div>
                </div>
            </div>

        </div>
    </div>
</body>
</html>

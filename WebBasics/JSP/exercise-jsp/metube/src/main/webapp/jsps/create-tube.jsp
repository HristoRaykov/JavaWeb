<%--
  Created by IntelliJ IDEA.
  User: hristocr
  Date: 02.02.19
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<html>
<c:import url="templates/head.jsp"/>
<body>
    <div class="container">
        <div class="jumbotron">
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
                    <h1>Create Tube!</h1>
                </div>
            </div>
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
                    <div class="container">
                        <hr>
                        <form action="/tubes/create" method="post">

                            <div class="row">
                                <div class="col col-md-12 d-flex justify-content-center">
                                    <label for="titleLabelId">Title</label>
                                </div>
                                <div class="col col-md-12 d-flex justify-content-center">
                                    <input type="text" name="name" id="titleLabelId">
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col col-md-12 d-flex justify-content-center">
                                    <label for="descriptionLabelId">Description</label>
                                </div>
                                <div class="col col-md-12 d-flex justify-content-center">
                                    <textarea name="description" id="descriptionLabelId" cols="23"
                                              rows="4"></textarea>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col col-md-12 d-flex justify-content-center">
                                    <label for="youTubeLinkLabelId">YouTube Link</label>
                                </div>
                                <div class="col col-md-12 d-flex justify-content-center">
                                    <input type="text" name="youTubeLink" id="youTubeLinkLabelId">
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col col-md-12 d-flex justify-content-center">
                                    <label for="uploaderLabelId">Uploader</label>
                                </div>
                                <div class="col col-md-12 d-flex justify-content-center">
                                    <input type="text" name="uploader" id="uploaderLabelId">
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col col-md-12 d-flex justify-content-center">
                                    <input type="submit" value="Create Tube">
                                </div>
                            </div>

                        </form>
                        <br>
                        <div class="row">
                            <div class="col col-md-12 d-flex justify-content-center">
                                <a href="/">Back to Home page</a>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>


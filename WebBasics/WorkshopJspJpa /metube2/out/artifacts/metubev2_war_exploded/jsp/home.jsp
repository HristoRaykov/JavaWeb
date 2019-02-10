<%@ page import="metube.domain.models.view.alltubesviewmodels.AllTubesViewModel" %>
<%@ page import="java.util.List" %>
<%@ page import="metube.domain.models.view.alltubesviewmodels.TubeViewModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% AllTubesViewModel model = (AllTubesViewModel) request.getAttribute("model");%>
<% List<TubeViewModel> tubes = model.getTubeViewModels(); %>
<% int rows = tubes.size() % 3 == 0 ? tubes.size() / 3 : tubes.size() / 3 + 1; %>
<html>
<head>
    <c:import url="templates/head.jsp"/>
</head>
<body>
    <div class="container-fluid">
        <header>
            <c:import url="templates/navbar.jsp"/>
        </header>
        <main>
            <hr class="my-2"/>
            <div class="text-center mt-3">
                <h4 class="h4 text-info">Welcome, <%=model.getUsername()%></h4>
            </div>
            <hr class="my-4">
            <div class="container">
                <% for (int i = 0; i < rows; i++) {%>
                <div class="row">
                    <% for (int j = 0; j < 3; j++) { %>
                    <% int index = i*3+j;%>
                    <% if (index>=tubes.size()) break;%>
                    <div class="col-md-4">
                        <a href="/tube/details/<%=tubes.get(index).getId()%>">
                            <img class="embed-responsive-item"
                                 src="https://img.youtube.com/vi/<%=tubes.get(index).getYouTubeId()%>/default.jpg"
                                 alt="thumbnail">
                        </a>
                    </div>
                    <% }%>
                </div>
                <%} %>
            </div>
            <hr class="my-3"/>
        </main>
        <footer>
            <c:import url="templates/footer.jsp"/>
        </footer>
    </div>
</body>
</html>

<%@ page import="metube.domain.models.view.UserProfileViewModel" %>
<%@ page import="java.util.List" %>
<%@ page import="metube.domain.models.view.PendingTubeViewModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% List<PendingTubeViewModel> pendingTubes = (List<PendingTubeViewModel>) request.getAttribute("pendingtubes");%>
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
                <h4 class="text-info text-center">Pending Tubes</h4>
            </div>
            <hr>
            <div class="container-fluid">
                <div class="row d-flex flex-column">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Title</th>
                            <th scope="col">Author</th>
                            <th scope="col">Status</th>
                            <th scope="col">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <% for (int i = 0; i < pendingTubes.size(); i++) { %>
                        <tr>
                            <td><%= i + 1%></td>
                            <td><%= pendingTubes.get(i).getTitle()%></td>
                            <td><%= pendingTubes.get(i).getAuthor()%></td>
                            <td>
                                <a class="btn btn-primary" href="/admin/tube/approve/<%=pendingTubes.get(i).getId()%>">Approve</a>
                                <a class="btn btn-danger" href="/admin/tube/decline/<%=pendingTubes.get(i).getId()%>">Decline</a>
                            </td>
                            <td><a href="/tube/details/<%=pendingTubes.get(i).getId()%>">Details</a></td>
                        </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
            <hr class="my-3"/>
        </main>
        <footer>
            <c:import url="templates/footer.jsp"/>
        </footer>
    </div>
</body>
</html>

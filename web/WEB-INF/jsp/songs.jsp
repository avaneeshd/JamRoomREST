<%-- 
    Document   : songs
    Created on : 22-Oct-2014, 00:15:38
    Author     : Avaneesh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Songs List</title>
    </head>
    <body>
        <h1>List</h1>
        <table border="1">
            <th><td>Name</td><td>Genere</td><td>Artist</td></th>
            <c:forEach var="song" items="${songs}">
            <tr>
                <td><c:out value="${song.getName()}"></c:out></td>
                <td><c:out value="${song.getGenere()}"></c:out></td>
                <td><c:out value="${song.getArtist()}"></c:out></td>
            </tr>
            
            </c:forEach>
            
            
        </table>
        <t>
    </body>
</html>

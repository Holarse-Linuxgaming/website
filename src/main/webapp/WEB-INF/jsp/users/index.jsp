<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

Available Users:
<table class="table">
    <thead>
        <tr>
            <th>ID</th>
            <th>Login</th>
            <th>Rollen</th>
            <th>Erstellt</th>
            <th>Letztes Login</th>
            <th>Letzte Tätigkeit</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${views}" var="view">    
            <tr>
                <td>${view.id}</td>
                <td><a href="${view.url}">${view.login}</a></td>
                <td>${view.rolesText}</td>       
                <td>${view.created}</td>
                <td>${view.lastLogin}</td>
                <td><span title="${view.lastAction}">${view.lastActionAgo}</span></td>         
            </tr>
        </c:forEach>
    </tbody>
</table>

<%@include file="/WEB-INF/jspf/pagination.jspf" %>
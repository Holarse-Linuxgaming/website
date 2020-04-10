<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>Drückblick-Organisation</h1>

<div class="table-responsive">
    <table class="table table-hover">
        <thead>
            <tr>
                <th scope="col"></th>
                <th scope="col">#</th>
                <th scope="col">Drückblick</th>
                <th scope="col">Eingereicht am</th>
                <th scope="col">Melder</th>
                <th scope="col">Nachricht</th>
                <th scope="col">Link</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${entries}" var="entry">
                <tr>
                    <td></td>
                    <th>${entry.id}</th>
                    <td>${entry.drueckblick.name}</td>
                    <td>${entry.created}</td>
                    <td>${entry.bearer}</td>
                    <td>${entry.message}</td>
                    <td>${entry.link}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
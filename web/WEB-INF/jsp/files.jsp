<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <!-- CSS only -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/bootstrap.min.css">

    <!-- JS, Popper.js, and jQuery -->
    <script src="${pageContext.request.contextPath}/statics/script/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/statics/script/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/statics/script/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/statics/script/myScript.js"></script>
</head>
<a href="${pageContext.request.contextPath}/download?path=Z%3a%5cMy_Files%5c11.txt">as</a>
<body>
<nav aria-label="breadcrumb">
    <ol class="breadcrumb" id="crumb">
    </ol>
</nav>
<table class="table table-hover">
    <thead class="thead-light">
    <tr>
        <th scope="col">文件名</th>
        <th scope="col">大小</th>
        <th scope="col">上次修改日期</th>
    </tr>
    </thead>
    <tbody id="tBody">
        <tr>
            <td><a href='#' onclick='refresh("Z:\\My_Files")'>init</a></td>
            <td></td>
            <td></td>
        </tr>
    </tbody>
</table>
</body>
</html>

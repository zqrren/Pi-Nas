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
    <script src="${pageContext.request.contextPath}/statics/script/myScript1.js"></script>
</head>
<body>
<div class="container">
    <div class="row no-gutters" style="height: 45px">
        <div class="col" >
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb" id="crumb">
                    <span hidden id='nowPath'>Z:\My_Files</span>
                </ol>
            </nav>
        </div>
        <div class="col-md-auto"></div>
        <div class="col-2">
            <div class="btn-group" role="group" style="width: 185px;height: 45px">
                <form hidden enctype="multipart/form-data" id="fileForm">
                    <input type="file" name="uploadFiles" id="file" multiple onchange="uploadFileChanged()">
                    <div id="pathParam"></div>
                </form>
                <button type="button" onclick="upload()" class="btn btn-primary btn-lg" style="font-size: 15px">上传</button>
                <button type="button" onclick="download()" class="btn btn-success btn-lg" id="downloadBtn" style="font-size: 15px">下载</button>
            </div>
        </div>
    </div>
    <div class="row no-gutters">
        <div class="col-12">
            <table class="table table-hover">
                <thead class="thead-light">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">文件名</th>
                        <th scope="col">大小</th>
                        <th scope="col">上次修改日期</th>
                    </tr>
                </thead>
                <tbody id="tBody">
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/statics/script/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/statics/script/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/statics/script/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/statics/script/myScript1.js"></script>
</head>
<body>
<div class="container">
    <div class="row no-gutters" style="height: 45px">
        <div class="col">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb" id="crumb">
                    <span hidden id='nowPath'>Z:\My_Files</span>
                </ol>
            </nav>
        </div>
        <div class="col-md-auto">
            <div class="btn-group" role="group" style="height: 45px">
                <form hidden enctype="multipart/form-data" id="fileForm">
                    <div id="pathParam"></div>
                </form>
                <div class="btn-group" role="group">
                    <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">新建
                    </button>
                    <div class="dropdown-menu" style="width: 17rem">
                        <div class="input-group" style="width: 15rem;margin: 0 1rem">
                            <input type="text" class="form-control" id="mkdirName" placeholder="请输入文件夹名称">
                            <div class="input-group-append">
                                <button class="btn btn-outline-secondary" type="button" id="mkdirBtn"
                                        onclick="_mkdir()">新建
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="btn-group" role="group">
                    <button id="btnGroupDrop1" type="button" class="btn btn-primary dropdown-toggle"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">上传
                    </button>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="#" onclick="upload()">上传文件</a>
                        <a class="dropdown-item" href="#" onclick="uploadFolder()">上传文件夹（未完善）</a>
                    </div>
                </div>
                <button type="button" onclick="download()" class="btn btn-success btn-lg" id="downloadBtn"
                        style="font-size: 1rem;">下载
                </button>
                <button type="button" onclick="_delete()" class="btn btn-danger btn-lg" id="deleteBtn"
                        style="font-size: 1rem;">删除
                </button>
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

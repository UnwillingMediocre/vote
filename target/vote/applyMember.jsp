<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Micro Vote</title>

    <script src="static/js/jquery-3.3.1.js"></script>
    <script src="static/js/jquery-3.3.1.min.js"></script>
    <link href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/css/my.css" rel="stylesheet">
    <script src="static/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
    <link rel="shortcut icon" type="image/x-icon" href="static/images/favicon.ico">
    <style type="text/css">
        .list-group-item {
            background-color: #f5f5f5;
        }

        a.list-group-item:focus,
        a.list-group-item:hover,
        button.list-group-item:focus,
        button.list-group-item:hover {
            color: #555;
            text-decoration: none;
            background-color: #dff0d8;
        }

        .container {
        }
    </style>
</head>

<body>
<div class="container">
    <!--导航条-->
    <div class="row">
        <div class="col-md-12" align="center">
            <nav class="navbar navbar-inverse">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="VoteIndex.jsp" style="padding: 0;margin-left: 15px"><img
                                src="static/images/logo.png" height="50px"></a>
                    </div>

                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav">
                            <li>
                                <a href="VoteIndex.jsp" style="font-size: 24px;color: white;margin-left: 20px">Micro Vote</a>
                            </li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li>
                                <a href="#" id="user"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></a>
                                <script type="text/javascript">
                                    var isLogined = '${sessionScope.user.userName}';
                                    var permission = '${sessionScope.user.permission}';
                                    if (isLogined !== '') {
                                        if (permission != 2) {
                                            $("#user").append(' ' + isLogined + "|管理").attr("href", "index.jsp");
                                        } else {
                                            $("#user").append(' ' + isLogined + '');
                                        }
                                    } else {
                                        $("#user").append(" 登录|注册").attr("href", "loginAndRegister.jsp");
                                    }
                                </script>
                            </li>
                            <li>
                                <a href="myVote.jsp">我的参与</a>
                            </li>
                            <c:if test="${sessionScope.user.permission==2}">
                                <li>
                                    <a href="applyMember.jsp">申请会员</a>
                                </li>
                            </c:if>

                            <li>
                                <a href="/vote_war/user?method=exit"><span class="glyphicon glyphicon-remove-sign"
                                                                           aria-hidden="true"></span> 退出</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
    </div>
    <form class="form-horizontal" action="/vote_war/user?method=applyMember" method="post">
        <div class="form-group">
            <label class="col-sm-2 control-label">申请标题</label>
            <div class="col-sm-10">
                <input name="name" type="text" class="form-control" placeholder="会员申请" style="width: 30%;">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">邮箱</label>
            <div class="col-sm-10">
                <input name="email" type="text" class="form-control" placeholder="常用的邮箱地址" style="width: 30%;">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">申请理由</label>
            <div class="col-sm-10">
                <textarea name="description" class="form-control" rows="5" style="width: 40%;"></textarea>
            </div>
        </div>
        <div class="form-group" style="border: none;padding-bottom: 0px;">
            <div style="margin-left: 500px;margin-top: 16px">
                <input type="hidden" name="userId" value="${sessionScope.user.userId}">
                <button id="create_btn" class="btn btn-primary" type="submit" >申请会员</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
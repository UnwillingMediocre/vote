<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>修改密码</title>

    <script src="static/js/jquery-3.3.1.js"></script>
    <script src="static/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="static/layer/layer.js"></script>
    <link href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/css/my.css" rel="stylesheet">
    <link href="static/layer/theme/default/layer.css">
    <script src="static/bootstrap-3.3.7-dist/js/bootstrap.js"></script>

    <style type="text/css">
        .btn-default {
            color: #333;
            background-color: #eee;
            border-color: #ccc;
            margin-top: 15px
        }

        .btn-default.focus, .btn-default:focus {
            color: #333;
            background-color: #BBBBBB;
            border-color: #8c8c8c
        }

        .btn-default:hover {
            color: #333;
            background-color: #BBBBBB;
            border-color: #adadad
        }
    </style>

</head>

<body>
<div class="container">
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
                                <a href="VoteIndex.jsp" style="font-size: 24px;color: white;margin-left: 20px">Micro
                                    Vote</a>
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
                            <c:if test="${user != null}">
                                <li class="dropdown">
                                    <a href="#" data-toggle="dropdown">个人中心&nbsp;<span class="caret"></span></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="/vote_war/user?method=myInfo&userId=${sessionScope.user.userId}">个人资料</a></li>
                                        <li><a href="updatePassword.jsp">修改密码</a></li>
                                    </ul>
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
    <div class="row">
        <div class="col-md-12">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-heading" style="font-size: 30px">修改密码</div>
                    <div class="panel-body" align="center">
                        <div class="input-group">
                        <span class="input-group-addon"
                              id="basic-addon1">&nbsp;&nbsp;&nbsp;&nbsp;原密码&nbsp;&nbsp;&nbsp;</span>
                            <input type="password" class="form-control" id="oldPwd"/>
                        </div>
                        <br/>
                        <div class="input-group">
                        <span class="input-group-addon"
                              id="basic-addon2">&nbsp;&nbsp;&nbsp;&nbsp;新密码&nbsp;&nbsp;&nbsp;</span>
                            <input type="password" class="form-control" id="newPwd1"/>
                        </div>
                        <br/>
                        <div class="input-group">
                            <span class="input-group-addon" id="basic-addon3">重复新密码</span>
                            <input type="password" class="form-control" id="newPwd2"/>
                        </div>
                        <button type="button" class="btn btn-default">修改密码</button>
                    </div>
                </div>
            </div>
            <div class="col-md-3"></div>
        </div>
    </div>
</div>
<script>
    $("button").click(function () {
        console.log($("#newPwd1").val() + "==>new");
        console.log($("#newPwd2").val() + "==>new2");
        if ($("#newPwd1").val() != $("#newPwd2").val()) {
            layer.msg("两次新密码不相同");
        } else {
            $.ajax({
                type: "post",
                url: "/vote_war/user?method=updatePassword&time=" + new Date().getTime(),
                data: {
                    id: '${sessionScope.user.userId}',
                    oldPwd: $("#oldPwd").val(),
                    newPwd: $("#newPwd1").val()
                },
                success: function (result) {
                    if (result == 'SUCCESS') {
                        $("input").val("");
                        layer.msg("修改成功");
                        window.location.href = "/vote_war/user?method=exit";
                    } else {
                        layer.msg("原密码错误");
                    }
                }
            });
        }

    });
</script>
</body>

</html>
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
    <script src="static/js/jquery.datetimepicker.js"></script>
    <script src="static/js/jquery.js"></script>
    <script src="static/js/jquery.datetimepicker.full.min.js"></script>
    <script src="static/js/jquery.datetimepicker.full.js"></script>
    <script src="static/js/jquery.datetimepicker.min.js"></script>
    <link rel="stylesheet" type="text/css" href="static/css/jquery.datetimepicker.css">
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
                                        <li><a href="/vote_war/user?method=myInfo&userId=${sessionScope.user.userId}">个人资料</a>
                                        </li>
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

    <!--所有投票-->
    <div class="row">
        <div class="col-md-12">
            <div class="col-md-3">
            </div>
            <div class="col-md-6">
                <form action="/vote_war/user?method=editInfo" method="post">
                    <input type="hidden" value="${voteInfo.userId}" name="userId">
                    <div class="panel panel-default">
                        <!-- Default panel contents -->
                        <div class="panel-heading" style="font-size: 30px">个人资料</div>
                        <div class="panel-body" align="center">
                            <div class="input-group">
                        <span class="input-group-addon"
                              id="basic-addon2">&nbsp;&nbsp;&nbsp;&nbsp;昵称&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                <input type="text" class="form-control" id="username" name="username"
                                       value="${voteInfo.userName}" disabled="disabled"/>
                            </div>
                            <br/>
                            <div class="input-group">
                        <span class="input-group-addon"
                              id="basic-addon5">&nbsp;&nbsp;&nbsp;&nbsp;性别&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                <div class="col-sm-3">
                                    <input type="radio" name="sex" value="男" class="radio-custom radio-primary"
                                           checked="checked" id="male">男
                                    <input type="radio" name="sex" value="女" class="radio-custom radio-primary"
                                           id="female">女
                                </div>
                            </div>
                            <br/>
                            <div class="input-group">
                        <span class="input-group-addon"
                              id="basic-addon7">&nbsp;&nbsp;&nbsp;&nbsp;生日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                <div class="col-sm-4">
                                    <input name="birthday" type="text" class="form-control" id="datetimepicker"
                                           value="${voteInfo.birthday}"/>
                                </div>
                            </div>
                            <br>
                            <div class="input-group">
                            <span class="input-group-addon"
                                  id="basic-addon3">&nbsp;&nbsp;&nbsp;&nbsp;手机号&nbsp;&nbsp;</span>
                                <input type="text" class="form-control" id="phone" name="phone"
                                       value="${voteInfo.phone}" disabled="disabled"/>
                            </div>
                            <br/>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon4">&nbsp;&nbsp;&nbsp;&nbsp;邮箱&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                <input type="text" class="form-control" id="email" name="email"
                                       value="${voteInfo.email}"/>
                            </div>
                            <BR>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon6">&nbsp;个性签名&nbsp;</span>
                                <textarea rows="2" class="form-control"
                                          name="personality">${voteInfo.personality}</textarea>
                            </div>
                            <button type="submit" class="btn btn-success" style="margin-top: 20px;margin-left: 420px">
                                修改资料
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-3">
            </div>
        </div>
    </div>
</div>
<script>
    $('#datetimepicker').datetimepicker();
    $('#datetimepicker').datetimepicker({
        lang: 'ch', //设置中文
        format: "Y-m-d" //格式化日期
    });
    $(checkVoteType());

    function checkVoteType() {
        var type = '${voteInfo.sex}';
        if (type == '男') {
            $("#male").prop("checked", "checked");
        } else {
            $("#female").prop("checked", "checked");
        }
    }
</script>
</body>

</html>
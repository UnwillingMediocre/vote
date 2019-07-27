<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${voteInfoVo.title }</title>

    <script src="static/js/jquery-3.3.1.js"></script>
    <script src="static/js/jquery-3.3.1.min.js"></script>
    <script src="static/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
    <link href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="shortcut icon" type="image/x-icon" href="static/images/favicon.ico">
    <style type="text/css">
        .progress-bar-info {
            background-color: #3762BC
        }
    </style>
</head>

<body>
<div class="container">
    <!-- 导航条-->
    <div class="row">
        <div class="col-md-12" align="center">
            <nav class="navbar navbar-inverse">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                                data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
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

    <!-- 投票区域 -->
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-success">
                <!-- Default panel contents -->
                <div class="panel-heading" style="font-size: 30px">${voteInfoVo.title}</div>
                <div class="panel-body" style="font-size: 15px;border-bottom: 1px solid silver;color: silver;">
                    <span class="glyphicon glyphicon-time"></span> ${voteInfoVo.endTime}&nbsp;&nbsp;
                    <span class="glyphicon glyphicon-thumbs-up"></span> ${voteInfoVo.totalVote}&nbsp;&nbsp;
                    <span class="glyphicon glyphicon-user"></span> ${voteInfoVo.username}
                </div>
                <div class="panel-body" style="font-size: 20px;border-bottom: 1px solid silver;">
                    ${voteInfoVo.description }
                </div>
                <div class="panel-body" style="font-size: 20px" id="optionArea">
                    <c:forEach items="${voteInfoVo.optionInfoVoList }" var="item">
                        ${item.content } <span>(<fmt:formatNumber type="number"
                                                                  value="${item.count/voteInfoVo.totalVote*100 }"
                                                                  pattern="0.00" maxFractionDigits="2"/>%, ${item.count}票)</span>
                        <c:if test="${item.isOption==1}">
                            <i class="glyphicon glyphicon-star-empty" style="color: #0ea74a;font-size: 20px"></i>
                        </c:if>
                        <br/><br/>
                        <div class="progress">
                            <div class="progress-bar progress-bar-info" role="progressbar"
                                 aria-valuenow="${item.count/voteInfoVo.totalVote*100 }" aria-valuemin="0"
                                 aria-valuemax="100" style="width: ${item.count/voteInfoVo.totalVote*100}%;">
                                <fmt:formatNumber type="number" value="${item.count/voteInfoVo.totalVote*100 }"
                                                  pattern="0.00" maxFractionDigits="2"/>%
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

</div>
</body>

</html>
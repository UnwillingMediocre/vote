<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
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
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12" align="center">
            <nav class="navbar navbar-inverse">
                <div class="container-fluid">
                    <!-- Brand and toggle get grouped for better mobile display -->
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
                            <li><a href="VoteIndex.jsp" style="font-size: 24px;color: white;margin-left: 20px">Micro
                                Vote</a>
                            </li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li>
                                <a href="javascript:void(0)"><span class="glyphicon glyphicon-user"
                                                                   aria-hidden="true"></span> ${sessionScope.user.userName}
                                </a>
                            </li>
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
        <div class="col-md-3" style="height: 1000px">
            <ul id="main-nav" class="nav nav-tabs nav-stacked" style="">
                <li class="active">
                    <a href="javascript:void(0)"> <i class="glyphicon glyphicon-th-large"></i> 首页
                    </a>
                </li>
                <li>
                    <a href="#voteSetting" class="nav-header collapsed" data-toggle="collapse"> <i
                            class="glyphicon glyphicon-cog"></i> 投票管理 <span
                            class="pull-right glyphicon glyphicon-chevron-down"></span>
                    </a>
                    <ul id="voteSetting" class="nav nav-list collapse secondmenu" style="height: 0px;">
                        <li>
                            <a href="createVote.jsp" target="iframe"><i class="glyphicon glyphicon-plus"></i>创建投票</a>
                        </li>
                        <c:if test="${user.permission==0}">
                            <li>
                                <a href="showAll.jsp" target="iframe"><i class="glyphicon glyphicon-th"></i>所有投票</a>
                            </li>
                        </c:if>
                        <li>
                            <a href="showVoteList.jsp" target="iframe"><i
                                    class="glyphicon glyphicon-th-list"></i>我的投票</a>
                        </li>
                    </ul>
                </li>
                <c:if test="${user.permission==0}">
                    <li>
                        <a href="#userSet" class="nav-header collapsed" data-toggle="collapse"> <i
                                class="glyphicon glyphicon-user"></i> 用户管理 <span
                                class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                        <ul id="userSet" class="nav nav-list collapse secondmenu" style="height: 0px;">
                            <li>
                                <a href="showUserList.jsp" target="iframe"><i class="glyphicon glyphicon-th-list"></i>用户信息</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#memberSet" class="nav-header collapsed" data-toggle="collapse"> <i
                                class="glyphicon glyphicon-asterisk"></i> 会员管理 <span
                                class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                        <ul id="memberSet" class="nav nav-list collapse secondmenu" style="height: 0px;">
                            <li>
                                <a href="showApplyList.jsp" target="iframe"><i class="glyphicon glyphicon-th-list"></i>申请列表</a>
                            </li>
                        </ul>
                    </li>
                </c:if>
                <li>
                    <a href="#chartSetting" class="nav-header collapsed" data-toggle="collapse"> <i
                            class="glyphicon glyphicon-stats"></i> 图表统计 <span
                            class="pull-right glyphicon glyphicon-chevron-down"></span>
                    </a>
                    <ul id="chartSetting" class="nav nav-list collapse secondmenu" style="height: 0px;">
                        <li>
                            <a href="voteAnalysis.jsp" target="iframe"><i class="glyphicon glyphicon-edit"></i>数据分析</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="javascript:void(0)"> <i class="glyphicon glyphicon-fire"></i> 关于系统<span
                            class="badge pull-right">1</span>
                    </a>
                </li>

            </ul>
        </div>
        <div class="col-md-9" style="height: 600px">
            <iframe name="iframe" frameborder="0" width="100%" height="100%"></iframe>
        </div>
    </div>
</div>
</body>

</html>
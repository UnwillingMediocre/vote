<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${voteInfoVo.title}</title>

    <script src="static/js/jquery-3.3.1.js"></script>
    <script src="static/js/jquery-3.3.1.min.js"></script>
    <script src="static/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
    <link href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="static/layer/layer.js"></script>
    <link href="static/layer/theme/default/layer.css">
    <link rel="shortcut icon" type="image/x-icon" href="static/images/favicon.ico">
</head>

<body>
<div class="container">
    <!-- 导航条-->
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

    <!-- 投票区域 -->
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-success">
                <div class="panel-heading" style="font-size: 30px">${voteInfoVo.title}</div>
                <div class="panel-body" style="font-size: 15px;border-bottom: 1px solid silver;color: silver;">
                    <span class="glyphicon glyphicon-time"></span> ${voteInfoVo.endTime}&nbsp;&nbsp;
                    <span class="glyphicon glyphicon-thumbs-up"></span> ${voteInfoVo.totalVote }&nbsp;&nbsp;
                    <span class="glyphicon glyphicon-user"></span> ${voteInfoVo.username }
                </div>
                <div class="panel-body" style="font-size: 20px;border-bottom: 1px solid silver;">
                    ${voteInfoVo.description}
                </div>
                <div class="panel-body" style="font-size: 20px" id="optionArea">
                    <div class="row" style="padding: 0px 20px;margin-top: 20px;">
                        <c:forEach items="${voteInfoVo.optionInfoVoList }" var="item">
                            <div class="col-md-2"></div>
                            <div class="col-md-<fmt:formatNumber type="number" value="${16/fn:length(voteInfoVo.optionInfoVoList)}" maxFractionDigits="0"/>"
                                 style="border: 1px solid silver;border-radius: 4px;margin-top: 10px;margin-left: 10px;"
                                 align="center">
                                    <%--<c:if test="${!empty(item.linkAddress)}">
                                    <div>
                                        <img src="static/images/${item.linkAddress}" height="150px">
                                    </div>
                                    </c:if>--%>
                                <div class="${voteInfoVo.type}">
                                    <label>
                                        <input type="${voteInfoVo.type }" name="optionsRadios" value="${item.count+1 }"
                                               option_id="${item.optionId}">
                                            ${item.content }
                                    </label>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="row" style="margin-top: 20px;">
                        <div class="col-md-12">
                            <center>
                                <button class="btn btn-success" id="vote_btn" style="width: 10%;">投票</button>
                            </center>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<script>
    $("#vote_btn").click(function () {
        var options = $("input[name='optionsRadios']:checked");
        console.log(options);
        if (options.length == 0) {
            layer.msg("无选项，不能提交投票！");
        } else {
            var param = [];
            $.each(options, function (index, item) {
                console.log($(item).val());
                var key = $(item).attr("option_id");
                key.toString();
                var value = $(item).val();
                param.push(key + "-" + value);
            });
            $.ajax({
                type: "get",
                url: "/vote_war/vote?method=doVote&time=" + new Date().getTime(),
                data: {
                    "options": param,
                    vote_id:${voteInfoVo.subjectId}
                },
                traditional: true,
                success: function () {
                    window.location.href = "/vote_war/vote?method=showVoteResult&vote_id=${voteInfoVo.subjectId}";
                }
            });
        }
    });
</script>

</body>

</html>
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
                        <form class="navbar-form navbar-left" role="search" style="margin-left: 104px">
                            <div class="form-group">
                                <input type="text" class="form-control" name="keyWord" placeholder="Search">
                            </div>
                            <button type="button" class="btn btn-default" id="search_btn">Submit</button>
                        </form>
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

    <!--所有投票-->
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading" style="font-size: 30px">人气投票</div>
                <div id="voteArea">
                </div>
            </div>
        </div>
        <div class="col-md-12" id="voteButton">
        </div>
    </div>
</div>
<script>
    $(function () {
        to_page(1);
    });

    function to_page(num) {
        $.ajax({
            url: "/vote_war/vote?method=getVoteAll&time=" + new Date().getTime(),
            data: {
                pageNum: num,
                keyWord: $("input[name=keyWord]").val()
            },
            type: "get",
            dataType: "json",
            success: function (result) {
                $("#voteArea").empty();
                $("#voteButton").empty();
                console.log(result);
                build_vote_list(result);
            }
        });
    }

    function build_vote_list(result) {
        var vote_list = result.list;
        $.each(vote_list, function (index, item) {
            var d = $("<div></div>").addClass("col-md-4").css("margin-top", "20px");
            var img = $("<img/>").attr("src", "static/images/Eminem.jpg").addClass("img-responsive center-block img-circle").attr("width", "50%");
            var a = $("<a></a>").attr("href", "/vote_war/vote?method=showDoVoteView&id=" + item.subjectId).addClass("list-group-item");
            var span1 = $("<span></span>").addClass("badge");
            var span2 = $("<span></span>").addClass("glyphicon glyphicon-thumbs-up").attr("aria-hidden", "true");
            span1.append(span2).append(" " + item.totalVote);
            a.append(span1).append(item.title);
            d.append(img).append(a);
            $("#voteArea").append(d);
        });

        var nav = $("<nav></nav>").attr("aria-label", "...");
        var ul = $("<ul></ul>").addClass("pager");
        var pre_a = $("<a></a>").append("<b>Previous</b>").attr("href", "javascript:void(0)");
        var pre_li = $("<li></li>");
        var next_a = $("<a></a>").append("<b>Next</b>").attr("href", "javascript:void(0)");
        var next_li = $("<li></li>");

        if (result.hasPrePage == false) {
            pre_li.addClass("disabled");
        } else {
            pre_a.css("color", "#2795DC");
            pre_li.click(function () {
                to_page(result.prePage);
            });
        }

        if (result.hasNextPage == false) {
            next_li.addClass("disabled");
        } else {
            next_a.css("color", "#2795DC");
            next_li.click(function () {
                to_page(result.nextPage);
            });
        }

        pre_li.append(pre_a);
        next_li.append(next_a);
        ul.append(pre_li).append("<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>").append(next_li);
        nav.append(ul);
        $("#voteButton").append(nav);
    }

    $("#search_btn").click(function () {
        to_page(1);
    });
</script>
</body>

</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>展示投票</title>

    <script src="static/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="static/layer/layer.js"></script>
    <link href="static/layer/theme/default/layer.css">
    <link href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="static/bootstrap-3.3.7-dist/js/bootstrap.js"></script>

    <style type="text/css">
        #table_area {
            border: 1px solid silver;
            border-radius: 4px;
            padding: 30px 15px 15px;
            background-color: #FCFCFC;
        }

        .table {
            text-align: center;
        }

        .table thead tr th {
            text-align: center;
        }

        .table-bordered td, .table-bordered th {
            border: 1.5px solid #CCCCCC !important;
        }

        button {
            margin-left: 3px;
        }

        .btn-info {
            color: #fff;
            background-color: #d2df08;
            border-color: #9cac16;
        }

        .btn-info:hover {
            color: #fff;
            background-color: #7c9710;
            border-color: #269abc;
        }

    </style>

</head>

<body>

<div class="container">
    <div class="row">
        <div class="col-md-12" id="vote_list">
            <div id="table_area" style="padding: 30px 15px 0px 15px;">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>用户名</th>
                        <th>手机号</th>
                        <th>注册时间</th>
                        <th>权限</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <div id="voteButton">
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        to_page(1);
    });

    function to_page(num) {
        $.ajax({
            type: "post",
            url: "/vote_war/user?method=userAll&time=" + new Date().getTime(),
            dataType: 'json',
            data: {pageNum: num},
            success: function (result) {
                $("#vote_list tbody").empty();
                $("#voteButton").empty();
                console.log(result);
                build_user_list(result)
            }
        });
    }
    function build_user_list(result) {
        var themes = result.list;
        $.each(themes, function (index, item) {
            var id_td = $("<td></td>").append(index + 1);
            var a = $("<a></a>").append(item.userName).css("cursor","pointer").attr("href", "javascript:void(0)").attr("user_id", item.userId);
            var username_td = $("<td></td>").append(a);
            var phone_td = $("<td></td>").append(item.phone);
            var register_td = $("<td></td>").append(item.registerTime);
            var permission_td;
            if (item.permission == 0) {
                permission_td = $("<td></td>").append("管理员");
            } else if (item.permission == 1) {
                permission_td = $("<td></td>").append("会员");
            } else {
                permission_td = $("<td></td>").append("普通");
            }
            a.click(function () {
                var userId = $(this).attr("user_id");
                layer.open({
                    type: 2,
                    title:"用户资料",
                    shadeClose: true,
                    shade: 0.4,
                    area: ['40%', '60%'],
                    content:"/vote_war/user?method=userInfo&userId=" + userId + "&time="+ new Date().getTime()
                });
                console.log(userId);
            });
            var status_td;
            var delVote_td;
            var voteInfo_td;
            var editVote_td;
            if (item.status == 1) {
                status_td = $("<td></td>").append("使用");

            } else {
                status_td = $("<td></td>").append("删除");
            }
            if (item.userName != "admin") {
                if (item.status == 1) {
                    delVote_td = $("<button type='button' class='btn btn-danger btn_del'></button>").append("删除").attr("user_id", item.userId);
                } else {
                    delVote_td = $("<button type='button' class='btn btn-success btn_revert'></button>").append("还原").attr("user_id", item.userId);
                }
                if (item.permission == 2) {
                    voteInfo_td = $("<button type='button' class='btn btn-primary btn_permission'></button>").append("权限").attr("user_id", item.userId);
                }else{
                    voteInfo_td = $("<button type='button' class='btn btn-warning btn_revoke'></button>").append("收回").attr("user_id", item.userId);
                }

                editVote_td = $("<button type='button' class='btn btn-info btn_init'></button>").append("初始密码").attr("user_id", item.userId).attr("email",item.email);
            } else {
                editVote_td = $("<button type='button' class='btn btn-warning'></button>").append("无操作");
            }
            var btn_td = $("<td></td>").css("padding", "0");
            btn_td.append(voteInfo_td).append(delVote_td).append(editVote_td);
            var tr = $("<tr></tr>").append(id_td).append(username_td).append(phone_td).append(register_td).append(permission_td).append(status_td).append(btn_td);
            $("#vote_list tbody").append(tr);
        });

        var nav = $("<nav></nav>").attr("aria-label", "...");
        var ul = $("<ul></ul>").addClass("pager").css("margin", "10px 10px");
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

    $(document).on("click", ".btn_revoke", function () {
        var user_id = $(this).attr("user_id");
        var sure = window.confirm("确认收回权限？");
        if (sure) {
            $.ajax({
                url: "/vote_war/user?method=setPermission&time=" + new Date().getTime(),
                type: "post",
                data: {
                    user_id: user_id,
                    permission:2
                },
                success: function (result) {
                    if (result === "" || result === null) {
                        window.location.reload();
                    } else {
                        layer.msg("权限收回失败！");
                    }
                }
            })
        }
    });
    $(document).on("click", ".btn_permission", function () {
        var user_id = $(this).attr("user_id");
        var sure = window.confirm("确认升级权限？");
        if (sure) {
            $.ajax({
                url: "/vote_war/user?method=setPermission&time=" + new Date().getTime(),
                type: "post",
                data: {
                    user_id: user_id,
                    permission:1
                },
                success: function (result) {
                    if (result === "" || result === null) {
                        window.location.reload();
                    } else {
                        layer.msg("权限高，责任重，请慎重！");
                    }
                }
            })
        }
    });

    $(document).on("click", ".btn_del", function () {
        var user_id = $(this).attr("user_id");
        var sure = window.confirm("确认删除？");
        if (sure) {
            $.ajax({
                url: "/vote_war/user?method=deleteUser&time=" + new Date().getTime(),
                type: "post",
                data: {
                    user_id: user_id
                },
                success: function (result) {
                    if (result === "" || result === null) {
                        window.location.reload();
                    } else {
                        layer.msg("删除失败");
                    }
                }
            })
        }
    });
    $(document).on("click", ".btn_revert", function () {
        var user_id = $(this).attr("user_id");
        var sure = window.confirm("确认还原？");
        if (sure) {
            $.ajax({
                url: "/vote_war/user?method=revertUser&time=" + new Date().getTime(),
                type: "post",
                data: {
                    user_id: user_id
                },
                success: function (result) {
                    if (result === "" || result === null) {
                        window.location.reload();
                    } else {
                        layer.msg("还原失败");
                    }
                }
            })
        }
    });

    $(document).on("click", ".btn_init", function () {
        var user_id = $(this).attr("user_id");
        var email = $(this).attr("email");
        $.ajax({
            url: "/vote_war/user?method=initialPassword&time=" + new Date().getTime(),
            type: "post",
            data: {
                user_id: user_id,
                email:email
            },
            success: function (result) {
                if (result.trim() == "" || result === null) {
                    layer.msg("密码初始化成功，初始密码已经发送到该用户邮箱");
                } else {
                    layer.msg("无法修改");
                }
            }
        })
    });
</script>
</body>

</html>

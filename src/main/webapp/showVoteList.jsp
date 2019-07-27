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
                        <th>投票名</th>
                        <th>截止时间</th>
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
    Date.prototype.format = function(fmt) {
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        for(var k in o) {
            if(new RegExp("("+ k +")").test(fmt)){
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
    }

    $(function () {
        to_page(1);
    });

    function to_page(num) {
        $.ajax({
            type: "post",
            url: "/vote_war/vote?method=showVoteList&time=" + new Date().getTime(),
            dataType: 'json',
            data: {
                user_id: '${sessionScope.user.userId}',
                pageNum: num
            },
            success: function (result) {
                $("#vote_list tbody").empty();
                $("#voteButton").empty();
                console.log(result);
                build_vote_list(result);
            }
        });
    }

    function build_vote_list(result) {
        var themes = result.list;
        $.each(themes, function (index, item) {
            var id_td = $("<td></td>").append(index + 1);
            var name_td = $("<td></td>").append(item.title);
            var description_td = $("<td></td>").append(new Date(item.endTime).format("yyyy-MM-dd hh:mm:ss"));
            var voteInfo_td = $("<button type='button' class='btn btn-primary btn_info'></button>").append("详情").attr("vote_id", item.id);
            var delVote_td = $("<button type='button' class='btn btn-danger btn_del'></button>").append("删除").attr("vote_id", item.id);
            var editVote_td = $("<button type='button' class='btn btn-info btn_edit'></button>").append("编辑").attr("vote_id", item.id);
            var btn_td = $("<td></td>").css("padding", "0");
            btn_td.append(voteInfo_td).append(delVote_td).append(editVote_td);
            var tr = $("<tr></tr>").append(id_td).append(name_td).append(description_td).append(btn_td);
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

    $(document).on("click", ".btn_info", function () {
        var vote_id = $(this).attr("vote_id");
        window.location.href = "/vote_war/vote?method=getVoteInfo&vote_id=" + vote_id + "&time=" + new Date().getTime();
    });

    $(document).on("click", ".btn_del", function () {
        var vote_id = $(this).attr("vote_id");
        var sure = window.confirm("确认删除？");
        if (sure) {
            $.ajax({
                url: "/vote_war/vote?method=deleteVote&time=" + new Date().getTime(),
                type: "post",
                data: {
                    vote_id: vote_id
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

    $(document).on("click", ".btn_edit", function () {
        var vote_id = $(this).attr("vote_id");
        $.ajax({
            url: "/vote_war/vote?method=check&time=" + new Date().getTime(),
            type: "post",
            data: {vote_id: vote_id},
            success: function (result) {
                console.log(result + "====>");
                if (result.trim() != "") {
                    window.location.href = "/vote_war/vote?method=editVote&vote_id=" + vote_id + "&time=" + new Date().getTime();
                } else {
                    layer.msg("无法修改");
                }
            }
        })
    });
</script>
</body>

</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>申请列表</title>

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
            <div id="table_area">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>申请人</th>
                        <th>申请标题</th>
                        <th>申请理由</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script>
    $.ajax({
        type: "post",
        url: "/vote_war/user?method=applyAll&time=" + new Date().getTime(),
        dataType: 'json',
        success: function (result) {
            console.log(result);
            var themes = result;
            $.each(themes, function (index, item) {
                var id_td = $("<td></td>").append(index + 1);
                var username_td = $("<td></td>").append(item.sponsor);
                var title_td = $("<td></td>").append(item.title);
                var description_td = $("<td></td>").append(item.description);
                var agreeApply_td;
                var notAgreeApply_td;
                agreeApply_td = $("<button type='button' class='btn btn-success btn_apply'></button>").append("同意申请").attr("email", item.email).attr("sponsor",item.sponsor).attr("id",item.id);
                notAgreeApply_td = $("<button type='button' class='btn btn-danger btn_notApply'></button>").append("拒绝申请").attr("email", item.email).attr("sponsor",item.sponsor).attr("id",item.id);
                var btn_td = $("<td></td>").css("padding", "0");
                btn_td.append(agreeApply_td).append(notAgreeApply_td);
                var tr = $("<tr></tr>").append(id_td).append(username_td).append(title_td).append(description_td).append(btn_td);
                $("#vote_list tbody").append(tr);
            });
        }
    });

    $(document).on("click", ".btn_apply", function () {
        var email = $(this).attr("email");
        var id = $(this).attr("id");
        var sponsor = $(this).attr("sponsor");
        var sure = window.confirm("确认同意？");
        if (sure) {
            $.ajax({
                url: "/vote_war/user?method=agreeApply&time=" + new Date().getTime(),
                type: "post",
                data: {
                    email: email,
                    sponsor:sponsor,
                    id:id
                },
                success: function (result) {
                    if (result === "" || result === null) {
                        window.location.reload();
                    } else {
                        layer.msg("同意失败");
                    }
                }
            })
        }
    });
    $(document).on("click", ".btn_notApply", function () {
        var email = $(this).attr("email");
        var sponsor = $(this).attr("sponsor");
        var id = $(this).attr("id");
        var sure = window.confirm("确认拒绝？");
        if (sure) {
            $.ajax({
                url: "/vote_war/user?method=notAgreeApply&time=" + new Date().getTime(),
                type: "post",
                data: {
                    email: email,
                    sponsor:sponsor,
                    id:id
                },
                success: function (result) {
                    if (result === "" || result === null) {
                        window.location.reload();
                    } else {
                        layer.msg("拒绝失败");
                    }
                }
            })
        }
    });
</script>
</body>

</html>
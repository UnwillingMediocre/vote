<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>创建投票</title>
    <script src="static/js/jquery-3.3.1.js"></script>
    <script src="static/js/jquery.datetimepicker.js"></script>
    <script src="static/js/jquery.js"></script>
    <script src="static/js/jquery.datetimepicker.full.min.js"></script>
    <script src="static/js/jquery.datetimepicker.full.js"></script>
    <script src="static/js/jquery.datetimepicker.min.js"></script>
    <script src="static/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
    <link href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/jquery.datetimepicker.css">
    <script type="text/javascript" src="static/layer/layer.js"></script>
    <link href="static/layer/theme/default/layer.css">

    <script>
        //图片上传预览    IE是用了滤镜。
        function previewImage(file, preview) {
            var MAXWIDTH = 51;
            var MAXHEIGHT = 34;
//           var div = document.getElementById('preview');
            var div = preview;
            if (file.files && file.files[0]) {
                $(div).prev().attr("link_address", file.files[0].name);
                console.log(file.files[0].name);
                $(div).empty();
                $(div).append('<img class="imghead" border="0" src="static/images/after.png" width="51" height="34" onclick="$(this).parent().next().click()">');
                var img = $(preview).children().eq(0);
                img.onload = function () {
                    var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
                    img.width = rect.width;
                    img.height = rect.height;
                    img.style.marginTop = rect.top + 'px';
                }
                var reader = new FileReader();
                reader.onload = function (evt) {
                    img.src = evt.target.result;
                }
                reader.readAsDataURL(file.files[0]);
            } else //兼容IE
            {
                var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
                file.select();
                var src = document.selection.createRange().text;
                $(div).empty();
                $(div).append('<img class=imghead border="0" src="static/images/after.png" width="51" height="34" onclick="$(this).parent().next().click()">');
                var img = $(preview).children().eq(0);
                img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
                var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
                status = ('rect:' + rect.top + ',' + rect.left + ',' + rect.width + ',' + rect.height);
                $(div).append("<div class=divhead style='width:" + rect.width + "px;height:" + rect.height + "px;margin-top:" + rect.top + "px;" + sFilter + src + "\"'></div>");
            }
        }

        function clacImgZoomParam(maxWidth, maxHeight, width, height) {
            var param = {top: 0, left: 0, width: width, height: height};
            if (width > maxWidth || height > maxHeight) {
                rateWidth = width / maxWidth;
                rateHeight = height / maxHeight;

                if (rateWidth > rateHeight) {
                    param.width = maxWidth;
                    param.height = Math.round(height / rateWidth);
                } else {
                    param.width = Math.round(width / rateHeight);
                    param.height = maxHeight;
                }
            }
            param.left = Math.round((maxWidth - param.width) / 2);
            param.top = Math.round((maxHeight - param.height) / 2);
            return param;
        }
    </script>

    <style type="text/css">
        .row {
            margin-right: -15px;
            margin-left: -15px;
            border: 1px solid silver;
            border-radius: 4px;
            padding: 25px 15px 15px;
            background-color: #FCFCFC;
            padding-top: 15px;
        }

        .btn-primary {
            color: #fff;
            background-color: #2795DC;
            border-color: #2e6da4;
        }

        .btn-primary:hover {
            color: #fff;
            background-color: #104E8B;
            border-color: #204d74;
        }

        .glyphicon {
            line-height: 1.42857143;
        }
    </style>
</head>

<body>
<div class="container">
    <div class="row" id="create_page">
        <div class="col-md-12">
            <form class="form-horizontal" action="/vote_war/vote?method=createVote" method="post" id="createVote">
                <div class="form-group">
                    <label class="col-sm-2 control-label">请输入标题</label>
                    <div class="col-sm-10">
                        <input name="name" type="text" class="form-control" placeholder="先有鸡还是先有蛋？" id="name">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">请描述标题</label>
                    <div class="col-sm-10">
                        <textarea name="description" class="form-control" rows="3" id="description"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">投票候选项</label>
                    <div class="col-sm-10" id="optionArea">
                        <div class="col-sm-12 one_option" style="margin: 0;padding: 0;">
                            <input type="text" class="form-control" style="display: inline;width: 80%" name="options">
                            <!-- 文件上传插件-->
                            <%--<div class="preview" style="display: inline;">
                                <img class="imghead" border="0" src="static/images/before.png" width="51" height="34" onclick="$(this).parent().next().click();">
                            </div>
                            <input type="file" onchange="previewImage(this,$(this).prev())" name="img" style="display: none;" class="previewImg">--%>
                            <!-- 文件上传插件-->

                            <button type="button" class="btn btn-danger btn_del" style="display: inline;width: 9%"><span
                                    class="glyphicon glyphicon-remove-circle"></span></button>
                            <br>
                        </div>
                        <div class="col-sm-12 one_option" style="margin: 0;padding: 0;">
                            <input type="text" class="form-control" style="display: inline;width: 80%" name="options">

                            <!-- 文件上传插件-->
                            <%--<div class="preview" style="display: inline;">
                                <img class="imghead" border="0" src="static/images/before.png" width="51" height="34" onclick="$(this).parent().next().click();">
                            </div>
                            <input type="file" onchange="previewImage(this,$(this).prev())" name="img" style="display: none;" class="previewImg">--%>
                            <!-- 文件上传插件-->

                            <button type="button" class="btn btn-danger btn_del" style="display: inline;width: 9%"><span
                                    class="glyphicon glyphicon-remove-circle"></span></button>
                            <br>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"></label>
                    <div class="col-sm-10">
                        <input type="button" id="add_option_btn" class="btn btn-success"
                               value="添加选项" style="margin-top: 10px"/>
                    </div>
                </div>
                <hr/>
                <div class="form-group">
                    <label class="col-sm-2 control-label">单选 | 多选</label>
                    <div class="col-sm-10">
                        <div class="radio" style="display: inline;"><label><input type="radio" value="1" name="type"
                                                                                  checked="checked"/>单选</label></div>
                        <div class="radio" style="display: inline;margin-left: 10px;"><label><input type="radio"
                                                                                                    value="2"
                                                                                                    name="type"/>多选</label>
                        </div>
                    </div>
                </div>
                <hr/>
                <div class="form-group">
                    <label class="col-sm-2 control-label">投票截止时间</label>
                    <div class="col-sm-10">
                        <input name="endtime" type="text" class="form-control" id="datetimepicker"/>
                    </div>
                </div>
                <div class="form-group" style="border: none;padding-bottom: 0px;">
                    <div style="margin-left: 620px;margin-top: 16px">
                        <input type="hidden" name="userId" value="${sessionScope.user.userId}">
                        <button id="create_btn" class="btn btn-primary" type="button" onclick="createVote()">创建投票
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    $('#datetimepicker').datetimepicker();
    $('#datetimepicker').datetimepicker({
        lang: 'ch', //设置中文
        format: "Y-m-d H:i:s" //格式化日期
    });

    $("body").on("click", "#add_option_btn", function () {
        var div = $("<div></div>").addClass("col-sm-12 one_option").attr("style", "margin: 0;padding: 0;");
        var option = $("<input>").addClass("form-control").attr("type", "text").attr("name", "options").attr("style", "display: inline;width: 80%");
        var del_btn = $("<button></button>").addClass("btn btn-danger btn_del").attr("type", "button").attr("style", "display: inline;width: 9%; margin-left:4px").append('<span class="glyphicon glyphicon-remove-circle"></span>');
        div.append(option).append(del_btn);
        div.appendTo("#optionArea");
    });

    $("#optionArea").on("click", ".btn_del", function () {
        $(this).parent().remove();
    });

    function createVote() {
        var datetimepicker = $("#datetimepicker").val();
        var name = $("#name").val();
        var description = $("#description").val();
        if(name == "" || description == ""){
            layer.msg("内容不能为空！");
        }else if (new Date(datetimepicker).getTime() - new Date().getTime() < 0) {
            layer.msg("截止时间不能小于当前时间");
        } else {
            $("#createVote").submit();
        }
    }
</script>
</body>

</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-body" id="info">
                        <div class="input-group">
                            <span>用户Id：</span>
                            <span>${voteInfo.userId}</span>
                        </div>
                        <br/>
                        <div class="input-group">
                            <span>用户名：</span>
                            <span>${voteInfo.userName}</span>
                        </div>
                        <br/>
                        <div class="input-group">
                            <span>性别：</span>
                            <span>${voteInfo.sex}</span>
                        </div>
                        <br/>
                        <div class="input-group">
                            <span>生日：</span>
                            <span>${voteInfo.birthday}</span>
                        </div>
                        <br>
                        <div class="input-group">
                            <span>手机号：</span>
                            <span>${voteInfo.phone}</span>
                        </div>
                        <br/>
                        <div class="input-group">
                            <span>邮箱：</span>
                            <span>${voteInfo.email}</span>
                        </div>
                        <BR>
                        <div class="input-group">
                            <span>个性签名：</span>
                            <span>${voteInfo.personality}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $.ajax({

    })
</script>
</body>
</html>

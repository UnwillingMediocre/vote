<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <title>Micro Vote</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="static/js/jquery-1.9.0.min.js"></script>
    <script type="text/javascript" src="static/layer/layer.js"></script>
    <script type="text/javascript" src="static/js/login.js"></script>
    <link href="static/css/login2.css" rel="stylesheet" type="text/css"/>
    <link href="static/layer/theme/default/layer.css">
    <link rel="shortcut icon" type="image/x-icon" href="static/images/favicon.ico">
</head>
<body>
<h1>Micro Vote</h1>

<div class="login" style="margin-top: 50px;">

    <div class="header">
        <div class="switch" id="switch">
            <a class="switch_btn_focus" id="switch_qlogin" href="javascript:void(0);" tabindex="7">快速登录</a>
            <a class="switch_btn" id="switch_login" href="javascript:void(0);" tabindex="8">快速注册</a>
            <div class="switch_bottom" id="switch_bottom" style="position: absolute; width: 64px; left: 0px;"></div>
        </div>
    </div>

    <div class="web_qr_login" id="web_qr_login" style="display: block;">

        <!--登录-->
        <div class="web_login" id="web_login">

            <div class="login-box">

                <div class="login_form">
                    <form action="#" name="loginform" accept-charset="utf-8" id="login_form" class="loginForm"
                          method="post">
                        <input type="hidden" name="did" value="0"/> <input type="hidden" name="to" value="log"/>
                        <div id="loginCue" class="cue" STYLE="display: none"></div>
                        <div class="uinArea" id="uinArea">
                            <label class="input-tips" for="u">帐&nbsp;&nbsp;&nbsp;&nbsp;号：</label>
                            <div class="inputOuter" id="uArea">
                                <input type="text" id="u" name="username" class="inputstyle" placeholder="输入用户名"/>
                            </div>
                        </div>
                        <div class="pwdArea" id="pwdArea">
                            <label class="input-tips" for="p">密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
                            <div class="inputOuter" id="pArea">
                                <input type="password" id="p" name="password" class="inputstyle"/>
                            </div>
                        </div>
                        <div class="pwdArea" id="codeArea" style="position: relative">
                            <label class="input-tips" for="c">验证码：</label>
                            <div class="inputOuter" id="code1" style="position: absolute;padding: 0 0 0 165px">
                                <input type="text" id="c" name="code" class="inputstyle" style="width: 100px"/>
                            </div>
                            <div style="position: absolute;padding: 10px 0 0 70px;" onclick="changeImg()">
                                <img src="/vote_war/user?method=getCode" style="border: 1px solid #ccc" width="80px"
                                     id="img"/>
                            </div>
                        </div>
                        <div style="padding-left: 50px; margin-top: 20px;">
                            <input type="button" value="登 录" style="width: 150px;" class="button_blue"
                                   onclick="login()"/>
                        </div>
                    </form>
                </div>

            </div>

        </div>
        <!--登录end-->
    </div>

    <!--注册-->
    <div class="qlogin" id="qlogin" style="display: none;">

        <div class="web_login">
            <form name="form2" id="regUser" accept-charset="utf-8" action="#" method="post">
                <input type="hidden" name="to" value="reg"/> <input type="hidden" name="did" value="0"/>
                <ul class="reg_form" id="reg-ul">
                    <li><label for="user" class="input-tips2">用户名：</label>
                        <div class="inputOuter2">
                            <input type="text" id="user" name="username" maxlength="16" class="inputstyle2" placeholder="数字字符(例:jsu123)"/>
                        </div>
                    </li>
                    <li><label for="phone" class="input-tips2">手机号：</label>
                        <div class="inputOuter2">
                            <input type="text" id="phone" name="phone" maxlength="16" class="inputstyle2"/>
                        </div>
                    </li>
                    <li><label for="email" class="input-tips2">邮箱：</label>
                        <div class="inputOuter2">
                            <input type="text" id="email" name="email" maxlength="20" class="inputstyle2" placeholder="xxxx@qq.com"/>
                        </div>
                    </li>
                    <li><label for="passwd" class="input-tips2">密码：</label>
                        <div class="inputOuter2">
                            <input type="password" id="passwd" name="passwd" maxlength="16" class="inputstyle2" placeholder="数字字符(例:jsu123)"/>
                        </div>
                    </li>
                    <li><label for="passwd2" class="input-tips2">确认密码：</label>
                        <div class="inputOuter2">
                            <input type="password" id="passwd2" name="password" maxlength="16" class="inputstyle2"/>
                        </div>
                    </li>
                    <li>
                        <div class="inputArea">
                            <input type="button" id="reg" style="margin-top: 10px; margin-left: 85px;width: 150px;"
                                   class="button_blue" value="注册" onclick="register()"/>
                        </div>
                    </li>
                </ul>
            </form>
        </div>

    </div>
    <!--注册end-->
</div>
</body>
</html>
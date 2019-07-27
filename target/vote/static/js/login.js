$(function () {

    $('#switch_qlogin').click(function () {
        $('#switch_login').removeClass("switch_btn_focus").addClass('switch_btn');
        $('#switch_qlogin').removeClass("switch_btn").addClass('switch_btn_focus');
        $('#switch_bottom').animate({left: '0px', width: '70px'});
        $('#qlogin').css('display', 'none');
        $('#web_qr_login').css('display', 'block');

    });
    $('#switch_login').click(function () {

        $('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
        $('#switch_qlogin').removeClass("switch_btn_focus").addClass('switch_btn');
        $('#switch_bottom').animate({left: '154px', width: '70px'});

        $('#qlogin').css('display', 'block');
        $('#web_qr_login').css('display', 'none');
    });
    if (getParam("a") == '0') {
        $('#switch_login').trigger('click');
    }

    $("#user").blur(function () {
        var username = $(this).val();
        $.ajax({
            url: "/vote_war/user?method=checkUsername&time=" + new Date().getTime(),
            type: "post",
            data: {
                username: username
            },
            success: function (result) {
                if (result === "" || result === null) {
                    $("#reg").attr('disabled', false);
                } else {
                    layer.msg(result);
                    $("#reg").attr('disabled', true);

                }
            }
        })
    })
});

function logintab() {
    scrollTo(0);
    $('#switch_qlogin').removeClass("switch_btn_focus").addClass('switch_btn');
    $('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
    $('#switch_bottom').animate({left: '154px', width: '96px'});
    $('#qlogin').css('display', 'none');
    $('#web_qr_login').css('display', 'block');

}


//根据参数名获得该参数 pname等于想要的参数名 
function getParam(pname) {
    var params = location.search.substr(1); // 获取参数 平且去掉？ 
    var ArrParam = params.split('&');
    if (ArrParam.length == 1) {
        //只有一个参数的情况 
        return params.split('=')[1];
    } else {
        //多个参数参数的情况
        for (var i = 0; i < ArrParam.length; i++) {
            if (ArrParam[i].split('=')[0] == pname) {
                return ArrParam[i].split('=')[1];
            }
        }
    }
}

function changeImg() {
    var date = new Date().getTime();
    $("#img").attr("src", "/vote_war/user?method=getCode&time=" + date);
}

function login() {
    var date = new Date().getTime();
    var username = $("#u").val();
    var password = $("#p").val();
    var code = $("#c").val();
    if (username.trim() === "") {
        layer.msg("用户名不能为空");
    } else if (password.trim() === "") {
        layer.msg("密码不能为空");
    } else if (code.trim() === "") {
        layer.msg("验证码不能为空");
    } else {
        $.ajax({
            url: "/vote_war/user?method=login&time=" + date,
            type: "post",
            data: {
                username: username,
                password: password,
                code: code
            },
            success: function (result) {
                console.log(result);
                if (result === "" || result === null) {
                    window.location.href = "index.jsp";
                } else if (result == '-1') {
                    window.location.href = "VoteIndex.jsp";
                } else {
                    layer.msg(result);
                    changeImg();
                }
            }
        })
    }
}

function register() {
    var date = new Date().getTime();
    var username = $("#user").val();
    var password = $("#passwd").val();
    var password2 = $("#passwd2").val();
    var phone = $("#phone").val();
    var email = $("#email").val();

    var emailReg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
    var phoneReg = /^1(3|4|5|6|7|8|9)\d{9}$/;
    var usernameReg = /^[\dA-Za-z_\-]{6,10}$/;
    var passwordReg = /^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)\S{6,20}$/;
    if (!username.match(usernameReg)) {
        layer.msg("用户名格式有限，谨慎输入。温馨提示：（格式：字符数字6-10位）");
    } else if (!phone.match(phoneReg)) {
        layer.msg("手机格式错误。温馨提示：（格式：数字11位）");
    } else if (!email.match(emailReg)) {
        layer.msg("邮箱格式错误。温馨提示：（例：xxxx@qq.com）");
    } else if (password !== password2) {
        layer.msg("两个密码不一致");
    } else if (!password.match(passwordReg) || !password2.match(passwordReg)) {
        layer.msg("密码格式有限，谨慎输入。温馨提示：（格式：字符数字6-10位）");
    } else {
        $.ajax({
            url: "/vote_war/user?method=register&time=" + date,
            type: "post",
            data: {
                username: username,
                password: password,
                phone: phone,
                email: email
            },
            success: function (result) {
                if (result === "" || result === null) {
                    window.location.reload();
                } else {
                    layer.msg(result);
                }
            }
        })
    }

}


$(function () {

    let num = 3;
    let tip = $(".tip");
    let flag = true;
    let pwd = $("#password");
    let cip = $(".cipher");
    $(".submit").click(function () {
        if ($("#username").val() === "" || pwd.val() === "" || cip.val() === "") {
            tip.html("请勿输入空字符");
            flag = false;
        } else if (control % 2 === 0){
            if (pwd.val() !== cip.val()) {
                tip.html("两次密码不一致，请重新输入")
                flag = false;
            } else {
                reg();
                return;
            }
        }
        if (num <= 0) {
            $(this).attr("disabled", "disabled");
            tip.html("请勿频繁点击")
        }
        if (flag) {
            login();
            return;
        }
        flag = true;
        tip.show();
        num--;
    })



    let control = 1;
    $(".reg_btn").click(function () {
        if (control % 2 != 0) { // 注册
            $(this).children().html("login");
            $(".submit").val("Register");
            $(".t2").html("Register in to your account");
            $(".t2").css("color","red");
            $(".cipher").attr("placeholder","password again");
            $(".cipher").attr("type","password");
        } else { // 登录
            $(this).children().html("register");
            $(".submit").val("Login");
            $(".t2").html("Login in to your account");
            $(".t2").css("color","black");
            $(".cipher").attr("placeholder","请输入管理员密钥");
            $(".cipher").attr("type","text");
        }
        $(".cipher").val("");
        $(".submit").removeAttr("disabled");
        tip.hide();
        num = 3;
        control ++;
    })

    // 登录
    function login(){
        console.log($("#adminForm").serialize());
        $.ajax({
            url: "/users",
            type: "GET",
            data: $("#adminForm").serialize(),
            dataType: "JSON",
            success: function (json) {
                if (json.state) {
                    console.log("登录成功");
                    location.href = "/web/admin/index.html";
                } else {
                    console.log("登录失败" + json.msg);
                }
                tip.html(json.msg)
            },
            error:function (xhr){
                console.log("登录时产生错误");

            }
        })
    }

    // 注册
    function reg() {
        console.log($("#adminForm").serialize());
        $.ajax({
            url: "/users",
            type: "POST",
            data: $("#adminForm").serialize(),
            dataType: "JSON",
            success: function (json) {
                if (json.state) {
                    console.log("注册成功");
                    location.href = "/web/admin/login.html";
                } else {
                    console.log("注册失败" + json.msg);
                }
                tip.html(json.msg)
            },
            error: function (xhr) {
                console.log("注册时产生错误");
            }
        })
    }
})
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>注册</title>


    <script src="${pageContext.request.contextPath }/js/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath }/js/md5.js" type="text/javascript"></script>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/css.css" rel="stylesheet">

    <script type="application/javascript">


        var clock = '';
        var nums = 0;
        var sendBtn;

        function getnums() {
            var LocalDelay = {};
            LocalDelay.nums = sessionStorage.getItem("nums");
            LocalDelay.time = sessionStorage.getItem("time");
            return LocalDelay;
        }

        function setnums(n) {
            sessionStorage.setItem("nums", n);
            sessionStorage.setItem("time", new Date().getTime());
        }

        <%--加载完成给登录按钮添加点击事件--%>
        $(function () {
            $("#registerBtn").click(function () {
                if (notNullValidate($("#username"))) {
                    alert("请输入用户名!")
                    return;
                }
                if (notNullValidate($("#password1"))) {
                    alert("请输入密码!")
                    return;
                }
                if (notNullValidate($("#password2"))) {
                    alert("请输入确认密码!")
                    return;
                }
                if (notNullValidate($("#validateCode"))) {
                    alert("请输入验证码!")
                    return;
                }
                submitToRegister();
            });

            var LocalDelay = getnums();
            var timeLine = parseInt((new Date().getTime() - LocalDelay.time) / 1000);

            if (LocalDelay.nums > timeLine) {
                nums = LocalDelay.nums - timeLine;
                sendBtn = $("#sendBtn");
                sendBtn.removeAttr("onclick");
                sendBtn.text(nums + "s");
                clock = setInterval(doLoop, 1000); //一秒执行一次
            }

        });

        function submitToRegister() {
            if ($("#password1").val() == $("#password2").val()) {
                $("#password_hidden").val(MD5($("#password1").val()));
                $("#registerForm").submit();
            } else {
                alert("两次密码不一致");
            }
        }

        function notNullValidate(s) {
            return (s.val() == "" || s.val() == null);
        }

        function getValidateCode() {

            var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
            if (!myreg.test($("#username").val())) {
                alert('请输入有效的手机号码！');
                return false;
            }

            sendBtn = $("#sendBtn");
            sendBtn.text("发送中...");
            sendBtn.removeAttr("onclick");

            var url = "${pageContext.request.contextPath}/userAction_getValidateCode.action";
            $.post(url, {username: $("#username").val()},
                    function (data) {
                        if (data.respCode == "00000") { // 请求成功
                            nums = 59;
                            sendBtn.text(nums + "s");
                            clock = setInterval(doLoop, 1000); //一秒执行一次
                            setnums(nums);
                        } else { // 请求失败
                            sendBtn.text("重新获取");
                            sendBtn.attr("onclick", "getValidateCode()");
                        }
                    }, 'json');
        }
        // 验证码倒计时
        function doLoop() {
            nums--;
            setnums(nums);
            if (nums > 0) {
                sendBtn.text(nums + 's');
            } else {
                clearInterval(clock); //清除js定时器
                sendBtn.attr("onclick", "getValidateCode()");
                sendBtn.text('获取验证码');
                nums = 59; //重置时间
            }
        }

    </script>

</head>
<body>
<div class="login">
    <div class="bg-l">
        <div class="login-con">
            <div class="head-lt">
                账户注册
                <button type="button" class="close head-tc" aria-label="Close" onclick="javascript:history.back(-1);">
                    ×
                </button>
            </div>
            <form id="registerForm" name="registerForm" method="post"
                  action="${pageContext.request.contextPath}/userAction_register.action">
                <div class="content-l">
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon label-1">&nbsp;</span>
                        <input id="username" name="username" type="text" class="form-control" placeholder="手机号"
                               aria-describedby="sizing-addon1">
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon label-2">&nbsp;</span>
                        <input id="password1" type="password" class="form-control" placeholder="密码"
                               aria-describedby="sizing-addon1">
                        <input id="password_hidden" name="password" type="hidden"/>
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon label-2">&nbsp;</span>
                        <input id="password2" type="password" class="form-control" placeholder="重复密码"
                               aria-describedby="sizing-addon1">
                    </div>
                    <div class="input-group input-group-lg  yanzheng">
                        <span class="input-group-addon pull-left label-3">&nbsp;</span>
                        <input id="validateCode" name="validateCode" type="text"
                               class="form-control pull-left yanzheng-t" placeholder="验证码"
                               aria-describedby="sizing-addon1">
                        <div class="pull-right text-center show-yz" style="cursor: pointer"><a id="sendBtn"
                                                                                               onclick="getValidateCode()">获取验证码</a>
                        </div>
                    </div>
                    <div class="clearfix select-a">
                        <div class="pull-left registered"><a href="login.jsp">登录账号</a></div>
                    </div>
                    <div class="login-btn"><a id="registerBtn">注&nbsp;&nbsp;&nbsp;&nbsp;册</a></div>
                    <div>
                        <font color="red">
                            <s:actionerror/>
                        </font>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
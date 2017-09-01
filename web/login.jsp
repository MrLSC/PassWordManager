<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>登录</title>

    <script src="${pageContext.request.contextPath }/js/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath }/js/md5.js" type="text/javascript"></script>
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/css.css" rel="stylesheet">

    <script type="text/javascript">
        <%--加载完成给登录按钮添加点击事件--%>
        $(function () {
            $("#loginBtn").click(function () {
                submitToLogin();
            });
        });
        function submitToLogin() {
            $("#password_hidden").val(MD5($("#password").val()))
            $("#loginform").submit()
        }
        //回车键监听
        function onEnterListener(e) {
            if (e.keyCode == 13) {
                submitToLogin();
                return false;
            }
        }
    </script>
</head>
<body>
<div class="login">
    <div class="bg-l">
        <div class="login-con">
            <div class="head-lt">
                账户登录
            </div>
            <div class="content-l">
                <form id="loginform" name="loginform" method="post" action="${pageContext.request.contextPath}/userAction_login.action">
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon label-1">&nbsp;</span>
                        <input id="username" name="username" type="text" class="form-control" placeholder="手机号"
                               aria-describedby="sizing-addon1">
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon label-2">&nbsp;</span>
                        <input id="password" type="password" class="form-control" placeholder="密码"
                               aria-describedby="sizing-addon1">
                        <input id="password_hidden" name="password" type="hidden">
                    </div>
                    <div class="input-group input-group-lg  yanzheng">
                        <span class="input-group-addon pull-left label-3">&nbsp;</span>
                        <input id="validateCode" name="validateCode" type="text"
                               class="form-control pull-left yanzheng-t" placeholder="验证码"
                               aria-describedby="sizing-addon1">
                        <div class="pull-right text-center show-yz">
                            <img id="vCode"
                                 src="${pageContext.request.contextPath}/validatecode.jsp"
                                 onclick="javascript:document.getElementById('loginform:vCode').src='${pageContext.request.contextPath}/validatecode.jsp?'+Math.random();"/>
                        </div>
                    </div>
                </form>
                <div class="clearfix select-a">
                    <div class="pull-left registered"><a href="register.jsp">免费注册</a></div>
                    <div class="pull-right forget"><a href="">忘记密码</a></div>
                </div>
                <div class="login-btn">
                    <a id="loginBtn">
                        <span id="login" style="margin-top:-36px;">登&nbsp;&nbsp;&nbsp;&nbsp;录</span>
                    </a>
                </div>
                <div>
                    <font color="red">
                        <s:actionerror/>
                    </font>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

</html>
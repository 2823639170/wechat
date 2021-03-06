<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>微信注册页面</title>

		<link type="text/css" rel="stylesheet" href="http://localhost:8080/wechat/static/css/style.css" >
		<script src="http://localhost:8080/wechat/static/js/jquery-1.7.2.js"></script>

		<script type="text/javascript">

			$(function () {

				<%--// 给验证码的图片，绑定单击事件--%>
				<%--$("#code_img").click(function () {--%>
					<%--// 在事件响应的function函数中有一个this对象。这个this对象，是当前正在响应事件的dom对象--%>
					<%--// src属性表示验证码img标签的 图片路径。它可读，可写--%>
					<%--// alert(this.src);--%>
					<%--this.src = "${basePath}kaptcha.jpg?d=" + new Date();--%>
				<%--});--%>

				// 给注册绑定单击事件
				$("#sub_btn").click(function () {
				// 	// 验证用户名：必须由字母，数字下划线组成，并且长度为5到12位
				// 	//1 获取用户名输入框里的内容
				// 	var usernameText = $("#username").val();
				// 	//2 创建正则表达式对象
				// 	var usernamePatt = /^\w{5,12}$/;
				// 	//3 使用test方法验证
				// 	if (!usernamePatt.test(usernameText)) {
				// 		//4 提示用户结果
				// 		$("span.errorMsg").text("用户名不合法！");
                //
				// 		return false;
				// 	}

                    // 邮箱验证：xxxxx@xxx.com
                    //1 获取邮箱里的内容
                    var emailText = $("#email").val();
                    //2 创建正则表达式对象
                    var emailPatt = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
                    //3 使用test方法验证是否合法
                    if (!emailPatt.test(emailText)) {
                        //4 提示用户
                        $("span.errorMsg").text("邮箱格式不合法！");

                        return false;
                    }



                    // 验证密码：必须由字母，数字下划线组成，并且长度为5到15位
					//1 获取用户名输入框里的内容
					var passwordText = $("#password").val();
					//2 创建正则表达式对象
					var passwordPatt = /^\w{5,15}$/;
					//3 使用test方法验证
					if (!passwordPatt.test(passwordText)) {
						//4 提示用户结果
						$("span.errorMsg").text("密码不合法！");

						return false;
					}

					// 验证确认密码：和密码相同
					//1 获取确认密码内容
					var repwdText = $("#repwd").val();
					//2 和密码相比较
					if (repwdText != passwordText) {
						//3 提示用户
						$("span.errorMsg").text("确认密码和密码不一致！");

						return false;
					}


					// 验证码：现在只需要验证用户已输入。
					var codeText = $("#code").val();

					//去掉验证码前后空格
					// alert("去空格前：["+codeText+"]")
					codeText = $.trim(codeText);
					// alert("去空格后：["+codeText+"]")

					if (codeText == null || codeText == "") {
						//4 提示用户
						$("span.errorMsg").text("验证码不能为空！");

						return false;
					}

					// 去掉错误信息
					$("span.errorMsg").text("");

				});

			});

		</script>
		<script>
            <c:if test="${message!=null}">
            alert("系统提示：${message}");
            </c:if>
		</script>

	<style type="text/css">
		.login_form{
			height:420px;
			margin-top: 25px;
		}

	</style>
	</head>
	<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo1.png" >
		</div>

			<div class="login_banner">

				<div id="l_content">
					<span class="login_word">欢迎注册</span>
				</div>

				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>注册微信号</h1>
								<span class="errorMsg">
									${ requestScope.msg }
								</span>
							</div>
							<div class="form">
								<form action="http://localhost:8080/wechat/user" method="post">
									<input type="hidden" name="method" value="register.do">
									<label>电子邮件：</label>
									<input class="itxt" type="text" placeholder="请输入邮箱地址"
										   value="${requestScope.email}"
										   autocomplete="off" tabindex="1" name="email" id="email" />
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="5-15位的数字、字母"
										   autocomplete="off" tabindex="1" name="password" id="password" />
									<br />
									<br />
									<label>确认密码：</label>
									<input class="itxt" type="password" placeholder="确认密码"
										   autocomplete="off" tabindex="1" name="repwd" id="repwd" />
									<br />
									<br />
									<label>验证码：</label>
									<input class="itxt" type="text" name="code" style="width: 80px;" id="code" />
									<img id="code_img" alt="" src="kaptcha.jpg" style="float: right; margin-right: 40px; width: 110px; height: 30px;">
									<br />
									<br />
									<input type="submit" value="注册" id="sub_btn" onclick="check()"/>
								</form>
							</div>

						</div>
					</div>
				</div>
			</div>

		<%--静态包含页脚内容--%>
		<%@include file="/pages/common/footer.jsp"%>


	</body>
</html>
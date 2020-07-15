<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	String email = request.getParameter("email");
if (email == null)
	email = "";
%>
<%
	String pass = request.getParameter("pass");
if (pass == null)
	pass = "";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>MyTaskMangaerApp</title>
<link rel="stylesheet" type="text/css" href="CSS/homepage.css" />
</head>
<body>

	<div class="grid-container">
		<div class="item-header">
			<%@ include file="header.html"%>
		</div>

		<div class="item-main">
			<section class="master">
				<div class="intro">
					<h2>Enter LogIn details</h2>
				</div>
				<div class="container-wrap register">


					<form method="post" action="LoginData.do">
						<div class="row">
							<div class="col-25">
								<label for="email">Enter Email ID </label>
							</div>
							<div class="col-75">
								<input type="text" name="email" />
							</div>
							<br />
						</div>
						<div class="row">
							<div class="col-25">
								<label for="pwd">Enter Password </label>
							</div>
							<div class="col-75">
								<input type="password" name="pass" />
							</div>
							<br /> <br />
						</div>
						<div class="row">
							<input class="btn" type="submit" value="Login"><br />
						</div>
						<br />
						<div style="color: red">${msg}</div>
					</form>

				</div>
				<div>
					<p>
						New to My Task Manager <b><a href="Register.do">click here</a></b> to register
					<p>
				</div>
			</section>
		</div>

		<div class="item-footer"><%@ include file="footer.html"%></div>
	</div>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>MyTaskMangaerApp</title>
<link rel="stylesheet" type="text/css" href="CSS/homepage.css" />
</head>
<body class="special">
	<div class="grid-container">

		<!--  Header -->
		<div class="item-header"><%@ include file="header.html"%></div>

		<!-- Main content goes here -->
		<div class="item-main">
			<section class="master">
				<div class="container-wrap divider">
				<div>
					<h1>Welcome to Home Page</h1><br/>
					<div class="buttons">
						<a class="btn" href="Register.do">Register</a>
						<a class="btn" href="Login.do">Login</a>
					</div>
					</div>
				</div>
			</section>
		</div>


		<!-- footer -->
		<div class="item-footer"><%@ include file="footer.html"%></div>
	</div>

</body>
</html>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="CSS/styleSheetCSS.css">
<title>My Task Manager</title>
</head>
<body>
	<div class="grid-container">
		<div class="item-header">
			<%@ include file="loggedInHeader.html"%>
		</div>
		<div class="item-menu"><%@ include file="menu.html"%></div>
		
		<div class="item-main">
			<section class="master">
			 <div class="intro">
					<h1>Welcome ${name} to My Task Manager App</h1></br></br>
				</div>
				<div class="container-wrap content">
					
					<br />
					<h2>
						todays date and time is
						<%=new Date()%></h2>
					<br />
					<br /> <!-- onchange="javascript:location.href = this.value;"-->
					<form action="setStorage.do"  method="get">
					Select the Storage medium
					<select name="storeAt">
					<option value="none" selected disabled hidden>select</option>
					<option value="online">online</option>
					<option value="offline">offline</option>
					</select>
					<input class="btn" type="submit" value="update">
					</form>
				</div>
				<div style="color: red;"><span>${msg}</span></div>

			</section>
		</div>

		<div class="item-footer"><%@ include file="footer.html"%></div>
	</div>

</body>
</html>
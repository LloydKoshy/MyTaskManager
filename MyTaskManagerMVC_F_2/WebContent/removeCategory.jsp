<%@page import="java.util.List"%>
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
	src="javaScript/ham.js" async></script>
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
					<h1>Select the Category</h1>
					</br>
					</br>
				</div>
				<div class="container-wrap content">
					<form action="removedCat.do" method="get">
						<label>Select Category of the task</label> 
						<select name='catName'>
							<%
								List<String> list = (List<String>) request.getAttribute("list");
							for (String s : list) {
								out.write("<option>" + s.replace(".todo", "") + "</option>");
							}
							%>
						</select> <input class="btn" type="submit">
					</form>
				</div>
				<div style="color: red;">
					<span>${msg}</span>
				</div>

			</section>
		</div>

		<div class="item-footer"><%@ include file="footer.html"%></div>
	</div>

</body>
</html>
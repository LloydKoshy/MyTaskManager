<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%
	String uname = request.getParameter("uname");
if (uname == null)
	uname = "";
%>
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
<%
	String rpass = request.getParameter("rpass");
if (rpass == null)
	rpass = "";
%>

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
			<h2>Create an Account</h2>
        </div>
				<div class="container-wrap register">
				
		        <form method="get" action="RegData.do">
		        <div class="row">
					<div class="col-25"><label for="name">Name  </label></div>
					<div class="col-75"><input type="text" name="uname" id="uname" value="<%=uname%>" /></div><br />
				</div>
				<div class="row">
					<div class="col-25"><label for="email">Email  </label></div>
					<div class="col-75"><input type="email" name="email" value="<%=email%>" /></div><br /> 
				</div>
				<div class="row">
					<div class="col-25"><label for="pwd">Password  </label></div>
					<div class="col-75"><input type="password" name="pass" value="<%=pass%>" /></div><br />
				</div>
				<div class="row">
					<div class="col-25"><label for="rpwd">Repeat-Password </label></div>
					<div class="col-75"><input type="password" name="rpass" value="<%=rpass%>" /><br/></div><br />
				</div>
				
				<div class="row">
					<input class="btn" type="submit" value="Register" /><br /><br />
				</div>
				
					<div style="color: green">${msg}</div>
			    </form>
			    </div>
			    <div>
					<p>
						Already registered <b><a href="Login.do">click here</a></b> to Log-In
					<p>
				</div>
			</section>
		</div>
		
		<div class="item-footer"><%@ include file="footer.html"%></div>
	</div>
</body>
</html>
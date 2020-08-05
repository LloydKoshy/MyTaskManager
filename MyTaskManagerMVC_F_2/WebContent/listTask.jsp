<%@page import="com.uttara.project.lloyd.TaskBean"%>
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
			<%@ include file="loggedInHeader.html"%><!--  to be modified -->
		</div>
		<div class="item-menu"><%@ include file="menu.html"%></div>

		<div class="item-main">
			<section class="master">
				<div class="intro">
					<h1>List of Tasks</h1>
					
				</div>
				<div class="container-wrap task-list">
					<div>
						<table border="1">

							<thead>
								<tr>
									<th>Sl_No</th>
									<th>Task_Name</th>
									<th>Description</th>
									<th>Tags</th>
									<th>Start_Date</th>
									<th>End_Date</th>
									<th>Priority</th>
									<th>Status</th>
								</tr>
							</thead>
							<tbody>
								<%
									int i = 1;
								List<TaskBean> list = (List<TaskBean>) request.getAttribute("list");

								for (TaskBean l : list) {
								%>

								<tr>
									<td><%=i++%></td>
									<td><%=l.getTaskName()%></td>
									<td><%=l.getDescription()%></td>
									<td><%=l.getTags()%></td>
									<td><%=l.getCreatedDate()%></td>
									<td><%=l.getPlannedEndDate()%></td>
									<td><%=l.getPriority()%></td>
									<td><%=l.getStatus()%></td>
								</tr>

								<%
									}
								%>

							</tbody>

						</table>
					</div>
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
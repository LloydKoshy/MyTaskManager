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
					<h1>Update Task</h1></br></br>
				</div>
				<div class="container-wrap content register">
					<form action="updateTaskInt.jsp"  method="get">
					<div class="row">
					<div class="col-50"><label>Task Name </label></div>
					<div class="col-50"><input type="text" name="taskName" value="${bean.getTaskName()}" }></div></br>
					</div>
					<div class="row">
					<div class="col-50"><label>Description </label></div>
					<div class="col-50"><textarea name="description" rows="4" cols="30" placeholder="Enter brief description">${bean.getDescription()}</textarea></div></br>
					</div>
					<div class="row">
					<div class="col-50"><label>Tags <h6>(comma separated)</h6> </label></div>
					<div class="col-50"><input type="text" name="tags" value="${bean.getTags()}" ></div></br>
					</div>
					<div class="row">
					<div class="col-50"><label>Start Date</label></div>
					<div class="col-50"><input type="date" name="createdDate" value="${bean.getCreatedDate()}" ></div></br>
					</div>
					<div class="row">
					<div class="col-50"><label>End Date</label></div>
					<div class="col-50"><input type="date" name="plannedEndDate" value="${bean.getPlannedEndDate()}" ></div></br>
					</div>
					<div class="row">
					<div class="col-50"><label>Priority</label></div>
					<div class="col-50"><Select name="priority" value="${bean.getPriority()}" >
					<option value="very high">very high</option>
					<option value="high">high</option>
					<option value="medium">medium</option>
					<option value="low">low</option>
					</Select></div></br>
					</div>
					<div class="row">
					<div class="col-50"><label>Status</label></div>
					<div class="col-50"><Select name="status" value="${bean.getStatus()}" >
					<option value="new">new</option>
					<option value="in-process">in-process</option>
					<option value="completed">completed</option>
					<option value="cancelled">cancelled</option>
					</Select></div></br></br>
					</div>
					<div class="row special">
					<input class="btn" type="submit">
					</div>
					</form>
				</div>
				<div style="color: red;"><span>${msg}</span></div>

			</section>
		</div>

		<div class="item-footer"><%@ include file="footer.html"%></div>
	</div>

</body>
</html>
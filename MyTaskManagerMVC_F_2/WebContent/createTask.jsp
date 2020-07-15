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
					<h1>Create Task</h1></br></br>
				</div>
				<div class="container-wrap content register">
					<form action="taskInt.jsp"  method="get">
					<div class="row">
					<div class="col-50"><label>Task Name </label></div>
					<div class="col-50"><input type="text" name="taskName"></div></br>
					</div>
					<div class="row">
					<div class="col-50"><label>Description </label></div>
					<div class="col-50"><textarea name="description" rows="4" cols="30" placeholder="Enter brief description" ></textarea></div></br>
					</div>
					<div class="row">
					<div class="col-50"><label>Tags <h6>(comma separated)</h6> </label></div>
					<div class="col-50"><input type="text" name="tags"></div></br>
					</div>
					<div class="row">
					<div class="col-50"><label>Start Date</label></div>
					<div class="col-50"><input type="date" name="createdDate"></div></br>
					</div>
					<div class="row">
					<div class="col-50"><label>End Date</label></div>
					<div class="col-50"><input type="date" name="plannedEndDate"></div></br>
					</div>
					<div class="row">
					<div class="col-50"><label>Priority</label></div>
					<div class="col-50"><Select name="priority">
					<option value="none" selected disabled hidden>select</option>
					<option value="very high">very high</option>
					<option value="high">high</option>
					<option value="medium">medium</option>
					<option value="low">low</option>
					</Select></div></br>
					</div>
					<div class="row">
					<div class="col-50"><label>Status</label></div>
					<div class="col-50"><Select name="status">
					<option value="none" selected disabled hidden>select</option>
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
<jsp:useBean id="taskBean" class="com.uttara.project.lloyd.TaskBean" scope="request">
<jsp:setProperty name="taskBean" property="*"/>
</jsp:useBean>
<jsp:forward page="createTask.do"></jsp:forward>>
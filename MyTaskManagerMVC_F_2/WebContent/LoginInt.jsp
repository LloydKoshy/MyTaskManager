<jsp:useBean id="login" class="com.uttara.project.lloyd.LoginBean" scope="request">
<jsp:setProperty name="login" property="*"/>
</jsp:useBean>
<jsp:forward page="LoggedIn.do"/>
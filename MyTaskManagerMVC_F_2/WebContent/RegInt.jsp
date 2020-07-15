<jsp:useBean id="regBean" class="com.uttara.project.lloyd.RegBean" scope="request" >
<jsp:setProperty name="regBean" property="*"/>
</jsp:useBean>
<jsp:forward page="Registration.do"></jsp:forward>
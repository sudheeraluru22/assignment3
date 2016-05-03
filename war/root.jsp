<%@page import="com.google.appengine.api.users.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- The HTML 4.01 Transitional DOCTYPE declaration-->
<!-- above set at the top of the file will set     -->
<!-- the browser's rendering engine into           -->
<!-- "Quirks Mode". Replacing this declaration     -->
<!-- with a "Standards Mode" doctype is supported, -->
<!-- but may lead to some differences in layout.   -->

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>DataBox</title>
</head>

<body>
<%User user= (User)request.getAttribute("user"); 

%>
	<c:choose>
		<c:when test="${user == null}">
		<p>
				Welcome! <a href="${login_url}">Sign in or register</a>
			</p>
			
		</c:when>
		<c:otherwise>
			<p>
				Welcome ${user.email} <br /> You can signout <a
					href="${logout_url}">here</a><br />
				<!-- add in a small form to allow the user to update the timezone with a number -->
			</p>
			<form action="/" method="post">
				<input type="" name="new_timezone" /> <input type="submit" />
			</form>
		</c:otherwise>
	</c:choose>
</body>
</html>
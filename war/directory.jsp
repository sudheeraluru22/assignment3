<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Directory</title>
</head>
<body>
<c:choose>
		<c:when test="${user == null}">
		<p>Hello I am here</p>
		</c:when>
		<c:otherwise>
			<p>
				Welcome <b> ${user.email} </b> <br /> 
				You can signout <a href="${logout_url}" style="color:red">Here</a><br />
				<!-- add in a small form to allow the user to update the timezone with a number -->
			</p>
			<p>----------------------------------------------------------------------------------------------</p>
			Current Directory Path : /<c:out value="${requestScope.currentDirectory.dirName}"></c:out><br><br>
			<form action="/" method="post">
				Please Enter Directory Name:
				<input type="text" name="directoryName" />
				<input type="hidden" name="currentDirectoryID" value="${requestScope.currentDirectory.id}" />
				<input type="submit" value="Create"/>
			</form>
			<p>----------------------------------------------------------------------------------------------</p>
			<br><br>
			<%-- Using JSTL forEach and out to loop a list and display items in table --%>
			 <c:if test="${directoriesList != null}"> 
				<table border="1" >
				<tbody>
				<tr><th>ID</th><th>Directory Name</th><th>Parent Directory ID</th></tr>
				<c:forEach items="${requestScope.directoriesList}" var="directory">
				<tr>
					<td><c:out value="${directory.dirID.id}"></c:out></td> 
				
				<td><a href="?id=${directory.id}&action=load"><c:out value="${directory.dirName}"></c:out></a></td>
				<td><c:out value="${directory.parentDirID}"></c:out></td>
				<td><a href="?id=${directory.id}&action=delete"><c:out value="Delete"></c:out></a></td>
				</tr>  
				</c:forEach>
				</tbody>
				</table>
				<br><br>
			 </c:if> 
		</c:otherwise>
	</c:choose>
</body>
</html>
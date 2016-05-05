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
			Current Directory Path :<a href="/root?id=&action=load"> /</a><c:out value="${requestScope.currentDirectory.dirName}"></c:out><br><br>
			<form action="/root" method="post">
				Please Enter Directory Name:
				<c:choose>
					<c:when test="${requestScope.action=='update'}">
					<input type="text" name="directoryName" value="${requestScope.currentDirectory.dirName}" />
						<input type="submit" value="Update" name="button"/>
					</c:when>
					<c:otherwise>
						<input type="text" name="directoryName" />
						<input type="submit" value="Create" name="button"/>
					</c:otherwise>
				</c:choose>
				
				
				<input type="hidden" name="currentDirectoryID" value="${requestScope.currentDirectory.id}" />
				
			</form>
			<br><br>
			<form method="post" action="/upload" enctype="multipart/form-data">
		
		
				Please select a file to upload : <input type="file" name="file" />
				<input type="submit" value="upload" />
				<input type="hidden" name="currentDirectoryID" value="${requestScope.currentDirectory.id}" />
			</form>
			<br><br>
			<b style="color:#8904B1"><i><c:out value="${requestScope.message}"></c:out></i></b>
			<p>----------------------------------------------------------------------------------------------</p>
			<br><br>
			<%-- Using JSTL forEach and out to loop a list and display items in table --%>
			 <c:if test="${directoriesList != null}"> 
				<table border="1" >
				<tbody>
				<tr><th>ID</th><th>Directory Name</th><th>Parent Directory ID</th><th>Delete</th><th>Update</th></tr>
				<c:forEach items="${requestScope.directoriesList}" var="directory">
				<tr>
					<td><c:out value="${directory.dirID.id}"></c:out></td> 
				
				<td><a href="/databox?id=${directory.id}&action=load"><c:out value="${directory.dirName}"></c:out></a></td>
				<td><c:out value="${directory.parentDirID}"></c:out></td>
				<td><a href="/root?id=${directory.id}&action=delete"><c:out value="Delete"></c:out></a></td>
				<td><a href="/root?id=${directory.id}&action=update"><c:out value="Update"></c:out></a></td>
				</tr>  
				</c:forEach>
				</tbody>
				</table>
				<br><br>
			 </c:if> 
			 
			 <c:if test="${filesList != null}"> 
				<table border="1" >
				<tbody>
				<tr><th>ID</th><th>File Name</th><th>Delete</th><th>Download</th></tr>
				<c:forEach items="${requestScope.filesList}" var="fileJdo">
				<tr>
					<td><c:out value="${fileJdo.fileID.id}"></c:out></td> 
				
				<td><c:out value="${fileJdo.fileName}"></c:out></td>
				<%-- <td><c:out value="${directory.parentDirID}"></c:out></td> --%>
				<td><a href="/upload?id=${fileJdo.id}&action=Delete"><c:out value="Delete"></c:out></a></td>
				<td><a href="/upload?id=${fileJdo.id}&action=Download"><c:out value="Download"></c:out></a></td>
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
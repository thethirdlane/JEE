<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LarkU</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/admin/addStudent" method="post" />
	<table border="1" width="200">
		<tr>
			<td>Name</td>
			<td><input type="text" name="name"/></td>
		</tr>
		<tr>
			<td><input type="submit" name="addStudent" value="Add Student" /></td>
		</tr>
	</table>
	</form>
</body>
</html>
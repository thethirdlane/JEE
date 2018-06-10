<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>LarkU Login</title>
<style>
html {
	width: 100%;
	height: 100%;
	background-color: #b0c4de;
}
</style>
</head>
<body>
	<h1>Please login</h1>
	<form method="post" action="j_security_check">
		<table>
			<tr>
				<td>User Name</td>
				<td><input type="text" name="j_username" /></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="j_password" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit"></td>
				<td><input type="reset" value="Reset"></td>
			</tr>
		</table>
	</form>

</body>
</html>

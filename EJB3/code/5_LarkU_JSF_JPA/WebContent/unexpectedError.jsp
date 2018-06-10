<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" href="css/larkU.css" type="text/css" />
</head>

<body>

	<h2>Whoops! The following bad and Unexpected Thing happend</h2>
	<h2>${ExceptionMessage}</h2>


	<br />
	<a href="${pageContext.request.contextPath}/registrar/index.xhtml">Home</a>
</body>
</html>

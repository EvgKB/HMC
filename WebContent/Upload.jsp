<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Upload file</title>
</head>
<body>
	Upload file<br>
	<form action="Upload.do" method="post" enctype="multipart/form-data">
		Description <input name="description" type="text"><br>
		File <input name="data" type="file"><br>
		<input type="submit"><br>
	</form>
</body>
</html>
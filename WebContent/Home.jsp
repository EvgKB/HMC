<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="formTags" uri="FormsTags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Media Catalog</title>
</head>
<body>
	<form action="Search.do" method="post" enctype="multipart/form-data">
		<input name="search" type="text">
		<input type="submit">
	</form>
	<a href ="Upload.html">Upload file</a><br>
	<formTags:MediaGallery />
</body>
</html>
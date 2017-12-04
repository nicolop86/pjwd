<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Using Context Param</title>
	</head>
		<body>
		settingOne: <%= application.getInitParameter("settingOne") %>,<br/>
		settingTwo: <%= application.getInitParameter("settingTwo") %>,<br/>
		DB Name: <%= config.getInitParameter("db.name") %>,<br/>
		DB Location: <%= config.getInitParameter("db.ip") %>
		</body>
</html>
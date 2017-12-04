<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String[] fruits = request.getParameterValues("fruit");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Chosen fruits!</title>
	</head>
	<body>
		<h2>Your Selections</h2>
		<%
		if(fruits == null){
		%>
			You did not select any fruits.<%
		}else{
		%><ul><%
			for(String fruit : fruits)
			{
				%><li><%= fruit %></li><%
			}
		%></ul><%
		}
		%>
	</body>
</html>
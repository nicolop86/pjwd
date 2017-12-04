<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%!
private final int five = 0;
protected String cowboy = "rodeo";
//The assignment below is not declarative and is a syntax error if uncommented
//cowboy = "test";
public long addFive(long number)
{
	return number + 5L;
}
public class MyInnerClass{
}
MyInnerClass instanceVariable = new MyInnerClass();
//WeirdClassWithinMethod is in method scope, so the declaration below is
// a syntax error if uncommented
//WeirdClassWithinMethod bad = new WeirdClassWithinMethod();
%>
<%
class WeirdClassWithinMethod{
}
WeirdClassWithinMethod weirdClass = new WeirdClassWithinMethod();
MyInnerClass innerClass = new MyInnerClass();
int seven;
seven = 7;
%>
<%= "Hello, World" %><br />
<%= addFive(12L) %>
<html>
	<head>
		<title>Hello World Application</title>
	</head>
	<body>
		Hello, World!
	</body>
</html>
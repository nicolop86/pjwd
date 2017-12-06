<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Product List</title>
	</head>
	<body>
		<h2>Product List</h2>
		<a href="<c:url value="/shop?action=viewCart" />">View Cart</a><br /><br />
		<%
		@SuppressWarnings("unchecked")
		Map<Integer, String> products =
		(Map<Integer, String>)request.getAttribute("products");
		for(int id : products.keySet()){
			%><%= products.get(id) %> <a href="<c:url value="/shop">
			<c:param name="action" value="addToCart" />
			<c:param name="productId" value="<%= Integer.toString(id) %>"/>
			</c:url>"> (+1)</a><br /><%
		}
		%>
	</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign-up</title>
</head>
<body>
<div style="text-align: center">
    <h1>Sign-up</h1>
    <p><% if (request.getAttribute("message") != null) { %>
       <%=request.getAttribute("message")%>
       <% } %> </p>
    <form action="signup" method="post">
        <table style="margin-left: auto;margin-right: auto">
            <tr>
                <td><label for="firstName">First Name:</label></td>
                <td><input type="text" id="firstName" name="firstName" maxlength="45" required></td>
            </tr>
            <tr>
                <td><label for="lastName">Last Name:</label></td>
                <td><input type="text" id="lastName" name="lastName" maxlength="45" required></td>
            </tr>
            <tr>
                <td><label for="username">Username:</label></td>
                <td><input type="text" id="username" name="username" maxlength="45" required></td>
            </tr>
            <tr>
                <td><label for="password">Password:</label></td>
                <td><input type="password" id="password" name="password" maxlength="45" required></td>
            </tr>
        </table>
        <br><br>
        <input type="submit" value="Sign Up">

    </form>
    <p><a href="${pageContext.request.contextPath}/index.jsp">Back to main page.</a></p>
</div>
</body>
</html>

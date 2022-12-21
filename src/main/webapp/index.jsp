<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>ICE Quimica SRL</title>
</head>
<body>
<div style="text-align: center">
    <h1>ICE Quimica SRL</h1>
        <h3>Please login below to access the application. </h3>
            <p><% if (request.getAttribute("message") != null) { %>
            <%= request.getAttribute("message") %>
            <% } %></p>
            <br>

    <form action="login" method="post" >
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
        <br><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <br><br>

        <input type="submit" value="Login">
    </form>
        <br>
        <p>New user? Sign-up <a href="signup">here.</a></p>
</div>
</body>
</html>
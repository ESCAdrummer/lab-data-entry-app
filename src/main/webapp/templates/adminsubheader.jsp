<!-- subheader to use under header when validating the user is an admin -->
<h3 class="centered">Hello, ${user.firstName}</h3>

<% if (request.getAttribute("admin") == null) { %>
<br>
<p class="centered">It looks like you don't have sufficient rights to access this section.
    If you believe this is an error, please contact the application administrator.</p>

<% } else { %>
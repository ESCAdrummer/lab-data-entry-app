<%@ page import="com.beans.testResults" %>
<%@ page import="java.util.ArrayList" %>
<%@include file="/templates/header.jsp"%>
<div class="centered">
    <h3>View Test History</h3>
        <h4>Please select one of the options below.</h4>
            <p><a href="view-tests?filter=date">View Test History by Date</a></p>
            <p><a href="view-tests?filter=user">View Test History by User</a></p>
            <p><a href="view-tests?filter=product">View Test History by Product</a></p>
            <br><br>

    <% if (request.getParameter("filter") != null ) { %>

        <!--display date selectors-->
        <% if (request.getParameter("filter").equals("date")) { %>

        <h4>Test history by date</h4>
        <br>

        <form action="view-tests" method="post">

            <label for="fromDate">From: </label>
            <input type="date" id="fromDate" name="fromDate">
            <br><br>

            <label for="toDate">To: </label>
            <input type="date" id="toDate" name="toDate">
            <br><br>

            <input type="submit" value="Submit Selection">

        </form>

        <% } %>

        <!--display product selectors-->
        <% if (request.getParameter("filter").equals("product")) { %>

        <h4>Test history by product</h4>
        <br>

        <form action="view-tests" method="post">
            <label for="productSelection">Choose a product:</label>
            <select name="productSelection" id="productSelection">
            <option value="none" selected></option>

            <% for (String productSelection : (ArrayList<String>)request.getAttribute("filterData")) { %>

                <option value="<%= productSelection%>"><%= productSelection%></option>

            <% } %>
            </select>

            <input type="submit" value="Submit Selection">

        </form>

        <% } %>

        <!--display user selectors-->
        <% if (request.getParameter("filter").equals("user")) { %>

        <h4>Test history by user</h4>
        <br>

        <form action="view-tests" method="post">

            <label for="userSelection">Choose a user:</label>
            <select name="userSelection" id="userSelection">
            <option value="none" selected></option>

            <% for (String userSelection : (ArrayList<String>)request.getAttribute("filterData")) { %>

                <option value="<%= userSelection%>"><%= userSelection%></option>

            <% } %>

            </select>

            <input type="submit" value="Submit Selection">

        </form>

        <% } %>

    <% } %>

    <!-- Display message before results if applicable -->
    <% if (request.getAttribute("fromDate")!=null && request.getAttribute("toDate")!=null) { %>
    <h4>Display by Date</h4>
    <p>${displayMessage}</p>
    <% } %>

    <% if (request.getAttribute("displayByProduct")!=null) { %>
    <h4>Display by Product</h4>
    <p>${displayMessage} ${displayByProduct}</p>
    <% } %>

    <% if (request.getAttribute("displayByUser")!=null) { %>
    <h4>Display by User</h4>
    <p>${displayMessage} ${displayByUser}</p>
    <% } %>

    <!-- Display results if available -->
    <% if (request.getAttribute("testResultsSet") != null) { %>

    <table>
        <tr>
            <th>Test date</th>
            <th>UserName</th>
            <th>Product</th>
            <th>Test</th>
            <th>Value</th>
            <th>Units</th>
        </tr>

        <% for (testResults testResultEntry : (ArrayList<testResults>) request.getAttribute("testResultsSet")) { %>

        <tr>
            <td><%=testResultEntry.getDate()%></td>
            <td><%=testResultEntry.getUsername()%></td>
            <td><%=testResultEntry.getProductName()%></td>
            <td><%=testResultEntry.getTestName()%></td>
            <td><%=testResultEntry.getValue()%></td>
            <td><%=testResultEntry.getUnits()%></td>

        </tr>

        <% } %>
    </table>

    <% } %>

</div>
<%@include file="/templates/footer.jsp"%>
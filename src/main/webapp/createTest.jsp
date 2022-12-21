<%@ page import="com.beans.products" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.beans.test" %>
<%@ include file="templates/header.jsp" %>
<div style="text-align: center">

  <h3>Hello, ${user.firstName}</h3>

  <p>Please select the product being tested.</p>

      <form action="new-test" method="post">

        <label for="selection">Choose a product:</label>
        <select name="selection" id="selection">
          <option value="0" selected></option>

          <% for (products product : (ArrayList<products>)request.getAttribute("productList")) { %>

              <option value="<%= product.getId()%>"><%= product.getName()%></option>

          <% } %>

        </select>

        <input type="submit" value="Select">

      </form>

<!--Displaying test set-->
  <% if (request.getAttribute("testList") != null) { %>

      <p>Set of tests for product: ${product.getName()}</p>
      <br>

  <table>
    <form action="submit-results" method="post">
    <tr>
      <th>Test name</th>
      <th>Unit of measure</th>
      <th>Value</th>
    </tr>

      <% for (test testEntry : (ArrayList<test>) request.getAttribute("testList")) { %>

    <tr>
      <td><%=testEntry.getName()%></td>
      <td><%=testEntry.getUnits()%></td>
      <td><input type="text" name="testId<%=testEntry.getId()%>result" maxlength="10" size="10" required></td>
    </tr>

    <% } %>

    <input type="submit" value="Submit Results">
    </form>

  </table>

<% } %>
</div>

<%@ include file="templates/footer.jsp" %>
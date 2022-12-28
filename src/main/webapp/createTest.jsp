<%@ page import="com.beans.products" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.beans.test" %>
<%@ include file="templates/header.jsp" %>

<div class="centered">

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

<!--Message if submitted successfully-->
    <% if (request.getAttribute("submissionMessage") != null) { %>
    <p><%=request.getAttribute("submissionMessage")%></p>
    <% } %>

<!--Displaying test set-->
  <% if (request.getAttribute("testList") != null) { %>

      <p>Set of tests for product: ${product.getName()}</p>
<br>

    <form action="submit-results" method="post">
      <table>
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
      </table>
      <br>
      <input type="text" name="productId" value="${product.getId()}" hidden="hidden">
      <input type="text" name="username" value="${user.username}" hidden="hidden">
      <input type="submit" value="Submit Results">
      <br>
    </form>



<% } %>
</div>

<%@ include file="templates/footer.jsp" %>
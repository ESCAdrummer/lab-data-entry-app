<!-- Header that contains the navigation bar -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" href="styles.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <title>ICE Quimica SRL</title>
</head>

<body>

  <div class="centered">
    <h1>ICE Quimica SRL</h1>
  </div>

  <div class="navbar">

    <a href="home">Home</a>

    <a href="new-test">New Test Result Entry</a>

    <div class="dropdown">

      <button class="dropbtn">View Test Results
        <i class="fa fa-caret-down"></i>
      </button>

      <div class="dropdown-content">
        <a href="view-tests?filter=date">View Test History by Date</a>
        <a href="view-tests?filter=users">View Test History by User</a>
      </div>

    </div>

    <div class="dropdown">

      <button class="dropbtn">Administration
        <i class="fa fa-caret-down"></i>
      </button>

      <div class="dropdown-content">
        <a href="products-admin">Products</a>
        <a href="users-admin">Users</a>
        <a href="tests-admin">Tests</a>
      </div>
    </div>

    <a href="logout">Logout</a>

  </div>
  <br>
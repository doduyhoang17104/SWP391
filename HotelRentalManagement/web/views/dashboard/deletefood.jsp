<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Food" %>
<%
    Food food = (Food) request.getAttribute("food");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Delete Food</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            padding: 30px;
        }
        .container {
            background-color: #fff;
            padding: 20px 30px;
            border-radius: 8px;
            max-width: 500px;
            margin: auto;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h2 {
            color: #c0392b;
        }
        p {
            margin: 10px 0;
            font-size: 16px;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-right: 10px;
        }
        .btn-delete {
            background-color: #e74c3c;
            color: white;
        }
        .btn-cancel {
            background-color: #3498db;
            color: white;
            text-decoration: none;
            padding: 10px 20px;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Confirm Delete</h2>
    <p>Are you sure you want to delete this food item?</p>
    <p><strong>Name:</strong> <%= food.getName() %></p>
    <p><strong>Description:</strong> <%= food.getDescription() %></p>
    <p><strong>Price:</strong> $<%= food.getPrice() %></p>
    <p><strong>Category:</strong> <%= food.getCategory() %></p>

    <form action="deletefood" method="get">
        <input type="hidden" name="id" value="<%= food.getId() %>" />
        <input type="submit" value="Confirm Delete" class="btn btn-delete" />
        <a href="managerfood" class="btn-cancel">Cancel</a>
    </form>
</div>
</body>
</html>

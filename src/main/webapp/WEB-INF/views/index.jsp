<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
	<title>Raspberry Pi - Web Controls - HOME</title>
	
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="description" content="" />
	<meta name="viewport" content="wid=device-wid, initial-scale=1">
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="shortcut icon" type="image/x-icon" href="/resources/images/favicon.ico"/>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <spring:url value="/resources/images" var="images" />
  </head>
  <body>
	<div class="jumbotron text-center">
		<img src="${images}/penguin.png" 
			wid="1000" 
			class="img-responsive"
			alt="Responsive image"
			onclick="$('html, body').animate({scrollTop: 600}, 2000);">
		<h1>Rashpberry Pi - Web Controls</h1>
		<p>IOT Project</p>
	</div>
	<hr />
	<div class="container">
		<h2>Controls</h2>
		<div class="panel panel-default">
			<div class="panel-heading">Light</div>
			<div class="panel-body">
				<div class="btn-group btn-group-toggle" data-toggle="buttons">
					<label class="btn btn-secondary"> <input type="radio" name="options" id="option1" autocomplete="off"	checked>ON</label>
					<label class="btn btn-secondary active"> <input type="radio" name="options" id="option2" autocomplete="off">OFF</label>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Message</div>
			<div class="panel-body">
				<input type="text" class="form-control" id="message">
				<br/>
				<a href="#" class="btn btn-primary btn-primary"><span class="glyphicon glyphicon-cloud-upload"></span> Send</a>
			</div>
		</div>
	</div>
  </body>
</html>

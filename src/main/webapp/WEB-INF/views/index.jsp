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
	<link rel="stylesheet" href="/resources/css/app.css">
	<link rel="shortcut icon" type="image/x-icon" href="/resources/images/favicon.ico"/>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="/resources/js/jquery-linenumbers.js"></script>
	<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="/resources/js/stomp.min.js"></script>
	<script src="/resources/js/sockjs.min.js"></script>
	<script src="/resources/js/app.js"></script>
	<spring:url value="/resources/images" var="images" />
  </head>
  <body onload="connect()">
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
					<label class="btn btn-secondary" onclick="sendLightOnCommand()"> <input type="radio" name="options" id="option1" autocomplete="off" checked>ON</label>
					<label class="btn btn-secondary active" onclick="sendLightOffCommand()"> <input type="radio" name="options" id="option2" autocomplete="off">OFF</label>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Message</div>
			<div class="panel-body">
				<div class="btn-group">
					<input id="messageText" type="text" class="form-control">
					<span id="searchclear" class="glyphicon glyphicon-remove-circle"></span>
				</div>
			</div>
			<div class="panel-body" id="button-panel">
				<button id="sendButton" type="button" class="btn btn-info" onclick="sendMessage();" disabled><span class="glyphicon glyphicon-send"></span> Send</button>
			</div>
			<div class="panel-body">
				<textarea class="form-control" rows="15" id="msg-console" readonly></textarea>
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
  </body>
</html>

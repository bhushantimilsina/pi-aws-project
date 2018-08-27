function connect() {
	var socket = new SockJS('/ws-endpoint');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/client-inbound-messages', function(
				messageOutput) {
			showMessageOutput(JSON.parse(messageOutput.body));
		});
	});
}

function showMessageOutput(messageOutput) {
	console.log(messageOutput);
	var message = messageOutput.timeStamp + "\t" + messageOutput.user + "\t" + messageOutput.message + "\n";
	$("textarea").append(message);
}

function sendMessage() {
	console.log("Send message to server ...")
	var user = "defaultUser";
	var text = document.getElementById('messageText').value;
	stompClient.send("/app/client-outbound-messages", {}, JSON.stringify({
		'user' : user,
		'text' : text
	}));
}

function sendLightOnCommand() {
	console.log("Send Light ON Command to server ...");
	var user = "defaultUser";
	stompClient.send("/app/client-outbound-command", {}, JSON.stringify({
		'device' : 'Light',
		'status' : 'ON',
		'user' : user
	}));
}

function sendLightOffCommand() {
	console.log("Send Light OFF Command to server ...");
	var user = "defaultUser";
	stompClient.send("/app/client-outbound-command", {}, JSON.stringify({
		'device' : 'Light',
		'status' : 'OFF',
		'user' : user
	}));
}

$(document).ready(function() {
    $('#sendButton').prop('disabled',true);
    $('#messageText').keyup(function(){
        $('#sendButton').prop('disabled', this.value == "" ? true : false);     
    });
    
    $('#searchclear').click(function() {
        $('#messageText').val('');
    });
});

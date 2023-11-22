'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var stompClient = null;
var username = null;
var email = null;

var colors = ['#2196F3', '#32c787', '#00BCD4', '#ff5652', '#ffc107', '#ff85af', '#FF9800', '#39bbb0'];

function showErrorMessage(message) {
    var errorMessage = document.getElementById('errorMessage');
    console.log('Error message element:', errorMessage);

    if (errorMessage) {
        errorMessage.innerText = message;
        errorMessage.style.fontWeight = 'bold';
        errorMessage.style.color = 'red';
    } else {
        console.error('Error: Could not find element with ID "errorMessage"');
    }
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    if (message.type === 'ERROR') {
        // Handle the error message
        console.log("error");
        showErrorMessage(message.content);
        disableChatActions();
        // You may also prevent further actions or display a specific message to the user
    } else {
        // Handle other message types as before
        var messageElement = document.createElement('li');
        if (message.type === 'JOIN') {
            messageElement.classList.add('event-message');
            message.content = message.sender + ' joined!';
        } else if (message.type === 'LEAVE') {
            messageElement.classList.add('event-message');
            message.content = message.sender + ' left!';
        } else {
            messageElement.classList.add('chat-message');
            var avatarElement = document.createElement('i');
            var avatarText = document.createTextNode(message.sender[0]);
            avatarElement.appendChild(avatarText);
            avatarElement.style['background-color'] = getAvatarColor(message.sender);
            messageElement.appendChild(avatarElement);
            var usernameElement = document.createElement('span');
            var usernameText = document.createTextNode(message.sender);
            usernameElement.appendChild(usernameText);
            messageElement.appendChild(usernameElement);
        }
        var textElement = document.createElement('p');
        var messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);
        messageElement.appendChild(textElement);
        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    }
}


function onConnected() {
    stompClient.subscribe('/topic/public', onMessageReceived);
    stompClient.send("/app/chat.addUser", {}, JSON.stringify({ sender: username, email: email, type: 'JOIN' }));
    connectingElement.classList.add('hidden');
    showMessage('Connected to the chat!');
}



function connect(event) {
    console.log('Connect function called');
    username = document.querySelector('#name').value.trim();
    email = document.querySelector('#email').value.trim();

    // Validate name and email
    if (!username || !email) {
        var validationMessage = document.getElementById('validationMessage');
        if (!validationMessage) {
            console.error('Validation message element not found');
        } else {
            console.log('Validation message:', validationMessage.innerText);
            validationMessage.innerText = 'Please enter both name and email.';
            validationMessage.style.fontWeight = 'bold';
            validationMessage.style.color = 'red';
        }
        event.preventDefault();  // Prevent the default form submission
        return;
    } else {
        document.getElementById('validationMessage').innerText = '';
    }

	checkEmailAndLeaveTime(email);
    event.preventDefault();
}


function checkEmailAndLeaveTime(email) {
    // Assuming you have an endpoint to check email and leave time in the backend
    // You may need to replace '/checkEmailAndLeaveTime' with the actual endpoint
    fetch('/checkEmailAndLeaveTime?email=' + encodeURIComponent(email))
        .then(response => response.json())
        .then(data => {
            if (data.exists && data.leaveTimeIsNull) {
            	console.log('Validation message:', validationMessage.innerText);
            validationMessage.innerText = 'User already is Chatting';
            validationMessage.style.fontWeight = 'bold';
            validationMessage.style.color = 'red';
            } else {
               	usernamePage.classList.add('hidden');
    			chatPage.classList.remove('hidden');

    			var socket = new SockJS('/ws');
    			stompClient = Stomp.over(socket);
    			stompClient.connect({}, onConnected, onError);

    			event.preventDefault();
            }
        })
        .catch(error => {
            console.error('Error checking email and leave time:', error);
        });
}



function showMessage(message) {
    var validationMessage = document.getElementById('validationMessage');
    validationMessage.innerText = message;
    validationMessage.style.fontWeight = "bold";
    validationMessage.style.color = 'red';
}

function onError(error) {
    showMessage('Could not connect to WebSocket server. Please refresh this page to try again!');
    connectingElement.style.color = 'red';
}

function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

window.addEventListener('beforeunload', function () {
    stompClient.send("/app/chat.leave", {}, JSON.stringify({ sender: username, type: 'LEAVE' }));
});

usernameForm.addEventListener('submit', connect, true);
messageForm.addEventListener('submit', sendMessage, true);

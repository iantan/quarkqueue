"use strict";

function init() {
    var request = new XMLHttpRequest();
    request.open('GET', '/queue', true);
    request.onload = function () {
        var resp = JSON.parse(request.responseText);
        document.getElementById("size").innerHTML = resp.size;
        var next = document.getElementById("next");
        if (next) {
            console.info(resp.peak);
            document.getElementById("next").innerHTML = resp.peak ? resp.peak.number - 1 : 0;
        }
    };

    request.send();
}

function init2(user) {
    console.log('init2 - ' + user);
    init();
    connect(user);
}

function add() {
    var request = new XMLHttpRequest();
    request.open('PUT', '/queue/add', true);
    request.onload = function () {
        var resp = JSON.parse(request.responseText);
        document.getElementById("customer-section").hidden = false;
        var number = document.getElementById("number");
        if (number) {
            document.getElementById("number").innerHTML = resp.number;
        }
          
        sendMessage();
        init();
    };

    request.send();
}

function poll() {
    var request = new XMLHttpRequest();
    request.open('POST', '/queue/poll', true);
    request.onload = function () {
        var resp = JSON.parse(request.responseText);
        document.getElementById("number").innerHTML = resp.number;

        init();
    };

    request.send();
}

var connected = false;
var socket;
function connect(user) {
    if (!connected) {
        var name = "board";
        socket = new WebSocket("ws://" + location.host + "/queue-socket/" + user);
        socket.onopen = function () {
            connected = true;
            console.log("Connected to the web socket");
//            $("#send").attr("disabled", false);
//            $("#connect").attr("disabled", true);
//            $("#name").attr("disabled", true);
//            $("#msg").focus();
        };
        socket.onmessage = function (m) {
            console.log("Got message: " + m.data);
            document.getElementById("size").innerHTML = JSON.parse(m.data).size;
//            $("#chat").append(m.data + "\n");
//            scrollToBottom();
        };
    }
}
;

function sendMessage() {
    if (connected) {
        socket.send("dummy");
    }
}
;

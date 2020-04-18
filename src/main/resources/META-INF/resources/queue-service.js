"use strict";

function onLoad(user) {
    var request = new XMLHttpRequest();
    request.open('GET', '/queue', true);
    request.onload = function () {
        var resp = JSON.parse(request.responseText);
        document.getElementById("lastestNumber").innerHTML = resp.peak ? resp.peak.number - 1 : 0;
        document.getElementById("qSize").innerHTML = resp.size;
    };
    request.send();
    connect(user);
}

function addToQueue() {
    var request = new XMLHttpRequest();
    request.open('PUT', '/queue/add', true);
    request.onload = function () {
        var resp = JSON.parse(request.responseText);
        document.getElementById("customer-section").hidden = false;
        document.getElementById("customer-number").innerHTML = resp.number;
        broadcast();
    };
    request.send();
}

function poll() {
    var request = new XMLHttpRequest();
    request.open('POST', '/queue/poll', true);
    request.onload = function () {
        var resp = JSON.parse(request.responseText);
        document.getElementById("customer-number").innerHTML = resp.number;
        broadcast();
    };
    request.send();
}

var socket;
var connected = false;
function connect(user) {
    if (!connected) {
        socket = new WebSocket("ws://" + location.host + "/queue-socket/" + user);
        socket.onopen = function () {
            connected = true;
            console.log("Connected to the web socket");
        };
        socket.onmessage = function (m) {
            var resp = JSON.parse(m.data);
            document.getElementById("lastestNumber").innerHTML = resp.lastestCustomerNumber;
            document.getElementById("qSize").innerHTML = resp.size;
        };
    }
}

function broadcast() {
    if (connected) {
        socket.send("refresh all");
    }
}
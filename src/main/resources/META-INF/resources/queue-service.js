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

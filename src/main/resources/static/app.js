var ws = null;

function connect() {
    ws = new WebSocket('ws://localhost:9000/chat');
    ws.onopen = function(e) {
        console.log(new Date().toJSON() + " Websocket Connected. ");
    };
    ws.onmessage = function(e) {
        var obj = JSON.parse(e.data);
        $("#chat").append("<tr><td>" + obj.name + "</td><td>" + obj.message + "</td></tr>");
        $("#date").html(obj.message);
        var scrollBottom = Math.max($('#mytable').height() - $('#mydiv').height() + 20, 0);
        $('#mydiv').scrollTop(scrollBottom);
    };
    ws.onclose = function(e) {};
    ws.onerror = function(err) {
        ws.close();
    };
}
connect();

function sendMessage() {
    if ($("#name").val() && $("#message").val()) {
        ws.send(JSON.stringify({
            'name': $("#name").val(),
            'message': $("#message").val()
        }));
        $("#message").val('');
    }
}
$(function() {
    $("form").on('submit', function(e) {
        e.preventDefault();
    });
    $("#send").click(function() {
        sendMessage();
    });
});
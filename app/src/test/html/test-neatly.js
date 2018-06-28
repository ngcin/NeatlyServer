var output = function(message) {
    var currentTime = "<span class='time'>" + moment().format('HH:mm:ss.SSS') + "</span>";
    var element = $("<div>" + currentTime + " " + message + "</div>");
    $('#console').append(element);
}

var clickNext = function() {
    nextAction();
}

var updateNext = function(buttonMsg, action) {
    nextAction = action;
    $('#nextBtn').html(buttonMsg);
}

var joinRoom = function() {
    //创建房间
    owner.emit("CREATE_NEATLY_ROOM", {}, function(room) {
        output("成功创建房间，房间号：" + room.roomId);
        clients.forEach(function(client) {
            client.emit("JOIN_NEATLY_ROOM", room.roomId, function(room) {
                if (room) {
                    output("加入房间，现在房间人数：" + room.playerCount);
                    updateNext("准备游戏", clickReady)
                }
            });
            client.on("ROOM_UPDATED", function(room) {
                $("#room").val(JSON.stringify(room, "&nbsp;"));
            });
        }, this);
    });
};

var clickReady = function() {

};

var nextAction = joinRoom;
var clients = [];
var owner = io("http://localhost:9092/wx");
clients.push(owner);
clients.push(io("http://localhost:9092", { forceNew: true }));
clients.push(io("http://localhost:9092", { forceNew: true }));
clients.push(io("http://localhost:9092", { forceNew: true }));
output("增加了4个玩家");
output("开始一系列动作");
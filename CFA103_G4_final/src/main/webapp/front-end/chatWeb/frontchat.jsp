<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<style>
#chatarea {
  overflow: hidden;
  background-color:#CCD5AE;
  position: fixed;
  display: none;
  bottom: 60px;
  right: 55px;
  z-index: 2;
  vertical-align: middle;
  text-align: center;
  width: 25%;
  height: 70%;
  border-radius: 10px;
  border: 1px solid black;
  
 }

#chat {
  position: fixed;
  bottom: 55px;
  right: 55px;
  width: 50px;
  height: 50px;
  line-height: 70px;
  vertical-align: middle;
  text-align: center;
  z-index: 1;
 
 }
 #chaticon{
 width: 100px;
 height: 100px;
 }
 #cancel{
 width: 70px;
 height: 10%;
 margin-left:80%;
 }
 
 .input-area{
 background-color:#CCD5AE;
 width: 100%;
 height: 15%;
 }
 
 
 #messagesArea{
 width: 100%;
 height: 75%;
 resize: none;
 box-sizing: border-box;
 background-color:white;
 overflow-y:scroll;
 
 }
 
 #message{  
/*  float:left; */
/*  margin-top:23px; */
/*  margin-left:10px; */
margin-top: 15px;
 }
 
 #sendMessage{
/*  float:right; */
/*  margin-top:23px; */
/*  margin-right:10px; */
 }
 
 #area{
  list-style: none;
  margin: 0;
  padding: 0;
}

#area li{
  display:inline-block;
  clear: both;
  padding: 20px;
  border-radius: 30px;
  margin-bottom: 2px;
  font-family: Helvetica, Arial, sans-serif;
  word-break:break-all;
  font-size:12px;
 
	
}

.friend{
  background: #F1DBB7;
  color: #696D59;
    
}

.me{
text-align: left;
  background: #99a083;
  color: #fff;
}

.div_mename{
 text-align: right;
}

.div_friendname{
 text-align: left;
}

.mename, .friendname {
    margin: 0px 0px;
    color:#696D59;
}
.friendname{
text-align: left;
}
.me:last-of-type {
  border-bottom-right-radius: 30px;
}
</style>


</head>
<body>
<div id="chatarea">
<img src="<%=request.getContextPath()%>/pic/icon/cancel.png" id="cancel" onclick="disconnect();">
<div id="row"></div>
 <div id="messagesArea" class="panel message-area" >
 <ul id="area"></ul>
 </div>
<div class="input-area">
  <input id="message" class="text-field" type="text"  onkeydown="if (event.keyCode == 13) sendMessage();"/> 
  <input type="submit" id="sendMessage" class="button" value="送出" onclick="sendMessage();" /> 
 </div>
</div>

<div id="chat" onclick="connect();">
<img src="<%=request.getContextPath()%>/pic/icon/chaticon.png" id="chaticon">
</div>
<script type="text/javascript">
   $(document).on("click", "#chat",function(){
    $("#chatarea").show("slow");
    $(this).hide("fast");
   });
  
   $(document).on("click", "#cancel",function(){
    $("#chat").show("fast");
    $("#chatarea").hide("slow");
   });
  </script>
  
  
  
  
</body>
<script>
 var MyPoint = "/FriendWS/${memberVO.mname}";
 var host = window.location.host;
 var path = window.location.pathname;
 var webCtx = path.substring(0, path.indexOf('/', 1));
 var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;


 var messagesArea = document.getElementById("messagesArea");
 var self = "${memberVO.mname}";
 var webSocket;

 function connect() {
  // create a websocket
  webSocket = new WebSocket(endPointURL);

  webSocket.onopen = function(event) {
   console.log("Connect Success!");
   
   var jsonObj = {
     "type" : "history",
     "sender" : self,
     "receiver" : "customer",
     "message" : ""
    };
   webSocket.send(JSON.stringify(jsonObj));
   
    
   
  };
  
  webSocket.onmessage = function(event) {
   var jsonObj = JSON.parse(event.data);
   if ("open" === jsonObj.type) {
    
    
   } else if ("history" === jsonObj.type) {
    messagesArea.innerHTML = '';
    var ul = document.createElement('ul');
    ul.id = "area";
    messagesArea.appendChild(ul);
    // 這行的jsonObj.message是從redis撈出跟好友的歷史訊息，再parse成JSON格式處理
    var messages = JSON.parse(jsonObj.message);
    for (var i = 0; i < messages.length; i++) {
     var historyData = JSON.parse(messages[i]);
     var showMsg = historyData.message;
     var li = document.createElement('li');
     var span = document.createElement('div');
     var div = document.createElement('div');
     var br = document.createElement('br');
     // 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
    
     
     historyData.sender === self ? span.textContent="" : span.textContent="管理員:";
     historyData.sender === self ? span.className += 'mename' : span.className += 'friendname';
     historyData.sender === self ? li.className += 'me' : li.className += 'friend';
     historyData.sender === self ? div.className += 'div_mename' : div.className += 'div_friendname';

     li.innerHTML = showMsg;
      
     div.appendChild(span); 
     div.appendChild(li);
     ul.appendChild(div);
     ul.appendChild(br);
    }
    messagesArea.scrollTop = messagesArea.scrollHeight;
   } else if ("chat" === jsonObj.type) {
    var li = document.createElement('li');
    var div = document.createElement('div');
    var span = document.createElement('div');
    jsonObj.sender === self ? span.textContent="" : span.textContent="管理員:";
    jsonObj.sender === self ? span.className += 'mename' : span.className += 'friendname';
    jsonObj.sender === self ? li.className += 'me' : li.className += 'friend';
    jsonObj.sender === self ? div.className += 'div_mename' : div.className += 'div_friendname';
    li.innerHTML = jsonObj.message;
    div.appendChild(span); 
    div.appendChild(li);
    document.getElementById("area").appendChild(div);
    messagesArea.scrollTop = messagesArea.scrollHeight;
   } else if ("close" === jsonObj.type) {
    
   }
   
  };

  webSocket.onclose = function(event) {
   console.log("Disconnected!");
  };
 }
 
 function sendMessage() {
  var inputMessage = document.getElementById("message");
  
  var message = inputMessage.value.trim();

  if (message === "") {
   alert("Input a message");
   inputMessage.focus();
  }  else {
   var jsonObj = {
    "type" : "chat",
    "sender" : self,
    "receiver" : "customer",
    "message" : message
   };
   webSocket.send(JSON.stringify(jsonObj));
   inputMessage.value = "";
   inputMessage.focus();
  }
 }
 
 
 // 註冊列表點擊事件並抓取好友名字以取得歷史訊息
 
 
 
 function disconnect() {
  webSocket.close();
 }
 
 
</script>
</html>
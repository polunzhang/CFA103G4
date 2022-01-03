<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<style>
#chat {
  position: fixed;
  bottom: 80px;
  right: 30px;
  width: 50px;
  height: 50px;
  line-height: 70px;
  vertical-align: middle;
  text-align: center;
  z-index: 5;
  
 }
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
  width: 35%;
  height: 75%;
  border-radius: 10px;
  border: 1px solid black;
  
 }
 #chaticon{
 width: 70px;
 height: 70px;
 }
 #cancel{
 width: 80px;
 height: 10%;
 margin-left:80%;
 }


 #statusOutput{
   color:#5F6351;
  height: 5%;
  margin-bottom:6px;
  
 }
 
 #custom{
 float:right;
 width: 30%;
 height: 70%;
 background-color:#99A083;
 }
 
 #messagesArea{
 width: 70%;
 height: 70%;
 background-color:white;
 overflow-y:scroll;
 
 }
 .input-area{
 height: 15%;
 width: 400px;
 height: 15%;
 background-color:#CCD5AE;
 }
 
 #message{
 
 margin-top:15px;

 }
 
 #sendMessage{


 }
 .column{
 background-color:white;
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
/*   float: right; */
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

.me:last-of-type {
  border-bottom-right-radius: 30px;
}
</style>


</head>
<body onload="connect();">
<div id="chatarea">
 <img src="<%=request.getContextPath()%>/pic/icon/cancel.png" id="cancel" >
 <div id="statusOutput" ></div>
 <div id="custom"><h5>顧客列表</h5></div>
 
 <div id="messagesArea" class="panel message-area" ></div>
 
<div class="input-area">
  <input id="message" class="text-field" type="text"  size="35"  onkeydown="if (event.keyCode == 13) sendMessage();" /> 
  <input type="submit" id="sendMessage" class="button" value="送出" onclick="sendMessage();" /> 
 </div>
</div>

<div id="chat" onclick="connect();" >
<img src="<%=request.getContextPath()%>/pic/icon/chaticon.png" id="chaticon">
</div>

<script type="text/javascript">
   $(document).on("click", "#chat",function(){
    $("#chatarea").show("slow");
    $(this).hide("fast");
    $("#chatarea").addClass("active");
   });
  
   $(document).on("click", "#cancel",function(){
    $("#chat").show("fast");
    $("#chatarea").hide("slow");
    $("#chatarea").removeClass("active");
   });
</script>
</body>

<script>
 var MyPoint = "/FriendWS/customer";
 var host = window.location.host;
 var path = window.location.pathname;
 var webCtx = path.substring(0, path.indexOf('/', 1));
 var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;

 var statusOutput = document.getElementById("statusOutput");
 var messagesArea = document.getElementById("messagesArea");
 var self = 'customer';
 var webSocket;

 function connect() {
  // create a websocket
  webSocket = new WebSocket(endPointURL);
  
  webSocket.onopen = function(event) {
   console.log("Connect Success!");
   document.getElementById('sendMessage').disabled = false;
   document.getElementById('connect').disabled = true;
   document.getElementById('disconnect').disabled = false;
  };

  webSocket.onmessage = function(event) {
   var jsonObj = JSON.parse(event.data);
   if ("open" === jsonObj.type) {
    refreshFriendList(jsonObj);
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
    
     
     historyData.sender === self ? span.textContent="" : span.textContent=statusOutput.textContent+":";
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
    var active = document.getElementsByClassName('active');
    console.log(active);
    if(!(jsonObj.sender === self) && active.length===0){
     alert("來自["+jsonObj.sender+"]的新訊息");
    }else{

    	 var li = document.createElement('li');
    	    var div = document.createElement('div');
    	    var span = document.createElement('div');
    	    jsonObj.sender === self ? span.textContent="" : span.textContent=statusOutput.textContent+":";
    	    jsonObj.sender === self ? span.className += 'mename' : span.className += 'friendname';
    	    jsonObj.sender === self ? li.className += 'me' : li.className += 'friend';
    	    jsonObj.sender === self ? div.className += 'div_mename' : div.className += 'div_friendname';
    	    li.innerHTML = jsonObj.message;
    	    div.appendChild(span); 
    	    div.appendChild(li);
    	    document.getElementById("area").appendChild(div);
    	    messagesArea.scrollTop = messagesArea.scrollHeight;
    	
    }
   
   } else if ("close" === jsonObj.type) {
    refreshFriendList(jsonObj);
   }
   
  };

  webSocket.onclose = function(event) {
   console.log("Disconnected!");
  };
 }
 
 function sendMessage() {
  var inputMessage = document.getElementById("message");
  var friend = statusOutput.textContent;
  var message = inputMessage.value.trim();

  if (message === "") {
   alert("Input a message");
   inputMessage.focus();
  } else if (friend === "") {
   alert("Choose a customer");
  } else {
   var jsonObj = {
    "type" : "chat",
    "sender" : self,
    "receiver" : friend,
    "message" : message
   };
   webSocket.send(JSON.stringify(jsonObj));
   inputMessage.value = "";
   inputMessage.focus();
  }
 }
 
 // 有好友上線或離線就更新列表
 function refreshFriendList(jsonObj) {
  var friends = jsonObj.users;
  var custom = document.getElementById("custom");
  custom.innerHTML = '';
  for (var i = 0; i < friends.length; i++) {
   if (friends[i] === self) { continue; }
   custom.innerHTML +='<div id=' + i + ' class="column" name="friendName" value=' + friends[i] + ' ><h2>' + friends[i] + '</h2></div>';
  }
  addListener();
 }
 // 註冊列表點擊事件並抓取好友名字以取得歷史訊息
 function addListener() {
  var container = document.getElementById("custom");
  container.addEventListener("click", function(e) {
   var friend = e.srcElement.textContent;
   updateFriendName(friend);
   var jsonObj = {
     "type" : "history",
     "sender" : self,
     "receiver" : friend,
     "message" : ""
    };
   webSocket.send(JSON.stringify(jsonObj));
  });
 }
 
 function disconnect() {
  webSocket.close();
  
 }
 function updateFriendName(name) {
  statusOutput.innerText = name;
 }
 
 $(document).on("click", "#cancel", function(event){
      $(".me").remove();
      $(".friend").remove();
      $(".friendname").remove();
      $("#statusOutput").text(" ");
 });
 
 
 
</script>
</html>
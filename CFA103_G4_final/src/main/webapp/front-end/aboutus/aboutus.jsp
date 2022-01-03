<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.news.model.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
 href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
 integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
 crossorigin="anonymous">
<title>最新資訊</title>

<style type="text/css">

#carouselExampleControls {
 width: 700px;
 height: 400px;
 margin: 0px auto;
}
.carousel-inner{
height: 100%;
width: 100%;
}
a{
height: 100%;
}
#main {
 width: 990px;
 height: auto;
 margin: 40px auto;
 
}

#introduce {
 width: 500px;
 float: left;
 margin-left:100px;
 
 
}

#map {
 width: 330px;
 height:300px;
}
p{

font-family:Aial ;   
font-size:18px;
font-weight: normal;
font-style: italic;
font-variant:small-caps;
letter-spacing: 2px;
}


</style>
</head>
<body>

<%@ include file="/front-end/front-end_background.jsp"%>
<main>
 <div id="carouselExampleControls" class="carousel slide"
  data-ride="carousel">
  <div class="carousel-inner">
   <div class="carousel-item active">
    <img
     src="<%=request.getContextPath()%>/pic/restaurant/restaurant1.jpg"
     class="d-block w-100" alt="...">
   </div>
   <div class="carousel-item">
    <img
     src="<%=request.getContextPath()%>/pic/restaurant/restaurant2.jfif"
     class="d-block w-100" alt="...">
   </div>
   <div class="carousel-item">
    <img src="<%=request.getContextPath()%>/pic/restaurant/restaurant3.jpg" class="d-block w-100" alt="...">
   </div>
  </div>
  <a class="carousel-control-prev" href="#carouselExampleControls"
   role="button" data-slide="prev"> <span
   class="carousel-control-prev-icon" aria-hidden="true"></span> <span
   class="sr-only">Previous</span>
  </a> <a class="carousel-control-next" href="#carouselExampleControls"
   role="button" data-slide="next"> <span
   class="carousel-control-next-icon" aria-hidden="true"></span> <span
   class="sr-only">Next</span>
  </a>
 </div>
 
 <div id="main">
  <div id="introduce">
  <h3>關於我們</h3>
   <p>
    民以食為天 但現今社會不單只吃飽就好<br> 琳瑯滿目的美食更是讓人難以做選擇<br> 所以我們希望設計精簡的網站<br>
    讓客人在瀏覽網站時更加輕鬆<br> 在訂位、點餐或是訂購需要的商品能輕易地操作<br> 而在後端使用者的部分<br>
    主要是以一般中小型沒有連鎖的餐廳去做客製化系統<br> 業者可以不需要再去做繁瑣的操作<br> 也能達到理想的需求<br>
    讓自己開的餐廳Business Is Booming 生意興隆!<br>
   </p>

 </div>

<div id="map"></div>
</div>
 <script
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVaq3BT1_r9Uuhgmg8gma0_Elj7M3_u5I&callback=initMap&v=weekly&channel=2"
      async
    ></script>
    
     <script>
    var geocoder ;
    let map;
 let markers = [];
 let position = [
  {label:'Business Is Booming',lat:24.95762764577764,lng:121.2250277661316},
 ];
 
    function initMap() {
      geocoder = new google.maps.Geocoder();
      map = new google.maps.Map(document.getElementById("map"), {
       center: { lat: 24.95762764577764, lng: 121.2250277661316 },
        zoom: 19,     
      });   
      
 for (var i = 0; i < position.length; i++) {
     addMarker(i);
    }
   }
    function addMarker(e) {
       markers[e] = new google.maps.Marker({
        position: {
        lat: position[e].lat,
        lng: position[e].lng
      },
    map: map,
    icon:'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png',
    animation: google.maps.Animation.BOUNCE,
     label: position[e].label
  });   
  }
    </script>
    

 <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
  integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
  crossorigin="anonymous"></script>
 <script
  src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
  integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
  crossorigin="anonymous"></script>
 <script
  src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
  integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
  crossorigin="anonymous"></script>
  
</main>
  <footer>
<%@ include file="/front-end/front-end_footer.jsp"%>
</footer>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit car -> trouble code form</title>

 <script
        src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&key=AIzaSyDxS8WSvbLkzJPrH2TVqfVspGs4QgSLWy8">
    </script>
<script>
    var map;
    function initialize() {
      var mapOptions = {
        zoom: 1,
        center: new google.maps.LatLng(48.295637, 26.6949621)
      };

      // Display a map on the page
      map = new google.maps.Map(document.getElementById('map-canvas'),mapOptions);

      // Multiple Markers
      var markers = [
            ['location 1', ${carTroubleCode.lat}, ${carTroubleCode.lon}],
            //['location 2', 46.78362043165866, 23.708488669011658]
      ];

      	var bounds = new google.maps.LatLngBounds();
      
      // Loop through our array of markers & place each one on the map  
        for( i = 0; i < markers.length; i++ ) {
            var position = new google.maps.LatLng(markers[i][1], markers[i][2]);
            bounds.extend(position);
            marker = new google.maps.Marker({
                position: position,
                map: map,
                title: markers[i][0]
            });

            // Automatically center the map fitting all markers on the screen
            map.fitBounds(bounds);
        }

     // Override our map zoom level once our fitBounds function runs (Make sure it only runs once)
        var boundsListener = google.maps.event.addListener((map), 'bounds_changed', function(event) {
        	this.setZoom(14)
            google.maps.event.removeListener(boundsListener);
        });
    }

    google.maps.event.addDomListener(window, 'load', initialize);
</script>

<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
	<div class="generic-container">
		<%@include file="authheader.jsp"%>
		<div class="well lead">Edit car trouble code</div>
		<form:form method="POST" modelAttribute="carTroubleCode" class="form-horizontal">
			<form:input type="hidden" path="id" id="id" />
			<form:input type="hidden" path="troubleCodeId" id="troubleCodeId" />
			<form:input type="hidden" path="carId" id="carId" />
			<form:input type="hidden" path="lat" id="lat" />
			<form:input type="hidden" path="lon" id="lon" />
			

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="job">job</label>
					<div class="col-md-7">
						<form:input type="text" path="job" id="job"
							class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="job" class="help-inline" />
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="job">coordinates</label>
					<div class="col-md-7">
						<div id="map-canvas" style="height:500px; width:500px"></div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-actions floatRight">
					<input type="submit" value="Update" class="btn btn-primary btn-sm" />
					or <a href="<c:url value='/list' />" class="btn btn-default btn-sm">Cancel</a>
				</div>
			</div>
			
			
		</form:form>
	</div>
</body>
</html>

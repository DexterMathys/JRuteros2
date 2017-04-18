var map;
var myURI = "/JRuteros/rest/route/37";
var flightPath;

var puntos = [];

// Evento
//google.maps.event.addDomListener(window, 'load', initialize);

/**
 * Inicializa el mapa
 */
function initMap() {
	var mapProp = {
			center : new google.maps.LatLng(-34.9038055, -57.9392111, 18),
			zoom : 10,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
	if (document.getElementById("map") != null){
		map = new google.maps.Map(document.getElementById("map"), mapProp);

		map.addListener('click', function(e) {
			agregarMarker(e.latLng, map);

		});
		puntos = [];
		initPolyline();
		obtenerMarkers();
		
	}
	
	//loadKmlLayer(src, map);
	
}

function initPolyline(){
	flightPath = new google.maps.Polyline({
		path : [],
		strokeColor : "#0000FF",
		strokeOpacity : 0.8,
		strokeWeight : 2,
		map: map
	});
} 

// Obtiene markers y los dibuja
function obtenerMarkers() {
	var points = $("#points").val().split(",");
	if (points != "") {
		$.each(points, function( index, apoint ) {
			p = apoint.split(" ");
			var punto = {
				lat : p[0],
				lon : p[1]
			};
			dibujarMarker(punto);
			if (index == 0) {
				map.setCenter(new google.maps.LatLng(p[0],  p[1]));
			}
		})
		
		dibujarRecorrido();
		//dibujarRecorridoCircular()
	}
	/*$.ajax({
		type: 'GET',
        url: myURI,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
		success : function(result) {
			puntos = [];
			$.each(result, function(i, dato) {
				dibujarMarker(dato);
			});
			dibujarRecorrido();
		}
	});*/
}

function dibujarMarker(dato) {

	var position = new google.maps.LatLng(dato.lat, dato.lon);

	var marker = new google.maps.Marker({
		position : position,
		icon: {
		      path: google.maps.SymbolPath.CIRCLE,
		      scale: 3
		    },
		id : dato.id
	});

	marker.addListener("rightclick", function(point) {
		//borra de la bd
		//borrarMarker(dato.id);
		//borrar de puntos
		puntos.splice(puntos.indexOf(marker), 1);
		marker.setMap(null);
		//dibujar de nuevo
		dibujarRecorrido();
		//quitar del input
		var removePoint = marker.getPosition().lat() + " " + marker.getPosition().lng();
		/*if ($('#points').val().split(",").length > 1 ) {
			removePoint = "," + removePoint;
		}*/
		points = $('#points').val().split(",");
		$.each(points, function( index, apoint ) {
			console.log(apoint);
			if (apoint == removePoint){
				points[index] = null;
			}
		})
		console.log(points);
		//var points = $('#points').val().replace(removePoint, '');
		points = jQuery.grep(points, function(n, i){
			  return (n !== "" && n != null);
			});
		console.log(points);
		$('#points').val(points.join(","));
		
	});

	puntos[puntos.length] = marker;
	marker.setMap(map);
}

function agregarMarker(latLng) {

	var punto = {
		lat : latLng.lat(),
		lon : latLng.lng()
	};
	dibujarMarker(punto);
	dibujarRecorrido();
	//$('<input>').attr({type: 'hidden', name: 'points[]', value: [punto.lat , punto.lon] }).appendTo('#route_points');
	points = $('#points').val();
	newPoint = punto.lat + " " + punto.lon;
	if (points != ''){
		$('#points').val(points +  "," + newPoint);
	}else {
		$('#points').val(newPoint);
	}
	

/*	$.ajax({
		data : punto,
		url : myURI,
		type : "POST",
		success : function(result) {
			obtenerMarkers();

		}
	});*/

}

function dibujarRecorrido() {
	drawPuntos = [];
	for (i = 0; i < puntos.length; i++) { 
	    drawPuntos[i] =  {lat: puntos[i].position.lat(), lng: puntos[i].position.lng()};
	}
	if ($("#is_circular").val() == "true"){
		drawPuntos[puntos.length] =  {lat: puntos[0].position.lat(), lng: puntos[0].position.lng()};
	}
	flightPath.setPath(drawPuntos);
}

function dibujarRecorridoCircular() {

	markers = puntos;
	markers[markers.length] = puntos[0];

	var flightPath = new google.maps.Polyline({
		path : markers,
		strokeColor : "#0000FF",
		strokeOpacity : 0.8,
		strokeWeight : 2
	});

	flightPath.setMap(map);
	
}


function limpiarMapa() {

	punto = {
		id : null
	};
	$.ajax({
		data : punto,
		url : myURI,
		type : "DELETE",
		success : function(result) {
			initMap();
		}
	});

}

function borrarMarker(id) {
	punto = {
		id : id
	};
	$.ajax({
		data : punto,
		url : myURI ,
		type : "DELETE",
		success : function(result) {
			initMap();
		}
	});
}

function borrarTodosMarkers() {
	
	$.ajax({
		data : punto,
		url : myURI ,
		type : "DELETE",
		success : function(result) {
			initMap();
		}
	});
}
function limpiarRecorrido(){
	flightPath.setMap(null);
	initPolyline();
	puntos = [];
}

//Sets the map on all markers in the array
function setMapOnAll(map) {
	for (var i = 0; i < puntos.length; i++) {
	    puntos[i].setMap(map);
	  }
	}

//Removes the markers from the map, but keeps them in the array.
function clearMarkers() {
	setMapOnAll(null);
}

// Deletes all markers in the array by removing references to them.
function deleteMarkers() {
	clearMarkers();
	limpiarRecorrido();
	$('#points').val("");
}


function loadKmlLayer(src, map) {
   /* var kmlLayer = new google.maps.KmlLayer(src, {
      suppressInfoWindows: true,
      preserveViewport: false,
      map: map
    });*/
	var ctaLayer = new google.maps.KmlLayer({
	    url: src,
	    map: map
	  });
  }


function initShow() {
	
	
	var mapProp = {
			center : new google.maps.LatLng(-34.9038055, -57.9392111, 18),
			zoom : 10,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
	if (document.getElementById("show_map") != null){
		map = new google.maps.Map(document.getElementById("show_map"), mapProp);
		puntos = [];
		initPolyline();
		obtenerMarkers();	
	}
}

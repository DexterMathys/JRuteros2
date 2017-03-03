/**
 * 
 */
initialize_select2_multiple = function(object) {
	$("#select2-routes").select2({
    width: "100%",
    multiple: true,
    allowClear: true,
    placeholder: "Seleccione las rutas"
   /** ajax: {
      theme: "bootstrap",
      url: url,
      dataType: 'json',
      delay: 250,
      data: function(params) {
        return {
          q: params.term,
          page: params.page
        };
      },
      processResults: function(data, page) {
        return {
          results: data
        };
      },
      cache: true
    },
    escapeMarkup: function(markup) {
      return markup;
    },
    minimumInputLength: 2*/
  });
};

initialize_datepicker = function(object) {
	$("#activity-datepicker").datepicker("option", "dateFormat","dd/mm/yyyy");
	$(".route-datepicker").datepicker("option", "dateFormat","dd/mm/yyyy");
};

//Grafico

initialize_grafico = function(object) {

	var data = {
		    labels: ["January", "February", "March", "April", "May", "June", "July"],
		    datasets: [
		        {
		            label: "My First dataset",
		            fillColor: "rgba(220,220,220,0.2)",
		            strokeColor: "rgba(220,220,220,1)",
		            pointColor: "rgba(220,220,220,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(220,220,220,1)",
		            data: [65, 59, 80, 81, 56, 55, 40]
		        },
		        {
		            label: "My Second dataset",
		            fillColor: "rgba(151,187,205,0.2)",
		            strokeColor: "rgba(151,187,205,1)",
		            pointColor: "rgba(151,187,205,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(151,187,205,1)",
		            data: [28, 48, 40, 19, 86, 27, 90]
		        }
		    ]
		};

	var ctx = document.getElementById("myChart");
	if (ctx != null){
		ctx = document.getElementById("myChart").getContext("2d");
		window.myBar = new Chart(ctx).Bar(data, {
			responsive : true
		});
	}
	
};

initialize_dataTables = function(object){
	var lenguageOptions = {
			"language": {
	            "sProcessing":     "Procesando...",
	            "sLengthMenu":     "Mostrar _MENU_ registros",
	            "sZeroRecords":    "No se encontraron resultados",
	            "sEmptyTable":     "Ningún dato disponible en esta tabla",
	            "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
	            "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
	            "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
	            "sInfoPostFix":    "",
	            "sSearch":         "Buscar:",
	            "sUrl":            "",
	            "sInfoThousands":  ",",
	            "sLoadingRecords": "Cargando...",
	            "oPaginate": {
	                "sFirst":    "Primero",
	                "sLast":     "Último",
	                "sNext":     "Siguiente",
	                "sPrevious": "Anterior"
	            },
	            "oAria": {
	                "sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
	                "sSortDescending": ": Activar para ordenar la columna de manera descendente"
	            }
        	}
		};
        $('#tableroutes').DataTable(lenguageOptions);
        $('#tableusers').DataTable(lenguageOptions);
        $('#tablesearch').DataTable(lenguageOptions);
        $('#datatable').DataTable(lenguageOptions);
};

$( document ).ready(function() {
	  initialize_datepicker();
	  initialize_select2_multiple();
	  initialize_dataTables();
	  initialize_grafico();
	  $(".select2").select2();
	  
});

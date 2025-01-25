var datos = JSON.parse(jsonInfo);

$(function() {
    // Solo activar el autocompletado si el select tiene la opción de 'title'
    $("#tags").on("change", function() {
        var selectedValue = $(this).val();
        console.log(selectedValue)
        if (selectedValue === "title" || selectedValue === "genre" || selectedValue === "character") {
            $("#keyword").autocomplete({
                source: function(request, response) {
                    var resultados = $.grep(datos, function(item) {
                        return item.toLowerCase().startsWith(request.term.toLowerCase());
                    });
                    response(resultados);
                }
            });
            $("#keyword").show();  // Mostrar el campo de búsqueda
        } else {
            $("#keyword").hide();  // Ocultar el campo si no es necesario
        }
    });
});

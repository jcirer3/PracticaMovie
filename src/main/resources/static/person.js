var datos = JSON.parse(jsonInfo);

$(function() {
    $("#tags").autocomplete({
        source: function(request, response) {
            var resultados = $.grep(datos, function(item) {
                return item.toLowerCase().startsWith(request.term.toLowerCase());
            });
            response(resultados);
        }
    });
});

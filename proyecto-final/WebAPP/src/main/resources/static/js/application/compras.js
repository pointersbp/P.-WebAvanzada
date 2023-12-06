var localizacion;
var tableBody;
var sample;

// Cambiar cuando integres al gateway el webapp.
$(document).ready(function() {
    if (document.location.hostname == "localhost")
        localizacion = document.location.protocol + "//" + document.location.hostname + ":8080";
    else {
        localizacion = "https://znzn00.ddns.net";
    }
    tableBody = document.getElementById('tData');
    sample = tableBody.children[0].cloneNode(true);
    rellenarTabla();
});
// /api/ventas/order
function rellenarTabla() {
    var modal = $("#errorModal");
    $.ajax({
        type: "GET",
        url: localizacion + "/api/ventas/order",
        headers: {"Authorization" : "Bearer " + localStorage.getItem("jwt")},
        crossDomain: true,
        async: true,
        success: function (datos) {
            datos.forEach(function(current) {
                sample.id = tempId;
                sample.children[0].innerText=current.id;
                sample.children[1].innerText=current.clientUsername;
                sample.children[2].innerText=current.createDate;
                sample.children[3].innerText=current.servicio.nombre;
                sample.children[4].innerText=current.total;
                sample.children[5].innerText=current.status;
                tableBody.appendChild(sample.cloneNode(true));
            });
        },
    });
}
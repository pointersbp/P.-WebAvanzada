var algoL;
// Cambiar cuando integres al gateway el webapp.
$(document).ready(function () {
    if (document.location.hostname == "localhost") {
        algoL = document.location.protocol + "//" + document.location.hostname + ":8080";
    } else {
        algoL = "https://znzn00.ddns.net";
    }
});

function orderAccept(id) {
    $.ajax({
        type: "POST",
        url: algoL + "/api/ventas/order/"+id+"/accept",
        headers: {"Authorization": "Bearer " + localStorage.getItem("jwt")},
        crossDomain: true,
        success: function (data) {
            console.log(`Accepted order ${id}`);
            document.location.reload();
        },
        error: function (data) {
            console.error("Algo paso.")
        }
        ,
        dataType: "json"
    });
}

function orderComplete(id) {
    $.ajax({
        type: "POST",
        url: algoL + "/api/ventas/order/"+id+"/complete",
        headers: {"Authorization": "Bearer " + localStorage.getItem("jwt")},
        crossDomain: true,
        success: function (data) {
            console.log(`Complete order ${id}`);
            document.location.reload();
        },
        error: function (data) {
            console.error("Algo paso.")
        }
        ,
        dataType: "json"
    });
}
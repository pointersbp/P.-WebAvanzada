// Aqui
var localizacion;
// Cambiar cuando integres al gateway el webapp.
$(document).ready(function () {
    if (document.location.hostname == "localhost") {
        localizacion = document.location.protocol + "//" + document.location.hostname + ":8080";
    } else {
        localizacion = "https://znzn00.ddns.net";
    }
});

function onLogin() {
    // Tomar los credenciales y enviar al
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    if (username != null && password != null &&
        username != '' && password != '') {
        $.ajax({
            type: "POST",
            url: localizacion + "/api/auth/login",
            crossDomain: true,
            data: jQuery.param({
                username: username,
                password: password
            }),
            success: function (data) {
                window.localStorage.setItem("jwt", data.access_token);
                document.getElementById("username").value = "";
                document.getElementById("password").value = "";
                // Enviar otro get para entrar en la vista normal.
                $.ajax({
                    type: "POST",
                    url: "/verifyAuthentication",
                    headers: {"Authorization": "Bearer " + localStorage.getItem("jwt")},
                    crossDomain: false,
                    async: true,
                    success: function (response) {
                        window.location = "/";
                    },
                    error: function () {
                        alert("No pudo autenticarse");
                    }
                });
            },
            error: function (data) {
                document.getElementById("username").value = "";
                document.getElementById("password").value = ""
                alert("Usuario o Contraseña incorrectos.");
            }
            ,
            dataType: "json"
        });
    }
    return false;
}
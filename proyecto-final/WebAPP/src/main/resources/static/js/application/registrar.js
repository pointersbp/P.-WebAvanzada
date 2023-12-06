var localizacion;

$(document).ready(function() {
    if (document.location.hostname == "localhost")
        localizacion = document.location.protocol + "//" + document.location.hostname + ":8080";
    else {
        localizacion = "https://znzn00.ddns.net";
    }
});

function registrar() {
    setTimeout(validar, 10);
    return false;
}

function validar() {
    var snombre = document.getElementById("name").value;
    var sapellido = document.getElementById("lastname").value;
    var correo = document.getElementById("email").value;
    var username = document.getElementById("username").value;
    var passa = document.getElementById("pass1").value;
    var passb = document.getElementById("pass2").value;
    //alert("Procediendo a registrar");
    // Verificar que las contraseñas sean iguales
    if (passa == passb) {
        if (validateEmail(correo)) {
            $.ajax({
                type: "POST",
                url: localizacion + "/api/auth/validate/userexist",
                data: jQuery.param({
                    username: username
                }),
                success: function(received) {
                    if (!received) {
                        // Registrarse... /api/auth/registro/usuario
                        var datos = {
                            id: null,
                            nombre: snombre,
                            apellido: sapellido,
                            email: correo,
                            username: username,
                            password: passa,
                            roles: null
                        };
                        //XMLHttpRequest.
                        $.ajax({
                            type: "POST",
                            url: localizacion + "/api/auth/registro/usuario",
                            // url:  "http://localhost:8380/api/auth/registro/usuario",
                            dataType: "json",
                            data: JSON.stringify(datos),
                            contentType: "application/json",
                            crossDomain: true,
                            async: true,
                            statusCode: {
                                200: function(data) {
                                }
                            },
                            // xhrFields: {
                            //     withCredentials: true
                            // },
                            success: function(data) {
                                alert("Usuario registrado correctamente");
                                window.location = document.location.origin + "/login";
                            } ,
                            error: function() {
                                alert("No pudo registrarse.")
                            }
                        });
                    } else {
                        alert("El nombre de usuario ya existe.");
                        document.getElementById("username").value = "";
                    }
                },
                error: function(error) {
                    alert("No pudo conectarse.");
                }
            });
        } else {
            alert("Correo inválido.")
            document.getElementById("email").value = "";
        }
    } else {
        alert("Las contraseñas no coinciden.")
        document.getElementById("pass1").value = "";
        document.getElementById("pass2").value = "";
    }
    return false;
}

function validateEmail(inputText)
{
    var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if(inputText.match(mailformat))
    {
        return true;
    }
    return false;
}
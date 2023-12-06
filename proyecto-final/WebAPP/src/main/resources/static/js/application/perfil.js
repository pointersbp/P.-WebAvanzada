///api/auth/perfil/perfil
var localizacion;

$(document).ready(function () {
    if (document.location.hostname == "localhost")
        localizacion = document.location.protocol + "//" + document.location.hostname + ":8080";
    else {
        localizacion = "https://znzn00.ddns.net";
    }
    setTimeout(rellenarDatos, 10);
});

function rellenarDatos() {
    var modal = $('#errorModal');
    $.ajax({
       type: "GET",
        url: localizacion + "/api/auth/perfil/perfil",
       headers: {"Authorization" : "Bearer " + localStorage.getItem("jwt")},
        crossDomain: true,
        async: true,
        success: function(datos) {
            document.getElementById("correoelec").value = datos.email;
            document.getElementById("nombrePersona").value = datos.nombre;
            document.getElementById("lastname").value = datos.apellido;
            document.getElementById("username").value = datos.username;
        },
        error: function() {
            modal.find('.modal-body p').text('No se pudo obtener los datos.');
            modal.find('.modal-header h5').html('Error');
            modal.modal('show');
        }
    });
}

function actualizarClave() {
    setTimeout(cambiarClave, 10);
    return false;
}

function cambiarClave() {
    var old_pass = document.getElementById("old_pass").value;
    var new_passa = document.getElementById("new_pass1").value;
    var new_passb = document.getElementById("new_pass2").value;
    var modal = $('#errorModal');

    if (new_passa == new_passb) {
        // Registrarse... /api/auth/registro/usuario

        $.ajax({
            type: "PUT",
            url: localizacion + "/api/auth/perfil/cambiarclave",
            headers: {"Authorization" : "Bearer " + localStorage.getItem("jwt")},
            crossDomain: true,
            async: true,
            data: jQuery.param({
                clave: old_pass,
                nueva: new_passa
            }),
            success: function(received) {
                if (received != '') {
                    modal.find('.modal-body p').text('Se ha cambiado su clave exitosamente.');
                    modal.find('.modal-header h5').html('Información');
                    modal.modal('show');
                } else {
                    modal.find('.modal-body p').text('Clave anterior incorrecta.');
                    modal.find('.modal-header h5').html('Error');
                    modal.modal('show');
                }
                document.getElementById("old_pass").value = '';
                document.getElementById("new_pass1").value = '';
                document.getElementById("new_pass2").value = '';
            },
            error: function (error) {
                modal.find('.modal-body p').text('No se pudo enviar el cambio de contraseña.');
                modal.find('.modal-header h5').html('Error');
                modal.modal('show');
            }

        });
    } else {
        modal.find('.modal-body p').text('Las contraseñas no coinciden.');
        modal.find('.modal-header h5').html('Error');
        modal.modal('show');
    }
}

function actualizarPerfil() {
    setTimeout(cambiarPerfil, 10);
    return false;
}
// /api/auth/perfil/modificar
function cambiarPerfil() {
    var username = document.getElementById("username").value;
    var correo = document.getElementById("correoelec").value;
    var snombre = document.getElementById("nombrePersona").value;
    var sapellido = document.getElementById("lastname").value;
    var modal = $('#errorModal');
    if (validateEmail(correo)) {
        var usuario = {
            id: null,
            nombre: snombre,
            apellido: sapellido,
            email: correo,
            username: username,
            password: null,
            roles: null
        };
        $.ajax({
            type: "PUT",
            url: localizacion + "/api/auth/perfil/modificar",
            headers: {"Authorization" : "Bearer " + localStorage.getItem("jwt")},
            dataType: "json",
            data: JSON.stringify(usuario),
            contentType: "application/json",
            crossDomain: true,
            async: true,
            statusCode: {
                200: function(data) {
                }
            },
            success: function(data) {
                modal.find('.modal-body p').text('Datos cambiados exitosamente.');
                modal.find('.modal-header h5').html('Información');
                modal.modal('show');
            } ,
            error: function() {
                modal.find('.modal-body p').text('No fue posible cambiar sus datos.');
                modal.find('.modal-header h5').html('Error');
                modal.modal('show');
            }
        });
    } else {
        modal.find('.modal-body p').text('Correo no válido.');
        modal.find('.modal-header h5').html('Error');
        modal.modal('show');
    }
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
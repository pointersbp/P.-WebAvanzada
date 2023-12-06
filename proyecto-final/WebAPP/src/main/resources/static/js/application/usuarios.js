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

function mostrarCrear() {
    resetModalForm();
    $('#formModal').find('.modal-header h5').html('Crear Usuario');
    $('#formModal').modal('show');
}

function resetModalForm() {
    document.getElementById("correoelec").value = "";
    document.getElementById("nombrePersona").value = "";
    document.getElementById("lastname").value = "";
    document.getElementById("username").disabled = false;
    document.getElementById("username").value = "";
    document.getElementById("pass1").value = "";
    document.getElementById("pass2").value = "";
    document.getElementById("autoridad").selectedIndex = -1;
    document.getElementById("botonusuario").innerText = "Crear Usuario";
    document.getElementById("botonusuario").name = "crear";
}

function rellenarTabla() {
    $.ajax({
        type: "GET",
        url: localizacion + "/api/auth/usuario",
        headers: {"Authorization" : "Bearer " + localStorage.getItem("jwt")},
        crossDomain: true,
        async: true,
        success: function (data) {
            var datos = data.filter(e => !e.roles.some(q => q.nombre === 'ROL_SUPER_ADMIN'));
            tableBody.innerHTML= '';
            datos.forEach(function(current) {
                // Escribir en la tabla;
                var tempId = 'usuario'+current.id
                sample.id = tempId;
                sample.children[0].innerText=current.id;
                sample.children[1].innerText=current.username;
                sample.children[2].innerText=current.roles[0].nombre;
                sample.children[3].innerText=current.email;
                sample.children[4].innerHTML= '<a onclick="modificarUsuario(\''+current.nombre+'\',\''+current.apellido+'\',\''+tempId+'\')" class="btn btn-info">' +
                    '<span class="material-icons">settings</span>' +
                    '</a><a onclick="eliminarUsuario(\''+current.username+'\');" class="btn btn-danger">' +
                    '<span class="material-icons">delete</span></a>';
                tableBody.appendChild(sample.cloneNode(true));
            });
        }
    });
}

function modificarUsuario(nombre, apellido, id){
    var info = document.getElementById(id);

    document.getElementById("correoelec").value = info.children[3].innerText;
    document.getElementById("nombrePersona").value = nombre;
    document.getElementById("lastname").value = apellido;
    document.getElementById("username").disabled = true;
    document.getElementById("username").value = info.children[1].innerText;
    document.getElementById("pass1").value = "";
    document.getElementById("pass2").value = "";
    document.getElementById("botonusuario").innerText = "Modificar Usuario";
    document.getElementById("botonusuario").name = "modificar";

    switch(info.children[3].innerText) {
        case "ROL_ADMIN":
            document.getElementById("autoridad").selectedIndex = 3;
            break;
        case "ROL_CLIENTE":
            document.getElementById("autoridad").selectedIndex = 2;
            break;
        case "ROL_EMPLEADO":
            document.getElementById("autoridad").selectedIndex = 1;
            break;
        default:
            document.getElementById("autoridad").selectedIndex = 0;
    }
    $('#formModal').modal('show');
}

function buscarUsuario() {
    var susername = document.getElementById('buscarUsuario').value;
    var modal = $('#errorModal');
    if (susername != '') {
        $.ajax({
            type: "POST",
            url: localizacion + "/api/auth/usuario/obtener",
            headers: {"Authorization" : "Bearer " + localStorage.getItem("jwt")},
            crossDomain: true,
            async: true,
            data: jQuery.param({username: susername}),
            success: function(received) {
                tableBody.innerHTML= '';
                if (received != '') {
                    // Vaciar tabla y ponerle los datos de recibido.
                    var tempId = 'usuario'+received.id
                    sample.id = tempId;
                    sample.children[0].innerText=received.id;
                    sample.children[1].innerText=received.username;
                    sample.children[2].innerText=received.roles[0].nombre;
                    sample.children[3].innerText=received.email;
                    sample.children[4].innerHTML= '<a onclick="modificarUsuario(\''+received.nombre+'\',\''+received.apellido+'\',\''+tempId+'\')" class="btn btn-info">' +
                        '<span class="material-icons">settings</span>' +
                        '</a><a onclick="eliminarUsuario(\''+received.username+'\');" class="btn btn-danger">' +
                        '<span class="material-icons">delete</span></a>';
                    tableBody.appendChild(sample.cloneNode(true));
                } else {
                    modal.find('.modal-body p').text('Nombre de usuario no existe.');
                    modal.find('.modal-header h5').html('Error');
                    modal.modal('show');
                }
            },
            error: function() {
                tableBody.innerHTML= '';
                modal.find('.modal-body p').text('No se pudo obtener los datos.');
                modal.find('.modal-header h5').html('Error');
                modal.modal('show');
            }
        });
    } else {
        rellenarTabla();
    }
    return false;
}

function crearmodificar() {
    setTimeout(procesar, 10);
    return false;
}

function procesar() {
    var selection = document.getElementById("botonusuario").name;
    var modal = $('#errorModal');
    // Datos
    var correo = document.getElementById("correoelec").value;
    var nombre = document.getElementById("nombrePersona").value;
    var apellido = document.getElementById("lastname").value;
    var username = document.getElementById("username").value;
    var clave = document.getElementById("pass1").value;
    var confirmar = document.getElementById("pass2").value;
    var rol = document.getElementById("autoridad").value;
    validaciones(correo, nombre, apellido, username, clave, confirmar, rol, selection, modal);
}

function validaciones(correo, nombre, apellido, username, clave, confirmar, rol, selection, modal) {
    // validaciones.
    if (username != '') {

        if (correo != '') {
            if (validateEmail(correo)) {
                if (nombre != '') {
                    if (apellido != '') {
                        if (rol != '') {
                            if (clave != '') {
                                if (confirmar != '') {
                                    if (confirmar == clave) {
                                        if (selection == 'crear') {
                                            $.ajax({
                                                type: "POST",
                                                url: localizacion + "/api/auth/validate/userexist",
                                                data: jQuery.param({
                                                    username: username
                                                }),
                                                success: function(success) {
                                                    if (!success) {
                                                        creacion(correo, nombre, apellido, username, clave, rol);
                                                    } else {
                                                        modal.find('.modal-body p').text('El usuario ya existe.');
                                                        modal.find('.modal-header h5').html('Advertencia');
                                                        modal.modal('show');
                                                    }
                                                },
                                                error: function() {
                                                    modal.find('.modal-body p').text('No se pudo realizar proceso.');
                                                    modal.find('.modal-header h5').html('Error');
                                                    modal.modal('show');
                                                }});
                                        } else if (selection == 'modificar') {
                                            modificacion(correo, nombre, apellido, username, clave, rol);
                                        } else {
                                            modal.find('.modal-body p').text('No se pudo realizar proceso.');
                                            modal.find('.modal-header h5').html('Error');
                                            modal.modal('show');
                                        }
                                    } else {
                                        modal.find('.modal-body p').text('Las claves no son iguales.');
                                        modal.find('.modal-header h5').html('Advertencia');
                                        modal.modal('show');
                                    }
                                } else {
                                    modal.find('.modal-body p').text('No puede dejar la confirmación de su clave vacía.');
                                    modal.find('.modal-header h5').html('Advertencia');
                                    modal.modal('show');
                                }
                            } else {
                                modal.find('.modal-body p').text('No puede dejar su clave vacía.');
                                modal.find('.modal-header h5').html('Advertencia');
                                modal.modal('show');
                            }
                        } else {
                            modal.find('.modal-body p').text('Debe seleccionar una autoridad.');
                            modal.find('.modal-header h5').html('Advertencia');
                            modal.modal('show');
                        }
                    } else {
                        modal.find('.modal-body p').text('Debe ingresar el apellido.');
                        modal.find('.modal-header h5').html('Advertencia');
                        modal.modal('show');
                    }
                } else {
                    modal.find('.modal-body p').text('Debe ingresar el nombre.');
                    modal.find('.modal-header h5').html('Advertencia');
                    modal.modal('show');
                }
            } else {
                modal.find('.modal-body p').text('Ingrese un correo correcto');
                modal.find('.modal-header h5').html('Advertencia');
                modal.modal('show');
            }
        } else {
            modal.find('.modal-body p').text('No puede dejar el correo vacío');
            modal.find('.modal-header h5').html('Advertencia');
            modal.modal('show');
        }


    } else {
        modal.find('.modal-body p').text('No puede dejar el nombre de usuario vacío.');
        modal.find('.modal-header h5').html('Advertencia');
        modal.modal('show');
    }
}

function creacion(correo, nombre, apellido, username, clave, rol) {
    $.ajax({
        type: "POST",
        url: localizacion + "/api/auth/usuario/crear",
        headers: {"Authorization" : "Bearer " + localStorage.getItem("jwt")},
        data: jQuery.param({correo: correo, nombre: nombre, apellido: apellido, username: username, clave: clave, rol: rol}),
        crossDomain: true,
        async: true,
        success: function(received) {
            if (received != '') {
                $('#errorModal').find('.modal-body p').text('Se pudo crear con éxito el usuario: ' + received.username);
                $('#errorModal').find('.modal-header h5').html('Información');
                $('#errorModal').modal('show');
            } else {
                $('#errorModal').find('.modal-body p').text('No se pudo crear el usuario: ' + received.username);
                $('#errorModal').find('.modal-header h5').html('Error');
                $('#errorModal').modal('show');
            }
            resetModalForm();
            rellenarTabla();
        },
        error: function () {
            $('#errorModal').find('.modal-body p').text('No se pudo crear el usuario.');
            $('#errorModal').find('.modal-header h5').html('Error');
            $('#errorModal').modal('show');
        }
    });
}

function modificacion(correo, nombre, apellido, username, clave, rol) {
    $.ajax({
        type: "PUT",
        url: localizacion + "/api/auth/usuario/cambiar",
        headers: {"Authorization" : "Bearer " + localStorage.getItem("jwt")},
        data: jQuery.param({correo: correo, nombre: nombre, apellido: apellido, username: username, clave: clave, rol: rol}),
        crossDomain: true,
        async: true,
        success: function(received) {
            if (received != '') {
                $('#errorModal').find('.modal-body p').text('Se pudo modificar con éxito el usuario: ' + received.username);
                $('#errorModal').find('.modal-header h5').html('Información');
                $('#errorModal').modal('show');
            } else {
                $('#errorModal').find('.modal-body p').text('No se pudo modificar el usuario: ' + received.username);
                $('#errorModal').find('.modal-header h5').html('Error');
                $('#errorModal').modal('show');
            }
            rellenarTabla();
        },
        error: function () {
            $('#errorModal').find('.modal-body p').text('No se pudo modificar el usuario.');
            $('#errorModal').find('.modal-header h5').html('Error');
            $('#errorModal').modal('show');
        }
    });
}

function validateEmail(inputText) {
    var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if(inputText.match(mailformat))
    {
        return true;
    }
    return false;
}
//
function eliminarUsuario(username) {
    $.ajax({
       type: "DELETE",
       url: localizacion + "/api/auth/usuario/remover",
        headers: {"Authorization" : "Bearer " + localStorage.getItem("jwt")},
        data: jQuery.param({username: username}),
        crossDomain: true,
        async: true,
        success: function (received) {
           if (received) {
               $('#errorModal').find('.modal-body p').text('Se eliminó el usuario.');
               $('#errorModal').find('.modal-header h5').html('Error');
               $('#errorModal').modal('show');
           } else {
               $('#errorModal').find('.modal-body p').text('No se pudo eliminar el usuario.');
               $('#errorModal').find('.modal-header h5').html('Error');
               $('#errorModal').modal('show');
           }
           rellenarTabla();
        },
        error: function () {
            $('#errorModal').find('.modal-body p').text('No se pudo eliminar el usuario.');
            $('#errorModal').find('.modal-header h5').html('Error');
            $('#errorModal').modal('show');
        }
    });
}
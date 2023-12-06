var localizacion;
var element;

// Cambiar cuando integres al gateway el webapp.
$(document).ready(function() {
    if (document.location.hostname == "localhost")
        localizacion = document.location.protocol + "//" + document.location.hostname + ":8080";
    else {
        localizacion = "https://znzn00.ddns.net";
    }
    $.ajax({
        type: "POST",
        url: localizacion + "/api/ventas/order/resumen",
        headers: {"Authorization" : "Bearer " + localStorage.getItem("jwt")},
        crossDomain:true,
        async: true,
        success: function (data) {
            var totalCompras = 0;
            var totalPendientes = 0;
            var totalCompletados = 0;
            data.forEach(function (object) {
                totalCompras += object.cantCompras;
                totalCompletados += object.cantPendientes;
                totalPendientes += object.cantCompletados;
            });
            document.getElementById("tituloCompras").innerText = totalCompras;
            document.getElementById("tituloPendientes").innerText = totalPendientes;
            document.getElementById("tituloCompletados").innerText = totalCompletados;
            //console.log("Hora de cambiar los graficos");
            crearGraficos(data);
        }
    });
});

function crearGraficos(data) {
    //console.log("ingresar a crear");
    if ($('#dailySalesChart').length != 0 || $('#completedTasksChart').length != 0 || $('#websiteViewsChart').length != 0) {
        /* ----------==========     Daily Sales Chart initialization    ==========---------- */
        /* Insertar aqui dailySalesChart */
        //console.log("creando");
        var dias = ['D', 'L', 'M', 'M', 'J', 'V', 'S'];
        var fecha = [];
        var datosCompras = [];
        var datosPendientes = [];
        var datosCompletados = [];
        var maxCompras = 0;
        var maxPendientes = 0;
        var maxCompletados = 0;
        data.forEach(function(object) {
            fecha.push(dias[(new Date(object.fecha)).getDay()]);
            datosCompras.push(object.cantCompras);
            datosPendientes.push(object.cantPendientes);
            datosCompletados.push(object.cantCompletados);
            if (object.cantCompras > maxCompras) {
                maxCompras=object.cantCompras;
            }
            if (object.cantPendientes > maxPendientes) {
                maxPendientes = object.cantPendientes;
            }
            if (object.cantCompletados > maxCompletados) {
                maxCompletados = object.cantCompletados;
            }
        });
        var graphCompras = Math.ceil(maxCompras/10 + 1) * 10;
        var graphPendientes = Math.ceil(maxPendientes/10 + 1) * 10;
        var graphCompletados = Math.ceil(maxCompletados/10 + 1) * 10;

        dataDailySalesChart = {
            labels: fecha,
            series: [
                datosCompras
            ]
        };

        optionsDailySalesChart = {
            lineSmooth: Chartist.Interpolation.cardinal({
                tension: 0
            }),
            low: 0,
            high: graphCompras,
            chartPadding: {
                top: 0,
                right: 0,
                bottom: 0,
                left: 0
            },
        }

        var dailySalesChart = new Chartist.Line('#dailySalesChart', dataDailySalesChart, optionsDailySalesChart);

        md.startAnimationForLineChart(dailySalesChart);



        /* ----------==========     Completed Tasks Chart initialization    ==========---------- */

        dataCompletedTasksChart = {
            labels: fecha,
            series: [
                datosCompletados
            ]
        };

        optionsCompletedTasksChart = {
            lineSmooth: Chartist.Interpolation.cardinal({
                tension: 0
            }),
            low: 0,
            high: graphCompletados, // creative tim: we recommend you to set the high sa the biggest value + something for a better look
            chartPadding: {
                top: 0,
                right: 0,
                bottom: 0,
                left: 0
            }
        }

        var completedTasksChart = new Chartist.Line('#completedTasksChart', dataCompletedTasksChart, optionsCompletedTasksChart);

        // start animation for the Completed Tasks Chart - Line Chart
        md.startAnimationForLineChart(completedTasksChart);


        /* ----------==========     Emails Subscription Chart initialization    ==========---------- */

        var dataWebsiteViewsChart = {
            labels: fecha,
            series: [
                datosPendientes
            ]
        };
        var optionsWebsiteViewsChart = {
            axisX: {
                showGrid: false
            },
            low: 0,
            high: graphPendientes,
            chartPadding: {
                top: 0,
                right: 5,
                bottom: 0,
                left: 0
            }
        };
        var responsiveOptions = [
            ['screen and (max-width: 640px)', {
                seriesBarDistance: 5,
                axisX: {
                    labelInterpolationFnc: function(value) {
                        return value[0];
                    }
                }
            }]
        ];
        var websiteViewsChart = Chartist.Bar('#websiteViewsChart', dataWebsiteViewsChart, optionsWebsiteViewsChart, responsiveOptions);

        //start animation for the Emails Subscription Chart
        md.startAnimationForBarChart(websiteViewsChart);
    }
}
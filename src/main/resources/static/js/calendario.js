$(document).ready(function() {
    $('#calendar').fullCalendar({
        locale: 'es',
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        defaultDate: moment().format("YYYY-MM-DD"),
        editable: true,
        eventLimit: true,
        events: {
            url: '/api/eventos/listar',
            type: 'GET',
            success: function(data) {
                // Mostrar los eventos en el calendario y en la lista lateral
                data.forEach(evento => {
                    $('#eventosLista').append(`
                        <li data-id="${evento.id}">
                            <strong>${evento.notificacion}</strong>
                            <div class="detallesEvento" style="display: none;">
                                Descripción: ${evento.descripcion} <br>
                                Dirección: ${evento.direccion} <br>
                                Fecha: ${evento.fechaEvento} <br>
                                Notificación: ${evento.notificacion}
                            </div>
                        </li>
                    `);
                });

                // Al hacer clic en el título del evento, mostrar detalles
                $('#eventosLista li').on('click', function() {
                    $(this).find('.detallesEvento').toggle(); // Mostrar u ocultar detalles
                });
            },
            error: function() {
                alert('Error al cargar los eventos.');
            }
        },
        selectable: true,
        selectHelper: true,
        select: function(start) {
            $('#fechaEvento').val(moment(start).format("YYYY-MM-DD"));
        },
        eventRender: function(event, element) {
            element.attr('title', event.notificacion);
        }
    });

    // Guardar evento y añadirlo a la lista lateral
    $('#guardarEventoBtn').on('click', function() {
        var event = {
            descripcion: $('#descripcion').val(),
            direccion: $('#direccion').val(),
            fechaEvento: $('#fechaEvento').val(),
            notificacion: $('#notificacion').val(),
            idAdmin: 1
        };

        $.ajax({
            url: '/api/eventos/crear',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(event),
            success: function(data) {
                // Recargar eventos en el calendario
                $('#calendar').fullCalendar('refetchEvents');

                // Añadir el nuevo evento a la lista lateral
                $('#eventosLista').append(`
                    <li data-id="${data.id}">
                        <strong>${data.notificacion}</strong>
                        <div class="detallesEvento" style="display: none;">
                            Descripción: ${data.descripcion} <br>
                            Dirección: ${data.direccion} <br>
                            Fecha: ${data.fechaEvento} <br>
                            Notificación: ${data.notificacion}
                        </div>
                    </li>
                `);

                // Añadir evento de clic para mostrar detalles
                $('#eventosLista li').last().on('click', function() {
                    $(this).find('.detallesEvento').toggle();
                });
            },
            error: function() {
                alert('Error al guardar el evento.');
            }
        });
    });
});

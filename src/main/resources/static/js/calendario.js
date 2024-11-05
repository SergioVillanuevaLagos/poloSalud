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
                $('#eventosLista').empty();
                data.forEach(evento => {
                    $('#eventosLista').append(`
                        <li data-id="${evento.id}">
                            <strong>${evento.notificacion}</strong>
                            <div class="detallesEvento" style="display: none;">
                                Descripción: ${evento.descripcion} <br>
                                Dirección: ${evento.direccion} <br>
                                Fecha: ${moment(evento.fechaEvento).format('DD-MM-YYYY')} <br>
                                Notificación: ${evento.notificacion}
                                <br><button class="eliminarEventoBtn" data-id="${evento.id}">Eliminar</button>
                            </div>
                        </li>
                    `);
                });

                $('#eventosLista li').on('click', function() {
                    $(this).find('.detallesEvento').toggle();
                });

                $('.eliminarEventoBtn').on('click', function(e) {
                    e.stopPropagation();

                    const eventId = $(this).data('id');
                    if (confirm('¿Estás seguro de que deseas eliminar este evento?')) {
                        $.ajax({
                            url: `/api/eventos/eliminar/${eventId}`,
                            type: 'DELETE',
                            success: function() {
                                $('#calendar').fullCalendar('refetchEvents');
                                $(`li[data-id="${eventId}"]`).remove();
                            },
                            error: function(xhr, status, error) {
                                alert('Error al eliminar el evento: ' + xhr.responseText);
                            }
                        });
                    }
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
            element.find('.fc-title').text(event.notificacion);
            element.attr('title', event.notificacion);
        }
    });

    $('#guardarEventoBtn').on('click', function() {
        if (!$('#descripcion').val() || !$('#direccion').val() || !$('#fechaEvento').val() || !$('#notificacion').val()) {
            alert('Todos los campos deben estar llenos.');
            return;
        }

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
                $('#calendar').fullCalendar('refetchEvents');
                $('#descripcion').val('');
                $('#direccion').val('');
                $('#fechaEvento').val('');
                $('#notificacion').val('');
                $('#eventosLista').append(`
                    <li data-id="${data.id}">
                        <strong>${data.notificacion}</strong>
                        <div class="detallesEvento" style="display: none;">
                            Descripción: ${data.descripcion} <br>
                            Dirección: ${data.direccion} <br>
                            Fecha: ${moment(data.fechaEvento).format('DD-MM-YYYY')} <br>
                            Notificación: ${data.notificacion}
                            <br><button class="eliminarEventoBtn" data-id="${data.id}">Eliminar</button>
                        </div>
                    </li>
                `);
            },
            error: function() {
                alert('Error al guardar el evento.');
            }
        });
    });

    $('#cancelarBtn').on('click', function() {
        $('#descripcion').val('');
        $('#direccion').val('');
        $('#fechaEvento').val('');
        $('#notificacion').val('');
    });
});

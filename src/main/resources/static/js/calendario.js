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
                // Elimina eventos previos para evitar duplicados
                $('#calendar').fullCalendar('removeEvents');

                const events = data.map(evento => ({
                    id: evento.id,
                    title: evento.notificacion, // Esto muestra la notificación en el calendario
                    start: evento.fechaEvento,
                    descripcion: evento.descripcion,
                    direccion: evento.direccion,
                    notificacion: evento.notificacion
                }));

                // Renderiza los eventos en el calendario
                $('#calendar').fullCalendar('renderEvents', events, true);

                $('#eventosLista').empty();
                events.forEach(evento => {
                    $('#eventosLista').append(`
                        <li data-id="${evento.id}">
                            <strong>${evento.title}</strong>
                            <div class="detallesEvento" style="display: none;">
                                <strong>Descripción:</strong> ${evento.descripcion} <br>
                                <strong>Dirección:</strong> ${evento.direccion} <br>
                                <strong>Fecha:</strong> ${moment(evento.start).format('DD-MM-YYYY')} <br>
                                <strong>Notificación:</strong> ${evento.notificacion}
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
                                // Elimina el evento del calendario y de la lista
                                $('#calendar').fullCalendar('removeEvents', eventId);
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
            // Mostrar la notificación como título del evento en el calendario
            element.find('.fc-title').text(event.title);
            element.attr('title', event.notificacion);
        }
    });

    $('#guardarEventoBtn').on('click', function() {
        if (!$('#descripcion').val() || !$('#direccion').val() || !$('#fechaEvento').val() || !$('#notificacion').val()) {
            alert('Todos los campos deben estar llenos.');
            return;
        }

        const event = {
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
                            <strong>Descripción:</strong> ${data.descripcion} <br>
                            <strong>Dirección:</strong> ${data.direccion} <br>
                            <strong>Fecha:</strong> ${moment(data.fechaEvento).format('DD-MM-YYYY')} <br>
                            <strong>Notificación:</strong> ${data.notificacion}
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

$(document).ready(function () {
  $('#calendar').fullCalendar({
    locale: 'es',
    header: {
      left: 'prev, today',
      center: 'title',
      right: 'today,next'
    },
    defaultDate: moment().format("YYYY-MM-DD"),
    editable: true,
    eventLimit: true,
    events: {
      url: '/api/eventos/listar',
      type: 'GET',
      success: function (data) {
        $('#eventosLista').empty();
        data.forEach(evento => {
          const fecha = moment(evento.fechaEvento).format('DD-MM-YYYY');
          const hora = moment(evento.fechaEvento).format('HH:mm');

          $('#eventosLista').append(`
            <li data-id="${evento.id}">
              <strong>${evento.notificacion}</strong>
              <div class="detallesEvento" style="display: none;">
                <strong>Descripción:</strong> ${evento.descripcion} <br>
                <strong>Dirección:</strong> ${evento.direccion} <br>
                <strong>Fecha:</strong> ${fecha} <br>
                <strong>Hora:</strong> ${hora} <br>
                <strong>Notificación:</strong> ${evento.notificacion}
                <br><button class="eliminarEventoBtn" data-id="${evento.id}">Eliminar</button>
              </div>
            </li>
          `);
        });

        $('#eventosLista li').on('click', function () {
          $(this).find('.detallesEvento').toggle();
        });

        $('.eliminarEventoBtn').on('click', function (e) {
          e.stopPropagation();

          const eventId = $(this).data('id');
          if (confirm('¿Estás seguro de que deseas eliminar este evento?')) {
            $.ajax({
              url: `/api/eventos/eliminar/${eventId}`,
              type: 'DELETE',
              success: function () {
                $('#calendar').fullCalendar('refetchEvents');
                $(`li[data-id="${eventId}"]`).remove();
              },
              error: function (xhr) {
                alert('Error al eliminar el evento: ' + xhr.responseText);
              }
            });
          }
        });
      },
      error: function () {
        alert('Error al cargar los eventos.');
      }
    },
    selectable: true,
    selectHelper: true,
    select: function (start) {
      $('#fechaEvento').val(moment(start).format("YYYY-MM-DDTHH:mm"));
    },
    eventRender: function (event, element) {
      element.find('.fc-title').text(event.notificacion);
      element.attr('title', event.notificacion);
    }
  });

  $('#guardarEventoBtn').on('click', function () {
    if (!$('#descripcion').val() || !$('#direccion').val() || !$('#fechaEvento').val() || !$('#notificacion').val()) {
      alert('Todos los campos deben estar llenos.');
      return;
    }

    const fechaEvento = moment($('#fechaEvento').val()).format("YYYY-MM-DDTHH:mm:ss");

    const event = {
      descripcion: $('#descripcion').val(),
      direccion: $('#direccion').val(),
      fechaEvento: fechaEvento,
      notificacion: $('#notificacion').val(),
      idAdmin: 1
    };

    $.ajax({
      url: '/api/eventos/crear',
      type: 'POST',
      contentType: 'application/json',
      data: JSON.stringify(event),
      success: function (data) {
        $('#calendar').fullCalendar('refetchEvents');
        $('#descripcion').val('');
        $('#direccion').val('');
        $('#fechaEvento').val('');
        $('#notificacion').val('');
        const fecha = moment(data.fechaEvento).format('DD-MM-YYYY');
        const hora = moment(data.fechaEvento).format('HH:mm');

        $('#eventosLista').append(`
          <li data-id="${data.id}">
            <strong>${data.notificacion}</strong>
            <div class="detallesEvento" style="display: none;">
              <strong>Descripción:</strong> ${data.descripcion} <br>
              <strong>Dirección:</strong> ${data.direccion} <br>
              <strong>Fecha:</strong> ${fecha} <br>
              <strong>Hora:</strong> ${hora} <br>
              <strong>Notificación:</strong> ${data.notificacion}
              <br><button class="eliminarEventoBtn" data-id="${data.id}">Eliminar</button>
            </div>
          </li>
        `);
      },
      error: function () {
        alert('Error al guardar el evento.');
      }
    });
  });

  $('#cancelarBtn').on('click', function () {
    $('#descripcion').val('');
    $('#direccion').val('');
    $('#fechaEvento').val('');
    $('#notificacion').val('');
  });
});

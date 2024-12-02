$(document).ready(function () {
  $('#calendar').fullCalendar({
    locale: 'es',
    header: {
      left: 'prev, today',
      center: 'title',
      right: 'today, next'
    },
    defaultDate: moment().format("YYYY-MM-DD"),
    editable: false,
    eventLimit: true,
    events: {
      url: '/api/eventos/listar',
      type: 'GET',
      success: function (data) {
        $('#eventosLista').empty(); 
        data.forEach(evento => {
          const fecha = moment(evento.fechaEvento).format('DD-MM-YYYY');
          const hora = moment(evento.fechaEvento).format('HH:mm');
          const listItem = `
            <li data-id="${evento.id}" class="evento-item">
              <strong>${evento.notificacion}</strong>
              <div class="detallesEvento" style="display: none;">
                <strong>Dirección:</strong> <span>${evento.direccion}</span><br>
                <strong>Fecha:</strong> <span>${fecha}</span><br>
                <strong>Hora del evento:</strong> <span>${hora}</span><br>
                <strong>Descripción:</strong> <span>${evento.descripcion}</span><br>
                <button class="eliminarEventoBtn" data-id="${evento.id}">Eliminar</button>
              </div>
            </li>
          `;
          $('#eventosLista').append(listItem);
        });

        // Agrega eventos a los nuevos elementos
        $('#eventosLista li').off('click').on('click', function () {
          $(this).find('.detallesEvento').toggle();
        });

        $('.eliminarEventoBtn').off('click').on('click', function (e) {
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
      element.find('.fc-title').html(event.notificacion); 
      element.attr('title', `${event.descripcion}`); 
    }
  });

  $('#guardarEventoBtn').on('click', function () {
    const descripcion = $('#descripcion').val();
    const direccion = $('#direccion').val();
    const fechaEvento = moment($('#fechaEvento').val()).format("YYYY-MM-DDTHH:mm:ss");
    const notificacion = $('#notificacion').val();

    if (!descripcion || !direccion || !fechaEvento || !notificacion) {
      alert('Todos los campos deben estar llenos.');
      return;
    }

    const evento = { descripcion, direccion, fechaEvento, notificacion, idAdmin: 1 };

    $.ajax({
      url: '/api/eventos/crear',
      type: 'POST',
      contentType: 'application/json',
      data: JSON.stringify(evento),
      success: function (data) {
        $('#calendar').fullCalendar('refetchEvents');
        $('#descripcion, #direccion, #fechaEvento, #notificacion').val('');
        const fecha = moment(data.fechaEvento).format('DD-MM-YYYY');
        const hora = moment(data.fechaEvento).format('HH:mm');

        const newItem = `
          <li data-id="${data.id}" class="evento-item">
            <strong>${data.notificacion}</strong>
            <div class="detallesEvento" style="display: none;">
              <strong>Descripción:</strong> <span>${data.descripcion}</span><br>
              <strong>Dirección:</strong> <span>${data.direccion}</span><br>
              <strong>Fecha:</strong> <span>${fecha}</span><br>
              <strong>Hora:</strong> <span>${hora}</span><br>
              <button class="eliminarEventoBtn" data-id="${data.id}">Eliminar</button>
            </div>
          </li>
        `;
        $('#eventosLista').append(newItem);
      },
      error: function () {
        alert('Error al guardar el evento.');
      }
    });
  });

  $('#cancelarBtn').on('click', function () {
    $('#descripcion, #direccion, #fechaEvento, #notificacion').val('');
  });
});

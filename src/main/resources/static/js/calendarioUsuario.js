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
        // Vaciar la lista antes de llenarla
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
              </div>
            </li>
          `;
          $('#eventosLista').append(listItem);
        });

        // Manejo de clic en los eventos listados
        $('#eventosLista li').off('click').on('click', function () {
          $(this).find('.detallesEvento').toggle();
        });
      },
      error: function () {
        alert('Error al cargar los eventos.');
      }
    },
    eventRender: function (event, element) {
      element.find('.fc-title').html(event.notificacion); 
      element.attr('title', `${event.descripcion}`); 
    }
  });
});

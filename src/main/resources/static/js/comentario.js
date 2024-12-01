$(document).ready(function () {
    const idUsuario = 1; // ID de usuario predeterminado
    const idPublicacion = $('#idPublicacion').val();

    // Cargar comentarios al cargar la página
    cargarComentarios();

    // Manejador para agregar un nuevo comentario
    $('#form-comentario').on('submit', async function (event) {
        event.preventDefault();
        const contenido = $('#contenido').val().trim();

        if (!contenido) {
            alert('El contenido del comentario no puede estar vacío.');
            return;
        }

        try {
            const response = await fetch("/api/comentarios/crear", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ contenido, idPublicacion, idUsuario }),
            });

            if (!response.ok) throw new Error("Error al crear comentario");

            const nuevoComentario = await response.json();
            mostrarComentario(nuevoComentario, true);
            $('#form-comentario')[0].reset();
        } catch (error) {
            console.error("Error al crear comentario:", error);
        }
    });

    // Función para cargar comentarios desde el servidor
    async function cargarComentarios() {
        try {
            const response = await fetch(`/api/comentarios/listar/${idPublicacion}`);
            const comentarios = await response.json();
            $('#lista-comentarios').empty();
            mostrarComentarios(comentarios);
        } catch (error) {
            console.error("Error al obtener comentarios:", error);
        }
    }

    // Función para mostrar todos los comentarios
    function mostrarComentarios(comentarios) {
        comentarios.forEach(comentario => mostrarComentario(comentario));
    }

    // Función para mostrar un solo comentario
    function mostrarComentario(comentario, agregarAlInicio = false) {
        const nombreUsuario = comentario.usuario || "Usuario desconocido";
        const fechaComentario = comentario.fecha || "Fecha no disponible";

            const comentarioHTML = `
        <li data-id="${comentario.id}" class="comentario-item">
            <div class="comentario">
                <div class="avatar">•</div>
                <div class="contenido-comentario">
                    <div class="usuario">
                        <span>${nombreUsuario}</span>
                        <span class="fecha">${fechaComentario}</span>
                    </div>
                    <p class="texto">${comentario.contenido || "Contenido no disponible"}</p>
                    <div class="acciones">
                        <a href="#" class="responder" data-id="${comentario.id}">Responder</a>
                        <a href="#" class="eliminar" data-id="${comentario.id}">Eliminar</a>
                    </div>
                    <ul class="respuestas"></ul>
                </div>
            </div>
        </li>
    `;

    if (agregarAlInicio) {
        $('#lista-comentarios').prepend(comentarioHTML);
    } else {
        $('#lista-comentarios').append(comentarioHTML);
    }
            // Eventos de acciones
        $(`.eliminar[data-id="${comentario.id}"]`).on('click', function (event) {
            event.preventDefault();
            eliminarComentario(comentario.id);
        });

        $(`.responder[data-id="${comentario.id}"]`).on('click', function (event) {
            event.preventDefault();
            mostrarFormularioRespuesta(comentario.id);
        });
    }

    // Función para eliminar un comentario
    async function eliminarComentario(id) {
        try {
            const response = await fetch(`/api/comentarios/eliminar/${id}`, {
                method: "DELETE",
            });

            if (!response.ok) throw new Error("Error al eliminar comentario");

            $(`li[data-id="${id}"]`).remove(); // Eliminar visualmente el comentario
        } catch (error) {
            console.error("Error al eliminar comentario:", error);
        }
    }

    // Función para mostrar el formulario de respuesta
    function mostrarFormularioRespuesta(idComentario) {
        const comentarioItem = $(`li[data-id="${idComentario}"]`);
        const respuestasContainer = comentarioItem.find('.respuestas');

        if (respuestasContainer.find('.form-respuesta').length > 0) {
            return; // Ya hay un formulario visible
        }

        const formularioHTML = `
            <form class="form-respuesta">
                <textarea class="textarea-respuesta" placeholder="Escribe tu respuesta..." required></textarea>
                <button type="submit" class="btn-enviar-respuesta">Responder</button>
                <button type="button" class="btn-cancelar-respuesta">Cancelar</button>
            </form>
        `;

        respuestasContainer.append(formularioHTML);

        // Manejar envío de respuesta
        respuestasContainer.find('.btn-enviar-respuesta').on('click', async function (event) {
            event.preventDefault();
            const respuesta = respuestasContainer.find('.textarea-respuesta').val().trim();

            if (!respuesta) {
                alert("La respuesta no puede estar vacía.");
                return;
            }

            try {
                const response = await fetch(`/api/comentarios/responder/${idComentario}`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ respuesta }),
                });

                if (!response.ok) throw new Error("Error al responder comentario");

                const nuevaRespuesta = await response.json();
                mostrarRespuesta(nuevaRespuesta, idComentario);
                respuestasContainer.find('.form-respuesta').remove(); // Eliminar formulario tras responder
            } catch (error) {
                console.error("Error al responder comentario:", error);
            }
        });

        // Manejar cancelación del formulario
        respuestasContainer.find('.btn-cancelar-respuesta').on('click', function () {
            respuestasContainer.find('.form-respuesta').remove();
        });
    }

    // Función para mostrar una respuesta
    function mostrarRespuesta(respuesta, idComentario) {
        const comentarioItem = $(`li[data-id="${idComentario}"]`);
        const respuestasContainer = comentarioItem.find('.respuestas');

        const respuestaHTML = `
            <li class="comentario-item">
                <div class="comentario">
                    <div class="avatar">•</div>
                    <div class="contenido-comentario">
                        <div class="usuario">
                            <span>${respuesta.usuario || "Usuario desconocido"}</span>
                            <span class="fecha">${respuesta.fecha || "Fecha no disponible"}</span>
                        </div>
                        <p class="texto">${respuesta.contenido}</p>
                    </div>
                </div>
            </li>
        `;

        respuestasContainer.append(respuestaHTML);
    }
});

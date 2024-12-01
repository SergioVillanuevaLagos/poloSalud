$(document).ready(function () {
    const idUsuario = 1; // ID de usuario predeterminado
    const idPublicacion = $('#idPublicacion').val();

    // Cargar comentarios cuando se carga la página
    cargarComentarios();

    // Manejador para el formulario de comentarios
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

    // Función para mostrar los comentarios en la página
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
                    </div>
                </div>
            </li>
        `;

        if (agregarAlInicio) {
            $('#lista-comentarios').prepend(comentarioHTML);
        } else {
            $('#lista-comentarios').append(comentarioHTML);
        }

        // Añadir evento al enlace eliminar
        $(`.eliminar[data-id="${comentario.id}"]`).on('click', function (event) {
            event.preventDefault();
            eliminarComentario(comentario.id);
        });

        // Añadir evento al enlace responder
        $(`.responder[data-id="${comentario.id}"]`).on('click', function (event) {
            event.preventDefault();
            responderComentario(comentario.id); // Aquí usamos el ID del comentario directamente
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

    // Función para responder un comentario
    async function responderComentario(idComentario) {
        const respuesta = prompt("Escribe tu respuesta:");

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

            // Si la respuesta fue exitosa, podemos hacer algo con el comentario respondido
            alert("Respuesta enviada correctamente");
            cargarComentarios(); // Recargar los comentarios después de responder
        } catch (error) {
            console.error("Error al responder comentario:", error);
        }
    }
});

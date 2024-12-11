$(document).ready(function () {
    let idUsuario = null; // Variable para almacenar el ID del usuario autenticado
    const idPublicacion = $('#idPublicacion').val(); // Obtener el ID de la publicación desde el HTML

    // Verificar si el usuario está autenticado y obtener su ID
    async function verificarAutenticacion() {
        try {
            const response = await fetch("/api/usuario/autenticado"); // Endpoint para obtener el usuario autenticado
            if (response.ok) {
                const usuario = await response.json();
                idUsuario = usuario.id; // Almacenar el ID del usuario autenticado
                cargarComentarios(idPublicacion); // Cargar comentarios si el usuario está autenticado
            } else {
                console.warn("Usuario no autenticado. Acceso restringido.");
            }
        } catch (error) {
            console.error("Error al verificar autenticación:", error);
        }
    }

    // Llamar a la función para verificar autenticación al cargar la página
    verificarAutenticacion();

    // Manejador para agregar un nuevo comentario
    $('#form-comentario').on('submit', async function (event) {
        event.preventDefault();
        const contenido = $('#contenido').val().trim();

        if (!contenido) {
            alert('El contenido del comentario no puede estar vacío.');
            return;
        }

        if (!idUsuario) {
            alert('Debes iniciar sesión para agregar un comentario.');
            return;
        }

        try {
            const response = await fetch("/api/comentarios/crear", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    contenido,
                    idPublicacion: idPublicacion,
                    idUsuario: idUsuario, // Enviar el ID del usuario autenticado
                }),
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
    async function cargarComentarios(idPublicacion) {
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

        // Evento para eliminar un comentario
        $(`.eliminar[data-id="${comentario.id}"]`).on('click', function (event) {
            event.preventDefault();
            eliminarComentario(comentario.id);
        });
    }

    // Función para eliminar un comentario
    async function eliminarComentario(id) {
        try {
            const response = await fetch(`/api/comentarios/eliminar/${id}`, {
                method: "DELETE",
            });

            if (!response.ok) {
                const error = await response.json();
                if (error.message === "No tienes permiso para eliminar este comentario") {
                    alert("No tienes permiso para eliminar este comentario.");
                } else {
                    throw new Error("Error al eliminar comentario");
                }
            } else {
                $(`li[data-id="${id}"]`).remove(); // Eliminar visualmente el comentario
            }
        } catch (error) {
            console.error("Error al eliminar comentario:", error);
        }
    }
});
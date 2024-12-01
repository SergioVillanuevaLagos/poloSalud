$(document).ready(function () {
    const idUsuario = 1; // ID de usuario predeterminado
    const idPublicacion = $('#idPublicacion').val();

    cargarComentarios();

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

    function mostrarComentarios(comentarios) {
        comentarios.forEach(comentario => mostrarComentario(comentario));
    }

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
                        <a href="#" class="responder">Responder</a>
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
}

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
});

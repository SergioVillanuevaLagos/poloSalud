$(document).ready(function () {
    const idUsuario = 1; // ID de usuario predeterminado
    const idPublicacion = $('#idPublicacion').val();

    // Cargar comentarios al iniciar la página
    cargarComentarios();

    // Manejar envío del formulario
    $('#form-comentario').on('submit', async function (event) {
        event.preventDefault();
        const contenido = $('#contenido').val();

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
            $('#form-comentario')[0].reset(); // Limpiar formulario
        } catch (error) {
            console.error("Error al crear comentario:", error);
        }
    });

    // Función para cargar comentarios
    async function cargarComentarios() {
        try {
            const response = await fetch(`/api/comentarios/listar/${idPublicacion}`);
            const comentarios = await response.json();
            $('#lista-comentarios').empty(); // Limpiar lista
            mostrarComentarios(comentarios);
        } catch (error) {
            console.error("Error al obtener comentarios:", error);
        }
    }

    // Función para mostrar múltiples comentarios
    function mostrarComentarios(comentarios) {
        comentarios.forEach(comentario => mostrarComentario(comentario));
    }

    // Función para mostrar un solo comentario
    function mostrarComentario(comentario, agregarAlInicio = false) {
        const comentarioHTML = `
            <li data-id="${comentario.id}" class="comentario-item">
                <div class="comentario">
                    <div class="avatar">•</div>
                    <div class="contenido-comentario">
                        <p class="usuario">${comentario.usuario || "Usuario desconocido"}</p>
                        <p class="texto">${comentario.contenido || "Contenido no disponible"}</p>
                        <a href="#" class="responder">Responder</a>
                        <p class="fecha">${comentario.fecha || "Fecha no disponible"}</p>
                    </div>
                </div>
            </li>
        `;

        if (agregarAlInicio) {
            $('#lista-comentarios').prepend(comentarioHTML);
        } else {
            $('#lista-comentarios').append(comentarioHTML);
        }
    }
});
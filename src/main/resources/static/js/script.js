document.addEventListener("DOMContentLoaded", async () => {
    await cargarNoticias();

    document.getElementById("form-noticia").addEventListener("submit", async (event) => {
        event.preventDefault();
        const formData = new FormData(event.target);

        formData.append("titulo", document.getElementById("titulo").value);
        formData.append("subtitulo", document.getElementById("subtitulo").value);
        formData.append("contenido", document.getElementById("contenido").value);
        formData.append("categoria", document.getElementById("categoria").value);
        formData.append("archivoAdjunto", document.getElementById("archivoAdjunto").files[0]);
        formData.append("urlPublicacion", document.getElementById("urlPublicacion").value);
        formData.append("fechPublicacion", document.getElementById("fechPublicacion").value);
        formData.append("idAdmin", document.getElementById("idAdmin").value);

        try {
            const response = await fetch("/noticias/crear", {
                method: "POST",
                body: formData
            });

            if (response.ok) {
                document.getElementById("resultado").textContent = "Noticia creada con éxito.";
                await cargarNoticias();
                event.target.reset();
            } else {
                document.getElementById("resultado").textContent = "Error al crear la noticia.";
            }
        } catch (error) {
            document.getElementById("resultado").textContent = "Error: " + error.message;
        }
    });
});

// Función para cargar todas las noticias
async function cargarNoticias() {
    try {
        const response = await fetch("/noticias/todas");
        const noticias = await response.json();

        const listaNoticias = document.getElementById("noticias-lista-items");
        listaNoticias.innerHTML = ''; // Limpia la lista

        noticias.forEach(noticia => {
            const noticiaItem = document.createElement("li");
            noticiaItem.classList.add("noticia-item");

            // Mostrar solo el título
            const noticiaTitulo = document.createElement("span");
            noticiaTitulo.textContent = noticia.titulo;
            noticiaTitulo.style.marginRight = "10px"; // Espaciado entre título y botón

            // Botón eliminar
            const botonEliminar = document.createElement("button");
            botonEliminar.textContent = "Eliminar";
            botonEliminar.classList.add("eliminar-boton");
            botonEliminar.dataset.id = noticia.id;

            // Evento para eliminar noticia
            botonEliminar.addEventListener("click", async () => {
                const confirmacion = confirm("¿Estás seguro de que quieres eliminar esta noticia?");
                if (confirmacion) {
                    await eliminarNoticia(noticia.id);
                    await cargarNoticias();
                }
            });

            // Añadir título y botón al contenedor
            noticiaItem.appendChild(noticiaTitulo);
            noticiaItem.appendChild(botonEliminar);
            listaNoticias.appendChild(noticiaItem);
        });
    } catch (error) {
        console.error("Error al cargar noticias:", error);
    }
}

// Función para eliminar noticia
async function eliminarNoticia(id) {
    try {
        const response = await fetch(`/noticias/eliminar/${id}`, { method: "DELETE" });

        if (response.ok) {
            console.log(`Noticia con ID ${id} eliminada.`);
        } else {
            alert("No se pudo eliminar la noticia.");
        }
    } catch (error) {
        console.error("Error al eliminar noticia:", error);
        alert("Ocurrió un error al intentar eliminar la noticia.");
    }
}

// Cargar noticias al iniciar
document.addEventListener("DOMContentLoaded", cargarNoticias);

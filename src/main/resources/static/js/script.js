document.addEventListener("DOMContentLoaded", async () => {
    await cargarNoticias();

    // Manejar el envío del formulario para crear una noticia
    document.getElementById("form-noticia").addEventListener("submit", async (event) => {
        event.preventDefault();
        const formData = new FormData(event.target);
        const fechPublicacion = document.getElementById("fechPublicacion").value;

        // Asegúrate de que todos los campos estén incluidos en el FormData
        formData.append("titulo", document.getElementById("titulo").value);
        formData.append("subtitulo", document.getElementById("subtitulo").value);
        formData.append("contenido", document.getElementById("contenido").value);
        formData.append("categoria", document.getElementById("categoria").value);
        formData.append("archivoAdjunto", document.getElementById("archivoAdjunto").files[0]);
        formData.append("urlPublicacion", document.getElementById("urlPublicacion").value);
        formData.append("fechPublicacion", fechPublicacion);
        formData.append("idAdmin", document.getElementById("idAdmin").value);

        try {
            const response = await fetch("/noticias/crear", {
                method: "POST",
                body: formData
            });

            if (response.ok) {
                const result = await response.json();
                document.getElementById("resultado").textContent = "Noticia creada con éxito: " + result.titulo;
                await cargarNoticias();
            } else {
                document.getElementById("resultado").textContent = "Error al crear la noticia.";
            }
        } catch (error) {
            document.getElementById("resultado").textContent = "Ocurrió un error: " + error.message;
        }
    });
});

// Función para cargar todas las noticias
async function cargarNoticias() {
    try {
        const response = await fetch("/noticias/todas"); // Solicita todas las noticias
        const noticias = await response.json();
        
        const listaNoticias = document.getElementById("noticias-lista-items");
        listaNoticias.innerHTML = ''; // Limpia la lista antes de recargarla

        noticias.forEach(noticia => {
            console.log("Noticia obtenida:", noticia); // Verifica el JSON de cada noticia

            // Crear elementos para la lista
            const noticiaItem = document.createElement("li");
            noticiaItem.classList.add("noticia-item");

            const noticiaTitulo = document.createElement("span");
            noticiaTitulo.textContent = noticia.titulo; // Muestra solo el título de la noticia

            const botonEliminar = document.createElement("button");
            botonEliminar.textContent = "Eliminar";
            botonEliminar.classList.add("eliminar-boton");
            botonEliminar.dataset.id = noticia.id; // Asigna el ID de la noticia

            // Evento para eliminar la noticia
            botonEliminar.addEventListener("click", async () => {
                const id = botonEliminar.dataset.id;

                if (!id || id === "undefined" || id === "") {
                    console.error("Error: ID no válido.");
                    alert("ID de noticia no válido.");
                    return;
                }

                const confirmacion = confirm("¿Estás seguro de que quieres eliminar esta noticia?");
                if (confirmacion) {
                    console.log("Eliminando noticia con ID:", id);
                    await eliminarNoticia(id);
                    await cargarNoticias(); // Recarga la lista después de eliminar
                }
            });

            // Añadir los elementos al contenedor
            noticiaItem.appendChild(noticiaTitulo);
            noticiaItem.appendChild(botonEliminar);
            listaNoticias.appendChild(noticiaItem);
        });
    } catch (error) {
        console.error("Error al cargar las noticias:", error);
    }
}

// Función para eliminar una noticia
async function eliminarNoticia(id) {
    try {
        const response = await fetch(`/noticias/eliminar/${id}`, {
            method: "DELETE"
        });

        if (response.ok) {
            console.log(`Noticia con ID ${id} eliminada correctamente.`);
        } else {
            console.error("Error al eliminar la noticia.");
            alert("No se pudo eliminar la noticia.");
        }
    } catch (error) {
        console.error("Error al eliminar la noticia:", error);
        alert("Ocurrió un error al intentar eliminar la noticia.");
    }
}

// Cargar noticias al iniciar
document.addEventListener("DOMContentLoaded", cargarNoticias);

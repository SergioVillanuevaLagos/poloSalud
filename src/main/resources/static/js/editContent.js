document.getElementById("form-noticia").addEventListener("submit", async (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);

    // Convertir la fecha al formato 'yyyy-MM-dd' para enviarla correctamente
    const fechPublicacion = document.getElementById("fechPublicacion").value;
    
    // Creamos un objeto con los datos del formulario
    const noticia = {
        titulo: formData.get("titulo"),
        subtitulo: formData.get("subtitulo"),
        contenido: formData.get("contenido"),
        categoria: formData.get("categoria"),
        archivoAdjunto: formData.get("archivoAdjunto"),
        urlPublicacion: formData.get("urlPublicacion"),
        fechPublicacion: fechPublicacion, // Usamos el valor de la fecha directamente
        idAdmin: formData.get("idAdmin")
    };

    try {
        const response = await fetch("/noticias/crear", {
            method: "POST",
            body: formData // Enviamos los datos como multipart/form-data
        });

        if (response.ok) {
            const result = await response.json();
            document.getElementById("resultado").textContent = "Noticia creada con éxito: " + result.titulo;
        } else {
            document.getElementById("resultado").textContent = "Error al crear la noticia.";
        }
    } catch (error) {
        document.getElementById("resultado").textContent = "Ocurrió un error: " + error.message;
    }
});

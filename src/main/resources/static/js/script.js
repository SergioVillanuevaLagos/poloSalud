document.addEventListener("DOMContentLoaded", async () => {
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
            } else {
                document.getElementById("resultado").textContent = "Error al crear la noticia.";
            }
        } catch (error) {
            document.getElementById("resultado").textContent = "Error: " + error.message;
        }
    });
});

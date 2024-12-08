// Abrir el modal de edición
function openModal() {
    const modal = document.getElementById('editModal');
    modal.style.display = 'block';
    modal.classList.add('show');
    modal.classList.remove('hide');

    // Mostrar todos los campos y precargar valores actuales
    const newTitleField = document.getElementById('newTitleInput');
    const newSubtitleField = document.getElementById('newSubtitle');
    const newParagraphField = document.getElementById('newParagraph');
    const newCategoryField = document.getElementById('newCategory');
    const newUrlField = document.getElementById('newUrl');

    newTitleField.value = document.getElementById('title').textContent;
    newSubtitleField.value = document.getElementById('subtitle').textContent;
    newParagraphField.value = document.getElementById('paragraph').textContent;
    newCategoryField.value = document.getElementById('category').textContent;
    newUrlField.value = document.getElementById('url').textContent;
}

// Cerrar el modal de contenido
function closeModal() {
    const modal = document.getElementById('editModal');
    modal.classList.remove('show');
    modal.classList.add('hide');

    // Esperar el final de la animación para ocultar completamente
    setTimeout(() => {
        modal.style.display = 'none';
    }, 300); // Duración de la animación en milisegundos
}

// Guardar los cambios realizados en el modal de contenido
function saveChanges() {
    const noticiaId = 1; // Reemplaza con el ID de la noticia actual
    const newTitle = document.getElementById('newTitleInput').value;
    const newSubtitle = document.getElementById('newSubtitle').value;
    const newParagraph = document.getElementById('newParagraph').value;
    const newCategory = document.getElementById('newCategory').value;
    const newUrl = document.getElementById('newUrl').value;

    // Validar campos obligatorios
    if (newTitle && newSubtitle && newParagraph && newCategory && newUrl) {
        // Actualizar dinámicamente el contenido en la página
        document.getElementById('title').textContent = newTitle;
        document.getElementById('subtitle').textContent = newSubtitle;
        document.getElementById('paragraph').textContent = newParagraph;
        document.getElementById('category').textContent = newCategory;
        document.getElementById('url').textContent = newUrl;

        // Construir objeto para la actualización en el servidor
        const noticia = {
            titulo: newTitle,
            subtitulo: newSubtitle,
            contenido: newParagraph,
            categoria: newCategory,
            urlPublicacion: newUrl
        };

        // Enviar la actualización al servidor
        fetch(`/noticias/actualizar/${noticiaId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(noticia)
        })
        .then(response => response.json())
        .then(data => {
            console.log('Noticia actualizada:', data);
            closeModal(); // Cerrar el modal tras guardar exitosamente
        })
        .catch(error => {
            console.error('Error al actualizar la noticia:', error);
            closeModal(); // Cerrar el modal incluso si ocurre un error
        });
    } else {
        alert('Por favor, completa todos los campos antes de guardar.');
    }
}
// Variable para determinar qué se está editando
let currentEditType = ''; 

// Abrir el modal de edición
function openModal(editType) {
    currentEditType = editType;
    const modal = document.getElementById('editModal');
    modal.style.display = 'block';
    modal.classList.add('show');
    modal.classList.remove('hide');

    // Mostrar u ocultar campos según lo que se está editando
    const newTitleField = document.getElementById('newTitleInput');
    const newSubtitleField = document.getElementById('newSubtitle');
    const newParagraphField = document.getElementById('newParagraph');

    if (editType === 'title') {
        newTitleField.style.display = 'block';
        newSubtitleField.style.display = 'none';
        newParagraphField.style.display = 'none';
        // Precargar el valor actual del título
        newTitleField.value = document.getElementById('title').textContent;
    } else {
        newTitleField.style.display = 'none';
        newSubtitleField.style.display = 'block';
        newParagraphField.style.display = 'block';
        // Precargar valores actuales del subtítulo y párrafo
        newSubtitleField.value = document.getElementById('subtitle').textContent;
        newParagraphField.value = document.getElementById('paragraph').textContent;
    }
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

// Abrir el modal para editar título
function openTitleModal() {
    const modal = document.getElementById('editTitleModal');
    modal.classList.remove('hide'); 
    modal.style.display = 'block';
    modal.classList.add('show');

    // Retraso para garantizar que las animaciones no interfieran
    setTimeout(() => {
        const newTitleField = document.getElementById('newTitleInput');
        newTitleField.style.display = 'block'; // Asegura que sea visible
        newTitleField.value = document.getElementById('title').textContent; // Precargar el título actual
    }, 300); // Coincide con la duración de la animación
}

// Cerrar el modal para editar título
function closeTitleModal() {
    const modal = document.getElementById('editTitleModal');
    modal.classList.remove('show');
    modal.classList.add('hide');

    // Usar un temporizador para ocultar después de la animación
    setTimeout(() => {
        modal.style.display = 'none';
    }, 300); 
}

// Guardar cambios del título
function saveTitleChanges() {
    const newTitle = document.getElementById('newTitleInput').value; // Corregido el ID
    if (newTitle) {
        document.getElementById('title').textContent = newTitle; // Actualizar el título
        // Llamar al servicio para actualizar el título en el servidor
        const noticiaId = 1; // Reemplaza con el ID de la noticia actual
        fetch(`/noticias/actualizarTitulo/${noticiaId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newTitle)
        })
        .then(response => response.json())
        .then(data => {
            console.log('Título actualizado:', data);
        })
        .catch(error => {
            console.error('Error al actualizar el título:', error);
        });
    }
    closeTitleModal(); // Cerrar el modal tras guardar
}

// Guardar los cambios realizados en el modal de contenido
function saveChanges() {
    const noticiaId = 1; // Reemplaza con el ID de la noticia actual
    if (currentEditType === 'title') {
        const newTitle = document.getElementById('newTitleInput').value;
        if (newTitle) {
            document.getElementById('title').textContent = newTitle;
            // Llamar al servicio para actualizar el título en el servidor
            fetch(`/noticias/actualizarTitulo/${noticiaId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newTitle)
            })
            .then(response => response.json())
            .then(data => {
                console.log('Título actualizado:', data);
            })
            .catch(error => {
                console.error('Error al actualizar el título:', error);
            });
        }
    } else {
        const newSubtitle = document.getElementById('newSubtitle').value;
        const newParagraph = document.getElementById('newParagraph').value;

        if (newSubtitle && newParagraph) {
            document.getElementById('subtitle').textContent = newSubtitle;
            document.getElementById('paragraph').textContent = newParagraph;
            // Llamar al servicio para actualizar el subtítulo y contenido en el servidor
            const noticia = {
                subtitulo: newSubtitle,
                contenido: newParagraph
            };
            fetch(`/noticias/actualizarSubtituloYContenido/${noticiaId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(noticia)
            })
            .then(response => response.json())
            .then(data => {
                console.log('Subtítulo y contenido actualizados:', data);
            })
            .catch(error => {
                console.error('Error al actualizar el subtítulo y contenido:', error);
            });
        }
    }
    closeModal(); // Cerrar el modal tras guardar
}

// Guardar la noticia
function saveNews() {
    const title = document.getElementById('title').textContent;
    const subtitle = document.getElementById('subtitle').textContent;
    const content = document.getElementById('paragraph').textContent;
    const publicationDate = new Date().toISOString().split('T')[0]; // Fecha de publicación actual

    const noticia = {
        titulo: title,
        subtitulo: subtitle,
        contenido: content,
        categoria: "General", // Puedes cambiar esto según sea necesario
        archivoAdjunto: "",
        urlPublicacion: "",
        fechPublicacion: publicationDate, // Agregar la fecha de publicación
        idAdmin: 1 // Reemplaza con el ID del administrador actual
    };

    fetch('/noticias/crear', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(noticia)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Noticia creada:', data);
        alert('Noticia guardada exitosamente');
    })
    .catch(error => {
        console.error('Error al guardar la noticia:', error);
        alert('Error al guardar la noticia');
    });
}
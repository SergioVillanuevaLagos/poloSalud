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
    const newCategoryField = document.getElementById('newCategory');
    const newFileField = document.getElementById('newFile');
    const newUrlField = document.getElementById('newUrl');
    const newDateField = document.getElementById('newDate');

    if (editType === 'title') {
        newTitleField.style.display = 'block';
        newSubtitleField.style.display = 'none';
        newParagraphField.style.display = 'none';
        newCategoryField.style.display = 'none';
        newFileField.style.display = 'none';
        newUrlField.style.display = 'none';
        newDateField.style.display = 'none';
        // Precargar el valor actual del título
        newTitleField.value = document.getElementById('title').textContent;
    } else {
        newTitleField.style.display = 'none';
        newSubtitleField.style.display = 'block';
        newParagraphField.style.display = 'block';
        newCategoryField.style.display = 'block';
        newFileField.style.display = 'block';
        newUrlField.style.display = 'block';
        newDateField.style.display = 'block';
        // Precargar valores actuales del subtítulo, párrafo, categoría, URL y fecha
        newSubtitleField.value = document.getElementById('subtitle').textContent;
        newParagraphField.value = document.getElementById('paragraph').textContent;
        newCategoryField.value = document.getElementById('category').textContent;
        newUrlField.value = document.getElementById('url').textContent;
        newDateField.value = document.getElementById('date').textContent;
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
    const newSubtitle = document.getElementById('newSubtitle').value;
    const newParagraph = document.getElementById('newParagraph').value;
    const newCategory = document.getElementById('newCategory').value;
    const newFile = document.getElementById('newFile').files[0];
    const newUrl = document.getElementById('newUrl').value;
    const newDate = document.getElementById('newDate').value;

    if (newSubtitle && newParagraph && newCategory && newUrl && newDate) {
        document.getElementById('subtitle').textContent = newSubtitle;
        document.getElementById('paragraph').textContent = newParagraph;
        document.getElementById('category').textContent = newCategory;
        document.getElementById('url').textContent = newUrl;
        document.getElementById('date').textContent = newDate;

        // Llamar al servicio para actualizar la noticia en el servidor
        const noticia = {
            subtitulo: newSubtitle,
            contenido: newParagraph,
            categoria: newCategory,
            archivoAdjunto: newFile ? newFile.name : "",
            urlPublicacion: newUrl,
            fechPublicacion: newDate
        };

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
        })
        .catch(error => {
            console.error('Error al actualizar la noticia:', error);
        });
    }
    closeModal(); // Cerrar el modal tras guardar
}

// Guardar la noticia
function saveNews() {
    const title = document.getElementById('title').textContent;
    const subtitle = document.getElementById('subtitle').textContent;
    const content = document.getElementById('paragraph').textContent;
    const category = document.getElementById('category').textContent;
    const file = document.getElementById('newFile').files[0];
    const url = document.getElementById('url').textContent;
    const publicationDate = document.getElementById('date').textContent;

    const noticia = {
        titulo: title,
        subtitulo: subtitle,
        contenido: content,
        categoria: category,
        archivoAdjunto: file ? file.name : "",
        urlPublicacion: url,
        fechPublicacion: publicationDate,
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
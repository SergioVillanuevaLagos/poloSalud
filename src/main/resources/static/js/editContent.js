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
    }
    closeTitleModal(); // Cerrar el modal tras guardar
}


f// Guardar los cambios realizados en el modal de contenido
function saveChanges() {
    if (currentEditType === 'title') {
        const newTitle = document.getElementById('newTitleInput').value;
        if (newTitle) {
            document.getElementById('title').textContent = newTitle;
        }
    } else {
        const newSubtitle = document.getElementById('newSubtitle').value;
        const newParagraph = document.getElementById('newParagraph').value;

        if (newSubtitle) {
            document.getElementById('subtitle').textContent = newSubtitle;
        }

        if (newParagraph) {
            // Convertir saltos de línea a <p> para mantener el formato
            const paragraphContainer = document.getElementById('paragraph');
            const paragraphs = newParagraph.split('\n'); // Dividir en líneas
            paragraphContainer.innerHTML = paragraphs
                .map(line => `<p>${line.trim()}</p>`) // Crear un párrafo por línea
                .join(''); // Combinar los párrafos en un solo contenido
        }
    }
    closeModal(); // Cerrar el modal tras guardar
}

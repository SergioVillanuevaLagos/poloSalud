// Código para scroll hacia el principio de la página
document.addEventListener('DOMContentLoaded', function () {
    const scrollToTopButton = document.querySelector('.bottom-right-image');

    scrollToTopButton.addEventListener('click', function (event) {
        event.preventDefault();
        window.scrollTo({ top: 0, behavior: 'smooth' });
    });
});

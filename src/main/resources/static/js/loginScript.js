document.addEventListener("DOMContentLoaded", function() {
    // Limpiar los campos del formulario de inicio de sesión
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.reset();
    }

    // Limpiar los campos del formulario de registro
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.reset();
    }

    // Event listeners para los formularios
    if (loginForm) {
        loginForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const formData = new FormData(this);
            const user = {
                correoElectronico: formData.get('correoElectronico'),
                contraseña: formData.get('contraseña')
            };
            fetch('/polo/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(user)
            })
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    if (data.status === 'success') {
                        window.location.href = '/polosalud/index';
                    } else {
                        document.getElementById('loginError').textContent = data.message;
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    document.getElementById('loginError').textContent = 'Error interno del servidor';
                })
                .finally(() => {
                    // Limpiar los campos del formulario de inicio de sesión
                    this.reset();
                });
        });
    }

    if (registerForm) {
        registerForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const formData = new FormData(this);
            const user = {
                nombreUsuario: formData.get('nombreUsuario'),
                correoElectronico: formData.get('correoElectronico'),
                contraseña: formData.get('contraseña'),
                rol: {
                    idRol: 2,
                    nombreRol: "usuario"
                },
                idDepartamento: null
            };

            // Validación de la contraseña
            if (!validatePassword(user.contraseña)) {
                document.getElementById('registerError').textContent = 'La contraseña debe tener al menos 8 caracteres, incluyendo al menos una letra mayúscula, una letra minúscula y un número.';
                return;
            }

            fetch('/polo/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(user)
            })
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    if (data.status === 'success') {
                        window.location.href = '/polosalud/index';
                        alert(data.message);
                    } else {
                        document.getElementById('registerError').textContent = data.message;
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    document.getElementById('registerError').textContent = 'Error interno del servidor';
                })
                .finally(() => {
                    // Limpiar los campos del formulario de registro
                    this.reset();
                });
        });
    }

    // Event listeners para los botones de cambio de formulario
    const btnSignIn = document.getElementById("btn-sign-in");
    const btnSignUp = document.getElementById("btn-sign-up");
    const container = document.querySelector(".container");

    if (btnSignIn) {
        btnSignIn.addEventListener("click", () => {
            container.classList.remove("toggle");
        });
    }

    if (btnSignUp) {
        btnSignUp.addEventListener("click", () => {
            container.classList.add("toggle");
        });
    }

    // Función para validar la contraseña
    function validatePassword(password) {
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
        return passwordRegex.test(password);
    }
});
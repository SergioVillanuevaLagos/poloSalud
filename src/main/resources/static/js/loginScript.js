const container = document.querySelector(".container");
const btnSignIn = document.getElementById("btn-sign-in");
const btnSignUp = document.getElementById("btn-sign-up");

btnSignIn.addEventListener("click", () => {
    container.classList.remove("toggle");
});

btnSignUp.addEventListener("click", () => {
    container.classList.add("toggle");
});

document.getElementById('loginForm').addEventListener('submit', function(event) {
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
            // Aquí puedes manejar la respuesta del servidor
            if (data.status === 'success') {
                window.location.href = '/polosalud/index';
            } else {
                document.getElementById('loginError').textContent = data.message;
            }
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('loginError').textContent = 'Error interno del servidor';
        });
});

document.getElementById('registerForm').addEventListener('submit', function(event) {
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
            // Aquí puedes manejar la respuesta del servidor
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
        });
});
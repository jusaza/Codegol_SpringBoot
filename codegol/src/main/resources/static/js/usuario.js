document.addEventListener("DOMContentLoaded", function () {

    const inputBusqueda = document.getElementById("buscarUsuario");
    const filas = document.querySelectorAll("tbody tr");

    inputBusqueda.addEventListener("keyup", function () {
        const texto = inputBusqueda.value.toLowerCase();

        filas.forEach(fila => {
            const correo = fila.children[0].textContent.toLowerCase();
            const nombre = fila.children[1].textContent.toLowerCase();
            const identificacion = fila.children[2].textContent.toLowerCase();

            if (
                correo.includes(texto) ||
                nombre.includes(texto) ||
                identificacion.includes(texto)
            ) {
                fila.style.display = "";
            } else {
                fila.style.display = "none";
            }
        });
    });
});


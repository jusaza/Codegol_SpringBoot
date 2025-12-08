// Variables inyectadas desde Thymeleaf
// matriculasData, asistenciasData, tiposAsistencia, idEntrenamiento

// Lista de matriculas ya agregadas para evitar duplicados
let matriculasAgregadas = new Set();

function agregarFila() {
    const select = document.getElementById("selectMatricula");
    const idMatricula = select.value;

    if (!idMatricula) {
        alert("Debe seleccionar un jugador.");
        return;
    }

    if (matriculasAgregadas.has(idMatricula)) {
        alert("Este jugador ya fue agregado.");
        return;
    }

    const matricula = matriculasData.find(m => m.id_matricula == idMatricula);
    if (!matricula) return;

    // Revisar si ya existe asistencia
    let asistenciaExistente = asistenciasData?.find(a => a.matricula.id_matricula == idMatricula);

    // Crear fila
    const tbody = document.getElementById("tablaAsistencia").querySelector("tbody");
    const tr = document.createElement("tr");

    tr.innerHTML = `
        <form action="/asistencia/guardar" method="post">
            <input type="hidden" name="id_entrenamiento" value="${idEntrenamiento}">
            <input type="hidden" name="id_matricula" value="${idMatricula}">

            <td>${matricula.id_jugador.nombre_completo}</td>

            <td>
                <select name="tipo" required>
                    <option value="">Seleccione</option>
                    ${tiposAsistencia.map(t => `
                        <option value="${t}" ${asistenciaExistente && asistenciaExistente.tipoAsistencia == t ? "selected" : ""}>${t}</option>
                    `).join("")}
                </select>
            </td>

            <td>
                <textarea name="just" rows="1">${asistenciaExistente ? asistenciaExistente.justificacion : ""}</textarea>
            </td>

            <td>
                <textarea name="obs" rows="1">${asistenciaExistente ? asistenciaExistente.observaciones : ""}</textarea>
            </td>

            <td>
                <button type="submit" style="background-color: #4CAF50; color:white;">Guardar</button>
            </td>

            <td>
                <button type="button" style="color:red;" onclick="eliminarFila(this)">Eliminar</button>
            </td>
        </form>
    `;

    tbody.appendChild(tr);

    // Marcar como agregado
    matriculasAgregadas.add(idMatricula);

    // Reset select
    select.value = "";
}

// Eliminar fila de la tabla
function eliminarFila(btn) {
    const tr = btn.closest("tr");
    const idMatricula = tr.querySelector("input[name='id_matricula']").value;

    // Eliminar del DOM
    tr.remove();

    // Quitar de la lista de agregadas
    matriculasAgregadas.delete(idMatricula);
}

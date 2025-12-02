
    const btnVerMas = document.querySelectorAll('.ver-mas');
    const ventanas = document.querySelectorAll('.ventana-flotante');

    btnVerMas.forEach(btn => {
      btn.addEventListener('click', () => {
        const id = btn.dataset.id;
        const ventana = document.getElementById('detalle-usuario-' + id);
        ventana.classList.add('active');
      });
    });

    window.addEventListener('click', function(e) {
      ventanas.forEach(ventana => {
        if (ventana.classList.contains('active') &&
            !ventana.contains(e.target) &&
            !e.target.classList.contains('ver-mas')) {
          ventana.classList.remove('active');
        }
      });
    });
document.addEventListener("DOMContentLoaded", function () {
    const menuToggle = document.querySelector(".menu-toggle");
    const navMenu = document.querySelector(".nav-menu");

    // Toggle para el menú hamburguesa
    menuToggle.addEventListener("click", function () {
        navMenu.classList.toggle("show");
        console.log('Menu toggled');
    });
});

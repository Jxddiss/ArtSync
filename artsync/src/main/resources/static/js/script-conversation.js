document.addEventListener("DOMContentLoaded", function () {
    const cartes = document.querySelectorAll(".carte");
    setTimeout(function () {
        cartes.forEach(function (carte, index) {
            setTimeout(function () {
                carte.classList.toggle("animation");
            }, index * 100);
        });
    }, 700);
});
const add= document.getElementById('add');
const inputAnnonce = document.getElementById('inputAnnonce');
let open = false
if (add){
    add.addEventListener('click', function() {
        if (!open) {
            gsap.to(inputAnnonce, {opacity: 1, width: '100%'});
            open = true;
        }else {
            gsap.to(inputAnnonce, {opacity: 0, width: '0%'});
            open = false;
        }
    });
}

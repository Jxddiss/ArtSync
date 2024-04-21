const add= document.getElementById('add');
const inputAnnonce = document.getElementById('inputAnnonce');
const newfilePreview = document.getElementById('newfilePreview');
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
if(newfilePreview){
    const fileInput = document.getElementById('filtInput');
    fileInput.addEventListener('change', function(){
        const file = fileInput.files[0];
        if(file){
            const reader = new FileReader();
            reader.onload = function(){
                const result = reader.result;
                newfilePreview.src = result;
            }
            reader.readAsDataURL(file);
        }
    });
}

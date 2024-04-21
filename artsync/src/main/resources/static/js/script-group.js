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

const openFichier = document.getElementById("openFichier")
if (openFichier){
    const dialogFichier = document.getElementById("dialogFichier")
    const close = document.getElementById("closeDialogFichier")
    openFichier.addEventListener("click", () => {
        dialogFichier.showModal();
        dialogFichier.style.display = "flex"
    })

    close.addEventListener("click", () => {
        dialogFichier.close();
        dialogFichier.style.display = "none"
    })
}

const imgApercuFile = document.getElementById("imgApercuFile")
if (imgApercuFile){
    const fileInput = document.getElementById('fileUploadInput');
    fileInput.addEventListener('change', function(){
        const file = fileInput.files[0];
        if(file){
            const reader = new FileReader();
            reader.onload = function(){
                const result = reader.result;
                imgApercuFile.src = result;
            }
            reader.readAsDataURL(file);
        }
    });
}
const tacheHolder = document.getElementById('taskShowScroller');
if (tacheHolder){
    const children = tacheHolder.children
    gsap.to(children, {duration: 0.5, opacity: 1, scale:1, delay: 0.15, stagger: 0.1, ease:'back', filter: 'blur(0px)'})
}
const userHolder = document.getElementById('userHolderScroller');
if (userHolder){
    const children = userHolder.children
    gsap.to(children, {duration: 0.5, opacity: 1, delay: 0.15, stagger: 0.1, ease:'back', filter: 'blur(0px)'})
}

const folderHolder = document.getElementById('fichiersHolder');
if (folderHolder){
    const children = folderHolder.children
    gsap.to(children, {duration: 0.5, opacity: 1, scale: 1, delay: 0.15, stagger: 0.1, ease:'back', filter: 'blur(0px)'})
}
const fichierHolder = document.getElementById('fichiersHolder');
if (fichierHolder){
    const children = fichierHolder.children
    gsap.to(children, {duration: 0.5, opacity: 1, scale: 1, delay: 0.15, stagger: 0.1, ease:'back', filter: 'blur(0px)'})
}

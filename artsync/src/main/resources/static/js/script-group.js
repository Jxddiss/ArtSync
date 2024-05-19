const add= document.getElementById('add');
const inputAnnonce = document.getElementById('inputAnnonce');
const newfilePreview = document.getElementById('newfilePreview');
let openAnnonce = false
if (add){
    add.addEventListener('click', function() {
        if (!openAnnonce) {
            gsap.to(inputAnnonce, {opacity: 1, width: '100%'});
            openAnnonce = true;
        }else {
            gsap.to(inputAnnonce, {opacity: 0, width: '0%'});
            openAnnonce = false;
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
const demandeHolder = document.getElementById("demandeHolderScroller")
if (demandeHolder){
    const children = demandeHolder.children
    gsap.to(children, {duration: 0.5, opacity: 1, delay: 0.15, stagger: 0.1, ease:'back', filter: 'blur(0px)'})
}

const vNavBar = document.querySelector(".verticalNavbar")
const verticalBtn = document.querySelector(".verticalButton")
const projectContent = document.querySelector(".projectContent")
let openNavbar = false
verticalBtn.addEventListener("click",function (){
    if (!openNavbar){
        openNavbar = true
        gsap.to(vNavBar,{
            display:"flex"
        })
        gsap.to(vNavBar,{
            width:"100%",
            opacity:1,
            duration:0.65,
            ease:"back"
        })
        gsap.to(projectContent,{
            width:"0%",
            opacity:0,
            duration:0.25,
        })
        gsap.to(projectContent,{
            display:"none",
            delay:0,
        })
        gsap.to(verticalBtn,{
            rotate:180,
            ease:"back"
        })
    }else{
        openNavbar = false
        gsap.to(vNavBar,{
            width:"0%",
            opacity:0,
            duration:0.25,
        })
        gsap.to(projectContent,{
            width:"100%",
            opacity:1,
            duration:0.65,
            ease:"back"
        })
        gsap.to(verticalBtn,{
            rotate:0,
            ease:"back"
        })
        gsap.to(projectContent,{
            display:"flex",
            delay:0,
        })
    }
})

const annonceBar = document.querySelector(".projectContainer2")
const annonceBtn = document.querySelector(".annonceBtn")
let openAnnonceMobile = false

annonceBtn.addEventListener("click",function (){
    if (!openAnnonceMobile){
        openAnnonceMobile = true
        gsap.to(annonceBar,{
            display:"flex"
        })
        gsap.to(annonceBar,{
            width:"100%",
            paddingRight:"10%",
            opacity:1,
            duration:0.65,
            ease:"back"
        })
        gsap.to(projectContent,{
            width:"55%",
            duration:0.25,
        })
        gsap.to(projectContent,{
            delay:0,
        })
        gsap.to(annonceBtn,{
            rotate:180,
            ease:"back"
        })
    }else{
        openAnnonceMobile = false
        gsap.to(annonceBar,{
            width:"0%",
            opacity:0,
            duration:0.25,
        })
        gsap.to(annonceBar,{
            display:"none"
        })
        gsap.to(projectContent,{
            width:"100%",
            opacity:1,
            duration:0.65,
            ease:"back"
        })
        gsap.to(annonceBtn,{
            rotate:0,
            ease:"back"
        })
    }
    console.log(openAnnonceMobile)
})

function deleteFichier(fichier,ficherId){
    console.log(ficherId)
    $.ajax({
        type: "DELETE",
        url: window.location.origin.toString()+"/api/fichier/delete",
        data: {fichierId: ficherId, origin:"groupe"},
        success : function (data){
            if(data === "Success"){
                console.log("FiCHIER EFFACÉ")
                fichier.remove()
            }else{
                console.log("FiCHIER FAILED")
            }
        }
    })
}


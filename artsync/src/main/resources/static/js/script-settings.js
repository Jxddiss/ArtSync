const settingHolder = document.querySelector(".settingHolder")
const persoHolder = document.querySelector(".persoHolder")
const compteButton = document.getElementById("compteSection")
const persoButton = document.getElementById("persoSection")
const sectionBtns = document.querySelectorAll(".sectionBtn")
const colorPicker = document.getElementById("colorPicker")
const pfpGlow = document.getElementById("pfpGlow")
const colorOutput = document.getElementById("colorOutput")
const backgroundOptions = document.querySelectorAll(".backgroundOption")
const textureOptions = document.querySelectorAll(".textureOption")
const form = document.getElementById("profilForm")
const accountBodies = document.querySelectorAll(".accountBody")
let localColor = "rgba(0,0,0,0)"
compteButton.addEventListener("click",function(){
    removeAllSelected()
    compteButton.classList.add("selected")
    settingHolder.style.display = "flex"
    persoHolder.style.display = "none"
})
persoButton.addEventListener("click",function(){
    removeAllSelected()
    persoButton.classList.add("selected")
    persoHolder.style.display = "flex"
    settingHolder.style.display = "none"
})

if (localStorage.getItem("glowColor")){
    localColor = localStorage.getItem("glowColor")
    pfpGlow.style.boxShadow = "0px 0px 19px 1px"+localColor
    colorPicker.value = localColor
    colorOutput.textContent = localColor
}
colorPicker.addEventListener("change", function (){
    pfpGlow.style.boxShadow = "0px 0px 19px 1px"+colorPicker.value
    colorOutput.textContent = colorPicker.value
    localStorage.setItem("glowColor", colorPicker.value)
    document.querySelector(':root').style.setProperty('--glow_color', localStorage.getItem("glowColor"))
})
function removeAllSelected(){
    sectionBtns.forEach(btn => {
        btn.classList.remove("selected")
    })
}
if (document.body.classList.item(1).split("-")[0]==="night"){
    form.style.color = "black"
}
let backgroundColor = ""
let backgroundTexture = ""
backgroundOptions.forEach(background=>{
    background.addEventListener("click",function (){
        document.body.style.background = window.getComputedStyle(background).getPropertyValue("background")
        const string = background.getAttribute("data-colorValue")
        if (string.split("-")[0]==="night"){
            document.body.classList.add("whiteFont")
            accountBodies.forEach(accountbody=>{
                accountbody.classList.add("darkBody")
            })
            form.style.color = "black"
        }else{
            accountBodies.forEach(accountbody=>{
                accountbody.classList.remove("darkBody")
            })
            document.body.classList.remove("whiteFont")
        }
        backgroundColor = string
        sendBackgroundData()
    })
})
const overlay1 = document.querySelector(".overlay")
textureOptions.forEach(texture=>{
    texture.addEventListener("click",function (){
        overlay1.style.backgroundImage = window.getComputedStyle(texture).getPropertyValue("background-image").toString()
        overlay1.style.display = "block"
        overlay1.style.height = window.getComputedStyle(document.body).getPropertyValue("height")
        backgroundTexture = texture.getAttribute("data-textureValue")
        sendBackgroundData()
    })
})

function sendBackgroundData(){

    $.ajax({
        type: "POST",
        url: window.location.origin.toString()+"/api/update/user-background",
        data: {backgroundColor: backgroundColor,backgroundTexture:backgroundTexture},
        success : function (data) {
            if(data!=="false"){
                console.log(data)

            }else{
                console.log("Failed")
            }
        },
    })
}

let email = document.getElementById("emailInput");
email.addEventListener("keyup",()=>{
    validateEmail(email)
});

let pseudo = document.getElementById("pseudoInput");
pseudo.addEventListener("keyup",()=>{
    validatePseudo(pseudo)
});

function validateForm(form){

    let password = document.getElementById("passwordInput");
    let conf = document.getElementById("confirmInput");
    let valid = true

    if(conf){
        if(password.value !== conf.value){
            showNotification({
                type: 'warn',
                message: 'Les mots de passes ne sont pas pareil',
                titre: 'Avertissement',
            })
            valid = false;
        }

        if(!validatePseudo(pseudo)){
            showNotification({
                type: 'warn',
                message: 'Ce pseudo existe déjà ',
                titre: 'Avertissement',
            })
            valid = false;
        }

        if(!validateEmail(email)){
            showNotification({
                type: 'warn',
                message: 'Cet email est déja utilisé',
                titre: 'Avertissement',
            })
            valid = false;
        }
    }
    return valid;
}

function validateEmail(input){
    let valid = false
    $.ajax({
        type:'POST',
        async:false,
        url:window.location.origin.toString() + "/api/utilisateur/check-email",
        data: {email:input.value, userId:userId},
        success: function (data){
            if(data !== "true"){
                input.style.boxShadow = `rgba(116, 9, 34, 0.44) 0px -10px 25px,
                    rgba(136, 5, 5, 0.33) 0px 25px 25px,
                    rgba(107, 3, 27, 0.17) 0px 10px 10px,
                    rgba(220, 16, 48, 0.77) 0px -10px 10px`;
            }else{
                input.style.boxShadow = "";
                valid = true;
            }
        }
    })
    console.log("email valid : "+ valid)
    return valid;
}


function validatePseudo(input){
    let valid = false
    $.ajax({
        type:'POST',
        async:false,
        url:window.location.origin.toString() + "/api/utilisateur/check-pseudo",
        data: {pseudo:input.value, userId:userId},
        success: function (data){
            if(data !== "true"){
                input.style.boxShadow = `rgba(116, 9, 34, 0.44) 0px -10px 25px,
                    rgba(136, 5, 5, 0.33) 0px 25px 25px,
                    rgba(107, 3, 27, 0.17) 0px 10px 10px,
                    rgba(220, 16, 48, 0.77) 0px -10px 10px`;
            }else{
                input.style.boxShadow = "";
                valid = true
            }
        }
    })
    console.log("pseudo valid : "+ valid)
    return valid;
}

const vNavBar = document.querySelector(".verticalNavbar")
const expandBtn = document.getElementById("expandButton")
let openNavbar   = false
expandBtn.addEventListener("click",function (){
    if (!openNavbar){
        openNavbar = true

        gsap.to(vNavBar,{
            width:"100%",
            opacity:1,
            duration:0.65,
            ease:"back"
        })
        gsap.to(settingHolder,{
            width:"0%",
            opacity:0,
            duration:0.25,
        })
        gsap.to(persoHolder,{
            width:"0%",
            opacity:0,
            duration:0.25,
        })
        gsap.to(expandBtn,{
            rotate:180,
            ease:"back"
        })
    }else{
        openNavbar = false
        console.log(getSelectedSection())
        gsap.to(vNavBar,{
            width:"0%",
            opacity:0,
            duration:0.25,
        })
        gsap.to(document.querySelector("."+getSelectedSection()),{
            width:"100%",
            opacity:1,
            duration:0.65,
            ease:"back"
        })
        gsap.to(expandBtn,{
            rotate:0,
            ease:"back"
        })
    }
})

function getSelectedSection() {
    for (const btn of sectionBtns) {
        if (btn.classList.contains("selected")) {
            if (btn.id === "compteSection") {
                console.log(document.querySelector(".settingHolder"));
                return "settingHolder";
            } else {
                return "persoHolder";
            }
        }
    }
    return "";
}
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

textureOptions.forEach(texture=>{
    texture.addEventListener("click",function (){
        overlay.style.backgroundImage = window.getComputedStyle(texture).getPropertyValue("background-image").toString()
        overlay.style.display = "block"
        overlay.style.height = window.getComputedStyle(document.body).getPropertyValue("height")
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

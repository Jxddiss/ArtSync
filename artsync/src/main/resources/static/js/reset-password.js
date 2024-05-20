const demandeForm = document.getElementById("formulaire-reset");
const pwd = document.getElementById("pwd");
const conf = document.getElementById("conf");

if (demandeForm){
    demandeForm.addEventListener("submit", event =>{
        event.preventDefault();
        let input = demandeForm.querySelector("input");
        if (input.value.trim() !== ""){
            demandeForm.submit()
        }
    })
}


function validateFormPwdChange(){

    let valide = true;
    if(pwd.value !== conf.value){
        showNotification({
            type: 'warn',
            message: 'Les mots de passes ne sont pas pareil',
            titre: 'Avertissement',
        })
        valide = false;
    }

    if(!validatePassword(pwd)){
        showNotification({
            type: 'warn',
            message: "Le mot de passe n'est pas assez fort : " +
                "il doit avoir 8 charactères, une majuscule, une minuscule, " +
                "un chiffre et un charactère spécial",
            titre: 'Avertissement',
        })
        const notif = document.querySelector(".notification-pop-container");
        notif.style.animationDuration = "7s"
        valide = false;
    }

    return valide;
}

if (pwd){
    pwd.addEventListener("keyup",()=>{
        validatePassword(pwd)
    })
}

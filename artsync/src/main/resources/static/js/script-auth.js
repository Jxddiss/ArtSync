const container = document.body; // Or any other parent element
const loginBox = document.querySelector('.auth-box');
const imgBox = document.querySelector('.img-box');
let form = document.getElementById("main");
const pswdRegex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\S+$).{8,}$/;

//Fonction pour boutton connexion/inscription
container.addEventListener('click', function(event) {
    if (event.target.classList.contains('selected')) {
        return;
    }
    if (event.target.id === 'signUp' || event.target.id === 'connect') {
        event.target.classList.add('selected');
        
        // Apply the animation
        loginBox.style.animation = 'blur-in 0.25s forwards';
        imgBox.style.animation = 'blur-in 0.25s forwards';

        // Listen for the animationend event
        loginBox.addEventListener('animationend', switchContentAndClass);
        imgBox.addEventListener('animationend', switchContentAndClass);
    }

    if (event.target.id === 'signUp') {
        // showNotification({
        //     type: 'info',
        //     message: "Le mot de passe " +
        //         "doit avoir 8 charactères minimums, une majuscule, une minuscule, " +
        //         "un chiffre et un charactère spécial",
        //     titre: 'Info',
        // })
        // const notif = document.querySelector(".notification-pop-container");
        // notif.style.animationDuration = "7s"

        document.getElementById('connect').classList.remove('selected');
        form = document.getElementById("main");
        form.action = "/inscription";
        
        var titre = document.getElementById('tire-section');
        titre.textContent = "VOUS Y ETES PRESQUE";
        var confirmElement = document.getElementById('confirmation');
        confirmElement.textContent = "S'INSCRIRE";
        confirmElement.style.marginTop = "15%";
        confirmElement.style.width = "75%";
        confirmElement.type = "button";
        
        var input1 = document.getElementById('input-1');
        input1.setAttribute("type", "email");
        input1.setAttribute('placeholder', 'E-mail');
        input1.setAttribute("name", "email");
        input1.style.width = "100%";

        var input2 = document.getElementById('input-2');
        input2.setAttribute("type", "text");
        input2.setAttribute("placeholder", "Nom d'utilsateur");
        input2.setAttribute("name", "username");
        input2.style.width = "100%";

        var indice = document.getElementById('indice');
        indice.style.display = 'none';

        var inputElement = document.createElement('input');
        inputElement.setAttribute('type', 'password');
        inputElement.setAttribute('placeholder', 'Mot de passe');
        inputElement.setAttribute('name', 'mdp');
        inputElement.setAttribute('required', '');
        inputElement.setAttribute('id', 'input-3');
        inputElement.style.width = "100%";
        var parentDiv = document.getElementById('holder-input');
        parentDiv.append(inputElement, confirmElement);

        var confirmPassword = document.createElement('input');
        confirmPassword.setAttribute("id","input-confirm")
        confirmPassword.setAttribute('type', 'password');
        confirmPassword.setAttribute('placeholder', 'Confirmer');
        confirmPassword.setAttribute('name', 'conf');
        confirmPassword.setAttribute('required', '');
        confirmPassword.style.width = "100%";
        var parentDiv = document.getElementById('holder-input');
        parentDiv.append(confirmPassword, confirmElement);

        // Create a div element with class "row"
        var rowDiv = document.createElement("div");
        rowDiv.classList.add("row");
        rowDiv.classList.add("d-flex");
        rowDiv.classList.add("justify-content-center")
        rowDiv.classList.add("gap-1")
        rowDiv.setAttribute("id", "row-temp");
        // Create the first input field for "Nom"
        var nomInput = document.createElement("input");
        nomInput.setAttribute("type", "text");
        nomInput.setAttribute("placeholder", "Nom");
        nomInput.setAttribute("name", "nom");
        nomInput.setAttribute("id", "input-4");
        nomInput.setAttribute("required", "");
        nomInput.style.width = "45%";

        // Create the second input field for "Prénom"
        var prenomInput = document.createElement("input");
        prenomInput.setAttribute("type", "text");
        prenomInput.setAttribute("placeholder", "Prénom");
        prenomInput.setAttribute("name", "prenom");
        prenomInput.setAttribute("id", "input-5");
        prenomInput.setAttribute("required", "");
        prenomInput.style.width = "45%";
        // Append the input fields to the row div
        rowDiv.appendChild(nomInput);
        rowDiv.appendChild(prenomInput);

        parentDiv.insertBefore(rowDiv,input1);
        parentDiv.style.marginTop = "5%";

        document.addEventListener("keyup", checkInput);
    }

    else if (event.target.id === 'connect') {
        document.removeEventListener("keyup", checkInput)

        document.getElementById('holder-input').style.marginTop = "25%";
        document.getElementById('signUp').classList.remove('selected');
        form = document.getElementById("main");
        form.action = "/authentification";
        console.log(form.action);
        if (document.getElementById('input-3')) {

            document.getElementById('input-3').remove();
            document.getElementById('input-confirm').remove();
            document.getElementById('row-temp').remove();
    
            var input1 = document.getElementById('input-1');
            input1.style.width = "150%";
            input1.setAttribute("type", "text");
            var input2 = document.getElementById('input-2');
            input2.setAttribute("type", "password");
            input2.style.width = "150%";

            var titre = document.getElementById('tire-section');
            titre.textContent = "HEUREUX DE VOUS REVOIR";

            var indice = document.getElementById('indice');
            indice.style.display = 'inline';

            var input1 = document.getElementById('input-1');
            input1.setAttribute('placeholder', 'Utilisateur');
            input1.setAttribute("name", "username");

            var input2 = document.getElementById('input-2');
            input2.setAttribute("placeholder", "Mot de passe");
            input2.setAttribute("name", "mdp");

            var confirmElement = document.getElementById('confirmation');
            confirmElement.textContent = "SE CONNECTER";
            confirmElement.style.marginTop = "45%";
            confirmElement.style.width = "125%";
            confirmElement.type = "submit";

            var main = document.getElementById('main');
            main.style.border = "none";
            main.style.height = "100%";
            loginBox.style.border = "1px solid rgb(255,255,255,0.5)";
            loginBox.style.borderRadius = "7px";
            imgBox.style.border = "1px solid rgb(255,255,255,0.5)";
            imgBox.style.borderRadius = "7px";

            if (document.getElementById('row-temporaire')) {
                while (document.getElementById('row-temporaire').firstChild) {
                    document.getElementById('row-temporaire').removeChild(document.getElementById('row-temporaire').firstChild);
                }
                document.getElementById('row-temporaire').remove();
                while (document.getElementById('choice-container-temp').firstChild) {
                    document.getElementById('choice-container-temp').removeChild(document.getElementById('choice-container-temp').firstChild);
                }
                document.getElementById('choice-container-temp').remove();
            }

            // Suppression du contenu carousel
            var carouselElement = document.getElementById('carouselExample');
            carouselElement.style.display = "block";
        }
    }
});

//Switch les classes et contenus
function switchContentAndClass() {
    const loginBoxContent = loginBox.innerHTML;
    const loginBoxClass = loginBox.className;
    const imgBoxContent = imgBox.innerHTML;
    const imgBoxClass = imgBox.className;

    loginBox.innerHTML = imgBoxContent;
    loginBox.className = imgBoxClass;
    imgBox.innerHTML = loginBoxContent;
    imgBox.className = loginBoxClass;

    // Apply the blur-out animation
    loginBox.style.animation = 'blur-out 0.25s forwards';
    imgBox.style.animation = 'blur-out 0.25s forwards';

    // Remove the event listener
    loginBox.removeEventListener('animationend', switchContentAndClass);
    imgBox.removeEventListener('animationend', switchContentAndClass);

    const confirmationButton = document.getElementById('confirmation');
    confirmationButton.addEventListener('click', confirmationButtonHandler);
}

function confirmationButtonHandler() {
    console.log('Button clicked');
    const formulaire = document.getElementById('main');
    const inputs = formulaire.querySelectorAll('input');
    const button = document.getElementById('confirmation');
    let isFilled = true;

    inputs.forEach(input => {
        if (input.value.trim() === '') {
            isFilled = false;
        }
    });

    if (isFilled && button.textContent === "S'INSCRIRE" && !document.getElementById('row-temporaire')) {
        let index = 0;
        //Ajustement des éléments courants
        const main = document.getElementById('main');
        main.style.border = "1px solid white";
        main.style.borderRadius = "10px";
        main.style.height = "fit-content";
        loginBox.style.border = "none";
        imgBox.style.border = "none";
        document.getElementById('carouselExample').style.display = "none";

        if (window.innerWidth < 700) {
            const authBox = document.querySelector(".auth-box");
            Array.from(authBox.children).forEach(child => {
                if (child.id !== 'formulaire') {
                    child.style.opacity = 0;
                } else {
                    Array.from(child.querySelector(".input-holder").children).forEach(innerChild => {
                        if (innerChild.id !== 'confirmation') {
                            innerChild.style.opacity = 0;
                        } else{
                            innerChild.style.width = "125%"
                            gsap.to(innerChild,{
                                y: "-500%",
                                x:"5%",
                                duration:0.25,
                                ease:"back"
                            })
                        }
                    });
                }
            });
        }


        //Ajout des nouveaux éléments
        var rowDiv = document.createElement("div");
        rowDiv.setAttribute("id", "row-temporaire");
        rowDiv.classList.add("row", "mt-5");
        var h4Element = document.createElement("h4");
        h4Element.classList.add("type-artist");
        h4Element.textContent = "QUELLE TYPE D'ARTISTE ÊTES-VOUS ?";
        rowDiv.appendChild(h4Element);

        //Container des choix
        var choiceContainerDiv = document.createElement("div");
        choiceContainerDiv.setAttribute("id", "choice-container-temp");
        choiceContainerDiv.classList.add("choice-container");

        var modelingChoice = createChoice("sphereIcon.png", "Modélisation 3D");
        var traditionalArtChoice = createChoice("paintBrushIcon.png", "Traditionnel");
        choiceContainerDiv.appendChild(modelingChoice);
        choiceContainerDiv.appendChild(traditionalArtChoice);

        var digitalArtChoice = createChoice("digitalIcon.png", "Numérique");
        var animationChoice = createChoice("animationIcon.png", "Animation");
        choiceContainerDiv.appendChild(digitalArtChoice);
        choiceContainerDiv.appendChild(animationChoice);


        var photoChoice = createChoice("photographIcon.png", "Photographe");
        var cineChoice = createChoice("cineIcon.png", "Cinéaste");
        choiceContainerDiv.appendChild(cineChoice);
        choiceContainerDiv.appendChild(photoChoice);

        var musicChoice = createChoice("musicIcon.png", "Musicien");
        var ecrivainChoice = createChoice("ecrivainIcon.png", "Écrivain");
        choiceContainerDiv.appendChild(musicChoice);
        choiceContainerDiv.appendChild(ecrivainChoice);

        var libreChoice = createChoice("libreIcon.png", "Libre");
        choiceContainerDiv.appendChild(libreChoice);
        
        // Fonction créer boite à choix
        function createChoice(iconSrc, text) {
            var colDiv = document.createElement("div");
            var containerChoix = document.createElement("div");
            containerChoix.style.background = "transparent";
            containerChoix.style.border = "none"
            containerChoix.classList.add("choice-a");
            var choiceBoxLabel = document.createElement("label");
            choiceBoxLabel.classList.add("choice-box");
            choiceBoxLabel.style.animation = 'flash-in .4s forwards';
            let radioEl = document.createElement("input");
            radioEl.type = "radio";
            radioEl.name = "specialite";
            radioEl.value = text;
            radioEl.style.display = "none";

            choiceBoxLabel.style.animation = 'none';
            setTimeout(function() {
                choiceBoxLabel.style.animation = 'flash-in 0.6s forwards';
                
            }, 90 * index);
            var imgElement = document.createElement("img");
            imgElement.src = "media/logo/" + iconSrc;
            imgElement.alt = "icon-choix";
            var pElement = document.createElement("p");
            pElement.textContent = text;
            choiceBoxLabel.appendChild(imgElement);
            choiceBoxLabel.appendChild(radioEl);
            choiceBoxLabel.appendChild(pElement);
            containerChoix.appendChild(choiceBoxLabel);
            colDiv.appendChild(containerChoix);
            index++;
            return colDiv;
        }

        // Ajout des éléments
        loginBox.insertBefore(rowDiv, loginBox.firstChild);
        loginBox.insertBefore(choiceContainerDiv, rowDiv.nextSibling);
        loginBox.style.display = "block";




    } else {
        console.log('Not all inputs are filled');
    }

    let getSelectedValue = document.querySelector( 'input[name="specialite"]:checked');
    if(getSelectedValue != null){
        button.type = "submit";
    }

}

function validateForm(form){
    let password = document.getElementById("input-3");
    let email = document.getElementById("input-1")
    let pseudo = document.getElementById("input-2")
    let conf = document.getElementById("input-confirm");
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

        if(!validatePassword(password)){
            showNotification({
                type: 'warn',
                message: "Le mot de passe n'est pas assez fort : " +
                    "il doit avoir 8 charactères, une majuscule, une minuscule, " +
                    "un chiffre et un charactère spécial",
                titre: 'Avertissement',
            })
            const notif = document.querySelector(".notification-pop-container");
            notif.style.animationDuration = "7s"
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

function validatePassword(input){
    if(!input.value.match(pswdRegex)){
        input.style.boxShadow = `rgba(116, 9, 34, 0.44) 0px -10px 25px,
        rgba(136, 5, 5, 0.33) 0px 25px 25px,
        rgba(107, 3, 27, 0.17) 0px 10px 10px,
        rgba(220, 16, 48, 0.77) 0px -10px 10px`;
        return false;
    }
    input.style.boxShadow = "";
    return true;
}


function validateEmail(input){
    let valid = false
    $.ajax({
        type:'POST',
        async:false,
        url:window.location.origin.toString() + "/api/utilisateur/check-email",
        data: {email:input.value, userId:0},
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
        data: {pseudo:input.value, userId:0},
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

function checkInput(event) {
    if (event.target && event.target.id === "input-3") {
        validatePassword(event.target);
    }

    if (event.target && event.target.id === "input-1"){
        validateEmail(event.target);
    }

    if (event.target && event.target.id === "input-2"){
        validatePseudo(event.target);
    }
}




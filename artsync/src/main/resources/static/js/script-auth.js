const container = document.body; // Or any other parent element
const loginBox = document.querySelector('.auth-box');
const imgBox = document.querySelector('.img-box');
let form = document.getElementById("main");

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
    }

    else if (event.target.id === 'connect') {
        document.getElementById('signUp').classList.remove('selected');
        form = document.getElementById("main");
        console.log(form.action);
        if (document.getElementById('input-3')) {

            document.getElementById('input-3').remove();
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

//Animation flash
function flash(element) {
    element.style.animation = 'flash-in 0.75s forwards';
    element.removeEventListener('animationend',flash);
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

        //Premier row des choix
        var firstChoiceRow = document.createElement("div");
        firstChoiceRow.classList.add("row");
        var modelingChoice = createChoice("sphereIcon.png", "MODÉLISATION 3D");
        var traditionalArtChoice = createChoice("paintBrushIcon.png", "ART TRADITIONNEL");
        firstChoiceRow.appendChild(modelingChoice);
        firstChoiceRow.appendChild(traditionalArtChoice);

        //Deuxieme row pour les case choix
        var secondChoiceRow = document.createElement("div");
        secondChoiceRow.classList.add("row", "mt-4");
        var digitalArtChoice = createChoice("digitalIcon.png", "ART NUMÉRIQUE");
        var animationChoice = createChoice("animationIcon.png", "ANIMATION");
        secondChoiceRow.appendChild(digitalArtChoice);
        secondChoiceRow.appendChild(animationChoice);
        choiceContainerDiv.appendChild(firstChoiceRow);
        choiceContainerDiv.appendChild(secondChoiceRow);
        
        // Fonction créer boite à choix
        function createChoice(iconSrc, text) {
            var colDiv = document.createElement("div");
            colDiv.classList.add("col-md-6");
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
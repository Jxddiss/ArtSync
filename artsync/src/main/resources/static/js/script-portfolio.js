const sectionHeader = document.querySelector('.sectionHeader');

//SECTION SELECTION
const dispositionContainer = document.querySelector('.selectContainer');
const selectabletemplates = document.querySelectorAll('#selectableTemplate');
const customTemplate = document.querySelector('.iconCase');

//SECTION CUSTOMIZATION
const customizeContainer = document.querySelector('.customizeContainer');
const selectable = document.querySelectorAll('#selectable');
const dropArea = document.getElementById('dropArea');
const redoBtn = document.getElementById('redoBtn');
const saveBtn = document.getElementById('saveBtn');
const cancelBtn = document.getElementById('cancelBtn');

//SECTION SELECTION CONTENEUR

const settingsContainer = document.querySelector('.containerSelectorContainer');

const slidesContainer = document.getElementById("slides-container");
const slide = document.querySelector(".slide");
const prevButton = document.getElementById("slide-arrow-prev");
const nextButton = document.getElementById("slide-arrow-next");

const inputHolderCouleur = document.getElementById("inputHolder");
const inputHolderSlider = document.getElementById("inputHolderSlider");

const btnBackground = document.getElementById("background");
const btnBorder = document.getElementById("border");
const btnOpacity = document.getElementById("opacity");
const btnBorderWidth = document.getElementById("borderWidth");
const btnCouleurs = document.querySelectorAll(".colorHolder");

let slider = document.getElementById("myRange");
let output = document.getElementById("valueSlider");

const btnRetour = document.getElementById("retour");
const btnContinuer = document.getElementById("continuer");

const templatePreview = document.getElementById("templatePlaceHolder");
let option = 'background';
let selectedOption = -1;
let currentCase = null;
let caseIds = [];
let startTime = Date.now();
const delay = 500; 


//SECTION AJOUT CONTENU
const addContentContainer = document.querySelector('.addContentContainer');
const checkBtnImg = document.getElementById('imageOption');
const checkBtnText = document.getElementById('textOption');
const images = document.querySelectorAll('.selectableImage');
const menuImage = document.querySelector('.menuImage');
const menuText = document.querySelector('.menuText');
const infoText = document.querySelector('.info');
const textInput = document.getElementById('textInput');
const colorInput = document.getElementById('colorInput');

const btnFormat = document.querySelectorAll('.btnFormat');
const number = document.getElementById('number');
let textAlign = 'center';
let dropAres = null;

const btnRetour1 = document.getElementById("retour1");
const btnContinuer1 = document.getElementById("continuer1");

const slidesContainer1 = document.getElementById("slides-container1");
const slide1 = document.querySelector(".slide1");
const prevButton1 = document.getElementById("slide-arrow-prev1");
const nextButton1 = document.getElementById("slide-arrow-next1");

const textPreview = document.getElementById("textPlaceholder");
const textPreviewHolder = document.getElementById("textElementHolder");
const templatePreview1 = document.getElementById("templatePlaceHolder1");
let currentFontSlide = 0;

let fontStyle = [];
const pixelFont = new FontFace('8BIT', 'url(fonts/PixeloidSansBold-PKnYd.ttf)');
pixelFont.load().then(function(loadedFont) {
    document.fonts.add(loadedFont)
    fontStyle = ['Arial', 'Times New Roman', 'Verdana', 'Georgia', 'Comis Sans MS', 'Impact', '"8BIT"', 'Papyrus', 'Lucida Console', 'Trebuchet MS']
}).catch(function(error) {
    console.log('Failed to load font: ' + error)
})

//SECTION FINALISATION
const finalisationContainer = document.querySelector('.finalisationContainer');
const templatePreviewFinal = document.getElementById('templatePlaceHolder2');
const btnConfirmerPortfolio = document.getElementById('confirmerPortfolio');
const btnAnnulerPortfolio = document.getElementById('annulerPortfolio');
const codeGenerationOutput = document.getElementById('codeGen');

let portfolioJSON = null;
let portfolioJSONCode = null;
const portfolioHolder = document.querySelector('.templatePreview1');

//SECTION SELECTION
selectabletemplates.forEach(element => {
    element.addEventListener('click', function() {
        while (dropArea.firstChild) {
            dropArea.removeChild(dropArea.firstChild);
        }

        const children = element.children;
        for (let i = 0; i < children.length; i++) {
            const cloneElement = children[i].cloneNode(true);
            cloneElement.classList.add('case');
            dropArea.appendChild(cloneElement);
        }
        fadeOutAndHide(dispositionContainer);
        addTextToHeader(sectionHeader, 'PERSONNALISATION DE LA DISPOSITION');
        setTimeout(() => {
            fadeInAndShow(customizeContainer);
        }, 550);
    });
});


customTemplate.addEventListener('click', function() {
    fadeOutAndHide(dispositionContainer);
    addTextToHeader(sectionHeader, 'PERSONNALISATION DE MODÈLE');
    setTimeout(() => {
        fadeInAndShow(customizeContainer);
    }, 550);
    while (dropArea.firstChild) {
        dropArea.removeChild(dropArea.firstChild);
    }
});



//SECTION CUSTOMIZATION
selectable.forEach(element => {
    element.setAttribute('draggable', 'true');
    element.addEventListener('dragstart', dragStart);
    element.addEventListener('dragend', dragEnd);
    
});
dropArea.addEventListener('dragover', dragOver);
dropArea.addEventListener('dragenter', dragEnter);
dropArea.addEventListener('dragleave', dragLeave);
dropArea.addEventListener('drop', drop);

function dragStart(event) {
    const value = event.target.getAttribute('data-value');
    event.dataTransfer.setData('text/plain', value);
}
function dragEnd() {
}
function dragOver(event) {
    event.preventDefault();
}
function dragEnter(event) {
    dropArea.classList.add('drag-over');
}
function dragLeave() {
    dropArea.classList.remove('drag-over');
}
function drop(event) {
    event.preventDefault();
    const cloneElement = document.createElement('div');
    const classes = event.dataTransfer.getData('text/plain');
    cloneElement.classList.add(classes);
    cloneElement.classList.add('case');
    dropArea.appendChild(cloneElement);
}

function fadeOutAndHide(element) {
    gsap.to(element, {duration: 0.5, opacity: 0, display: 'none'});
}
function fadeInAndShow(element) {
    gsap.to(element, {duration: 0.5, opacity: 1, display: 'flex'});
}
function addTextToHeader(element, text) {
    gsap.to(element, {duration: 0.5, text: text});
}
redoBtn.addEventListener('click', function() {
    const lastElement = dropArea.lastElementChild;
    if (lastElement) {
        lastElement.remove();
    }
});
saveBtn.addEventListener('click', function() {
    fadeOutAndHide(customizeContainer);
    addTextToHeader(sectionHeader, 'PERSONNALISATION DU PORTFOLIO');
    while (templatePreview.firstChild) {
        templatePreview.removeChild(templatePreview.firstChild);
    }
    const children = dropArea.children;
    for (let i = 0; i < children.length; i++) {
        const cloneElement = children[i].cloneNode(true);
        cloneElement.classList.add('case');
        templatePreview.appendChild(cloneElement);
    }
    caseIds = [];
    const childrenSlides = Array.from(slidesContainer.children);
    for (let i = 1; i < childrenSlides.length; i++) {
        slidesContainer.removeChild(childrenSlides[i]);
    }

    const childrenPreview = templatePreview.children;
    for (let i = 0; i < childrenPreview.length; i++) {

        const className = childrenPreview[i].className.split(' ')[0];
        const trimmedClassName = className.substring(4);
        const li = document.createElement('li');
        li.classList.add('slide');
        const bigHolder = document.createElement('div');
        bigHolder.classList.add('bigHolder');
        const big = document.createElement('div');
        big.classList.add('big');
        big.classList.add('big' + trimmedClassName);

        bigHolder.appendChild(big);
        li.appendChild(bigHolder);
        slidesContainer.appendChild(li);

        const caseId = 'case' + i;
        big.setAttribute('data-case', caseId);
        childrenPreview[i].setAttribute('data-case', caseId);
        caseIds.push(caseId);
    }
    selectedOption = -1;
    setTimeout(() => {
        fadeInAndShow(settingsContainer);
    }, 550); 
});
cancelBtn.addEventListener('click', function() {
    fadeOutAndHide(customizeContainer);
    addTextToHeader(sectionHeader, 'CHOISISSEZ VOTRE MODÈLE DE PORTFOLIO');
    setTimeout(() => {
        fadeInAndShow(dispositionContainer);
    }, 550);
});


//SECTION SELECTION CONTENEUR
nextButton.addEventListener("click", () => {
    const currentTime = Date.now();
    if (currentTime > startTime + delay) {
        const slideWidth = slide.clientWidth;
        const maxScrollLeft = slidesContainer.scrollWidth - slidesContainer.clientWidth;
    if (slidesContainer.scrollLeft < maxScrollLeft) {
        slidesContainer.scrollLeft += slideWidth;
        selectedOption++;
        const cases = templatePreview.querySelectorAll('.case');
        cases.forEach(element => {
            element.style.transition = 'transform 0.25s';
            element.style.transform = 'scale(1)';
        });
        if (selectedOption >= 0) {
            const currentCase = getCurrentCase();
            currentCase.style.transition = 'transform 0.25s';
            currentCase.style.transform = 'scale(1.1)';
        }
        startTime = currentTime;
    }
    }
});
prevButton.addEventListener("click", () => {
    const currentTime = Date.now();
    if (currentTime > startTime + delay) {
        const slideWidth = slide.clientWidth;
    if (slidesContainer.scrollLeft > 0) {
        slidesContainer.scrollLeft -= slideWidth;
        selectedOption--;
        const cases = templatePreview.querySelectorAll('.case');
        cases.forEach(element => {
            element.style.transition = 'transform 0.25s';
            element.style.transform = 'scale(1)';
        });
        if (selectedOption >= 0) {
            const currentCase = getCurrentCase();
            currentCase.style.transition = 'transform 0.25s';
            currentCase.style.transform = 'scale(1.1)';
        }
        startTime = currentTime;
    }
    }
});

btnBackground.addEventListener("click", () => {
    option = 'background';
    if (inputHolderCouleur.style.display === 'none') {
        gsap.to(inputHolderSlider, {duration: 0.5, opacity: 0, display: 'none'});
        gsap.to(inputHolderCouleur, {duration: 0.5, opacity: 1, display: 'flex', delay:0.5});
    }

    if (!btnBackground.classList.contains('selectedOption')) {
        btnBackground.classList.add('selectedOption');
    }

    if (btnBorder.classList.contains('selectedOption')) {
        btnBorder.classList.remove('selectedOption');
    }
    if (btnOpacity.classList.contains('selectedOption')) {
        btnOpacity.classList.remove('selectedOption');
    }
    if (btnBorderWidth.classList.contains('selectedOption')) {
        btnBorderWidth.classList.remove('selectedOption');
    }
});
btnBorder.addEventListener("click", () => {
    option = 'border';
    if (inputHolderCouleur.style.display === 'none') {
        gsap.to(inputHolderSlider, {duration: 0.5, opacity: 0, display: 'none'});
        gsap.to(inputHolderCouleur, {duration: 0.5, opacity: 1, display: 'flex', delay:0.5});
    }
    if (!btnBorder.classList.contains('selectedOption')) {
        btnBorder.classList.add('selectedOption');
    }
    if (btnBackground.classList.contains('selectedOption')) {
        btnBackground.classList.remove('selectedOption');
    }
    if (btnOpacity.classList.contains('selectedOption')) {
        btnOpacity.classList.remove('selectedOption');
    }
    if (btnBorderWidth.classList.contains('selectedOption')) {
        btnBorderWidth.classList.remove('selectedOption');
    }

});
btnOpacity.addEventListener("click", () => {
    option = 'opacity';
    if (window.getComputedStyle(inputHolderSlider).display === 'none') {
        gsap.to(inputHolderCouleur, { duration: 0.5, opacity: 0, display: 'none' });
        gsap.to(inputHolderSlider, { duration: 0.5, opacity: 1, display: 'flex', delay:0.5 });
    }
    slider.value = 100;
    if (!btnOpacity.classList.contains('selectedOption')) {
        btnOpacity.classList.add('selectedOption');
    }
    if (btnBorder.classList.contains('selectedOption')) {
        btnBorder.classList.remove('selectedOption');
    }
    if (btnBackground.classList.contains('selectedOption')) {
        btnBackground.classList.remove('selectedOption');
    }
    if (btnBorderWidth.classList.contains('selectedOption')) {
        btnBorderWidth.classList.remove('selectedOption');
    }
});
btnBorderWidth.addEventListener("click", () => {
    option = 'borderWidth';
    if (window.getComputedStyle(inputHolderSlider).display === 'none') {
        gsap.to(inputHolderCouleur, { duration: 0.5, opacity: 0, display: 'none' });
        gsap.to(inputHolderSlider, { duration: 0.5, opacity: 1, display: 'flex', delay:0.5 });
    }
    slider.value = 0;
    if (!btnBorderWidth.classList.contains('selectedOption')) {
        btnBorderWidth.classList.add('selectedOption');
    }
    if (btnBorder.classList.contains('selectedOption')) {
        btnBorder.classList.remove('selectedOption');
    }
    if (btnOpacity.classList.contains('selectedOption')) {
        btnOpacity.classList.remove('selectedOption');
    }
    if (btnBackground.classList.contains('selectedOption')) {
        btnBackground.classList.remove('selectedOption');
    }
});


btnCouleurs.forEach(element => {
    element.addEventListener("click", () => {
        const color = element.getAttribute('data-color');
        if (selectedOption >= -1) {
            currentCase = getCurrentCase();
            if (currentCase === null) {
                switch (option) {
                    case 'background':
                        templatePreview.style.backgroundColor = color;
                        break;
                    case 'border':
                        templatePreview.style.borderColor = color;
                        break;
                }
            }
            switch (option) {
                case 'background':
                    currentCase.style.backgroundColor = color;
                    break;
                case 'border':
                    currentCase.style.borderColor = color;
                    break;
            }
        }
    });
});

slider.oninput = function() {
    output.innerHTML = this.value;
    if (selectedOption >= -1) {
        currentCase = getCurrentCase();
        if (currentCase === null) {
            switch (option) {
                case 'opacity':
                    templatePreview.style.opacity = this.value / 100;
                    break;
                case 'borderWidth':
                    templatePreview.style.borderWidth = this.value + 'px';
                    break;
            }
        }
        switch (option) {
            case 'opacity':
                currentCase.style.opacity = this.value / 100;
                break;
            case 'borderWidth':
                currentCase.style.borderWidth = this.value + 'px';
                break;
        }
    }
};
btnRetour.addEventListener("click", () => {
    fadeOutAndHide(settingsContainer);
    addTextToHeader(sectionHeader, 'PERSONNALISATION DE MODÈLE');
    setTimeout(() => {
        fadeInAndShow(customizeContainer);
    }, 550);
});
btnContinuer.addEventListener("click", () => {
    fadeOutAndHide(settingsContainer);
    addTextToHeader(sectionHeader, 'AJOUT DE CONTENU');
    while (templatePreview1.firstChild) {
        templatePreview1.removeChild(templatePreview1.firstChild);
    }
    const children = templatePreview.children;
    for (let i = 0; i < children.length; i++) {
        const cloneElement = children[i].cloneNode(true);
        cloneElement.style.transform = 'scale(1)';
        templatePreview1.appendChild(cloneElement);
        cloneElement.classList.add('dropArea');
        const className = children[i].className.split(' ')[0];
        const trimmedClassName = className.substring(4);
        cloneElement.style.display = 'grid';
        cloneElement.style.gridTemplateColumns = 'repeat(' + trimmedClassName[0] + ', 1fr)';
        cloneElement.style.gridTemplateRows = 'repeat(' + trimmedClassName[2] + ', 1fr)';

    }
    templatePreview1.style.backgroundColor = templatePreview.style.backgroundColor;
    templatePreview1.style.borderColor = templatePreview.style.borderColor;
    templatePreview1.style.opacity = templatePreview.style.opacity;
    templatePreview1.style.borderWidth = templatePreview.style.borderWidth;
    
    dropAres = document.querySelectorAll('.dropArea');
    dropAres.forEach(element => {
        element.addEventListener('dragover', dragOver);
        element.addEventListener('dragenter', dragEnter);
        element.addEventListener('dragleave', dragLeave);
        element.addEventListener('drop', drop1);
    });
    setTimeout(() => {
        fadeInAndShow(addContentContainer);
    }, 550);
});

function getCurrentCase() {
    if (selectedOption >= 0) {
        return templatePreview.querySelector('[data-case="' + caseIds[selectedOption] + '"]');
    }
    return null;
}



//SECTION AJOUT CONTENU

btnFormat.forEach(element => {
    element.addEventListener('click', function() {
        btnFormat.forEach(element => {
            element.removeAttribute('id', 'selectedFormat');
            
        });
        element.setAttribute('id', 'selectedFormat');
        textAlign = element.getAttribute('data-align');
        textPreview.style.textAlign = textAlign;
    });
});
number.addEventListener('keyup', function() {
    textPreview.style.fontSize = number.value + 'px';
});

nextButton1.addEventListener("click", () => {
    const currentTime = Date.now();
    if (currentTime > startTime + delay) {
        const slideWidth = slide1.clientWidth;
        const maxScrollLeft = slidesContainer1.scrollWidth - slidesContainer.clientWidth;
    if (slidesContainer1.scrollLeft < maxScrollLeft) {
        slidesContainer1.scrollLeft += slideWidth;
        startTime = currentTime;
        currentFontSlide++;
        document.fonts.ready.then(function() {
            changeFont(currentFontSlide);
        });
    }}
});
prevButton1.addEventListener("click", () => {
    const currentTime = Date.now();
    if (currentTime > startTime + delay) {
        const slideWidth = slide1.clientWidth;
    if (slidesContainer1.scrollLeft > 0) {
        slidesContainer1.scrollLeft -= slideWidth;
        startTime = currentTime;
        currentFontSlide--;
        document.fonts.ready.then(function() {
            changeFont(currentFontSlide);
        });
    }}
});
textInput.addEventListener('keyup', function() {
    textPreview.innerHTML = textInput.value;
});
colorInput.addEventListener('change', function() {
    textPreview.style.color = colorInput.value;
});

function changeFont(currentFontSlide) {
    textPreview.style.fontFamily = fontStyle[currentFontSlide];
}


checkBtnImg.addEventListener('change', function() {
    if (checkBtnImg.checked) {
        fadeOutAndHide(menuText);
        addTextToHeader(infoText, 'VOS PUBLICATIONS <i class="bi bi-info-circle" id="infoIcon" title="Si vous ne voyez pas une publication, mettez la publique"></i>');
        //timeout
        setTimeout(() => {
            fadeInAndShow(menuImage);
        }, 490);

    }
});
checkBtnText.addEventListener('change', function() {
    if (checkBtnText.checked) {
        fadeOutAndHide(menuImage);
        addTextToHeader(infoText, 'PERSONNALISATION DE TEXTE');
        setTimeout(() => {
            fadeInAndShow(menuText);
        }, 490);
    }
});

btnRetour1.addEventListener("click", () => {
    fadeOutAndHide(addContentContainer);

    addTextToHeader(sectionHeader, 'PERSONNALISATION DU PORTFOLIO');
    setTimeout(() => {
        fadeInAndShow(settingsContainer);
    }, 550);
});

textPreviewHolder.setAttribute('draggable', 'true');
textPreviewHolder.addEventListener('dragstart', dragStart);
textPreviewHolder.addEventListener('dragend', dragEnd);
textPreviewHolder.addEventListener('dragover', dragOver);
textPreviewHolder.addEventListener('dragenter', dragEnter);
textPreviewHolder.addEventListener('dragleave', dragLeave);
images.forEach(element => {
    element.setAttribute('draggable', 'true');
    element.addEventListener('dragstart', dragStart);
    element.addEventListener('dragend', dragEnd);
    element.addEventListener('dragover', dragOver);
    element.addEventListener('dragenter', dragEnter);
    element.addEventListener('dragleave', dragLeave);
});


function drop1(event) {
    if (checkBtnImg.checked) {
        event.preventDefault();
        const cloneElement = document.createElement('div');
        cloneElement.classList.add('post');
        const cloneElementChild = document.createElement('img');
        const classes = event.dataTransfer.getData('text/plain');
        cloneElementChild.src = "moon.jpg"; //changer ca plus tard au post de l'utilisateur pis rajouter peut etre un data value avec l'id du post
        cloneElementChild.classList.add(classes);
        cloneElement.appendChild(cloneElementChild);
        cloneElementChild.setAttribute('draggable', 'false');
        event.target.appendChild(cloneElement);
    } else if (checkBtnText.checked) {
        event.preventDefault();
        const cloneElement = document.createElement('div');
        if (textPreview.innerHTML.length >= 14) {
            cloneElement.classList.add('post2');
        } else {
            cloneElement.classList.add('post');
        }
        const cloneElementChild = document.createElement('p');
        cloneElementChild.innerHTML = textPreview.innerHTML;
        cloneElementChild.style.color = textPreview.style.color;
        cloneElementChild.style.fontFamily = textPreview.style.fontFamily;
        cloneElementChild.style.fontSize = textPreview.style.fontSize;
        cloneElementChild.style.textAlign = textPreview.style.textAlign;
        cloneElementChild.setAttribute('draggable', 'false');
        cloneElement.appendChild(cloneElementChild);
        event.target.appendChild(cloneElement);
    }

}

function dragOver(event) {
    event.preventDefault();
}

btnContinuer1.addEventListener("click", () => {
    fadeOutAndHide(addContentContainer);
    addTextToHeader(sectionHeader, 'FINALISATION DU PORTFOLIO');
    templatePreviewFinal.style.transform = 'scale(0.5)';
    setTimeout(() => {
        fadeInAndShow(finalisationContainer);
        templatePreview1.style.transition = 'transform 0.5s';
        templatePreviewFinal.style.transform = 'scale(1)';
    }, 550);
    while (templatePreviewFinal.firstChild) {
        templatePreviewFinal.removeChild(templatePreviewFinal.firstChild);
    }
    const children = templatePreview1.children;
    for (let i = 0; i < children.length; i++) {
        const cloneElement = children[i].cloneNode(true);
        templatePreviewFinal.appendChild(cloneElement);
    }
    templatePreviewFinal.style.backgroundColor = templatePreview1.style.backgroundColor;
    templatePreviewFinal.style.borderColor = templatePreview1.style.borderColor;
    templatePreviewFinal.style.opacity = templatePreview1.style.opacity;
    templatePreviewFinal.style.borderWidth = templatePreview1.style.borderWidth;

    portfolioJSON = convertPortfolioToJSON(templatePreview1);
    portfolioJSONCode = JSONToBase64(portfolioJSON);
    codeGenerationOutput.textContent = JSONToBase64(portfolioJSON);
    while (portfolioHolder.firstChild) {
        portfolioHolder.removeChild(portfolioHolder.firstChild);
    }
    portfolioHolder.appendChild(generatePortfolioFromJSON(base64ToJSON(portfolioJSONCode)));    
});


function convertPortfolioToJSON(portfolioHTML) {
    let templatePlaceHolder = portfolioHTML;
    //Portoflio
    let portfolioJSON = {
        background: templatePlaceHolder.style.backgroundColor || 'rgb(174, 217, 255, 0.5)' ,
        borderColor: templatePlaceHolder.style.borderColor || 'rgb(52, 140, 255, 0.5)',
        opacity: templatePlaceHolder.style.opacity || 1.0,
        borderWidth: templatePlaceHolder.style.borderWidth || '4px',
        items: []
    };
    //Cases
    let caseDivs = templatePlaceHolder.querySelectorAll('.case');
    caseDivs.forEach(function(caseDiv) {
        let caseJSON = {
            background: caseDiv.style.backgroundColor || 'rgba(147, 194, 255, 0.5)',
            borderColor: caseDiv.style.borderColor || 'rgb(52, 140, 255, 0.5)',
            opacity: caseDiv.style.opacity || 1.0,
            borderWidth: caseDiv.style.borderWidth || '3px',
            classes: caseDiv.className,
            items: []
        };
        //Posts
        for (let i = 0; i < caseDiv.children.length; i++) {
            let child = caseDiv.children[i];
            if (child.classList.contains('post') || child.classList.contains('post2')) {
                let postJSON = {};
                //Image ou Texte
                let imgElement = child.querySelector('img');
                if (imgElement) {
                    postJSON.type = 'image';
                    postJSON.src = imgElement.src;
                } else {
                    postJSON.type = 'text';
                    let pElement = child.querySelector('p');
                    postJSON.fontFamily = pElement.style.fontFamily;
                    postJSON.textAlign = pElement.style.textAlign;
                    postJSON.color = pElement.style.color;
                    postJSON.fontSize = pElement.style.fontSize;
                    postJSON.textContent = pElement.textContent;
                }
                caseJSON.items.push(postJSON);
            }
        }
        portfolioJSON.items.push(caseJSON);
    });
    return portfolioJSON;
}

function generatePortfolioFromJSON(portfolioJSON) {
    //Portoflio
    let templatePlaceHolder = document.createElement('div');
    templatePlaceHolder.classList.add('templatePlaceHolder');
    templatePlaceHolder.style.backgroundColor = portfolioJSON.background;
    templatePlaceHolder.style.borderColor = portfolioJSON.borderColor;
    templatePlaceHolder.style.opacity = portfolioJSON.opacity;
    templatePlaceHolder.style.borderWidth = portfolioJSON.borderWidth;

    //Cases
    portfolioJSON.items.forEach(function(caseJSON) {
        let caseDiv = document.createElement('div');
        caseDiv.classList.add('case');
        caseDiv.style.backgroundColor = caseJSON.background;
        caseDiv.style.borderColor = caseJSON.borderColor;
        caseDiv.style.opacity = caseJSON.opacity;
        caseDiv.style.borderWidth = caseJSON.borderWidth;
        caseDiv.className = caseJSON.classes;
        caseDiv.style.display = 'grid';

        let classNames = caseDiv.className.split(' ');
        let row = 1;
        let col = 1;
        classNames.forEach(function(className) {
            let match = className.match(/case(\d+)x(\d+)/);
            if (match) {
                col = parseInt(match[1]);
                row = parseInt(match[2]);
            }
        });
        caseDiv.style.gridTemplateRows = 'repeat(' + row + ', 1fr)';
        caseDiv.style.gridTemplateColumns = 'repeat(' + col + ', 1fr)';
        caseJSON.items.forEach(function(postJSON) {
            let postElement;
            //Image ou Texte
            if (postJSON.type === 'image') {
                postElement = document.createElement('div');
                postElement.classList.add('post');
                let img = document.createElement('img');
                img.src = postJSON.src;
                postElement.appendChild(img);
            } else if (postJSON.type === 'text') {
                postElement = document.createElement('div');
                postElement.classList.add('post');
                let p = document.createElement('p');
                p.style.fontFamily = postJSON.fontFamily;
                p.style.textAlign = postJSON.textAlign;
                p.style.color = postJSON.color;
                p.style.fontSize = postJSON.fontSize;
                p.textContent = postJSON.textContent;
                postElement.appendChild(p);
            }
            caseDiv.appendChild(postElement);
        });
        templatePlaceHolder.appendChild(caseDiv);
    });
    return templatePlaceHolder;
}

function JSONToBase64(jsonObject) {
    let jsonString = JSON.stringify(jsonObject);
    let base64String = btoa(jsonString);
    return base64String;
}

function base64ToJSON(base64String) {
    let jsonString = atob(base64String);
    let jsonObject = JSON.parse(jsonString);
    return jsonObject;
}


//SECTION FINALISATION
templatePreviewFinal.addEventListener('mousemove', function(event) {
    templatePreviewFinal.style.transition = 'transform 0s';
    const x = event.clientX;
    const y = event.clientY;
    const rect = templatePreviewFinal.getBoundingClientRect();
    const xPosition = x - rect.left;
    const yPosition = y - rect.top;
    const xPercent = (xPosition / rect.width) * 100;
    const yPercent = (yPosition / rect.height) * 100;
    const xSkew = (xPercent - 50) / 10;
    const ySkew = (yPercent - 50) / 10;

    templatePreviewFinal.style.boxShadow = xSkew+25 + 'px ' + ySkew + 'px 50px rgba(0, 0, 0, 0.5)';
    templatePreviewFinal.style.transform = 'scale(1.05) skew(' + ySkew + 'deg, ' + xSkew + 'deg)';
    templatePreviewFinal.addEventListener('mouseleave', function() {
        templatePreviewFinal.style.transition = 'transform 0.5s';
        templatePreviewFinal.style.transform = 'skew(0deg, 0deg)';
    });
});

btnAnnulerPortfolio.addEventListener('click', function() {
    fadeOutAndHide(finalisationContainer);
    addTextToHeader(sectionHeader, 'AJOUT DE CONTENU');
    setTimeout(() => {
        fadeInAndShow(addContentContainer);
    }, 550);
});


const postBtn = document.getElementById('post');
const portfolioBtn = document.getElementById('portfolio');
const postHolder = document.getElementById('postHolder');
const portfolioHolder = document.getElementById('portfolioHolder');
const portfolioContainer = document.querySelector('.templatePreview1');
const ouvrir = document.getElementById('ouvrir');
const postPane = document.getElementById('postPane');
const changePfp = document.getElementById('changePfp');
const pfpPane = document.getElementById('pfpPane');
const postPreview = document.getElementById('postPreview');
const placeholderIcon = document.getElementById('placeholderIcon');
const postInput = document.getElementById('file-input');
const pfpPreview = document.getElementById('pfpPreview');
const fileInput = document.getElementById('file-input2');
let fontStyle = [];
const postElements = document.getElementsByClassName('post');
const code = document.getElementById('codePortfolio');

//btn section
postBtn.addEventListener('click', () => {
    console.log('click');
    postBtn.classList.add('selectbtn');
    portfolioBtn.classList.remove('selectbtn');
    postHolder.style.display = 'flex';
    portfolioHolder.style.display = 'none';
});
portfolioBtn.addEventListener('click', () => {
    portfolioBtn.classList.add('selectbtn');
    postBtn.classList.remove('selectbtn');
    postHolder.style.display = 'none';
    portfolioHolder.style.display = 'flex';
});



//dialog section
if (ouvrir && postPane) {
    ouvrir.addEventListener('click', function() {
        postPane.showModal()
        postPane.style.display = 'flex';
    });
}
const closeButton = document.getElementById('fermer');
if (closeButton && postPane) {
    closeButton.addEventListener('click', function() {
        postPane.close();
        postPane.style.display = 'none';
    });
}
//postPreview section
if (postInput && postPreview) {
    postInput.addEventListener('change', function() {
        const file = postInput.files[0];
        const reader = new FileReader();
        reader.onload = function() {
            postPreview.src = reader.result;
        }
        reader.readAsDataURL(file);
    });
}
if (changePfp && pfpPane) {
    changePfp.addEventListener('click', function() {
        pfpPane.showModal()
        pfpPane.style.display = 'flex';
    });
}
const closePfp = document.getElementById('fermerPfp');
if (closePfp && pfpPane) {
    closePfp.addEventListener('click', function() {
        pfpPane.close();
        pfpPane.style.display = 'none';
    });
}

if (fileInput && pfpPreview) {
    fileInput.addEventListener('change', function() {
        const file = fileInput.files[0];
        const reader = new FileReader();
        reader.onload = function() {
            pfpPreview.src = reader.result;
        }
        reader.readAsDataURL(file);
        placeholderIcon.style.display = 'none';
    });
}

//portfolio section
// pour tester le code
// const codeBase64 = "eyJiYWNrZ3JvdW5kIjoicmdiKDE3NCwgMjE3LCAyNTUsIDAuNSkiLCJib3JkZXJDb2xvciI6InJnYig1MiwgMTQwLCAyNTUsIDAuNSkiLCJvcGFjaXR5IjoxLCJib3JkZXJXaWR0aCI6IjRweCIsIml0ZW1zIjpbeyJiYWNrZ3JvdW5kIjoicmdiYSgxNDcsIDE5NCwgMjU1LCAwLjUpIiwiYm9yZGVyQ29sb3IiOiJyZ2IoNTIsIDE0MCwgMjU1LCAwLjUpIiwib3BhY2l0eSI6MSwiYm9yZGVyV2lkdGgiOiIzcHgiLCJjbGFzc2VzIjoiY2FzZSBjYXNlNHgxIGRyb3BBcmVhIiwiaXRlbXMiOltdfSx7ImJhY2tncm91bmQiOiJyZ2JhKDE0NywgMTk0LCAyNTUsIDAuNSkiLCJib3JkZXJDb2xvciI6InJnYig1MiwgMTQwLCAyNTUsIDAuNSkiLCJvcGFjaXR5IjoxLCJib3JkZXJXaWR0aCI6IjNweCIsImNsYXNzZXMiOiJjYXNlIGNhc2UxeDQgZHJvcEFyZWEiLCJpdGVtcyI6W119LHsiYmFja2dyb3VuZCI6InJnYmEoMTQ3LCAxOTQsIDI1NSwgMC41KSIsImJvcmRlckNvbG9yIjoicmdiKDUyLCAxNDAsIDI1NSwgMC41KSIsIm9wYWNpdHkiOjEsImJvcmRlcldpZHRoIjoiM3B4IiwiY2xhc3NlcyI6ImNhc2UgY2FzZTJ4MiBkcm9wQXJlYSIsIml0ZW1zIjpbXX0seyJiYWNrZ3JvdW5kIjoicmdiYSgxNDcsIDE5NCwgMjU1LCAwLjUpIiwiYm9yZGVyQ29sb3IiOiJyZ2IoNTIsIDE0MCwgMjU1LCAwLjUpIiwib3BhY2l0eSI6MSwiYm9yZGVyV2lkdGgiOiIzcHgiLCJjbGFzc2VzIjoiY2FzZSBjYXNlMXgyIGRyb3BBcmVhIiwiaXRlbXMiOltdfSx7ImJhY2tncm91bmQiOiJyZ2JhKDE0NywgMTk0LCAyNTUsIDAuNSkiLCJib3JkZXJDb2xvciI6InJnYig1MiwgMTQwLCAyNTUsIDAuNSkiLCJvcGFjaXR5IjoxLCJib3JkZXJXaWR0aCI6IjNweCIsImNsYXNzZXMiOiJjYXNlIGNhc2UxeDEgZHJvcEFyZWEiLCJpdGVtcyI6W119LHsiYmFja2dyb3VuZCI6InJnYmEoMTQ3LCAxOTQsIDI1NSwgMC41KSIsImJvcmRlckNvbG9yIjoicmdiKDUyLCAxNDAsIDI1NSwgMC41KSIsIm9wYWNpdHkiOjEsImJvcmRlcldpZHRoIjoiM3B4IiwiY2xhc3NlcyI6ImNhc2UgY2FzZTJ4MSBkcm9wQXJlYSIsIml0ZW1zIjpbXX0seyJiYWNrZ3JvdW5kIjoicmdiYSgxNDcsIDE5NCwgMjU1LCAwLjUpIiwiYm9yZGVyQ29sb3IiOiJyZ2IoNTIsIDE0MCwgMjU1LCAwLjUpIiwib3BhY2l0eSI6MSwiYm9yZGVyV2lkdGgiOiIzcHgiLCJjbGFzc2VzIjoiY2FzZSBjYXNlM3gxIGRyb3BBcmVhIiwiaXRlbXMiOltdfV19"
if (code) {
    const codeBase64 = code.textContent;
    if (codeBase64) {
        const jsonPortfolio = base64ToJSON(codeBase64);
        const portfolio = generatePortfolioFromJSON(jsonPortfolio);
        portfolioContainer.appendChild(portfolio);
    } else {
        portfolioContainer.style.display = 'none';
    }

}
const pixelFont = new FontFace('8BIT', 'url(/fonts/PixeloidSansBold-PKnYd.ttf)');
pixelFont.load().then(function(loadedFont) {
    document.fonts.add(loadedFont)
    fontStyle = ['Arial', 'Times New Roman', 'Verdana', 'Georgia', 'Comis Sans MS', 'Impact', '"8BIT"', 'Papyrus', 'Lucida Console', 'Trebuchet MS']
}).catch(function(error) {
    console.log('Failed to load font: ' + error)
})
function base64ToJSON(base64String) {
    let jsonString = atob(base64String);
    let jsonObject = JSON.parse(jsonString);
    return jsonObject;
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
function applyRainbowEffect(elementId) {
    const element = document.getElementById(elementId);
    if (element){
        const rainbowColors = ['#e3d58f','#e3ba8f','#e38f8f','#e38fc4','#b58fe3','#8fa1e3','#8fcbe3','#8fe3bf','#90e38f','#d8e38f'];
        let startColorIndex = 0;

        function updateRainbowEffect() {
            let colorIndex = startColorIndex;
            const spanElements = [];
            for (let i = 0; i < element.textContent.length; i++) {
                const char = element.textContent[i];
                const span = document.createElement('span');
                span.textContent = char;
                span.style.color = rainbowColors[colorIndex];
                spanElements.push(span);
                colorIndex = (colorIndex + 1) % rainbowColors.length;
            }
            element.innerHTML = '';
            spanElements.forEach(span => element.appendChild(span));

            startColorIndex = (startColorIndex + 1) % rainbowColors.length;
        }
        setInterval(updateRainbowEffect, 70);
    }

}
applyRainbowEffect('anchorPortfolio');


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
const pfpBase = document.getElementById("pfpBase")
const fileInput = document.getElementById('file-input2');
const bannerInput = document.getElementById('file-input3');
let fontStyle = [];
const postElements = document.getElementsByClassName('post');
const code = document.getElementById('codePortfolio');
const arrow = document.getElementById("arrow")
const bannerButton = document.getElementById("bannerButton")
const dialogBanner = document.getElementById("dialogBanner")
const bannerImgPreview = document.getElementById("bannerImgPreview")
let bannerDialogOpen = false
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
    });
}
const closePfp = document.getElementById('fermerPfp');
if (closePfp && pfpPane) {
    closePfp.addEventListener('click', function() {
        pfpPane.close();
    });
}

//banner dialog
if (bannerButton && dialogBanner) {
    bannerButton.addEventListener('click', function() {
        dialogBanner.showModal();
    });
}


if (fileInput && pfpPreview) {
    fileInput.addEventListener('change', function() {
        const file = fileInput.files[0];
        const reader = new FileReader();
        reader.onload = function() {
            pfpPreview.src = reader.result;
            gsap.to(pfpPreview, {duration: 0.75,x: '100%', opacity: 1, delay: 0.15, ease:'sine'});
            gsap.to(pfpBase, {duration: 0.75,x: '-100%', opacity: 1, delay: 0.15, ease:'sine'});
            gsap.to(arrow, {duration:0.5,x:'-210%', opacity:1, ease:'sine'})
        }
        reader.readAsDataURL(file);
        placeholderIcon.style.display = 'none';
    });
}
if (bannerInput && bannerImgPreview) {
    bannerInput.addEventListener('change', function() {
        const file = bannerInput.files[0];
        const reader = new FileReader();
        reader.onload = function() {
            bannerImgPreview.src = reader.result;
        }
        reader.readAsDataURL(file);
    });
}

function checkFile(form){
    const input = form.querySelector("input[type='file']");
    if (input.files && input.files[0]) {
        stompClientNotif.send("/app/notification/post/"+pseudoUser,{},JSON.stringify(
            {
                type: 'info',
                pseudoSender: pseudoUser,
                message: `Nouvelle post de ${pseudoUser}` ,
                titre: 'Nouveau post',
                imgSender: userImage,
                urlNotif: window.location.origin.toString() + '/feed'
            }
        ));
        return true;
    }
    return false;
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


//========= carte des poste ================

let dialog = document.getElementById("dialog-card-focus");
let imgDialog = dialog.querySelector("img");
let videoDeDialog = dialog.querySelector('video');

let publications = document.querySelectorAll(".publication");

publications.forEach(function (publication,index) {

    publication.addEventListener("click", function () {
        if (publication.querySelector('video')){
            videoDeDialog.src = publication.querySelector('video').src;
            videoDeDialog.style.display = "block"
            imgDialog.style.display = 'none'
            imgDialog.src = ''
        }else{
            let imageUrl = publication.querySelector("img").src;
            videoDeDialog.style.display = "none"
            imgDialog.style.display = 'block'
            imgDialog.src = imageUrl;
            videoDeDialog.src = ""
        }

        let cardPost = publication.querySelector(".card-post");


        let cardPostDialog = cardPost.cloneNode(true);
        cardPostDialog.style.display = "flex";
        if (userId != null){
            handleLike(cardPostDialog);
        }
        dialog.querySelector(".poste").appendChild(cardPostDialog)
        dialog.showModal();
    })
});

imgDialog.addEventListener("click", function(){
    if (imgDialog.dataset.maximiser == "true") {
        imgDialog.style.objectFit = "contain";
        imgDialog.dataset.maximiser = "false";
    }else{
        imgDialog.style.objectFit = "cover";
        imgDialog.dataset.maximiser = "true";
    }
})

dialog.querySelector("#close-dialog").addEventListener("click",function (){
    let cardPoste = dialog.querySelector(".card-post");
    const posteList = document.querySelectorAll(".publication")

    posteList.forEach(post =>{
        let card = post.querySelector(".card-post");
        if ( card.getAttribute("post-id") === cardPoste.getAttribute("post-id")){
            post.removeChild(card);
            cardPoste.style.display = 'none';
            post.appendChild(cardPoste);
        }
    })
    dialog.close();
})

//========== commentaire =========
const listeEnvComm = document.querySelectorAll(".liste-commentaires");

function addComment(commentaireForm, postId){
    let commentHolder = dialog.querySelector(".liste-commentaires")

    const comment = commentaireForm.comment.value

    const newComment = document.createElement("li");
    newComment.classList.add("commentaire")
    newComment.innerHTML=`
                    <a href="#!" class="info-comment">
                    <img
                      src="/media/images/utilisateur/${userImage}"
                      alt=""
                      class="profile-pic-banner-border-small"
                    />
                  </a>
                  <p>
                    <strong>@${pseudoUser}</strong>
                    ${comment}
                  </p>
                `
    commentHolder.appendChild(newComment);
    commentaireForm.comment.value = ""
}

function ajouterCommentaire(form){
    $.ajax({
        type: "POST",
        url: window.location.origin.toString()+"/post/comment",
        data: {comment: form.comment.value, postId: form.postId.value},
        success : function (data) {
            if(data === "true"){
                console.log("PASSED")
                addComment(form,form.postId.value)
            }else{
                console.log("FAILED")
            }
        },
    })

    if (userId !== form.ownerId.value){
        stompClientNotif.send("/app/notification/"+form.ownerId.value,{},JSON.stringify(
            {
                type: 'info',
                pseudoSender: pseudoUser,
                message: `Nouveau commentaire de ${pseudoUser}`,
                titre: 'Nouveau commentaire',
                imgSender: userImage,
                urlNotif: window.location.origin.toString() + '/feed'
            }
        ));
    }

    return false;

}

//========= likes ====<
function handleLike(cardpost){
    const likeSymbol = cardpost.querySelector(".like")
    const postId = likeSymbol.getAttribute("post-id");
    const ownerId = likeSymbol.getAttribute("owner-id")
    const icon = likeSymbol.querySelector("i");

    if(localStorage.getItem(`like-post-${postId}-${userId}`)){
        if(localStorage.getItem(`like-post-${postId}-${userId}`) === "true"){
            if (likeSymbol.dataset.clicked === "false"){
                likeSymbol.dataset.clicked = "true";
                icon.style.color = "red";
                let fill = icon.classList[1] + "-fill";
                icon.classList.remove(icon.classList[1]);
                icon.classList.add(fill);
            }
        }else{
            likeSymbol.dataset.clicked = "false";
        }
    }else{
        localStorage.setItem(`like-post-${postId}-${userId}`,`false`);
    }

    likeSymbol.firstElementChild.addEventListener("mouseout", (e) => {
        if (likeSymbol.dataset.clicked === "false") {
            let newIconClass = icon.classList[1].replace("-fill", "");
            icon.classList.remove(icon.classList[1]);
            icon.classList.add(newIconClass);
        }
    });

    likeSymbol.firstElementChild.addEventListener("mouseover", (e) => {
        if (likeSymbol.dataset.clicked === "false") {
            let fill = icon.classList[1] + "-fill";
            icon.classList.remove(icon.classList[1]);
            icon.classList.add(fill);
        }
    });

    likeSymbol.firstElementChild.addEventListener("click", (e) => {
        const info = likeSymbol.querySelector("p").innerText
        let nbLike = +info.split(" ").at(0);

        if (likeSymbol.dataset.clicked === "false") {
            likeSymbol.dataset.clicked = "true";
            icon.style.color = "red";
            likePost("like",postId,ownerId);
            nbLike++
            likeSymbol.querySelector("p").innerText = `${nbLike} J'aimes`;
            localStorage.setItem(`like-post-${postId}-${userId}`,`true`);
        } else {
            likePost("unlike", postId,ownerId);
            let newIconClass = icon.classList[1].replace("-fill", "");
            icon.classList.remove(icon.classList[1]);
            icon.classList.add(newIconClass);
            likeSymbol.dataset.clicked = "false";
            icon.style.color = "black";
            nbLike--
            likeSymbol.querySelector("p").innerText = `${nbLike} J'aimes`;
            localStorage.setItem(`like-post-${postId}-${userId}`,`false`);
        }
    });
}

function likePost(type, postId,idPostOwner){
    $.ajax({
        type: "POST",
        url: window.location.origin.toString()+"/post/like",
        data: {like: type, postId: postId},
        success : function (data) {
            if(data === "true"){
                console.log("PASSED")
            }else{
                console.log("FAILED")
            }
        },
    })

    if (userId !== idPostOwner){
        if(type === "like"){
            stompClientNotif.send("/app/notification/"+idPostOwner,{},JSON.stringify(
                {
                    type: 'info',
                    pseudoSender: pseudoUser,
                    message: `Nouvelle mention jaime de ${pseudoUser}` ,
                    titre: 'Nouveau like',
                    imgSender: userImage,
                    urlNotif: window.location.origin.toString() + '/feed'
                }
            ));
        }
    }
}


const dialog = document.getElementById("dialog-card-focus");
document.addEventListener("DOMContentLoaded", function () {
    let imgDialog = dialog.querySelector("img");

    let cardSamples = document.querySelectorAll(".card-sample");

    cardSamples.forEach(function (cardSample,index) {
        cardSample.addEventListener("mouseover", function () {
            var text = cardSample.querySelector(".text");
            text.classList.remove("no-animation");
        });

        setTimeout(function() {
            cardSample.classList.toggle("animation");
        },index*100);

        cardSample.addEventListener("click", function () {
            let imageUrl = cardSample.querySelector(".img-card").src;
            let cardPost = cardSample.querySelector(".card-post");
            let userIdData = cardSample.querySelector(".img-card").getAttribute("data-user")



            imgDialog.src = imageUrl;
            let cardPostDialog = cardPost.cloneNode(true);
            cardPostDialog.style.display = "flex";
            if (userIdData != null){
                handleLike(cardPostDialog);
            }
            dialog.querySelector(".poste").appendChild(cardPostDialog)
            dialog.showModal();

            const comments = cardPostDialog.querySelectorAll(".commentaire")
            comments.forEach(comment =>{
                comment.querySelector("i").addEventListener("click",function (){
                    deleteCommentaire(comment,comment.getAttribute("data-commentId"))
                })
            })
        })

        const gridStyle = 1 + Math.floor(Math.random() * 4);
        cardSample.classList.add(`card-${gridStyle}`);
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
        const posteList = document.querySelectorAll(".card-sample")

        posteList.forEach(post =>{
            let card = post.querySelector(".card-post")
            if ( card.getAttribute("post-id") === cardPoste.getAttribute("post-id")){
                post.removeChild(card)
                cardPoste.style.display = 'none';
                post.appendChild(cardPoste)
            }
        })
        dialog.close();
    })
});

//========== commentaire =========
const listeEnvComm = document.querySelectorAll(".liste-commentaires");

function addComment(commentaireForm, postId){
    let commentHolder =  dialog.querySelector(".liste-commentaires")

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
                  <i class="bi bi-x-lg"></i>
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
}

document.addEventListener("submit", event =>{
    event.preventDefault();
})

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

function deleteCommentaire(commentaire,commentaireID){
    console.log(commentaireID)
    $.ajax({
        type: "DELETE",
        url: window.location.origin.toString()+"/commentaire/delete",
        data: {commentaireId: commentaireID},
        success : function (data){
            if(data === "Success"){
                console.log("COMMENTAIRE EFFACÉ")
                commentaire.remove()
            }else{
                console.log("COMMENTAIRE FAILED")
            }
        }
    })
}

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

            imgDialog.src = imageUrl;
            let cardPostDialog = cardPost.cloneNode(true);
            cardPostDialog.style.display = "flex";
            dialog.querySelector(".poste").appendChild(cardPostDialog)

            dialog.showModal();
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
        let poste = dialog.querySelector(".poste")
        poste.removeChild(cardPoste);
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
                `
    commentHolder.appendChild(newComment);
    commentaireForm.comment.value = ""

    const listeEnvComm = document.querySelectorAll(".liste-commentaires");
    listeEnvComm.forEach(commEnv =>{
        if (commEnv.getAttribute("post-id")
            === commentaireForm.postId.value
            && commEnv !== commentHolder){

            commEnv.appendChild(newComment.cloneNode(true))
        }
    })
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

document.addEventListener("DOMContentLoaded", function () {
    let dialog = document.getElementById("dialog-card-focus");
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


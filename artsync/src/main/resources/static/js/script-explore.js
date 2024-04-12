document.addEventListener("DOMContentLoaded", function () {
    let dialog = document.getElementById("dialog-card-focus");
    let imgDialog = dialog.querySelector("img");
    document.getElementById("open-nav-cote").addEventListener("click", function (e) {
        document.getElementById("nav-cote").classList.toggle("nav-cote-large");
        if (document.getElementById("nav-cote").classList.contains("nav-cote-large")) {
            document.getElementById("icone-fleche").classList.remove("fa-arrow-right");
            document.getElementById("icone-fleche").classList.add("fa-times");
            document.getElementById("filtre").style.width = "auto";
            document.getElementById("filtre").style.opacity = "1";
            document.getElementById("filtre").style.transition = "opacity 1s ease-in-out";
            document.getElementById("filtre").style.margin = "1.5em";
            document.querySelector(".container-post").style.maxWidth = "88%";
        } else {
            document.getElementById("icone-fleche").classList.remove("fa-times");
            document.getElementById("icone-fleche").classList.add("fa-arrow-right");
            document.getElementById("filtre").style.width = "0";
            document.getElementById("filtre").style.opacity = "0";
            document.getElementById("filtre").style.transition = "none";
            document.getElementById("filtre").style.margin = "0";//mettre classe filtre-open plutot
            document.querySelector(".container-post").style.maxWidth = "100%";
        }
    });

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
            let backgroundImage = cardSample.querySelector(".img-card").style.backgroundImage;
            // Retire le url( "et ") du string backgroundImage de la carte qui est cliqué
            let imageUrl = backgroundImage.slice(5, -2);
            let cardPost = cardSample.querySelector(".card-post");

            imgDialog.src = imageUrl;
            let cardPostDialog = cardPost.cloneNode(true);
            cardPostDialog.style.display = "flex";
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
        let poste = dialog.querySelector(".poste")
        poste.removeChild(cardPoste);
        dialog.close();
    })
});


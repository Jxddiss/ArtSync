document.addEventListener("DOMContentLoaded", function() {
  
  // Prend tout les boutons pour ensuite les animer
  const options = document.getElementById("option-chat");
  const video = document.getElementById("button-fonction-video");
  const phone = document.getElementById("button-fonction-call");
  const paint = document.getElementById("button-fonction-paint");
  const music = document.getElementById("button-fonction-music");
  const btnOpenOptions = document.getElementById("open-options");
  const remoteVideo = document.getElementById("remoteVideo");
  const localVideo = document.getElementById("localVideo");
  const localVideoHolder = document.getElementById("local-holder");
  const videoDialog = document.getElementById("video-chat");

  btnOpenOptions.addEventListener("click", function(e) {
    if (options.dataset.state === "inactive") {
      document.getElementById("icone-fleche").classList.remove("fa-arrow-right");
      document.getElementById("icone-fleche").classList.add("fa-times");
      translateAvecDelai(video, "2em", 0);
      translateAvecDelai(phone, "5.5em", 50);
      translateAvecDelai(paint, "9em", 100);
      translateAvecDelai(music, "12.5em", 150);
      btnOpenOptions.classList.toggle("btn-ouvert");
      options.dataset.state = "active";
    } else {
      document.getElementById("icone-fleche").classList.remove("fa-times");
      document.getElementById("icone-fleche").classList.add("fa-arrow-right");
      translateAvecDelai(video, "0", 0);
      translateAvecDelai(phone, "0", 50);
      translateAvecDelai(paint, "0", 100);
      translateAvecDelai(music, "0", 150);
      btnOpenOptions.classList.toggle("btn-ouvert");
      options.dataset.state = "inactive";
    }
  });

  video.addEventListener("click", function() {
    videoDialog.showModal();
      
    if (navigator.mediaDevices.getUserMedia) {
        navigator.mediaDevices.getUserMedia({ video: true })
        .then(function (stream) {
            remoteVideo.srcObject = stream;
            localVideo.srcObject = stream;
        })
        .catch(function (err0r) {
            console.log("Something went wrong! : " + err0r);
        });
      }
      
  })

  $( function() {
    $("#local-holder").draggable();
  } );

  // Animer les cartes avec les contacts pour les faire apparaitre
  const cartes = document.querySelectorAll(".carte");
  setTimeout(function() {
    cartes.forEach(function(carte,index) {
      setTimeout(function() {
        carte.classList.toggle("animation");
      },index*100);
    });
  },700);

  
});

/**
 * Ajoute un délai lors de l'ouverture des boutons de fonctionnatés
 * ajouté pour le chat
 */
function translateAvecDelai(element, translation, delay) {
  setTimeout(function() {
    element.classList.toggle("inactive");
    element.style.transform = "translate(" + translation + ")";
    element.classList.toggle("btn-ouvert");
  }, delay);

  /** chat video */
  
}
document.addEventListener("DOMContentLoaded", function() {
  
  // Prend tout les boutons pour ensuite les animer
  const options = document.getElementById("option-chat");

  const paint = document.getElementById("button-fonction-paint");
  const music = document.getElementById("button-fonction-music");
  const btnOpenOptions = document.getElementById("open-options");
  const listePersonnes = document.getElementById("liste-personne");
  const listeGroupes = document.getElementById("liste-groupe");


  // place contact courrant en premier
  const newFirstElement = document.querySelector(".carte-active"); //element which should be first in E

  if(newFirstElement.dataset.type === "personne"){
    listePersonnes.insertBefore(newFirstElement, listePersonnes.firstChild);
  }else{
    listeGroupes.insertBefore(newFirstElement, listeGroupes.firstChild);
  }

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



  $( function() {
    $("#local-holder").draggable();
  } );

  // Animer les cartes avec les contacts pour les faire apparaitre
  const cartes = document.querySelectorAll(".carte");
  setTimeout(function() {
    cartes.forEach(function(carte,index) {
      setTimeout(function() {
        carte.classList.toggle("animation");
      },index*60);
    });
  },200);

  
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

}

function getExtension(filename) {
  let parts = filename.split('.');
  return parts[parts.length - 1];
}

function isImage(filename) {
  let ext = getExtension(filename);
  switch (ext.toLowerCase()) {
    case 'jpg':
    case 'gif':
    case 'bmp':
    case 'png':
      return true;
  }
  return false;
}
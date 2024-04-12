
const ouvrir = document.querySelector('#ouvrir');
const postPane = document.querySelector('#postPane');

if (ouvrir && postPane) {
    ouvrir.addEventListener('click', function() {
        postPane.showModal();
    });
}
const closeButton = document.querySelector('#fermer');
if (closeButton && postPane) {
    closeButton.addEventListener('click', function() {
        postPane.close();
    });
}

const ouvrirProfilePan = document.querySelector('#openProfilePan');
const profilePane = document.querySelector('#profilePane');
if (ouvrirProfilePan && profilePane) {
ouvrirProfilePan.addEventListener('click', function() {
profilePane.showModal();
});
}
const closeButtonProfile = document.querySelector('#fermerProfilePan');
if (closeButtonProfile && profilePane) {
closeButtonProfile.addEventListener('click', function() {
profilePane.close();
});
}

document.addEventListener("DOMContentLoaded", function() {
    const buttons = document.querySelectorAll(".template-button");

    buttons.forEach(button => {
        button.addEventListener("click", function() {
            // Supprimer la classe 'selected' de tous les boutons
            document.querySelectorAll(".template-button").forEach(btn => {
                btn.removeAttribute("id");
            });

            // Ajouter la classe 'selected' au bouton cliqué
            this.setAttribute("id", "selected");

            // Récupérer la classe du bouton cliqué
            const className = this.classList[1];

            // Appliquer le fond correspondant en fonction de la classe du bouton cliqué
            document.querySelector(".template").style.background = `var(--${className})`;
        });
    });
    document.getElementById("selected").setAttribute("id", "selected");
});

const dropArea = document.getElementById('drop-area');

dropArea.addEventListener('dragover', function(event) {
  event.preventDefault();
  dropArea.classList.add('hover');
});

dropArea.addEventListener('dragleave', function(event) {
  event.preventDefault();
  dropArea.classList.remove('hover');
});

dropArea.addEventListener('drop', function(event) {
  event.preventDefault();
  dropArea.classList.remove('hover');

  const files = event.dataTransfer.files;
  handleFiles(files);
});

function handleFiles(files) {
  const fileLabel = document.getElementById('file-label');
  if (files.length > 0) {
    fileLabel.innerText = files[0].name;
  } else {
    fileLabel.innerText = 'Glissez et déposez les fichiers ici ou cliquez pour les sélectionner';
  }
}

const fileInput = document.getElementById('file-input');
fileInput.addEventListener('change', function(event) {
  handleFiles(fileInput.files);
});
document.addEventListener("DOMContentLoaded", function() {
  
  // Prend tout les boutons pour ensuite les animer
  const options = document.getElementById("option-chat");

  const paint = document.getElementById("button-fonction-paint");
  const music = document.getElementById("button-fonction-music");
  const btnOpenOptions = document.getElementById("open-options");
  const remoteVideo = document.getElementById("remoteVideo");
  const localVideo = document.getElementById("localVideo");
  const localVideoHolder = document.getElementById("local-holder");

  const videoBoxes = document.querySelectorAll('.video-box2');


  videoBoxes.forEach(videoBox => {
    videoBox.addEventListener('click', () => {
      const videoHolder = videoBox.closest('.videoHolder');
      const focusedVideoHolder = document.querySelector('.focus');

      if (focusedVideoHolder && focusedVideoHolder.parentElement !== videoHolder) {
        focusedVideoHolder.parentElement.style.flex = '1 0 auto';
        focusedVideoHolder.parentElement.style.marginRight = "0%";
        focusedVideoHolder.classList.remove('focus');
        secondaryVideoHolder.append(focusedVideoHolder.parentElement);
        videoHolder.style.flex = '0 0 80%';
        videoHolder.style.marginRight = "20%";
        videoBox.classList.add('focus')
        remoteHolder.append(videoHolder)
      }
      else if (!focusedVideoHolder && !videoBox.classList.contains('focus')) {
        videoHolder.style.flex = '0 0 80%';
        videoHolder.style.marginRight = "20%";
        videoBox.classList.add('focus');
        const otherVideoHolders = Array.from(remoteHolder.children).filter(child => child !== videoHolder.parentElement);
        otherVideoHolders.forEach(holder => {
          secondaryVideoHolder.appendChild(holder);
        });
        remoteHolder.append(videoHolder)
      }
      else if (focusedVideoHolder.parentElement === videoHolder) {
        videoHolder.style.flex = '1 0 auto';
        videoHolder.style.marginRight = "0%";
        videoBox.classList.remove('focus');
        videoBoxes.forEach(vidBox => remoteHolder.append(vidBox.parentElement))
      }
    });
  });

  const userInfoHolders = document.querySelectorAll(".userInfoHolder")
  if (userInfoHolders){
    userInfoHolders.forEach(holder => {
      holder.addEventListener('mouseenter', () => {
          gsap.to(holder.querySelector('h4'),{
            x:"75%",
            duration: 0.25,
            delay:0.25,
            opacity:1,
            ease:"back"
          })
      });

      holder.addEventListener('mouseleave', () => {
        gsap.to(holder.querySelector('h4'),{
          x:"-75%",
          duration: 0.25,
          delay:0.025,
          opacity:0,
          ease:"sine"
        })
      });
    });
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
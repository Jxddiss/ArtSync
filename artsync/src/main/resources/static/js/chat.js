let contactsPrivesArray = [];

document.addEventListener("DOMContentLoaded", function() {

  // Prend tout les boutons pour ensuite les animer
  const options = document.getElementById("option-chat");

  const btnOpenOptions = document.getElementById("open-options");
  const listePersonnes = document.getElementById("liste-personne");
  const conversationRename = document.querySelectorAll(".carte");
  const listeGroupes = document.getElementById("liste-groupe");

  conversationRename.forEach(convElem => {
    if(convElem.dataset.type === "personne" && !convElem.classList.contains("carte-active")){
      contactsPrivesArray.push(convElem);
    }
  });

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
      btnOpenOptions.classList.toggle("btn-ouvert");
      options.dataset.state = "active";
    } else {
      document.getElementById("icone-fleche").classList.remove("fa-times");
      document.getElementById("icone-fleche").classList.add("fa-arrow-right");
      translateAvecDelai(video, "0", 0);
      translateAvecDelai(phone, "0", 50);
      btnOpenOptions.classList.toggle("btn-ouvert");
      options.dataset.state = "inactive";
    }
  });



  $( function() {
    $("#local-holder").draggable();
  } );

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


const vNavBar = document.getElementById("liste-contacts")
const expandBtn = document.getElementById("expandButton")
const chatContent = document.querySelector(".main-content")
let openNavbar   = false

expandBtn.addEventListener("click",function (){
  if (!openNavbar){
    openNavbar = true
    gsap.to(vNavBar,{
      display:"flex",
    })
    gsap.to(vNavBar,{
      width:"100%",
      opacity:1,
      duration:0.65,
      ease:"back"
    })

    gsap.to(chatContent,{
      width:"0%",
      opacity:0,
      duration:0.25,
    })
    gsap.to(chatContent,{
      display:"none",
      delay:0,
    })
    gsap.to(expandBtn,{
      rotate:180,
      ease:"back"
    })
  }else{
    openNavbar = false
    gsap.to(vNavBar,{
      width:"0%",
      opacity:0,
      duration:0.25,
    })
    gsap.to(vNavBar,{
      display:"none",
    })
    gsap.to(chatContent,{
      width:"100%",
      opacity:1,
      duration:0.65,
      ease:"back"
    })
    gsap.to(chatContent,{
      rotate:0,
      ease:"back"
    })
    gsap.to(chatContent,{
      display:"flex",
      delay:0,
    })
  }
})

/* clear la connection au websocket*/
let isOnIos = navigator.userAgent.match(/iPad/i)|| navigator.userAgent.match(/iPhone/i);
if(isOnIos){
  let unloaded = false;
  window.addEventListener('visibilitychange', function () {
    if (document.hidden) {
      stompClient.disconnect();
    }else{
      socket = new SockJS('/ws');
      stompClient = Stomp.over(socket);

      stompClient.connect({}, function(frame) {
        stompClient.subscribe('/topic/conversation/[[${conversationCourrante.getId()}]]', function(message) {
          addMessage(JSON.parse(message.body));
        });
      });
      unloaded = false;
    }
  });
}else{
  window.addEventListener('beforeunload', function (e) {
    delete e['returnValue'];
    stompClient.disconnect();
  });
}

function showNotificationChat(notification) {
  if(notification.appel){
    showAppel(notification);
  }else {
    contactsPrivesArray.forEach(contact => {
      if(contact.getAttribute("pseudo") === notification.pseudoSender && !notification.urlNotif.includes('projet')){
        if(contact.lastElementChild.tagName !== 'span'){
          let notifIndicator = document.createElement("span")
          notifIndicator.classList.add("notification-indicator")
          contact.appendChild(notifIndicator);
        }
      }
    })
  }

  $.ajax({
    type:'POST',
    url: window.location.origin.toString() + "/notification/set-lu",
    data: {id:notification.id}
  })
}

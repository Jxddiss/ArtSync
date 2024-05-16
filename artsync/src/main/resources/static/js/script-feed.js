const dialogOpenStories = document.getElementById("stories-open-dialog");
const btnPublier = document .getElementById("btn-publier")
const postPane = document.getElementById('postPane');
const closeButton = document.getElementById('fermer');
const postInput = document.getElementById('file-input');
const userId = document.body.getAttribute("user-id");

//=========== change aspect-ratio media ========
document.querySelectorAll(".media-holder").forEach((mediaHolder) => {
  const tootltip = mediaHolder.lastElementChild;
  const tootltipIcon = tootltip.firstElementChild;
  const media = mediaHolder.firstElementChild;

  tootltip.addEventListener("click", function () {
    if (mediaHolder.dataset.maximiser === "true") {
      media.style.objectFit = "contain";
      mediaHolder.dataset.maximiser = "false";
      tootltipIcon.classList.remove("bi-arrows-angle-contract");
      tootltipIcon.classList.add("bi-arrows-angle-expand");
    } else {
      media.style.objectFit = "cover";
      mediaHolder.dataset.maximiser = "true";
      tootltipIcon.classList.remove("bi-arrows-angle-expand");
      tootltipIcon.classList.add("bi-arrows-angle-contract");
    }
  });
});

/*========= change icon like and mouseover click ============*/

document.querySelectorAll(".like").forEach((likeSymbol) => {
  const postId = likeSymbol.getAttribute("post-id");
  const ownerId = likeSymbol.getAttribute("owner-id")
  const icon = likeSymbol.querySelector("i");

  if(localStorage.getItem(`like-post-${postId}-${userId}`)){
    if(localStorage.getItem(`like-post-${postId}-${userId}`) === "true"){
      likeSymbol.dataset.clicked = "true";
      icon.style.color = "red";
      let fill = icon.classList[1] + "-fill";
      icon.classList.remove(icon.classList[1]);
      icon.classList.add(fill);
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
});

//========== Gsap Apparition des postes on scroll =============

let postContainer = gsap.utils.toArray(".trig");

postContainer.forEach((post) => {
  gsap.from(post, {
    scale: 0.1,
    opacity: 0,
    autoAlpha: 0,
    scrollTrigger: {
      trigger: post,
      start: "-=600 bottom",
      end: "-=200 90%",
      scrub: 1,
    },
  });
});
/*
//========= Stories =============
var stories = document.getElementById("stories");

stories.addEventListener("wheel", function (e) {
  if (e.deltaY > 0) stories.scrollLeft += 45;
  else stories.scrollLeft -= 45;
});

stories.addEventListener("mouseover", function () {
  document.querySelector("body").style.overflowY = "hidden";
});

stories.addEventListener("mouseout", function () {
  document.querySelector("body").style.overflowY = "scroll";
});

stories.childNodes.forEach((story) => {
  story.addEventListener("click", function () {
    story.classList.toggle("storie-seen");
    dialogOpenStories.showModal();
  });
})*/

//========= publier Open Dialog =============
btnPublier.addEventListener('click', function() {
  postPane.showModal()
  postPane.style.display = 'flex';
});



closeButton.addEventListener('click', function() {
  postPane.close();
  postPane.style.display = 'none';
});

//postPreview section
postInput.addEventListener('change', function() {
  const file = postInput.files[0];
  const reader = new FileReader();
  reader.onload = function() {
    postPreview.src = reader.result;
  }
  reader.readAsDataURL(file);
});

function checkFile(form){
  const input = form.querySelector("input[type='file']");
  if (input.files && input.files[0]) {
    stompClientNotif.send("/app/notification/post/"+pseudoUser,{},JSON.stringify(
        {
          type: 'info',
          pseudoSender: pseudoUser,
          message: `Nouveau post de ${pseudoUser}` ,
          titre: 'Nouveau post',
          imgSender: userImage,
          urlNotif: window.location.origin.toString() + '/feed'
        }
    ));
    return true;
  }
  return false;
}

//========== commentaire =========
const commentaireForm = document.querySelectorAll(".input-box-commentaire");
const listeEnvComm = document.querySelectorAll(".liste-commentaires");
commentaireForm.forEach(commentForm =>{
    let commentHolder;
    listeEnvComm.forEach(commEnv =>{
      if (commEnv.getAttribute("post-id") === commentForm.getAttribute("post-id")){
        console.log("----------------------- ici -------------------")
        commentHolder = commEnv;
      }
    })

    let commentInput = commentForm.querySelector("input");
    commentForm.addEventListener("submit", function (event){
      event.preventDefault();

      const comment = commentInput.value

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
                        ${comment}
                      </p>
                    `
      commentHolder.appendChild(newComment);
      commentForm.querySelector("input").value = ""
    })
})

function ajouterCommentaire(form){
  $.ajax({
    type: "POST",
    url: window.location.origin.toString()+"/post/comment",
    data: {comment: form.comment.value, postId: form.postId.value},
    success : function (data) {
      if(data === "true"){
        console.log("PASSED")
      }else{
        console.log("FAILED")
      }
    },
  })

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

//========== button mobile =========
const vNavBar = document.querySelector(".dashboard-left")
const expandBtn = document.getElementById("expandButton")
const feedContent = document.getElementById("container-post")
let openNavbar   = false

expandBtn.addEventListener("click",function (){
  if (!openNavbar){
    openNavbar = true
    gsap.to(vNavBar,{
      width:"100%",
      opacity:1,
      duration:0.65,
      ease:"back",

    })
    gsap.to(vNavBar,{
      display:"flex"
    })
    gsap.to(feedContent,{
      width:"0%",
      opacity:0,
      duration:0.25,
    })
    gsap.to(feedContent,{
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
      duration:0.25
    })
    gsap.to(vNavBar,{
      display:"none"
    })
    gsap.to(feedContent,{
      width:"95%",
      opacity:1,
      delay:0.5,
      duration:0.65,
      ease:"back"
    })
    gsap.to(expandBtn,{
      rotate:0,
      ease:"back"
    })
    gsap.to(feedContent,{
      display:"flex",
      delay:0,
    })
  }
})


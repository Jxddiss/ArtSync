const dialogOpenStories = document.getElementById("stories-open-dialog");
const btnPublier = document .getElementById("btn-publier")
const postPane = document.getElementById('postPane');
const closeButton = document.getElementById('fermer');
const postInput = document.getElementById('file-input');

//=========== change aspect-ratio media ========
document.querySelectorAll(".media-holder").forEach((mediaHolder) => {
  const tootltip = mediaHolder.lastElementChild;
  const tootltipIcon = tootltip.firstElementChild;
  const media = mediaHolder.firstElementChild;

  tootltip.addEventListener("click", function () {
    if (mediaHolder.dataset.maximiser == "true") {
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
  likeSymbol.firstElementChild.addEventListener("mouseout", (e) => {
    if (likeSymbol.dataset.clicked === "false") {
      let newIconClass = e.target.classList[1].replace("-fill", "");
      e.target.classList.remove(e.target.classList[1]);
      e.target.classList.add(newIconClass);
    }
  });

  likeSymbol.firstElementChild.addEventListener("mouseover", (e) => {
    if (likeSymbol.dataset.clicked === "false") {
      let fill = e.target.classList[1] + "-fill";
      e.target.classList.remove(e.target.classList[1]);
      e.target.classList.add(fill);
    }
  });

  likeSymbol.firstElementChild.addEventListener("click", (e) => {
    if (likeSymbol.dataset.clicked === "false") {
      likeSymbol.dataset.clicked = "true";
      e.target.style.color = "red";
    } else {
      let newIconClass = e.target.classList[1].replace("-fill", "");
      e.target.classList.remove(e.target.classList[1]);
      e.target.classList.add(newIconClass);
      likeSymbol.dataset.clicked = "false";
      e.target.style.color = "black";
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
})

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
    return true;
  }
  return false;
}

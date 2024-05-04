
const cards = document.querySelectorAll('.magic-card');
const sprintImages = document.querySelectorAll('#sprintImage');
const dashes = document.querySelectorAll('.dashProgress');
const milestoneHeaders = document.querySelectorAll('.milestoneHeader');
const collaborationContainer = document.querySelector('.showcaseGroup');
const finalPreview = document.querySelector('.finalPreview');
const socialHolder = document.querySelector('.socialHolder');
const commentHolder = document.querySelector('.commentHolder');
const comments = document.querySelectorAll('.comment');
const likeCount = document.getElementById('likeCount');
const commentCount = document.getElementById('commentCount');
const pod1 = document.querySelector('.pod1');
const pod2 = document.querySelector('.pod2');
const pod3 = document.querySelector('.pod3');
const podiumTrigger = document.querySelector('.triggerPodium');
const podiumHeader = document.querySelector('.podiumHeader');
const podiumHeaderString = [' DU JOUR', ' DE LA SEMAINE', ' DU MOIS', ' DE L\'ANNÉE'];
const textChange = document.getElementById('textChanger')
const imageHolder1 = document.getElementById('holder1');
const imageHolder2 = document.getElementById('holder2');
const imageHolder3 = document.getElementById('holder3');
const coloredImage = document.getElementById('coloredImage');
const bgImage = document.getElementById('bgImage');
const winningPic = document.getElementById('winningPic');
//set color image to 0 opacity
coloredImage.style.opacity = 0;
gsap.registerPlugin(ScrollTrigger);


//SECTION INFORMATION
//Cards
cards.forEach((card, index) => {
    gsap.from(card, {
        scrollTrigger: {
        trigger: card,
        start: 'top 80%',
        },
        x: -100,
        opacity: 0,
        duration: 1,
        delay: index * 0.2,
        ease: 'back'
    });
});

// SECTION GROUPE + SOCIAL+ PODIUM
let numberOfImages = 42
let index = 1;

function appendImage() {
    if (index <= numberOfImages) {
        let image = document.createElement('img');
        image.src = `media/image/wreckingBallSketchLayers/${index}.png`;
        imageHolder1.appendChild(image);
        index++;
        setTimeout(appendImage, 45);
    }
}
function removeImages() {
    while (imageHolder1.firstChild) {
        imageHolder1.removeChild(imageHolder1.firstChild);
    }
}
ScrollTrigger.create({
    trigger: imageHolder1,
    start: 'top 80%',
    onEnter: () => {
        index = 1;
        removeImages();
        appendImage();
    }
});
ScrollTrigger.create({
    trigger: imageHolder2,
    start: 'top 80%',
    onEnter: () => {
        gsap.fromTo(coloredImage, {
            opacity: 0,
        }, {
            opacity: 1,
            delay: 1,
            duration: 1.5,
        });
    }
});
ScrollTrigger.create({
    trigger: imageHolder3,
    start: 'top 80%',
    onEnter: () => {
        gsap.fromTo(bgImage, {
            x: -300,
        }, {
            x: 0,
            duration: 1.5,
            ease: 'circular',
        });
    }
});
//Dashes progress
dashes.forEach((dash) => {
    gsap.to(dash, {
        scrollTrigger: {
            trigger: dash,
            start: 'top 60%',
            end: 'bottom 80%',
    
            scrub: 1,
        },
        opacity: 0.5,
    });
});


//Images placeholders
sprintImages.forEach((sprintImage) => {
    gsap.to(sprintImage, {
        scrollTrigger: {
            trigger: sprintImage,
            start: 'top 60%',
            end: 'bottom 80%',
            scrub: 1,
        },
        opacity: 1,
        boxShadow: '0 0 50px 12px rgba(255, 255, 255, 0.25)',
    });
});

//Milestones headers
gsap.to(milestoneHeaders[0], {
    scrollTrigger: {
        trigger: milestoneHeaders[0],
        start: 'top 60%',
        end: 'bottom 80%',
        scrub: 1,
    },
    text: `SPRINT 1 <i class="bi bi-check-square" style="color:green"></i>`,
});
gsap.to(milestoneHeaders[1], {
    scrollTrigger: {
        trigger: milestoneHeaders[1],
        start: 'top 60%',
        end: 'bottom 80%',
        scrub: 1,
    },
    text: `SPRINT 2 <i class="bi bi-check-square" style="color:green"></i>`,
});
gsap.to(milestoneHeaders[2], {
    scrollTrigger: {
        trigger: milestoneHeaders[2],
        start: 'top 60%',
        end: 'bottom 80%',
        scrub: 1,
    },
    text: `SPRINT 3 <i class="bi bi-check-square" style="color:green"></i>`,
});
const timeline = gsap.timeline();

let output = '395%'
let output0 = '-100'
if (window.innerWidth < 700) {
    output='85%'
    output0='-10'
}
timeline
    .to(finalPreview, {
        scrollTrigger: {
            trigger: finalPreview,
            start: 'top 5%',
            end: output,
            scrub: 1,
            pin: true,
            pinSpacing: false,

        }
    })
    .to(sprintImages[sprintImages.length - 1], {
        scrollTrigger: {
            trigger: sprintImages[sprintImages.length - 1],
            start: 'top 80%',
            end: '100%',
            scrub: 1,
        },
        delay: 4,
        x: output0,
        scale: 1,
    }, "<")
    .to(socialHolder, {
        scrollTrigger: {
            trigger: socialHolder,
            start: 'top 15%',
            end: '20%',
            scrub: 1,
        },
        x: 0,
        delay: 0.5,
        y: 200,
        opacity: 1,
    }, "<")
    .to(comments, {
        scrollTrigger: {
            trigger: commentHolder,
            start: 'top 15%',
            end: '400%',
            scrub: 1,
        },
        opacity: 0.75,
        y: -1580,
        ease: "sine"
    }, "<")
    .to(likeCount, {
        scrollTrigger: {
            trigger: socialHolder,
            start: 'top 15%',
            end: '200%',
            scrub: 1,
        },
        textContent: "1257",
        duration: 4,
        snap: { textContent: 1 },
        ease: "sine",
    }, "<")
    .to(commentCount, {
        scrollTrigger: {
            trigger: socialHolder,
            start: 'top 15%',
            end: '200%',
            scrub: 1,
        },
        textContent: "215",
        duration: 4,
        snap: { textContent: 1 },
        ease: "sine",
    }, "<")
    .to(pod1, {
        scrollTrigger: {
            trigger: podiumTrigger,
            start: 'bottom 50%',
            end: 'bottom 85%',
            scrub: 1,
        },
        height: '20rem',
    }, "<")
    .to(pod2, {
        scrollTrigger: {
            trigger: podiumTrigger,
            start: 'bottom 50%',
            end: 'bottom 90%',
            scrub: 1,
        },
        height: '10rem',
    }, "<")
    .to(pod3, {
        scrollTrigger: {
            trigger: podiumTrigger,
            start: 'bottom 50%',
            end: 'bottom 95%',
            scrub: 1,
        },
        height: '5rem',
    }, "<")
    .to(podiumHeader, {
        scrollTrigger: {
            trigger: podiumTrigger,
            start: 'top 50%',
            end: 'bottom 70%',
            scrub: 1,
        },
        opacity: 1,
    }, "<");

ScrollTrigger.create({
    trigger: podiumTrigger,
    start: 'bottom 50%',
    end: 'bottom 70%',
    onEnter: () => {
        gsap.to(sprintImages[sprintImages.length - 1], {
            duration: 1,
            scale: 0.9,
            x:"45%",
            ease: "sine",
            boxShadow: '0 0 75px 25px rgba(255, 191, 108, 0.25)',
            overwrite: true
        });
        gsap.to(socialHolder, {
            duration: 0.5,
            opacity: 0,
            ease: "sine",
        });
        gsap.to(winningPic, {
            delay: 1.5,
            duration: 1,
            ease: "sine",
            css: {
                filter: "drop-shadow(0 0 10px rgba(255, 191, 108, 0.25))"
            },
            scale: 3.2,
        })
    }
});

let i = 0;
if (window.innerWidth > 700){
    setInterval(() => {
        gsap.to(textChange, {
            duration: 0.5,
            text: podiumHeaderString[i],
            ease: "sine",
        });
        i = (i + 1) % podiumHeaderString.length;
    }, 1500);

    gsap.set('.siteHeader, .buttonStartHolder', { opacity: 0 });
}


//SECTION FIN DE PAGE
const tl = gsap.timeline();
const siteHeader = document.querySelector('.siteHeader');
const headerTitle = document.getElementById('siteTitle');
const headerSubtitle = document.getElementById('siteDesc');
const startButton = document.querySelector('.buttonStartHolder');

ScrollTrigger.create({
    trigger: siteHeader,
    start: 'top 25%',
    end: 'bottom 80%',
    scrub: 1, 
    onEnter: () => {
        gsap.to(siteHeader, {
            opacity: 1,
            duration: 1,
            ease: "sine",
        });
        gsap.to(headerTitle, {
            duration: 1,
            ease: "sine",
            opacity: 1,
        }); 
        gsap.to(headerSubtitle, {
            text: "Où l'art rencontre la collaboration.",
            duration: 1,
            delay: 1,
            ease: "sine",
            opacity: 1,
        });
        gsap.to(startButton, {
            opacity: 1,
            delay: 3,
            duration: 1,
            ease: "sine",
        });
    },
});
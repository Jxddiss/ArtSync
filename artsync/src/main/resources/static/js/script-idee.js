
//Input
const inputField = document.getElementById('inputField');
const output = document.querySelector('.output');
const outputHolder = document.getElementById('outputHolder');

inputField.addEventListener('input', function() {
    output.textContent = this.value;
    gsap.to('.output', {
        duration: 3,
        opacity: 1,
        ease: "back.out",
    });
});


//Idées backgrounds
const colors = ['rgb(255, 220, 249)', 'rgb(255, 251, 201)', 'rgb(201, 225, 255)', 'rgb(201, 225, 255)'];

gsap.set(".idee", { opacity: 0 });

function animateElement(element, index) {
    const randomColor = colors[Math.floor(Math.random() * colors.length)];

    gsap.fromTo(element, {
        opacity: 0,
        y: gsap.utils.random(-15, -600),
        x: gsap.utils.random(0, 1200),
        color: randomColor
    }, {
        duration: gsap.utils.random(2, 3),
        opacity: 0.5,
        y: '-=30',
        ease: 'back.out',
        color: 'grey',
        onComplete: () => {
            gsap.to(element, {
                duration: 1,
                opacity: 0,
                ease: 'sine',
                onComplete: () => {
                    if (index === gsap.utils.toArray(".idee").length - 1) {
                        animateAllElements();
                    }
                }
            });
        },
        delay: index * 0.5
    });
}

function animateAllElements() {
    gsap.utils.toArray(".idee").forEach((element, index) => {
        animateElement(element, index);
    });
}
animateAllElements();


//Idées choix 
function animateBackground() {
    gsap.utils.toArray(".idee-output").forEach((element, index) => {
        gsap.fromTo(element, {
            duration: 1,
            backgroundColor: "rgb(0,0,0,0.1)", 
            delay: index * 0.075
        }, {
            duration: 1,
            backgroundColor: "rgba(255, 255, 255, 0.1)",
            boxShadow: "0 0 25px 10px rgba(0, 0, 0, 0.1)",
            ease: "back",
            delay: index * 0.08, 
            onComplete: () => {
                if (index === gsap.utils.toArray(".idee-output").length - 1) {
                    gsap.utils.toArray(".idee-output").forEach((element, index) => {
                        gsap.to(element, {
                            duration: 0.75,
                            boxShadow: "0 0 0px 0px rgba(255, 255, 255, 0)",
                            backgroundColor: "transparent",
                            delay: index * 0.07 
                        });
                    });
                }
            }
        });
    });
}
animateBackground();
setInterval(() => {
    animateBackground();
}, 10000);


const children = outputHolder.children
gsap.to(children, {duration: 0.5, opacity: 1, scale:1, delay: 0.15, stagger: 0.1, ease:'back', filter: 'blur(0px)'})
const pods = document.querySelectorAll('.podium');
const ribbon = document.querySelector('.ribbon');
const postHolder = document.querySelector('.postHolder');
const posts = document.querySelectorAll('.post');



const stringsDate = ["DU JOUR", "DE LA SEMAINE", "DU MOIS", "DE L'ANNÉE"]




//Podiums
gsap.to(pods[0], {height: '8rem', duration: 1, ease: 'back', delay: 0.25});
gsap.to(pods[1], {height: '12rem', duration: 1, ease: 'back', delay: 0.25});
gsap.to(pods[2], {height: '5rem', duration: 1, ease: 'back', delay: 0.25});

//Ribbons
let ribbonOutput = "125%"
gsap.to(ribbon.children, {width: ribbonOutput, duration: 0.4, ease: 'back', stagger: 0.3});

//PostHolder
gsap.to(postHolder, {opacity: 1, duration: 1, delay:0.25, scrollTrigger: {
    trigger: postHolder,
    start: 'top 80%'
}});

//Posts
posts.forEach((post, index) => {
    gsap.to(post, {opacity: 1, y: 0, duration: 2, delay: 0.1 + index*0.25, scrollTrigger: {
        trigger: postHolder,
        start: 'top 80%'
    }});
});

const cards = document.querySelectorAll(".card-3d");

cards.forEach( el =>{
    el.addEventListener("mousemove", e =>{

        let elRect = el.getBoundingClientRect();

        let x = e.clientX - elRect.x;
        let y = e.clientY - elRect.y;

        let midCardWidth = elRect.width / 2;
        let midCardHeight = elRect.height / 2;

        let angleY = -(x - midCardWidth) / 10;
        let angleX = (y - midCardHeight) / 10;

        let glowX = x / elRect.width * 100;
        let glowY = y / elRect.height * 100;

        let offsetX = x / elRect.width * 3;
        let offsetY = y / elRect.height * 3;

        el.children[0].style.transform = `rotateX(${angleX}deg) rotateY(${angleY}deg) scale(1.1)`;
        el.children[0].style.transition = 'transform 0.15s ease-out, box-shadow 0.25s ease-in-out'
        el.children[0].children[0].style.transform = `translate(-${offsetX}%, ${offsetY}%) scale(1.1)`;

        el.children[1].style.transform = `rotateX(${angleX}deg) rotateY(${angleY}deg) scale(1.1)`;
        el.children[1].style.transition = 'all 0.15s ease-out'
        el.children[1].style.background = `radial-gradient(circle at ${glowX}% ${glowY}%, rgba(248, 243, 228, 0.22), transparent)`;
    })

    el.addEventListener("mouseenter",e =>{
        el.children[0].children[1].style.transform = `translateX(0)`
    })

    el.addEventListener("mouseleave", ()=>{
        el.children[0].style.transform = `rotateX(0) rotateY(0)`
        el.children[0].style.transition = 'transform 0.5s ease-in, box-shadow 0.25s ease-in-out'
        el.children[0].children[0].style.transform = `translate(0%,0%)`;
        el.children[0].children[1].style.transform = `translateX(-100%)`
        el.children[1].style.transform = `rotateX(0) rotateY(0)`
        el.children[1].style.background = `radial-gradient(circle at 80% 180%, rgba(248, 243, 228, 0.22), transparent)`;
        el.children[1].style.transition = 'all 0.5s ease-in'
    })

})
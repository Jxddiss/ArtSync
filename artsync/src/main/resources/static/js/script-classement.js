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
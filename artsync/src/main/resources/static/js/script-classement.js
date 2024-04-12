const pods = document.querySelectorAll('.podium');
const ribbon = document.querySelector('.ribbon');
const postHolder = document.querySelector('.postHolder');
const posts = document.querySelectorAll('.post');
const timeButtons = document.querySelector('.timeButtons');
const timeButtonsChildren = timeButtons.children;
const timeButtonsArray = Array.from(timeButtonsChildren);
const timeInput = document.getElementById('timeInput');
const stringsDate = ["DU JOUR", "DE LA SEMAINE", "DU MOIS", "DE L'ANNÉE"]


//Buttons
timeButtonsArray.forEach(button => {
    button.addEventListener('click', function() {
        timeButtonsArray.forEach(button => {
            button.classList.remove('selectedButton');
        });
        this.classList.add('selectedButton');
    });
});
timeButtonsArray.forEach((button, index) => {
    button.addEventListener('click', function() {
        gsap.to(timeInput, {text: stringsDate[index], duration: 0.5, ease: 'back'});
    });
});

//Podiums
gsap.to(pods[0], {height: '8rem', duration: 1, ease: 'back', delay: 0.25});
gsap.to(pods[1], {height: '12rem', duration: 1, ease: 'back', delay: 0.25});
gsap.to(pods[2], {height: '5rem', duration: 1, ease: 'back', delay: 0.25});

//Ribbons
gsap.to(ribbon.children, {width: '125%', duration: 0.4, ease: 'back', stagger: 0.3});

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
document.addEventListener('DOMContentLoaded', function() {
    const verticalNavbar = document.querySelector('.vertical-header');
    const options = document.querySelectorAll('.option');
    const closeBtn = options.item(0);

    let menuOpen = true;
    closeBtn.addEventListener('click', function() {
        if (menuOpen) {
            menuOpen = false;
            options.forEach(option => {
                gsap.to(option.lastElementChild, {
                    opacity: 0,
                    duration: 0.2,
                    onComplete: () =>{
                        option.lastElementChild.style.display = "none";
                    }
                });
            });
            gsap.to(verticalNavbar,{
                borderRadius: "0px 15px 0px 0px",
                width:"3rem",
                ease: "sine",
                delay: 0.2
            })
        }
        else{
            menuOpen = true;
            options.forEach(option => {
                option.lastElementChild.style.display = "block";
                gsap.to(option.lastElementChild, {
                    opacity: 1,
                    duration: 1,
                    delay: 0.5
                });
            });
            gsap.to(verticalNavbar,{
                borderRadius: "0px 60px 0px 0;x",
                width:"20rem",
                ease: "sine",
            })
        }
    });
});
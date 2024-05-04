const resultHolder = document.querySelector(".resultHolder")
const children = resultHolder.children
const threads = document.querySelectorAll(".forumHolder");
const filterTags = document.querySelectorAll(".filterTag")
const checkedFilters = [];
const filteredResult = [];

const vNavBar = document.querySelector(".verticalHeader")
const expandBtn = document.getElementById("expandButton")
const forumContent = document.querySelector(".forumContent")
let openNavbar   = false

expandBtn.addEventListener("click",function (){
    if (!openNavbar){
        openNavbar = true
        gsap.to(vNavBar,{
            width:"100%",
            opacity:1,
            duration:0.65,
            ease:"back"
        })
        gsap.to(forumContent,{
            width:"0%",
            opacity:0,
            duration:0.25,
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
        gsap.to(forumContent,{
            width:"100%",
            opacity:1,
            duration:0.65,
            ease:"back"
        })
        gsap.to(expandBtn,{
            rotate:0,
            ease:"back"
        })
    }
})

gsap.to(children,{
    scale: 1,
    opacity: 1,
    duration: 0.25,
    ease: "back",
    stagger: 0.1
})

function gestionDeThread() {
    checkedFilters.length = 0;
    filterTags.forEach(filter => {
        if (filter.checked) {
            checkedFilters.push(filter.value);
        } else if (!filter.checked && checkedFilters.includes(filter.value)){
            const index = checkedFilters.indexOf(filter.value);
            if (index !== -1) {
                checkedFilters.splice(index, 1);
            }
        }
    });
    threads.forEach(thread => {
        const threadTags = Array.from(thread.querySelectorAll(".threadTags .tag")).map(tag => tag.textContent);
        const hasMatchingFilter = threadTags.some(tag => checkedFilters.includes(tag));

        if (hasMatchingFilter) {
            filteredResult.push(thread);
        } else if (!hasMatchingFilter && filteredResult.includes(thread)) {
            const index = filteredResult.indexOf(thread);
            if (index !== -1) {
                filteredResult.splice(index, 1);
            }
        }
        else{
            thread.style.display = "none";
        }
    });

    filteredResult.forEach(thread => {
        thread.style.display = "flex";
    });
    if (checkedFilters.length < 1){
        threads.forEach(thread => {
            thread.style.display = "flex";
        });
    }
}

filterTags.forEach(filter => {
    filter.addEventListener("change", gestionDeThread);

});

threads.forEach(thread => {
    const chevron = thread.querySelector(".threadOptions").querySelector(".chevronDiv").querySelector("i")
    chevron.addEventListener("click", function(){
        const commentSection = thread.querySelector(".commentSection")
        const formContainer = thread.querySelector(".commentForm")
        const comments = thread.querySelectorAll(".comment")
        if (commentSection.style.minHeight !== "8rem"){
            gsap.to(commentSection,{
                height:"fit-content",
                minHeight:"8rem",
                ease:"back",
                delay:0.1,
                duration:0.5,
                paddingBottom: "10%",
            })
            gsap.to(formContainer,{
                opacity: 1
            })
            gsap.to(comments,{
                minHeight:"5rem",
                opacity:1,
                duration:0.5,
                stagger:0.1,
            })
        }else{
            gsap.to(commentSection,{
                minHeight:"0",
                height:"0",
                duration:0.5,
                ease:"back",
                paddingBottom: "0"
            })
            gsap.to(formContainer,{
                opacity: 0,
                duration:0.15,
            })
            gsap.to(comments,{
                opacity:0,
                duration:0.05
            })
        }

    })
})
function ajouterCommentaire(form){
    $.ajax({
        type: "POST",
        url: window.location.origin.toString()+"/forum/comment",
        data: {comment: form.comment.value, forumId: form.forumId.value},
        success : function (data) {
            if(data === "true"){
                console.log("PASSED")
            }else{
                console.log("FAILED")
            }
        },
    })
}



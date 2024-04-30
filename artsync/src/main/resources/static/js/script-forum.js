const resultHolder = document.querySelector(".resultHolder")
const children = resultHolder.children
const threads = document.querySelectorAll(".forumHolder");
const filterTags = document.querySelectorAll(".filterTag")
const checkedFilters = [];
const filteredResult = [];

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


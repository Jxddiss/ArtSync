const resultHolder = document.querySelector(".resultHolder")
const children = resultHolder.children

gsap.to(children,{
    scale: 1,
    opacity: 1,
    duration: 0.25,
    ease: "back",
    stagger: 0.1
})
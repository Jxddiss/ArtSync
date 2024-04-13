const rechercheHeader = document.getElementById('rechercheHeader');
let headerText = rechercheHeader.textContent
const resultContainer = document.getElementById('resultContainer')

rechercheHeader.textContent = 'RECHERCHE'
gsap.to(rechercheHeader, {duration: 0.5, text: headerText,delay:0})

//appear each child of resultContainer one by one from opacity 0 to 1 gsap
const children = resultContainer.children
gsap.to(children, {duration: 0.5, opacity: 1, scale:1, delay: 0.15, stagger: 0.1, ease:'back', filter: 'blur(0px)'})



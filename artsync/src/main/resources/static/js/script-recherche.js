const rechercheHeader = document.getElementById('rechercheHeader');
const userFilter = document.getElementById('userFilter')
const groupFilter = document.getElementById('groupFilter')
const streamFilter = document.getElementById('streamFilter')

const rechercheTexts = ["RECHERCHE D'UTILISATEURS", "RECHERCHE DE GROUPES", "RECHERCHE DE STREAMS"]

userFilter.addEventListener('click', () => {
    gsap.to(rechercheHeader, {duration: 0.5, text: rechercheTexts[0]})
    selectFilter(userFilter)
})
groupFilter.addEventListener('click', () => {
    gsap.to(rechercheHeader, {duration: 0.5, text: rechercheTexts[1]})
    selectFilter(groupFilter)
})
streamFilter.addEventListener('click', () => {
    gsap.to(rechercheHeader, {duration: 0.5, text: rechercheTexts[2]})
    selectFilter(streamFilter)
})

function selectFilter(filter) {
    userFilter.classList.remove('selectedFilter')
    groupFilter.classList.remove('selectedFilter')
    streamFilter.classList.remove('selectedFilter')
    filter.classList.add('selectedFilter')
}
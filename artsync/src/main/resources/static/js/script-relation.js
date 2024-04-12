//Carte utilisateurs
document.addEventListener("DOMContentLoaded", function() {
    const cards = document.querySelectorAll('.carte');
  
    cards.forEach(function(card, index) {
      setTimeout(function() {
        card.classList.add('animate');
      }, index * 150); 
    });
  });

//Carte activité
document.addEventListener("DOMContentLoaded", function() {
    const activityCards = document.querySelectorAll('.activity-card');
    console.log("yes");
    activityCards.forEach(function(card, index) {
      setTimeout(function() {
        card.classList.add('animate');
      }, index * 100); 
    });
});
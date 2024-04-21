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

const newfilePreview = document.getElementById('newfilePreview');
if(newfilePreview){
    console.log("HARRO")
    const fileInput = document.getElementById('filtInput');
    fileInput.addEventListener('change', function(){
        const file = fileInput.files[0];
        if(file){
            const reader = new FileReader();
            reader.onload = function(){
                const result = reader.result;
                newfilePreview.src = result;
            }
            reader.readAsDataURL(file);
        }
    });
}

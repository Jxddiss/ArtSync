const demandeFormulaire = document.getElementById("formulaire-demande");
const input = demandeFormulaire.querySelector("input");
const textarea = demandeFormulaire.querySelector("textarea");

demandeFormulaire.addEventListener("submit", ev=>{
    ev.preventDefault()
    if (input.value.trim() === '' || textarea.value.trim() === ''){
        showNotification({
            type: 'warn',
            message: 'Tous les champs doivent être remplie!',
            titre: 'Avertissement',
        })
    }else{
        $.ajax({
            type: "POST",
            url: window.location.origin.toString()+"/api/demandes-admin/envoyer",
            data: {email: input.value, message: textarea.value},
            success : function (data) {
                if(data === "true"){
                    console.log("PASSED")
                    showNotification({
                        type: 'success',
                        message: 'Demande envoyée',
                        titre: 'Success',
                    })
                }else if(data === "exist"){
                    console.log("FAILED")
                    showNotification({
                        type: 'danger',
                        message: "Demande pour ce compte déjà faite",
                        titre: 'Erreur',
                    })
                }else{
                    console.log("FAILED")
                    showNotification({
                        type: 'danger',
                        message: "Erreur lors de l'envoie de la demande",
                        titre: 'Erreur',
                    })
                }
            },
            error: function (xhr){
                let erreur = JSON.parse(xhr.responseText)
                showNotification({
                    type: 'danger',
                    message: `${erreur.message}`,
                    titre: `Erreur ${erreur.httpStatusCode}`,
                })
            },
        })
        input.value = ''
        textarea.value = ''
    }
})
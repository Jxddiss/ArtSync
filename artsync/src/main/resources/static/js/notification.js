const listeNotifPop = document.getElementById("notification-pop-liste");
const notifcationContainer = document.getElementById("notifcationContainer");

function showNotification(notification){
    var audio;
    if(notification.appel){
        audio = new Audio("/media/audio/notification/ringtone.mp3");
        audio.play();
        setTimeout(()=>{audio.pause()},5500);
    }else{
        audio = new Audio("/media/audio/notification/pop.mp3");
        audio.play();
    }

    const notificationElement = document.createElement("a");
    notificationElement.innerHTML = `<h4 class="notification-pop-title">${notification.titre}</h4>
                            <hr/>
                            <div class="body-notification-pop">
                                ${notification.appel ? `<i class="bi bi-telephone-fill"></i>` : ''}
                                <p class="notification-pop-message"> ${notification.message}</p>
                            </div>`
    listeNotifPop.appendChild(notificationElement);
    notificationElement.classList = `notification-pop-container notification-${notification.type} ${notification.appel ? `shake` : 'slide-left'}`;
    notificationElement.href = notification.urlNotif === undefined ? '' : notification.urlNotif;
    setTimeout(()=>{listeNotifPop.removeChild(notificationElement)},8000);
    if (notifcationContainer){
        addNotifContainer(notification);
    }
}

function addNotifContainer(notification){
    if(!listeNotifPop){
        $.ajax({
            type:'POST',
            url: window.location.origin.toString() + "/notification/set-lu",
            data: {id:notification.id}
        })
    }
    var noNotifTitle = document.getElementById("no-notif-title");

    if (noNotifTitle){
        notifcationContainer.removeChild(noNotifTitle);
    }

    const notificationElement = document.createElement("a");
    notificationElement.classList = "notification";
    notificationElement.innerHTML = `${notification.imgSender ?
        `<img src="media/images/${notification.imgSender}" alt="" class="profile-pic-banner-border-small" />` : ''}
                                              <p class="notification-texte"> ${notification.message}</p>`;
    notificationElement.href = notification.urlNotif;
    notifcationContainer.appendChild(notificationElement);
}
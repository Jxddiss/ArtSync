const listeNotifPop = document.getElementById("notification-pop-liste");
const notifcationContainer = document.getElementById("notifcationContainer");
const notifAppelDialog = document.getElementById("appel-notif");
const videoDialog = document.getElementById("video-chat");
let audio;

function showNotification(notification){
    audio = new Audio("/media/audio/notification/pop.mp3");
    audio.play();

    const notificationElement = document.createElement("a");
    notificationElement.innerHTML = `<h4 class="notification-pop-title">${notification.titre}</h4>
                            <hr/>
                            <div class="body-notification-pop">
                                <p class="notification-pop-message"> ${notification.message}</p>
                            </div>`
    listeNotifPop.appendChild(notificationElement);
    notificationElement.classList = `notification-pop-container notification-${notification.type} slide-left`;
    notificationElement.href = notification.urlNotif === undefined ? '' : notification.urlNotif;
    setTimeout(()=>{listeNotifPop.removeChild(notificationElement)},8000);
    if (notifcationContainer && notification.id !== undefined){
        addNotifContainer(notification);
    }
    if(notification.id !== undefined){
        $.ajax({
            type:'POST',
            url: window.location.origin.toString() + "/notification/set-lu",
            data: {id:notification.id}
        })
    }
}

function addNotifContainer(notification){
    let noNotifTitle = document.getElementById("no-notif-title");

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

function showAppel(notification){
    if(videoDialog){
        if(videoDialog.open){
            return;
        }
    }

    audio = new Audio("/media/audio/notification/ringtone.mp3");
    audio.play();
    setTimeout(()=>{audio.pause()},6500);

    notifAppelDialog.innerHTML = `
        <img src="/media/images/${notification.imgSender}" alt="">
        <span>@${notification.pseudoSender}</span>
        <div class="control-holder">
            <button type="button"
                    class="button-video"
                    id="btn-answer"
                    data-url="${notification.urlNotif}">
                <i class="bi bi-telephone-inbound"></i>
            </button>
            <button type="button"
                    class="button-video"
                    id="btn-decline">
                <i class="bi bi-x-lg"></i>
            </button>
        </div>`

    notifAppelDialog.showModal();
    gsap.from(notifAppelDialog,{
        width:"0%",
        height:"0%",
        opacity:0,
        duration:0.65,
        ease:"back"
    })
    gsap.to(notifAppelDialog,{
        height: "50vh",
        width: "50vh",
        opacity:1,
        duration:0.65,
        ease:"back"
    })
    setTimeout(()=>{notifAppelDialog.classList.toggle("fadeIn");},2000)
    //setTimeout(()=>{notifAppelDialog.close()},7000)

    $.ajax({
        type:'POST',
        url: window.location.origin.toString() + "/notification/set-lu",
        data: {id:notification.id}
    })
}


document.addEventListener("click",(ev)=>{
    if(ev.target.id === 'btn-decline' || ev.target.parentElement.id === 'btn-decline'){
        audio.pause();
        notifAppelDialog.close();
    }

    if(ev.target.id === 'btn-answer'){
        if(ev.target.dataset.url !== window.location.pathname.toString()){
            window.location.href = ev.target.dataset.url;
        }else{
            video.click();
        }
    }else if(ev.target.parentElement.id === 'btn-answer'){
        if(!ev.target.parentElement.dataset.url.includes(window.location.pathname.toString())){
            window.location.href = ev.target.parentElement.dataset.url;
        }else{
            video.click();
        }
    }
})
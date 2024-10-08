document.addEventListener("DOMContentLoaded", function() {

    //======= Déclaration des variables =================
    const remoteVideo = document.getElementById("remoteVideo");
    const localVideo = document.getElementById("localVideo");
    const hangUpButton = document.getElementById("hang-up-button");
    const camToggle = document.getElementById("cam-toggle");
    const microToggle = document.getElementById("micro-toggle");
    const displayToggle = document.getElementById("display-toggle");
    let localStream;
    let localPeer;
    let stompClientVideo;
    let offer;
    let cam = true;
    let displayStream;

    // ==== Configuration des ICE servers (En cas de problème de parfeu pour la connection peer to peer )
    const config = {
        iceServers: [
            {
                urls: "stun:stun1.l.google.com:19302"
            },
            {
                urls:'turn:relay1.expressturn.com:3478',
                username:'ef8EOWQXK1M7HXY1AI',
                credential:'mor3P6U6DOFc1r3R'
            }
        ],
        trickle: true,
        rtcpMuxPolicy:"negotiate",
        bundlePolicy:"max-compat"
    };

    const displayMediaOptions = {
        video: {
            displaySurface: "browser",
        },
        audio: {
            suppressLocalAudioPlayback: false,
        },
        preferCurrentTab: false,
        selfBrowserSurface: "exclude",
        systemAudio: "include",
        surfaceSwitching: "include",
        monitorTypeSurfaces: "include",
    };

    localPeer = new RTCPeerConnection(config)

    camToggle.addEventListener("click", function() {
        toggleCam();
    });

    microToggle.addEventListener("click", function() {
        toggleMic();
    });

    displayToggle.addEventListener("click", function() {
        toggleDisplay()
    });

    /*
    * Prend le media de la caméra et ensuite si la promesse est résolu
    * un appel est lancé grace a WebRTC
    * */
    video.addEventListener("click", function() {
        videoDialog.showModal();
        videoDialog.style.display="flex"
        if (navigator.mediaDevices.getUserMedia) {
            navigator.mediaDevices.getUserMedia({ video: true,audio: true })
                .then(stream => {
                    call(stream,"video")
                })
                .catch(function (error) {
                    console.log("Something went wrong! : " + error);
                });
        }
    });

    /*
    * Lance un appel après avoir pris le média de l'utilisateur
    * et désactive la vidéo pendant l'appel
    * */
    phone.addEventListener("click", function() {
        videoDialog.showModal();
        if (navigator.mediaDevices.getUserMedia) {
            navigator.mediaDevices.getUserMedia({ video: true,audio: true })
                .then(stream => {
                    call(stream,"phone")
                })
                .catch(function (error) {
                    console.log("Something went wrong! : " + error);
                });
        }
    });

    /* Fonction qui permet de se connecter au websocket et denvoyer les information nécessaire a la
    * connexion WebRTC. De plus, on définie les callback selon les évènnements WebRTC et met à jour
    * le remote video
    * */
    function call(stream,type){
        localStream = stream;
        if (type === "phone") {
            //Désactive la caméra
            toggleCam();
        }
        const socketVideo = new SockJS('/websocket');
        stompClientVideo = Stomp.over(socketVideo);
        stompClientVideo.connect({}, function(frame) {
            //==== Envoie une notification de type appel
            stompClientVideo.send("/app/notification/"+idAmi,{},JSON.stringify(
                {
                    type: 'info',
                    appel: true,
                    pseudoSender: pseudoUser,
                    message: `Appel de ${pseudoUser}`,
                    titre: 'Appel...',
                    urlNotif: window.location.pathname.toString() + '?appel=true',
                    imgSender: profilImgUserLogin
                }
            ));

            //=== Abonnements au topic de la conversation qui lance cette logique lorsqu'un appel est démaré a notre encontre
            stompClientVideo.subscribe('/topic/appel/call/'+conversationId+"/"+idUser, (call) => {
                console.log("appel de : " + call.body);

                //=== Ce qui sera fait a chaque fois qu'un stream est ajouté à la connection
                localPeer.ontrack = (event) => {
                    remoteVideo.srcObject = event.streams[0];
                }

                //=== Ce qui sera fait a chaque fois qu'un ICE candidate est ajouté à la connection
                localPeer.onicecandidate = (event) => {
                    if (event.candidate) {
                        let candidate = {
                            type: 'candidate',
                            label: event.candidate.sdpMLineIndex,
                            id: event.candidate.candidate,
                        }
                        console.log("sending candidate : " + JSON.stringify(candidate));
                        stompClientVideo.send('/app/chat/appel/candidate/'+conversationId+"/"+idAmi, {}, JSON.stringify({
                            toUser: idAmi,
                            fromUser: idUser,
                            candidate: candidate
                        }));
                    }
                }

                //=== préparation de l'envoie de notre vidéo, on l'ajoute à la connexion
                localStream.getTracks().forEach(track => localPeer.addTrack(track, localStream));

                //=== Préparation de l'offre de connexion, et on met la description de la connexion en local decription
                // de notre côté
                localPeer.createOffer().then((description) => {
                    localPeer.setLocalDescription(description);
                    console.log("Setting local description : " + JSON.stringify(description));

                    //=== Envoie de l'offre au websocket de l'utilisateur
                    stompClientVideo.send('/app/chat/appel/offer/'+conversationId+"/"+idAmi, {}, JSON.stringify({
                        toUser: idAmi,
                        fromUser: idUser,
                        offer: description
                    }))
                })
            });

            /*
            * Abonnement au topic qui retourne toutes les offres qui nous son dirigées, Met le remote description selon
            * l'offre reçu du websocket
            * */
            stompClientVideo.subscribe('/topic/appel/offer/'+conversationId+"/"+idUser, (of) => {
                console.log("offer de : " + of.body);
                offer = JSON.parse(of.body)["offer"];

                //=== Ce qui sera fait a chaque fois qu'un stream est ajouté à la connection
                localPeer.ontrack = (event) => {
                    remoteVideo.srcObject = event.streams[0];
                }

                //=== Ce qui sera fait a chaque fois qu'un ICE candidate est ajouté à la connection
                localPeer.onicecandidate = (event) => {
                    if (event.candidate) {
                        let candidate = {
                            type: 'candidate',
                            label: event.candidate.sdpMLineIndex,
                            id: event.candidate.candidate,
                        }
                        console.log("sending candidate : " + JSON.stringify(candidate));
                        stompClientVideo.send('/app/chat/appel/candidate/'+conversationId+"/"+idAmi, {}, JSON.stringify({
                            toUser: idAmi,
                            fromUser: idUser,
                            candidate: candidate
                        }));
                    }
                }

                //=== préparation de l'envoie de notre vidéo, on l'ajoute à la connexion
                localStream.getTracks().forEach(track => localPeer.addTrack(track, localStream));

                //=== met l'offre en remote description
                localPeer.setRemoteDescription(new RTCSessionDescription(offer));

                //=== Préparation de l'offre de connexion, et on met la description de la connexion en local decription
                // de notre côté
                localPeer.createAnswer().then((description) => {
                    localPeer.setLocalDescription(description);
                    console.log("Setting local description : " + JSON.stringify(description));
                    stompClientVideo.send('/app/chat/appel/answer/'+conversationId+"/"+idAmi, {}, JSON.stringify({
                        toUser: idAmi,
                        fromUser: idUser,
                        answer: description
                    }))
                })
            })

            //== Abonnement au topic des réponse pour pouvoir mettre le remote description dans le cas ou on initie l'appel
            stompClientVideo.subscribe('/topic/appel/answer/'+conversationId+"/"+idUser, (answer) => {
                console.log("answer de : " + answer.body);
                let answerO = JSON.parse(answer.body)["answer"];
                localPeer.setRemoteDescription(new RTCSessionDescription(answerO));
            })

            //== Abonnement au topic des candidate pour pouvoir ajouté les candidate qui nous sont envoyé
            stompClientVideo.subscribe('/topic/appel/candidate/'+conversationId+"/"+idUser, (candidate) => {
                console.log("candidate de : " + candidate.body);
                let candidateO = JSON.parse(candidate.body)["candidate"];
                let iceCandidate = new RTCIceCandidate({
                    sdpMLineIndex: candidateO["label"],
                    candidate: candidateO["id"]
                });
                localPeer.addIceCandidate(iceCandidate);
                console.log("added candidate : " + JSON.stringify(candidateO));
            })

            //== Ajout de l'utilisateur dans la liste d'appels courrant au niveau du serveur WebSocket
            stompClientVideo.send("/app/chat/appel/add/"+conversationId, {}, idUser)

            //== initiation d'un appel vers l'ami ===
            stompClientVideo.send("/app/chat/appel/call/"+conversationId+"/"+idAmi, {}, idUser)

            //=== Écoute si l'appel à été arrêté et rénitialise les connections
            stompClientVideo.subscribe("/topic/appel/remove/"+conversationId, (call) => {
                if (localPeer) {
                    localPeer.close();
                    localPeer = null;
                }
                if (stompClientVideo) {
                    stompClientVideo.disconnect();
                }
                location.href = '/utilisateur/conversation/'+conversationId;
            })

            //=== Fonction pour se déconnecter et rénitialiser toutes le connection
            hangUpButton.addEventListener("click", function() {
                if (localPeer) {
                    localPeer.close();
                    localPeer = null;
                }
                if (stompClientVideo) {
                    stompClientVideo.send("/app/chat/appel/remove/"+conversationId, {}, idUser + ":" + idAmi);
                    stompClientVideo.disconnect();
                }
                location.href = '/utilisateur/conversation/'+conversationId;
            })

            let isOnIos = navigator.userAgent.match(/iPad/i)|| navigator.userAgent.match(/iPhone/i);
            if(isOnIos){
                let unloaded = false;
                window.addEventListener('visibilitychange', function () {
                    if (document.hidden) {
                        stompClientVideo.send("/app/chat/appel/remove/"+conversationId, {}, idUser + ":" + idAmi);
                    }
                });
            } else {
                window.addEventListener('beforeunload', function (e) {
                    delete e['returnValue'];
                    stompClientVideo.send("/app/chat/appel/remove/"+conversationId, {}, idUser + ":" + idAmi);
                });
            }
        })



        //=== ajout de notre vidéo en local sur la page
        localVideo.srcObject = stream;
    }

    //=== désactive la vidéo sur le stream courrant
    function toggleCam(){
        if (localStream) {
            localStream.getVideoTracks()[0].enabled = !localStream.getVideoTracks()[0].enabled;
            if (localStream.getVideoTracks()[0].enabled) {
                camToggle.innerHTML = '<i class="bi bi-camera-video"></i>';
            }else {
                camToggle.innerHTML = '<i class="bi bi-camera-video-off"></i>';
            }
        }
    }

    //=== désactive le micro sur le stream courrant
    function toggleMic(){
        if (localStream) {
            localStream.getAudioTracks()[0].enabled = !localStream.getAudioTracks()[0].enabled;
            if (localStream.getAudioTracks()[0].enabled) {
                microToggle.innerHTML = '<i class="bi bi-mic"></i>';
            }else {
                microToggle.innerHTML = '<i class="bi bi-mic-mute"></i>';
            }
        }
    }

    function toggleDisplay(){
        if(cam){
            navigator.mediaDevices.getDisplayMedia(displayMediaOptions)
                .then(stream =>{
                    switchStream(stream,'display');
                    displayToggle.innerHTML = `<i class="bi bi-camera"></i>`;
                })
                .catch(error =>{
                    console.log(error);
                })
        }else{
            navigator.mediaDevices.getUserMedia({ video: true,audio: true })
                .then(stream => {
                    switchStream(stream,'cam');
                    displayToggle.innerHTML = `<i class="bi bi-display"></i>`;
                })
                .catch(error =>{
                    console.log(error);
                })
        }
        cam = !cam;
    }

    function switchStream(stream, type){
        if(type === 'display'){
            if (localStream) {
                localStream.getVideoTracks()[0].stop();
            }
            displayStream = stream;
        }else{
            if (displayStream) {
                displayStream.getTracks().forEach(function(track) {
                    track.stop();
                });
            }

            localStream = stream;
        }

        const videoSender = localPeer.getSenders().find(sender => sender.track.kind === 'video');
        if (videoSender) {
            videoSender.replaceTrack(stream.getVideoTracks()[0]);
        }

        localVideo.srcObject = stream;
    }
})


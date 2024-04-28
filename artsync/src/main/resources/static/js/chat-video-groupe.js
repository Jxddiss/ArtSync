document.addEventListener("DOMContentLoaded", function() {

    //======= Déclaration des variables =================
    const remoteVideo = document.getElementById("remoteVideo");
    const localVideo = document.getElementById("localVideo");
    const hangUpButton = document.getElementById("hang-up-button");
    const camToggle = document.getElementById("cam-toggle");
    const microToggle = document.getElementById("micro-toggle");
    const displayToggle = document.getElementById("display-toggle");
    let localStream;
    let displayStream;
    let peerConnectionArray = [];
    let stompClientVideo;
    let offer;
    let cam = true;

    // ==== Configuration des ICE servers (En cas de problème de parfeu pour la connection peer to peer )
    const config = {
        iceServers: [
            {
                urls: "stun:stun.l.google.com:19302"
            }
        ]
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
        if (navigator.mediaDevices.getUserMedia) {
            navigator.mediaDevices.getUserMedia({ video: true,audio: true })
                .then(stream => {
                    const socketStream = new SockJS('/websocket');
                    stompClientVideo = Stomp.over(socketStream);
                    localStream = stream;
                    stompClientVideo.connect({}, function (frame){
                        stompClientVideo.send('/app/chat/appel/groupe/new/'+conversationId,{},JSON.stringify({
                            id: idUser,
                            photoUrl: photoUrl,
                            pseudo: pseudoUser
                        }));
                        stompClientVideo.subscribe('/topic/appel/groupe/new/' + conversationId, (newUserEvent) => {
                            const user = JSON.parse(newUserEvent.body)

                            stompClientVideo.send('/app/chat/appel/groupe/add/'+conversationId+'/'+user.id ,{}, JSON.stringify({
                                id: idUser,
                                photoUrl: photoUrl,
                                pseudo: pseudoUser
                             }));
                            handleUserJoin(user,"new")
                        });

                        stompClientVideo.subscribe('/topic/appel/groupe/add/' + conversationId+"/"+idUser, (newUserEvent) => {
                            const user = JSON.parse(newUserEvent.body)
                            handleUserJoin(user,"add")
                        });
                        //----HANDLE LEAVE------
                        // stompClientVideo.subscribe('/topic/live/leave/'+userPseudo, function(viewerLeaveEvent) {
                        //     const viewerLeftPseudo = viewerLeaveEvent.body;
                        //     currentCount--;
                        //     viewCount.innerText = currentCount.toString();
                        //     stompClientStream.send('/app/live/count/'+userPseudo,{},JSON.stringify({
                        //         currentCount:currentCount
                        //     }));
                        //     handleUserLeave(viewerLeftPseudo);
                        // });
                    });
                    localVideo.srcObject = stream;
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


    function handleUserJoin(user, type){
        let track = false
        if (user.id.toString() === idUser) {
            return
        }
        const peerConnection = new RTCPeerConnection(config);
        peerConnectionArray.push({
            userId: user.id,
            peerConnection: peerConnection
        });
        peerConnection.ontrack = (event) => {
            //remoteVideo.srcObject = event.streams[0];
            if (!track){
                track = true
                appendVideo(event.streams[0],user)
            }

        }
        peerConnection.onicecandidate = (event) => {
            if (event.candidate) {
                let candidate = {
                    type: 'candidate',
                    label: event.candidate.sdpMLineIndex,
                    id: event.candidate.candidate,
                }
                console.log("sending candidate : " + JSON.stringify(candidate));
                stompClientVideo.send('/app/chat/appel/group/candidate/'+conversationId+"/"+idUser+"/"+user.id, {}, JSON.stringify({
                    toUser: user.id,
                    fromUser: idUser,
                    candidate: candidate
                }));
            }
        }
        localStream.getTracks().forEach(track => peerConnection.addTrack(track, localStream));

        stompClientVideo.subscribe('/topic/appel/groupe/candidate/'+conversationId+"/"+user.id+"/"+idUser, (candidate) => {
            console.log("candidate de : " + candidate.body);
            let candidateO = JSON.parse(candidate.body)["candidate"];
            let iceCandidate = new RTCIceCandidate({
                sdpMLineIndex: candidateO["label"],
                candidate: candidateO["id"]
            });
            peerConnection.addIceCandidate(iceCandidate);
            console.log("added candidate : " + JSON.stringify(candidateO));
        })
        //create offer
        if (type==="new"){
            peerConnection.createOffer().then((description) => {
                peerConnection.setLocalDescription(description);
                console.log("Setting local description : " + JSON.stringify(description));

                //=== Envoie de l'offre au websocket de l'utilisateur
                stompClientVideo.send('/app/chat/appel/groupe/offer/'+conversationId+"/"+idUser+"/"+user.id, {}, JSON.stringify({
                    toUser: user.id,
                    fromUser: idUser,
                    offer: description
                }))
            })
        }

        //get offer
        stompClientVideo.subscribe('/topic/appel/groupe/offer/'+conversationId+"/"+user.id+"/"+idUser, (of) => {
            console.log("offer de : " + of.body);
            offer = JSON.parse(of.body)["offer"];

            //=== Ce qui sera fait a chaque fois qu'un stream est ajouté à la connection


            //=== Ce qui sera fait a chaque fois qu'un ICE candidate est ajouté à la connection
            peerConnection.onicecandidate = (event) => {
                if (event.candidate) {
                    let candidate = {
                        type: 'candidate',
                        label: event.candidate.sdpMLineIndex,
                        id: event.candidate.candidate,
                    }
                    console.log("sending candidate : " + JSON.stringify(candidate));
                    stompClientVideo.send('/app/chat/appel/groupe/candidate/'+conversationId+"/"+idUser+"/"+user.id, {}, JSON.stringify({
                        toUser: user.id,
                        fromUser: idUser,
                        candidate: candidate
                    }));
                }
            }

            //=== préparation de l'envoie de notre vidéo, on l'ajoute à la connexion

            //=== met l'offre en remote description
            peerConnection.setRemoteDescription(new RTCSessionDescription(offer));

            //=== Préparation de l'offre de connexion, et on met la description de la connexion en local decription
            // de notre côté
            peerConnection.createAnswer().then((description) => {
                peerConnection.setLocalDescription(description);
                console.log("Setting local description : " + JSON.stringify(description));
                stompClientVideo.send('/app/chat/appel/groupe/answer/'+conversationId+"/"+idUser+"/"+user.id, {}, JSON.stringify({
                    toUser: user.id,
                    fromUser: idUser,
                    answer: description
                }))
            })
        })
        //== Abonnement au topic des réponse pour pouvoir mettre le remote description dans le cas ou on initie l'appel
        stompClientVideo.subscribe('/topic/appel/groupe/answer/'+conversationId+"/"+user.id+"/"+idUser, (answer) => {
            console.log("answer de : " + answer.body);
            let answerO = JSON.parse(answer.body)["answer"];
            peerConnection.setRemoteDescription(new RTCSessionDescription(answerO));
        })


    }
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
            peerConnectionArray.forEach(peer => {
                const videoSender = peer.peerConnection.getSenders().find(sender => sender.track.kind === 'video');
                if (videoSender) {
                    videoSender.replaceTrack(localStream.getVideoTracks()[0]);
                }
            });
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
            peerConnectionArray.forEach(peer => {
                const audioSender = peer.peerConnection.getSenders().find(sender => sender.track.kind === 'audio');
                if (audioSender) {
                    audioSender.replaceTrack(localStream.getAudioTracks()[0]);
                }
            });
        }
    }
    function toggleDisplay(){
        if(cam){
            navigator.mediaDevices.getDisplayMedia(displayMediaOptions)
                .then(stream =>{
                    switchStream(stream,'display');
                })
                .catch(error =>{
                    console.log(error);
                })
        }else{
            navigator.mediaDevices.getUserMedia({ video: true,audio: true })
                .then(stream => {
                    switchStream(stream,'cam');
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
                displayStream.getVideoTracks()[0].stop();
            }

            localStream = stream;
        }

        peerConnectionArray.forEach(peer => {
            const videoSender = peer.peerConnection.getSenders().find(sender => sender.track.kind === 'video');
            if (videoSender) {
                videoSender.replaceTrack(stream.getVideoTracks()[0]);
            }
        });

        streamVideo.srcObject = stream;
    }
    function appendVideo(stream,user){

        //À FAIRE - > INFO USER
        const videoHolderElement = document.createElement("div");
        videoHolderElement.classList.add("videoHolder")
        videoHolderElement.innerHTML = `
                <video autoplay class="video-box2"></video>
                <div class="userInfoHolder">
                    <img src="https://www.w3schools.com/w3images/lights.jpg" alt="">
                    <h4>@johnDoe</h4>
                </div>
        `
        console.log(videoHolderElement.firstChild)
        videoHolderElement.querySelector("video").srcObject = stream;
        remoteHolder.append(videoHolderElement)
    }
})


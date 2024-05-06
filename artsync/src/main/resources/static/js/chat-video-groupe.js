
document.addEventListener("DOMContentLoaded", function() {

    //======= Déclaration des variables =================
    const remoteVideo = document.getElementById("remoteVideo");
    const localVideo = document.getElementById("localVideo");
    const hangUpButton = document.getElementById("hang-up-button");
    const camToggle = document.getElementById("cam-toggle");
    const microToggle = document.getElementById("micro-toggle");
    const displayToggle = document.getElementById("display-toggle");
    const videoBoxes = document.querySelectorAll('.video-box2');
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

    const offerOptions = {
        offerToReceiveAudio: true,
        offerToReceiveVideo: true,
        voiceActivityDetection: false,
        iceRestart: false,
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

    hangUpButton.addEventListener("click",()=>{
        stompClientVideo.send('/app/chat/appel/groupe/leave/'+conversationId,{},idUser);
        peerConnectionArray.forEach(peer =>{
            peer.peerConnection.close();
            peer.peerConnection = null;
            const index = peerConnectionArray.indexOf(peer);
            if (index > -1) {
                peerConnectionArray.splice(index, 1);
            }
        })
        localStream.getTracks().forEach(function(track) {
            track.stop();
        });
        if(displayStream){
            displayStream.getTracks().forEach(function(track) {
                track.stop();
            });
        }
        unfocusedVideoHolder.innerHTML = "";
        secondaryVideoHolder.innerHTML = "";
        window.location.reload();
    })

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
                            if (user.id.toString() !== idUser){
                                handleUserJoin(user,"new")
                            }
                        });

                        stompClientVideo.subscribe('/topic/appel/groupe/add/' + conversationId+"/"+idUser, (newUserEvent) => {
                            const user = JSON.parse(newUserEvent.body)
                            if (user.id.toString() !== idUser){
                                handleUserJoin(user,"add")
                            }
                        });
                        //----HANDLE LEAVE------
                        stompClientVideo.subscribe('/topic/appel/groupe/leave/'+conversationId, function(userLeaveEvent) {
                            const leaveUserId = userLeaveEvent.body;
                            handleUserLeave(leaveUserId);
                         });

                        let isOnIos = navigator.userAgent.match(/iPad/i)|| navigator.userAgent.match(/iPhone/i);
                        if(isOnIos){
                            let unloaded = false;
                            window.addEventListener('visibilitychange', function () {
                                if (document.hidden) {
                                    stompClientVideo.send('/app/chat/appel/groupe/leave/'+conversationId,{},idUser);
                                    setTimeout(stompClientVideo.disconnect(),1000);
                                }else{
                                    location.reload();
                                }
                            });
                        } else {
                            window.addEventListener('beforeunload', function (e) {
                                delete e['returnValue'];
                                stompClientVideo.send('/app/chat/appel/groupe/leave/'+conversationId,{},idUser);
                                setTimeout(stompClientVideo.disconnect(),1000);
                            });
                        }

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
    *
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
    });*/


    function handleUserJoin(user, type){
        let track = false
        const peer = new RTCPeerConnection(config);
        peerConnectionArray.push({
            userId: user.id,
            peerConnection: peer
        });

        const currentUser = peerConnectionArray.find(utilisateur => utilisateur.userId === user.id);
        const peerConnection = currentUser.peerConnection;

        stompClientVideo.subscribe('/topic/appel/groupe/candidate/'+conversationId+"/"+user.id+"/"+idUser, (candidate) => {
            console.log("candidate de : " + JSON.parse(candidate.body)["fromUser"]);
            let candidateO = JSON.parse(candidate.body)["candidate"];
            let iceCandidate = new RTCIceCandidate({
                sdpMLineIndex: candidateO["label"],
                candidate: candidateO["id"]
            });
            peerConnection.addIceCandidate(iceCandidate).then(()=>{
                console.log("added candidate : ")
            }).catch(error =>{
                console.log("--------------- erreur --------------" + error);
            })
        })

        peerConnection.ontrack = (event) => {
            //remoteVideo.srcObject = event.streams[0];
            if (!track){
                track = true
                appendVideo(event.streams[0],user)
            }
        }

        localStream.getTracks().forEach(track => peerConnection.addTrack(track, localStream));

        //create offer
        if (type==="new"){
            stompClientVideo.subscribe('/topic/appel/groupe/ready/'+conversationId+"/"+user.id+"/"+idUser, () =>{
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

                peerConnection.oniceconnectionstatechange = () => {
                    console.log(`ICE connection state changed to: ${peerConnection.iceConnectionState}`);
                    if (peerConnection.iceConnectionState === "disconnected" || peerConnection.iceConnectionState === "failed") {
                        // Re-gather ICE candidates and attempt to re-establish the connection
                        peerConnection.createOffer()
                            .then((description) => {
                                peerConnection.setLocalDescription(description);
                                stompClientVideo.send('/app/chat/appel/groupe/offer/'+conversationId+"/"+idUser+"/"+user.id, {}, JSON.stringify({
                                    toUser: user.id,
                                    fromUser: idUser,
                                    offer: description
                                }))
                            })
                            .catch((error) => {
                                console.error("Error creating offer:", error);
                            });
                    }
                };
            })

            //== Abonnement au topic des réponse pour pouvoir mettre le remote description dans le cas ou on initie l'appel
            stompClientVideo.subscribe('/topic/appel/groupe/answer/'+conversationId+"/"+user.id+"/"+idUser, (answer) => {
                console.log("answer de : " + JSON.parse(answer.body)["fromUser"]);
                let answerO = JSON.parse(answer.body)["answer"];
                peerConnection.setRemoteDescription(new RTCSessionDescription(answerO));
            })
        }

        if(type === "add"){
            //get offer
            stompClientVideo.subscribe('/topic/appel/groupe/offer/'+conversationId+"/"+user.id+"/"+idUser, (of) => {
                console.log("offer de : " + JSON.parse(of.body)["fromUser"]);
                offer = JSON.parse(of.body)["offer"];

                //=== Ce qui sera fait a chaque fois qu'un ICE candidate est ajouté à la connection
                peerConnection.onicecandidate = (event) => {
                    if (event.candidate) {
                        let candidate = {
                            type: 'candidate',
                            label: event.candidate.sdpMLineIndex,
                            id: event.candidate.candidate,
                        }
                        //console.log("sending candidate : " + JSON.stringify(candidate));
                        stompClientVideo.send('/app/chat/appel/groupe/candidate/'+conversationId+"/"+idUser+"/"+user.id, {}, JSON.stringify({
                            toUser: user.id,
                            fromUser: idUser,
                            candidate: candidate
                        }));
                    }
                }

                //=== préparation de l'envoie de notre vidéo, on l'ajoute à la connexion
                //localStream.getTracks().forEach(track => peerConnection.addTrack(track, localStream));

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

            setTimeout(()=>{
                stompClientVideo.send('/app/chat/appel/groupe/ready/'+conversationId+"/"+idUser+"/"+user.id,{},"ready")
            },500)
        }

    }

    function handleUserLeave(leaveUserId) {
        console.log("------------------- leave",leaveUserId)
        peerConnectionArray.forEach(peer => {
            console.log("---------------- peer : ",peer.userId)
            if (peer.userId.toString() === leaveUserId.toString()) {
                console.log("----------------found peer : ",peer.userId)
                peer.peerConnection.close();
                peer.peerConnection = null;
                const videoHolder = document.getElementById(`user-${peer.userId}`);
                videoHolder.remove();
                const index = peerConnectionArray.indexOf(peer);
                if (index > -1) {
                    peerConnectionArray.splice(index, 1);
                }
            }
        })
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

        peerConnectionArray.forEach(peer => {
            const videoSender = peer.peerConnection.getSenders().find(sender => sender.track.kind === 'video');
            if (videoSender) {
                videoSender.replaceTrack(stream.getVideoTracks()[0]);
            }
        });

        localVideo.srcObject = stream;
    }
    function appendVideo(stream,user){

        const videoHolderElement = document.createElement("div");
        videoHolderElement.classList.add("videoHolder")
        videoHolderElement.id = "user-"+user.id;
        videoHolderElement.dataset.focus = "false";
        videoHolderElement.innerHTML = `
                <video autoplay class="video-box2" playsinline></video>
                <div class="userInfoHolder">
                    <img src="/media/images/${user.photoUrl}" alt="">
                    <h4>@${user.pseudo}</h4>
                </div>
        `
        let infoHolder = videoHolderElement.querySelector(".userInfoHolder");
        infoHolder.style.opacity = "0";
        let videoBox = videoHolderElement.querySelector("video")
        videoBox.setAttribute("playsinline",'true');
        videoHolderElement.querySelector("video").srcObject = stream;
        unfocusedVideoHolder.append(videoHolderElement)
        videoBox.addEventListener('click', () => {
            if (videoBox.parentElement.dataset.focus === "false"){
                videoBox.parentElement.dataset.focus = "true";
                unfocusedVideoHolder.removeChild(videoBox.parentElement)
                if (secondaryVideoHolder.firstChild){
                    secondaryVideoHolder.firstChild.dataset.focus = "false";
                    unfocusedVideoHolder.appendChild(secondaryVideoHolder.firstChild);
                }
                secondaryVideoHolder.appendChild(videoBox.parentElement);
                secondaryVideoHolder.style.width = "90%";
                unfocusedVideoHolder.style.width = "20em";
            }else{
                videoBox.parentElement.dataset.focus = "false";
                if (videoBox.parentElement.parentElement === secondaryVideoHolder){
                    secondaryVideoHolder.removeChild(videoBox.parentElement);
                    unfocusedVideoHolder.appendChild(videoBox.parentElement);
                    secondaryVideoHolder.style.width = "";
                    unfocusedVideoHolder.style.width = ""
                }
            }
        });

        videoBox.addEventListener("mouseenter",()=>{
            let infoHolder = videoHolderElement.querySelector(".userInfoHolder");
            infoHolder.style.opacity = "1";
        })
        videoBox.addEventListener("mouseout",()=>{
            let infoHolder = videoHolderElement.querySelector(".userInfoHolder");
            infoHolder.style.opacity = "0";
        })
    }
})


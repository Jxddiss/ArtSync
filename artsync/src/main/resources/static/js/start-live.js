
document.addEventListener("DOMContentLoaded", function() {
    //====== Déclaration des variables
    let streamerPeerConnections = [];
    let streamerStream;
    let displayStream;
    let stompClientStream;
    let offer;

    const endLiveButton = document.getElementById("hang-up-button");
    const camToggle = document.getElementById("cam-toggle");
    const microToggle = document.getElementById("micro-toggle");
    const displayToggle = document.getElementById("display-toggle");
    const inputTitre = document.getElementById("titre-live-input");


    messageNotStarted.style.display = "none";
    titreLive.style.display = "none";
    buttonStart.style.display = "none";

    let cam = true;

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



    // ==== Configuration des ICE servers (En cas de problème de parfeu pour la connection peer to peer )
    const config = {
        iceServers: [
            {
                urls: "stun:stun.l.google.com:19302"
            },
            {
                urls:'turn:relay1.expressturn.com:3478',
                username:'ef8EOWQXK1M7HXY1AI',
                credential:'mor3P6U6DOFc1r3R'
            }
        ]
    };

    inputTitre.addEventListener("keyup",function (ev){
        if(ev.key === "Enter"){
            if (inputTitre.value.trim() !== ''){
                titreLive.innerText = inputTitre.value;
                titreLive.style.display = "block";
                inputTitre.value = '';
                inputTitre.remove();
                buttonStart.style.display = "block";
                startLive();
            }
        }
    })

    function startLive(){
        buttonStart.addEventListener("click",(ev) =>{
            buttonStart.style.visibility = 'hidden';
            navigator.mediaDevices.getUserMedia({ video: true,audio: true })
                .then(stream => {
                    const socketStream = new SockJS('/websocket');
                    stompClientStream = Stomp.over(socketStream);
                    streamerStream = stream;
                    stompClientStream.connect({}, function (frame){
                        stompClientStream.send('/app/live/start/'+userPseudo,{},titreLive.innerText.toString());
                        stompClientStream.subscribe('/topic/live/new/' + userPseudo, (newViewerEvent) => {
                            currentCount ++;
                            viewCount.innerText = currentCount.toString();
                            const viewerPseudo = newViewerEvent.body;
                            stompClientStream.send('/app/live/count/'+userPseudo,{},JSON.stringify({
                                currentCount:currentCount,
                                pseudo:viewerPseudo,
                            }));
                            AddNewUserJoinMessage(viewerPseudo);
                            handleNewViewer(viewerPseudo);
                        });
                        stompClientStream.subscribe('/topic/live/chat/'+userPseudo, function(message) {
                            addMessageLive(JSON.parse(message.body));
                        });
                        stompClientStream.subscribe('/topic/live/leave/'+userPseudo, function(viewerLeaveEvent) {
                            const viewerLeftPseudo = viewerLeaveEvent.body;
                            currentCount--;
                            viewCount.innerText = currentCount.toString();
                            stompClientStream.send('/app/live/count/'+userPseudo,{},JSON.stringify({
                                currentCount:currentCount
                            }));
                            handleUserLeave(viewerLeftPseudo);
                        });

                        let isOnIos = navigator.userAgent.match(/iPad/i)|| navigator.userAgent.match(/iPhone/i);
                        if(isOnIos){
                            let unloaded = false;
                            window.addEventListener('visibilitychange', function () {
                                if (document.hidden) {
                                    stompClientStream.send('/app/live/end/'+userPseudo,{},userPseudo);
                                    setTimeout(stompClientStream.disconnect(),1000);
                                }else{
                                    location.reload();
                                }
                            });
                        } else {
                            window.addEventListener('beforeunload', function (e) {
                                delete e['returnValue'];
                                stompClientStream.send('/app/live/end/'+userPseudo,{},userPseudo);
                                setTimeout(stompClientStream.disconnect(),1000);
                            });
                        }
                    });
                    streamVideo.srcObject = stream;
                })
                .catch(function (error) {
                    console.log("Something went wrong! : " + error);
                });

            microToggle.addEventListener("click",toggleMic);
            camToggle.addEventListener("click",toggleCam);
            displayToggle.addEventListener("click",toggleDisplay);
            endLiveButton.addEventListener("click",endLive);
            messageInput.addEventListener("keyup", function(event) {
                if (event.key === "Enter") {
                    sendChat();
                }
            });
        })
    }

    function handleNewViewer(viewerPseudo){
        const viewerPeerConnection = new RTCPeerConnection(config);
        streamerPeerConnections.push({
            username: viewerPseudo,
            peerConnection: viewerPeerConnection
        });


        const currentViewer = streamerPeerConnections.find(viewer => viewer.username === viewerPseudo);
        const streamerPeerConnection = currentViewer.peerConnection;

        //=== Ce qui sera fait a chaque fois qu'un ICE candidate est ajouté à la connection
        streamerPeerConnection.onicecandidate = (event) => {
            if (event.candidate) {
                var candidate = {
                    type: 'candidate',
                    label: event.candidate.sdpMLineIndex,
                    id: event.candidate.candidate,
                }
                console.log("sending candidate : " + JSON.stringify(candidate));
                stompClientStream.send('/app/live/candidate/'+viewerPseudo+'/'+userPseudo, {}, JSON.stringify({
                    fromUser: userPseudo,
                    candidate: candidate
                }));
            }
        }

        //=== préparation de l'envoie de notre vidéo, on l'ajoute à la connexion
        streamerStream.getTracks().forEach(track => streamerPeerConnection.addTrack(track, streamerStream));

        streamerPeerConnection.createOffer().then((description) => {
            streamerPeerConnection.setLocalDescription(description);
            console.log("Setting local description : " + JSON.stringify(description));

            stompClientStream.send('/app/live/offer/'+userPseudo+"/"+viewerPseudo,{}, JSON.stringify({
                fromUser: userPseudo,
                offer: description
            }));
        });

        stompClientStream.subscribe('/topic/live/answer/'+viewerPseudo+'/'+userPseudo,(ans) =>{
            var answer = JSON.parse(ans.body)["answer"];
            console.log("answer : "+answer);
            streamerPeerConnection.setRemoteDescription(new RTCSessionDescription(answer));
        });

        stompClientStream.subscribe('/topic/live/candidate/'+userPseudo+'/'+viewerPseudo, (candidate) => {
            console.log("candidate de : " + candidate.body);
            var candidateO = JSON.parse(candidate.body)["candidate"];
            var iceCandidate = new RTCIceCandidate({
                sdpMLineIndex: candidateO["label"],
                candidate: candidateO["id"]
            });

            streamerPeerConnection.addIceCandidate(iceCandidate)
                .then(()=>{
                    console.log("added candidate : " + JSON.stringify(candidateO));
                })
                .catch(error => {
                    console.log(error);
                })

        });
    }

    function toggleCam(){
        if (streamerStream) {
            streamerStream.getVideoTracks()[0].enabled = !streamerStream.getVideoTracks()[0].enabled;
            if (streamerStream.getVideoTracks()[0].enabled) {
                camToggle.innerHTML = '<i class="bi bi-camera-video"></i>';
            }else {
                camToggle.innerHTML = '<i class="bi bi-camera-video-off"></i>';
            }
            streamerPeerConnections.forEach(peer => {
                const videoSender = peer.peerConnection.getSenders().find(sender => sender.track.kind === 'video');
                if (videoSender) {
                    videoSender.replaceTrack(streamerStream.getVideoTracks()[0]);
                }
            });
        }
    }

    //=== désactive le micro sur le stream courrant
    function toggleMic(){
        if (streamerStream) {
            streamerStream.getAudioTracks()[0].enabled = !streamerStream.getAudioTracks()[0].enabled;
            if (streamerStream.getAudioTracks()[0].enabled) {
                microToggle.innerHTML = '<i class="bi bi-mic"></i>';
            }else {
                microToggle.innerHTML = '<i class="bi bi-mic-mute"></i>';
            }
            streamerPeerConnections.forEach(peer => {
                const audioSender = peer.peerConnection.getSenders().find(sender => sender.track.kind === 'audio');
                if (audioSender) {
                    audioSender.replaceTrack(streamerStream.getAudioTracks()[0]);
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
            if (streamerStream) {
                streamerStream.getVideoTracks()[0].stop();
            }
            displayStream = stream;
        }else{
            if (displayStream) {
                displayStream.getTracks().forEach(function(track) {
                    track.stop();
                });
            }
            streamerStream = stream;
        }

        streamerPeerConnections.forEach(peer => {
            const videoSender = peer.peerConnection.getSenders().find(sender => sender.track.kind === 'video');
            if (videoSender) {
                videoSender.replaceTrack(stream.getVideoTracks()[0]);
            }
        });

        streamVideo.srcObject = stream;
    }

    function endLive(){
        stompClientStream.send('/app/live/end/'+userPseudo,{},userPseudo);
        buttonStart.style.visibility = 'visible';
        streamVideo.srcObject = null;
        streamerPeerConnections.forEach(peer =>{
            if(peer.peerConnection){
                peer.peerConnection.close();
                peer.peerConnection = null;
            }
            const index = streamerPeerConnections.indexOf(peer);
            if (index > -1) {
                streamerPeerConnections.splice(index, 1);
            }
        })
        streamerStream.getTracks().forEach(function(track) {
            track.stop();
        });
        displayStream.getTracks().forEach(function(track) {
            track.stop();
        });
    }

    function sendChat(){
        if (messageInput.value.trim() !== ''){
            const messageText = messageInput.value;
            stompClientStream.send('/app/live/chat/'+userPseudo,{},JSON.stringify(
                {
                    senderPseudo:userPseudo,
                    senderPhoto:userPhoto,
                    text : messageText,
                    dateTimeEnvoie : new Date(),
                })
            )
            messageInput.value = '';
        }
    }

    function handleUserLeave(viewerLeftPseudo) {
        streamerPeerConnections.forEach(peer => {
            if (peer.username === viewerLeftPseudo) {
                peer.peerConnection.close();
                peer.peerConnection = null;
                const index = streamerPeerConnections.indexOf(peer);
                if (index > -1) {
                    streamerPeerConnections.splice(index, 1);
                }
            }
        })
    }
});
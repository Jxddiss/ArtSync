document.addEventListener("DOMContentLoaded", function() {

    const remoteVideo = document.getElementById("remoteVideo");
    const localVideo = document.getElementById("localVideo");
    const hangUpButton = document.getElementById("hang-up-button");
    const camToggle = document.getElementById("cam-toggle");
    const microToggle = document.getElementById("micro-toggle");
    let localStream;
    let remoteStream;
    let localPeer;

    let stompClientVideo;

    // ICE Server Configurations
    const config = {
        iceServers: [
            {
                urls: "stun:stun.l.google.com:19302"
            }
        ]
    };

    localPeer = new RTCPeerConnection(config)

    camToggle.addEventListener("click", function() {
        toggleCam();
    });

    microToggle.addEventListener("click", function() {
        toggleMic();
    });

    video.addEventListener("click", function() {
        videoDialog.showModal();
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
    function call(stream,type){
        localStream = stream;
        if (type === "phone") {
            toggleCam();
        }
        const socketVideo = new SockJS('/websocket');
        stompClientVideo = Stomp.over(socketVideo);
        stompClientVideo.connect({}, function(frame) {
            stompClientVideo.send("/app/notification/"+idAmi,{},JSON.stringify(
                {
                    type: 'info',
                    appel: true,
                    pseudoSender: pseudoUser,
                    message: 'Appel de ',
                    titre: 'Appel...',
                    urlNotif: window.location.pathname.toString()
                }
            ));
            stompClientVideo.subscribe('/topic/appel/call/'+conversationId+"/"+idUser, (call) => {
                console.log("appel de : " + call.body);
                localPeer.ontrack = (event) => {
                    remoteVideo.srcObject = event.streams[0];
                }

                localPeer.onicecandidate = (event) => {
                    if (event.candidate) {
                        var candidate = {
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

                localStream.getTracks().forEach(track => localPeer.addTrack(track, localStream));

                localPeer.createOffer().then((description) => {
                    localPeer.setLocalDescription(description);
                    console.log("Setting local description : " + JSON.stringify(description));
                    stompClientVideo.send('/app/chat/appel/offer/'+conversationId+"/"+idAmi, {}, JSON.stringify({
                        toUser: idAmi,
                        fromUser: idUser,
                        offer: description
                    }))
                })
            });

            stompClientVideo.subscribe('/topic/appel/offer/'+conversationId+"/"+idUser, (offer) => {
                console.log("offer de : " + offer.body);
                var offer = JSON.parse(offer.body)["offer"];
                localPeer.ontrack = (event) => {
                    remoteVideo.srcObject = event.streams[0];
                }
                localPeer.onicecandidate = (event) => {
                    if (event.candidate) {
                        var candidate = {
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

                localStream.getTracks().forEach(track => localPeer.addTrack(track, localStream));

                localPeer.setRemoteDescription(new RTCSessionDescription(offer));
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

            stompClientVideo.subscribe('/topic/appel/answer/'+conversationId+"/"+idUser, (answer) => {
                console.log("answer de : " + answer.body);
                var answerO = JSON.parse(answer.body)["answer"];
                localPeer.setRemoteDescription(new RTCSessionDescription(answerO));
            })

            stompClientVideo.subscribe('/topic/appel/candidate/'+conversationId+"/"+idUser, (candidate) => {
                console.log("candidate de : " + candidate.body);
                var candidateO = JSON.parse(candidate.body)["candidate"];
                var iceCandidate = new RTCIceCandidate({
                    sdpMLineIndex: candidateO["label"],
                    candidate: candidateO["id"]
                });
                localPeer.addIceCandidate(iceCandidate);
                console.log("added candidate : " + JSON.stringify(candidateO));
            })

            stompClientVideo.send("/app/chat/appel/add/"+conversationId, {}, idUser)
            stompClientVideo.send("/app/chat/appel/call/"+conversationId+"/"+idAmi, {}, idUser)
            stompClientVideo.subscribe("/topic/appel/remove/"+conversationId, (call) => {
                if (localPeer) {
                    localPeer.close();
                    localPeer = null;
                }
                if (stompClientVideo) {
                    stompClientVideo.disconnect();
                }
                location.reload()
            })

            hangUpButton.addEventListener("click", function() {
                if (localPeer) {
                    localPeer.close();
                    localPeer = null;
                }
                if (stompClientVideo) {
                    stompClientVideo.send("/app/chat/appel/remove/"+conversationId, {}, idUser + ":" + idAmi);
                    stompClientVideo.disconnect();
                }
                location.reload()
            })
        })
        localVideo.srcObject = stream;
    }

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
})


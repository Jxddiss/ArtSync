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
    const iceServers = {
        iceServer: {
            urls: "stun:stun.l.google.com:19302"
        }
    };

    localPeer = new RTCPeerConnection(iceServers)

    video.addEventListener("click", function() {
        videoDialog.showModal();
        if (navigator.mediaDevices.getUserMedia) {
            navigator.mediaDevices.getUserMedia({ video: true,audio: false })
                .then(stream => {
                    localStream = stream;
                    const socketVideo = new SockJS('/websocket');
                    stompClientVideo = Stomp.over(socketVideo);
                    stompClientVideo.connect({}, function(frame) {
                        stompClientVideo.subscribe('/topic/appel/call/'+idUser, (call) => {
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
                                    stompClientVideo.send('/app/chat/appel/candidate/'+idAmi, {}, JSON.stringify({
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
                                stompClientVideo.send('/app/chat/appel/offer/'+idAmi, {}, JSON.stringify({
                                    toUser: idAmi,
                                    fromUser: idUser,
                                    offer: description
                                }))
                            })
                        });

                        stompClientVideo.subscribe('/topic/appel/offer/'+idUser, (offer) => {
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
                                    stompClientVideo.send('/app/chat/appel/candidate/'+idAmi, {}, JSON.stringify({
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
                                stompClientVideo.send('/app/chat/appel/answer/'+idAmi, {}, JSON.stringify({
                                    toUser: idAmi,
                                    fromUser: idUser,
                                    answer: description
                                }))
                            })
                        })

                        stompClientVideo.subscribe('/topic/appel/answer/'+idUser, (answer) => {
                            console.log("answer de : " + answer.body);
                            var answerO = JSON.parse(answer.body)["answer"];
                            localPeer.setRemoteDescription(new RTCSessionDescription(answerO));
                        })

                        stompClientVideo.subscribe('/topic/appel/candidate/'+idUser, (candidate) => {
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
                        stompClientVideo.send("/app/chat/appel/call/"+idAmi, {}, idUser)
                    })
                    localVideo.srcObject = stream;
                })
                .catch(function (error) {
                    console.log("Something went wrong! : " + error);
                });
        }
    });
})
document.addEventListener("DOMContentLoaded", function() {
    //====== Déclaration des variables
    let streamerPeerConnection;
    let streamerStream;
    let stompClientStream;
    let offer;


    // ==== Configuration des ICE servers (En cas de problème de parfeu pour la connection peer to peer )
    const config = {
        iceServers: [
            {
                urls: "stun:stun.l.google.com:19302"
            }
        ]
    };

    if (navigator.mediaDevices.getUserMedia) {
        navigator.mediaDevices.getUserMedia({ video: true,audio: true })
            .then(stream => {
                startLive(stream);
                streamVideo.srcObject = stream;
            })
            .catch(function (error) {
                console.log("Something went wrong! : " + error);
            });
    }

    function startLive(stream){
        streamerStream = stream;
        streamerPeerConnection = new RTCPeerConnection(config);

        const socketStream = new SockJS('/websocket');
        stompClientStream = Stomp.over(socketStream);

        stompClientStream.connect({}, function (frame){

            streamerPeerConnection.createOffer().then((description) => {
                streamerPeerConnection.setLocalDescription(description);
                console.log("Setting local description : " + JSON.stringify(description));

                stompClientStream.send('/app/live/'+userPseudo,{}, JSON.stringify({
                    fromUser: userPseudo,
                    answer: description
                }));
            });

            stompClientStream.subscribe('/topic/live/offers/'+userPseudo,(of) =>{
                offer = JSON.parse(of.body)["offer"];

                //=== Ce qui sera fait a chaque fois qu'un ICE candidate est ajouté à la connection
                streamerPeerConnection.onicecandidate = (event) => {
                    if (event.candidate) {
                        var candidate = {
                            type: 'candidate',
                            label: event.candidate.sdpMLineIndex,
                            id: event.candidate.candidate,
                        }
                        console.log("sending candidate : " + JSON.stringify(candidate));
                        stompClientStream.send('/app/live/candidate/'+userPseudo, {}, JSON.stringify({
                            fromUser: userPseudo,
                            candidate: candidate
                        }));
                    }
                }

                //=== préparation de l'envoie de notre vidéo, on l'ajoute à la connexion
                streamerPeerConnection.getTracks().forEach(track => streamerPeerConnection.addTrack(track, streamerStream));

                //=== met l'offre en remote description
                streamerPeerConnection.setRemoteDescription(new RTCSessionDescription(offer));

                //=== Préparation de l'offre de connexion, et on met la description de la connexion en local decription
                // de notre côté
                streamerPeerConnection.createAnswer().then((description) => {
                    streamerPeerConnection.setLocalDescription(description);
                    console.log("Setting local description : " + JSON.stringify(description));
                    stompClientStream.send('/app/live/answer/'+userPseudo, {}, JSON.stringify({
                        fromUser: userPseudo,
                        answer: description
                    }));
                });
            });

            stompClientStream.subscribe('/topic/live/candidate/'+userPseudo, (candidate) => {
                console.log("candidate de : " + candidate.body);
                var candidateO = JSON.parse(candidate.body)["candidate"];
                var iceCandidate = new RTCIceCandidate({
                    sdpMLineIndex: candidateO["label"],
                    candidate: candidateO["id"]
                });
                streamerPeerConnection.addIceCandidate(iceCandidate);
                console.log("added candidate : " + JSON.stringify(candidateO));
            });
        });
    }
});
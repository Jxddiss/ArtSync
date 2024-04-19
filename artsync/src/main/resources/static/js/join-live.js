document.addEventListener("DOMContentLoaded", function() {
    //====== Déclaration des variables
    let viewerPeerConnection;
    let stompClientViewer;
    let offer;

    // ==== Configuration des ICE servers (En cas de problème de parfeu pour la connection peer to peer )
    const config = {
        iceServers: [
            {
                urls: "stun:stun.l.google.com:19302"
            }
        ]
    };

    viewerPeerConnection = new RTCPeerConnection(config);

    const socketViewer = new SockJS('/websocket');
    stompClientViewer = Stomp.over(socketViewer);

    stompClientViewer.subscribe('/topic/live/'+pseudoStreamer, (of) =>{
        console.log("offer de : " + of.body);
        offer = JSON.parse(of.body)["offer"];

        //=== Ce qui sera fait a chaque fois qu'un stream est ajouté à la connection
        viewerPeerConnection.ontrack = (event) => {
            streamVideo.srcObject = event.streams[0];
        }

        //=== Ce qui sera fait a chaque fois qu'un ICE candidate est ajouté à la connection
        viewerPeerConnection.onicecandidate = (event) => {
            if (event.candidate) {
                var candidate = {
                    type: 'candidate',
                    label: event.candidate.sdpMLineIndex,
                    id: event.candidate.candidate,
                }
                console.log("sending candidate : " + JSON.stringify(candidate));
                stompClientViewer.send('/app/live/candidate/' + pseudoStreamer, {}, JSON.stringify({
                    fromUser: userPseudo,
                    candidate: candidate
                }));
            }
        }

        //=== met l'offre en remote description
        viewerPeerConnection.setRemoteDescription(new RTCSessionDescription(offer));

        //=== Préparation de l'offre de connexion, et on met la description de la connexion en local decription
        // de notre côté
        viewerPeerConnection.createAnswer().then((description) => {
            localPeer.setLocalDescription(description);
            console.log("Setting local description : " + JSON.stringify(description));
            stompClientViewer.send('/app/live/answer/'+pseudoStreamer, {}, JSON.stringify({
                toUser: idAmi,
                fromUser: idUser,
                answer: description
            }))
        })
    })

});
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

    stompClientViewer.connect({}, function (frame){
        stompClientViewer.send('/app/live/new/'+pseudoStreamer,{},userPseudo);
        stompClientViewer.subscribe('/topic/live/offer/'+pseudoStreamer+"/"+userPseudo, (of) =>{
            console.log("offer de : " + of.body);
            offer = JSON.parse(of.body)["offer"];

            //=== Ce qui sera fait a chaque fois qu'un stream est ajouté à la connection
            viewerPeerConnection.ontrack = (event) => {
                streamVideo.srcObject = event.streams[0];
            }

            //=== met l'offre en remote description
            viewerPeerConnection.setRemoteDescription(new RTCSessionDescription(offer));

            //=== Préparation de l'offre de connexion, et on met la description de la connexion en local decription
            // de notre côté
            viewerPeerConnection.createAnswer().then((description) => {
                viewerPeerConnection.setLocalDescription(description);
                console.log("Setting local description : " + JSON.stringify(description));
                stompClientViewer.send('/app/live/answer/'+userPseudo+'/'+pseudoStreamer, {}, JSON.stringify({
                    fromUser: userPseudo,
                    answer: description
                }))
            })

            //=== Ce qui sera fait a chaque fois qu'un ICE candidate est ajouté à la connection
            viewerPeerConnection.onicecandidate = (event) => {
                if (event.candidate) {
                    var candidate = {
                        type: 'candidate',
                        label: event.candidate.sdpMLineIndex,
                        id: event.candidate.candidate,
                    }
                    console.log("sending candidate : " + JSON.stringify(candidate));
                    stompClientViewer.send('/app/live/candidate/'+pseudoStreamer+'/'+userPseudo, {}, JSON.stringify({
                        fromUser: userPseudo,
                        candidate: candidate
                    }));
                }
            }
        })

        //== Abonnement au topic des candidate pour pouvoir ajouté les candidate qui nous sont envoyé
        stompClientViewer.subscribe('/topic/live/candidate/'+userPseudo+'/'+pseudoStreamer, (candidate) => {
            console.log("candidate de : " + candidate.body);
            var candidateO = JSON.parse(candidate.body)["candidate"];
            var iceCandidate = new RTCIceCandidate({
                sdpMLineIndex: candidateO["label"],
                candidate: candidateO["id"]
            });
            viewerPeerConnection.addIceCandidate(iceCandidate);
            console.log("added candidate : " + JSON.stringify(candidateO));
        })
    })

});
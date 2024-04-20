document.addEventListener("DOMContentLoaded", function() {
    //====== Déclaration des variables
    let streamerPeerConnections = [];
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
                const socketStream = new SockJS('/websocket');
                stompClientStream = Stomp.over(socketStream);
                streamerStream = stream;
                stompClientStream.connect({}, function (frame){
                    stompClientStream.subscribe('/topic/live/new/' + userPseudo, (newViewerEvent) => {
                        const viewerPseudo = newViewerEvent.body;
                        handleNewViewer(viewerPseudo);
                    });
                    stompClientStream.send()
                });
                streamVideo.srcObject = stream;
            })
            .catch(function (error) {
                console.log("Something went wrong! : " + error);
            });
    }

    function handleNewViewer(viewerPseudo){
        const viewerPeerConnection = new RTCPeerConnection(config);
        streamerPeerConnections.push({
            username: viewerPseudo,
            peerConnection: viewerPeerConnection
        });


        const currentViewer = streamerPeerConnections.find(viewer => viewer.username === viewerPseudo);
        const streamerPeerConnection = currentViewer.peerConnection;

        //=== préparation de l'envoie de notre vidéo, on l'ajoute à la connexion
        streamerStream.getTracks().forEach(track => streamerPeerConnection.addTrack(track, streamerStream));

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
            if (streamerStream.remoteDescription){
                streamerPeerConnection.addIceCandidate(iceCandidate)
                    .then(()=>{
                        console.log("added candidate : " + JSON.stringify(candidateO));
                    })
                    .catch(error => {
                        console.log(error);
                    })
            }
        });
    }
});
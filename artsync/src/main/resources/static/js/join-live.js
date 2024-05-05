
document.addEventListener("DOMContentLoaded", function() {
    //====== Déclaration des variables
    let viewerPeerConnection;
    let stompClientViewer;
    let offer;



    buttonStart.style.display = "none";

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

    const socketViewer = new SockJS('/websocket');
    stompClientViewer = Stomp.over(socketViewer);

    stompClientViewer.connect({}, function (frame){
        if(streamStarted){
            messageNotStarted.style.display = "none";
            buttonStart.style.display = "block";
        }else{
            stompClientViewer.subscribe('/topic/live/start/'+pseudoStreamer,(ev)=>{
                titreLive.innerText = ev.body;
                messageNotStarted.style.display = "none";
                buttonStart.style.display = "block";
            });
        }

        buttonStart.addEventListener("click",(ev)=>{
            buttonStart.style.visibility = 'hidden';
            viewerPeerConnection = new RTCPeerConnection(config);

            stompClientViewer.subscribe('/topic/live/count/'+pseudoStreamer,(ev)=>{
                let countEvent = JSON.parse(ev.body);
                viewCount.innerText = countEvent.currentCount;
                if(countEvent.pseudo){
                    AddNewUserJoinMessage(countEvent.pseudo);
                }
            })

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

            stompClientViewer.subscribe('/topic/live/chat/'+pseudoStreamer, function(message) {
                addMessageLive(JSON.parse(message.body));
            });

            stompClientViewer.subscribe('/topic/live/end/'+pseudoStreamer, function (){
                viewerPeerConnection.close();
                streamVideo.srcObject = null;
                messageNotStarted.innerText = "Live terminé"
                buttonStart.style.display = "none";
                buttonStart.style.visibility = 'visible';
                messageNotStarted.style.display = "block";
            })

            messageInput.addEventListener("keyup", function(event) {
                if (event.key === "Enter") {
                    sendChat();
                }
            });

            let isOnIos = navigator.userAgent.match(/iPad/i)|| navigator.userAgent.match(/iPhone/i);
            if(isOnIos){
                let unloaded = false;
                window.addEventListener('visibilitychange', function () {
                    if (document.hidden) {
                        stompClientViewer.send('/app/live/leave/'+pseudoStreamer,{},userPseudo);
                        setTimeout(stompClientViewer.disconnect(),1000);
                    }else{
                        location.reload();
                    }
                });
            } else {
                window.addEventListener('beforeunload', function (e) {
                    delete e['returnValue'];
                    stompClientViewer.send('/app/live/leave/'+pseudoStreamer,{},userPseudo);
                    setTimeout(stompClientViewer.disconnect(),1000);
                });
            }

        });
    });

    function sendChat(){
        if (messageInput.value.trim() !== ''){
            const messageText = messageInput.value;
            stompClientViewer.send('/app/live/chat/'+pseudoStreamer,{},JSON.stringify(
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
});
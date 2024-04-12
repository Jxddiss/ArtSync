import { GLTFLoader } from "https://cdn.skypack.dev/three@0.129.0/examples/jsm/loaders/GLTFLoader.js";

//renderer
const renderer = new THREE.WebGLRenderer({ alpha: true });
renderer.setSize(window.innerWidth, window.innerHeight);
renderer.shadowMap.enabled = true;
document.getElementById('container3D').appendChild(renderer.domElement);

//scene
const scene = new THREE.Scene();

//camera
const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);

//light
const ambiantLight = new THREE.AmbientLight(0xffffff, 0.5);
scene.add(ambiantLight);
const light = new THREE.DirectionalLight(0xffffff,5);
light.position.set(-50, 10, -25);
light.intensity =3.5;
light.castShadow = true;


scene.add(light);

let trophy = null;
let medal = null;
let wreath = null;

const loader = new GLTFLoader();

//trophy
loader.load(
    `media/models/trophy/scene.gltf`,
    function (gltf) {
        trophy = gltf.scene;
        scene.add(trophy);
        const scaleOutput = 15;
        trophy.scale.set(scaleOutput,scaleOutput,scaleOutput);
        trophy.position.set(205,0,-200)
        trophy.rotation.x = 0.3;
        trophy.rotation.z = -0.15;
        light.target = trophy;
        gsap.to(trophy.position, {x: 105, duration: 1, delay: 0.5, ease: 'back'});
        
    },
    function (xhr) {
      console.log((xhr.loaded / xhr.total * 100) + '% loaded');
    },
    function (error) {
    console.error(error);
    }
);

//medal
loader.load(
    `media/models/medal/scene.gltf`,
    function (gltf) {
        medal = gltf.scene;
        scene.add(medal);
        const scaleOutput = 5;
        medal.scale.set(scaleOutput,scaleOutput,scaleOutput);
        medal.position.set(-200,0,-200)
        medal.rotation.x = 1.35;
        medal.rotation.z = 0.5;
        medal.rotation.y = 1.75;
        light.target = medal;
        gsap.to(medal.position, {x: -115, duration: 1, delay: 0.75, ease: 'back'});
    },
    function (xhr) {
      console.log((xhr.loaded / xhr.total * 100) + '% loaded');
    },
    function (error) {
    console.error(error);
    }
);

//wreath
loader.load(
    `media/models/wreath/scene.gltf`,
    function (gltf) {
        wreath = gltf.scene;
        scene.add(wreath);
        const scaleOutput = 1250;
        wreath.scale.set(scaleOutput,scaleOutput,scaleOutput);
        wreath.position.set(-2,0.35,-201)
        wreath.rotation.x = 3;
        wreath.rotation.z = 3.12;
        wreath.traverse((o) => {
            if (o.isMesh) {
                o.material.transparent = true;
                o.material.opacity = 0;
            }
        });
        wreath.traverse((o) => {
            if (o.isMesh) {
                gsap.to(o.material, {opacity: 0.25, duration: 3, delay: 1.75});
            }
        });

        light.target = wreath;
    },
    function (xhr) {
      console.log((xhr.loaded / xhr.total * 100) + '% loaded');
    },
    function (error) {
    console.error(error);
    }
);


//Animate
function animate() {

    if (trophy) {
        trophy.rotation.y += 0.001;
        trophy.position.y = Math.sin(Date.now() * 0.002) * 2;
        trophy.position.y -= 10;
    }
    if (medal) {
        medal.rotation.y += 0.003;
        medal.position.y = Math.sin(Date.now() * 0.003) * 4;
        medal.position.y += 5;
    }

    requestAnimationFrame(animate);
    renderer.render(scene, camera);
}
animate();

function updateCamera() {
    const aspect = window.innerWidth / window.innerHeight;
    const fov = aspect > 1 ? 45 : 75;
    camera.aspect = aspect;
    camera.fov = fov;
    camera.updateProjectionMatrix();
}

window.addEventListener("resize", updateCamera);

updateCamera();
window.addEventListener("resize", () => {
    renderer.setSize(window.innerWidth, window.innerHeight);
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
});

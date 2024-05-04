import { OrbitControls } from 'https://threejsfundamentals.org/threejs/resources/threejs/r122/examples/jsm/controls/OrbitControls.js';
import { GLTFLoader } from "https://cdn.skypack.dev/three@0.129.0/examples/jsm/loaders/GLTFLoader.js";
//Renderer
const renderer = new THREE.WebGLRenderer({ alpha: true });
renderer.setSize(window.innerWidth, window.innerHeight);
renderer.shadowMap.enabled = true;
document.getElementById('container3D').appendChild(renderer.domElement);
//Scene
const scene = new THREE.Scene();
//Camera
const camera = new THREE.PerspectiveCamera(
    45,
    window.innerWidth / window.innerHeight,
    0.01,
    1000
);
camera.position.z = 1;
//Ambient light
const ambientLight = new THREE.AmbientLight( 0x404040);
scene.add(ambientLight);
//Point light
const spotLight = new THREE.SpotLight(0xFFFFFF, 5, 15);
scene.add(spotLight);
spotLight.position.set(0, 5, 5);
spotLight.intensity = 1
spotLight.distance = 100
spotLight.angle = 5
spotLight.focus = 5
spotLight.position.set(0.025,1,5)
let model = null;   
//Cube
function createBoxWithRoundedEdges( width, height, depth, radius0, smoothness ) {
    let shape = new THREE.Shape();
    let eps = 0.00001;
    let radius = radius0 - eps;
    shape.absarc( eps, eps, eps, -Math.PI / 2, -Math.PI, true );
    shape.absarc( eps, height -  radius * 2, eps, Math.PI, Math.PI / 2, true );
    shape.absarc( width - radius * 2, height -  radius * 2, eps, Math.PI / 2, 0, true );
    shape.absarc( width - radius * 2, eps, eps, 0, -Math.PI / 2, true );
    let geometry = new THREE.ExtrudeBufferGeometry( shape, {
      amount: depth - radius0 * 2,
      bevelEnabled: true,
      bevelSegments: smoothness * 2,
      steps: 1,
      bevelSize: radius,
      bevelThickness: radius0,
      curveSegments: smoothness
    });
    geometry.center();
    return geometry;
}
const cubeGeometry = createBoxWithRoundedEdges(0.05, 0.05, 0.05,0.0025, 5);
const cubeMaterial = new THREE.MeshStandardMaterial({ color: 'rgb(50,50,50)', metalness: 1.05, roughness: 0.5});
model = new THREE.Mesh(cubeGeometry, cubeMaterial);
scene.add(model);

const points = [
    new THREE.Vector3(-0.2,.2,1),
    new THREE.Vector3(-0.1,.1,0.9),
    new THREE.Vector3(0,-0.22,0.25),
    new THREE.Vector3(0.44,0,0),
    new THREE.Vector3(0.5,0.3,-0.25),
    new THREE.Vector3(0.38,0.5,-0.45),
    new THREE.Vector3(0.1,0.58,-0.5),
    new THREE.Vector3(-0.2,0.55,-0.5),
    new THREE.Vector3(-0.35,0.4,-0.35),
    new THREE.Vector3(-0.45,0.2,-0.2),
    new THREE.Vector3(-0.4,0,-0.05),
    new THREE.Vector3(-0.2,-0.15,0.2),
    new THREE.Vector3(0,-0.15,0.4),
    new THREE.Vector3(0.1,-0.01,0.7),
]
const points2= [
    new THREE.Vector3(-0.2,.2,1),
    new THREE.Vector3(-0.1,.1,0.9),
    new THREE.Vector3(0,-0.22,0.25),
    new THREE.Vector3(0.44,0,0),
    new THREE.Vector3(0.5,0.3,-0.25),
    new THREE.Vector3(0.38,0.5,-0.45),
    new THREE.Vector3(0.1,0.58,-0.5),
    new THREE.Vector3(-0.2,0.55,-0.5),
    new THREE.Vector3(-0.35,0.4,-0.35),
    new THREE.Vector3(-0.45,0.2,-0.2),
    new THREE.Vector3(-0.4,0,-0.05),
    new THREE.Vector3(-0.2,-0.15,0.2),
    new THREE.Vector3(0.245,-0.025,0.8),
]

const points3= [
    new THREE.Vector3(-0.2,.2,1),
    new THREE.Vector3(-0.1,.1,0.9),
    new THREE.Vector3(0,-0.22,0.25),
    new THREE.Vector3(0.44,0,0),
    new THREE.Vector3(0.5,0.3,-0.25),
    new THREE.Vector3(0.38,0.5,-0.45),
    new THREE.Vector3(0.1,0.58,-0.5),
    new THREE.Vector3(-0.2,0.55,-0.5),
    new THREE.Vector3(-0.35,0.4,-0.35),
    new THREE.Vector3(-0.45,0.2,-0.2),
    new THREE.Vector3(-0.4,0,-0.05),
    new THREE.Vector3(-0.2,-0.15,0.2),
    new THREE.Vector3(0.08,0.055,0.6),
]

const points4= [
    new THREE.Vector3(-0.2,.2,1),
    new THREE.Vector3(-0.1,.1,0.9),
    new THREE.Vector3(0,-0.22,0.25),
    new THREE.Vector3(0.44,0,0),
    new THREE.Vector3(0.5,0.3,-0.25),
    new THREE.Vector3(0.38,0.5,-0.45),
    new THREE.Vector3(0.1,0.58,-0.5),
    new THREE.Vector3(-0.2,0.55,-0.5),
    new THREE.Vector3(-0.35,0.4,-0.35),
    new THREE.Vector3(-0.45,0.2,-0.2),
    new THREE.Vector3(-0.4,0,-0.05),
    new THREE.Vector3(-0.2,-0.15,0.2),
    new THREE.Vector3(0.045 ,-0.06,0.75),
]
const points5= [
    new THREE.Vector3(-0.2,.2,1),
    new THREE.Vector3(-0.1,.1,0.9),
    new THREE.Vector3(0,-0.22,0.25),
    new THREE.Vector3(0.44,0,0),
    new THREE.Vector3(0.5,0.3,-0.25),
    new THREE.Vector3(0.38,0.5,-0.45),
    new THREE.Vector3(0.1,0.58,-0.5),
    new THREE.Vector3(-0.2,0.55,-0.5),
    new THREE.Vector3(-0.35,0.4,-0.35),
    new THREE.Vector3(-0.45,0.2,-0.2),
    new THREE.Vector3(-0.4,0,-0.05),
    new THREE.Vector3(-0.2,-0.15,0.2),
    new THREE.Vector3(0.11 ,0.032,0.75),
]

const littleCubeGeometry = createBoxWithRoundedEdges(0.027, 0.027, 0.027, 0.001, 5);
const littleCubeMaterial = new THREE.MeshStandardMaterial({ color: 'rgb(50,50,50)', metalness: 1.05, roughness: 0.5});
const littleCube = new THREE.Mesh(littleCubeGeometry, littleCubeMaterial);
littleCube.receiveShadow = true;
littleCube.castShadow = true;
scene.add(littleCube);

const littleCubeGeometry3 = createBoxWithRoundedEdges(0.027, 0.027, 0.027, 0.001, 5);
const littleCubeMaterial3 = new THREE.MeshStandardMaterial({ color: 'rgb(50,50,50)', metalness: 1.05, roughness: 0.5});
const littleCube3 = new THREE.Mesh(littleCubeGeometry3, littleCubeMaterial3);
littleCube3.receiveShadow = true;
littleCube3.castShadow = true;
scene.add(littleCube3);

const littleCubeGeometry4 = createBoxWithRoundedEdges(0.027, 0.027, 0.027, 0.001, 5);
const littleCubeMaterial4 = new THREE.MeshStandardMaterial({ color: 'rgb(50,50,50)', metalness: 1.05, roughness: 0.5});
const littleCube4 = new THREE.Mesh(littleCubeGeometry4, littleCubeMaterial4);
littleCube4.receiveShadow = true;
littleCube4.castShadow = true;
scene.add(littleCube4);

const littleCubeGeometry5 = createBoxWithRoundedEdges(0.025, 0.025, 0.025, 0.001, 5);
const littleCubeMaterial5 = new THREE.MeshStandardMaterial({ color: 'rgb(50,50,50)', metalness: 1.05, roughness: 0.5});
const littleCube5 = new THREE.Mesh(littleCubeGeometry5, littleCubeMaterial5);
littleCube5.receiveShadow = true;
littleCube5.castShadow = true;
scene.add(littleCube5);

const path = new THREE.CatmullRomCurve3(points,false)
const path2 = new THREE.CatmullRomCurve3(points2,false);
const path3 = new THREE.CatmullRomCurve3(points3,false);
const path4 = new THREE.CatmullRomCurve3(points4,false);
const path5 = new THREE.CatmullRomCurve3(points5,false);





function easeInOutQuad(t) {
    return t < 0.5 ? 2 * t * t : -1 + (4 - 2 * t) * t;
}
let t = 0;
let t2 = -0.1;
let t3 = -0.09;
let t4 = -0.25;
let t5 = -0.05;

//Animation
function animate() {
    if (model !== null && t <= 1) {
        const time = Date.now();
        t = (time/565 % 6)/6;
        t = easeInOutQuad(t);
        const position = path.getPointAt(t);
        model.position.copy(position);
        model.rotation.x += 0.015;
        model.rotation.y += 0.025;
        //little cube path
        t2 = (time/565 % 6)/6 - 0.1;
        if (t2 >= 0) {
            t2 = easeInOutQuad(t2);
            //little cube path
            const position2 = path2.getPointAt(t2);
            littleCube.position.copy(position2);
            littleCube.rotation.x += 0.015;
            littleCube.rotation.y += 0.025;
        }
        t3 = (time/565 % 6)/6 - 0.1;
        if (t3 >= 0) {
            t3 = easeInOutQuad(t2);
            //little cube path
            const position3 = path3.getPointAt(t3);
            littleCube3.position.copy(position3);
            littleCube3.rotation.x -= 0.015;
            littleCube3.rotation.y += 0.025;
        }
        t4 = (time/565 % 6)/6 - 0.1;
        if (t4 >= 0) {
            t4 = easeInOutQuad(t3);
            //little cube path
            const position4 = path4.getPointAt(t4);
            littleCube4.position.copy(position4);
            littleCube4.rotation.x += 0.015;
            littleCube4.rotation.y -= 0.025;
        }
        t5 = (time/565 % 6)/6 - 0.1;
        if (t5 >= 0) {
            t5 = easeInOutQuad(t5);
            //little cube path
            const position5 = path5.getPointAt(t4);
            littleCube5.position.copy(position5);
            littleCube5.rotation.x += 0.015;
            littleCube5.rotation.y -= 0.025;
        }
        if (position.distanceTo(points[points.length - 1]) < 0.01) {
            t = 2; 
            document.getElementById('slogan2').style.zIndex = 4;
            gsap.to(model.position, { z: "+=0.01", y:"+=0.005", ease: "rough", duration: 8 })
            gsap.to(littleCube.position, { z: "+=0.01", y:"+=0.002", x:"+=0.0021", ease: "rough", duration: 8 })
            gsap.to(littleCube3.position, { z: "+=0.02", y:"+=0.01", x:"+=0.04", ease: "rough", duration: 8 })
            gsap.to(littleCube4.position, { z: "+=0.01", y:"+=0.002", x:"+=0.025", ease: "rough", duration: 8 })
            gsap.to(littleCube5.position, { z: "+=0.015", y:"-=0.0025", x:"+=0.0025", ease: "rough", duration: 8 })
        }
        if (model.position.distanceTo(points[6]) < 0.01 && window.innerWidth>700) {
            document.getElementById('slogan').style.zIndex = 3;
            let tl = gsap.timeline({ yoyo: true, repeat: 1, repeatDelay: 0.4});
            tl.to("#slogan > *:nth-child(1)", { duration: 0.2, delay: 0.0025, x: 250, ease:'back' });
            tl.to("#slogan > *:nth-child(2)", { duration: 0.2, delay: .0025, x: -245, ease:'back' });
            tl.to("#slogan > *:nth-child(3)", { duration: 0.2, delay: .0025, x: 300, ease:'back' });
        }
    }
    else if (t > 1 && model !== null) {
        model.rotation.x += 0.00125; 
        model.rotation.y += 0.00125;
        littleCube.rotation.x += 0.00125;
        littleCube3.rotation.x -= 0.0015;
        littleCube3.rotation.y += 0.00125;
        littleCube4.rotation.x -= 0.0015;
        littleCube4.rotation.y -= 0.00125;
        littleCube5.rotation.x -= 0.00125;
        littleCube5.rotation.y += 0.00125;
    }
    renderer.render(scene, camera);
    requestAnimationFrame(animate);
}
window.onload = function() {
    // Start the animation
    requestAnimationFrame(animate);
};

window.addEventListener('resize', () => {
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(window.innerWidth, window.innerHeight);
});
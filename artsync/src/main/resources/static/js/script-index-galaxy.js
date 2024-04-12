import { GLTFLoader } from "https://cdn.skypack.dev/three@0.129.0/examples/jsm/loaders/GLTFLoader.js";

const renderer = new THREE.WebGLRenderer({alpha: true });
renderer.setSize(window.innerWidth, window.innerHeight);
document.getElementById('containerGalaxy').appendChild(renderer.domElement);
const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(
    45,
    window.innerWidth / window.innerHeight,
    0.0001,
    1000
);
camera.position.z = 25;
const light = new THREE.AmbientLight(0xffffff, 1);
scene.add(light);

const directionLight = new THREE.DirectionalLight(0xffffff, 1);
directionLight.position.set(0, 0, 29);
scene.add(directionLight);
directionLight.intesity = 15;



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
const cube1 = new THREE.Mesh(cubeGeometry, cubeMaterial);
scene.add(cube1);

const cubeGeometry2 = createBoxWithRoundedEdges(0.06, 0.06, 0.06,0.0025, 5);
const cubeMaterial2 = new THREE.MeshStandardMaterial({ color: 'rgb(50,50,50)', metalness: 1.05, roughness: 0.5});
const cube2 = new THREE.Mesh(cubeGeometry2, cubeMaterial2);
scene.add(cube2);


const points = [
    new THREE.Vector3( 0.15, 0.09, 24.8 ),
    new THREE.Vector3( 0.2, 0.06, 24.75 ),
    new THREE.Vector3( 0.7, 0.05, 24 ),
    new THREE.Vector3( 0.65, -0.09, 23.5 ),

    new THREE.Vector3( 0.4, -0.09, 23.8 ),
    new THREE.Vector3( 0.3, -0.05, 24.5 ),
    new THREE.Vector3( 0.1, -0.05, 24.6 ),
]
const points2 = [
    new THREE.Vector3( -0.7, 0.09, 24.8 ),
    new THREE.Vector3( -0.35, 0.06, 24.9 ),

    new THREE.Vector3( -0.0525, 0, 24.7 ),
]

const path = new THREE.CatmullRomCurve3(points,false)
const path2 = new THREE.CatmullRomCurve3(points2,false)


let galaxy = null
let invisible = false
const loader = new GLTFLoader();
loader.load(
    `media/models/galaxy/scene.gltf`,
    function (gltf) {
        galaxy = gltf.scene;
        scene.add(galaxy);
        galaxy.position.set(0,0,11)
        galaxy.rotation.x =0.6;
        galaxy.traverse((node) => {
            if (node.isMesh) {
                if (Array.isArray(node.material)) {
                    node.material.forEach(material => {
                        material.opacity = 0;
                    });
                } else {
                    node.material.opacity = 0;
                }
            }
        });
        
    },
    function (xhr) {
      console.log((xhr.loaded / xhr.total * 100) + '% loaded');
    },
    function (error) {
    console.error(error);
    }
);

function easeInOutQuad(t) {
    return t < 0.5 ? 2 * t * t : -1 + (4 - 2 * t) * t;
}
let rotationSpeed = 0.05;
let t=2; 
ScrollTrigger.create({
    trigger: document.getElementById('containerGalaxy'),
    start: "top 20%",
    end: "600%",
    scrub: 1,
    onEnter: () => {
        t = 0;
        rotationSpeed = 0.05;
    }
});
function animate() {
    if (t<1) {
        const time = Date.now();
        t = (time/400 % 6)/6;
        t = easeInOutQuad(t);
        let position = path.getPointAt(t);
        cube1.position.copy(position);
        cube1.rotation.x -= 0.015;
        cube1.rotation.y -= 0.035;
        rotationSpeed-=0.00001;

        const position2 = path2.getPointAt(t);
        cube2.position.copy(position2);
        cube2.rotation.x += 0.0045;
        cube2.rotation.y += 0.0065;
        cube2.rotation.y += 0.01;
        rotationSpeed-=0.00001;
        if (position.distanceTo(points[points.length - 1]) < 0.01) {
            t = 2; 
            gsap.to(cube1.position, { x:"-=0.065" ,z: "+=0.015", y:"+=0.025", ease: "sine", duration: 3 })
            gsap.to(cube2.position, { x:"+=0.005" ,z: "-=0.025", y:"+=0.015", ease: "sine", duration: 3 })
            gsap.to(cube1.rotation, { x:"-=1" , y:"-=1", ease: "sine", duration: 3 })
            gsap.to(cube2.rotation, { x:"+=1" ,z: "-=1", y:"-=0.015", ease: "sine", duration: 4 })
        }

    }
    if (galaxy){
        galaxy.rotation.y -= 0.005;
    }

    if (camera.position.z < 22 && !invisible) {
        if (galaxy) {
            invisible = true;
            galaxy.traverse((node) => {
                if (node.isMesh) {
                    if (Array.isArray(node.material)) {
                        node.material.forEach(material => {
                            gsap.to(material, {
                                opacity: 1,
                            });
                        });
                    } else {
                        gsap.to(node.material, {
                            opacity: 1,
                        });
                    }
                }
            });
        }
    } else if (camera.position.z >= 22 && invisible) {
        if (galaxy) {
            invisible = false;
            galaxy.traverse((node) => {
                if (node.isMesh) {
                    if (Array.isArray(node.material)) {
                        node.material.forEach(material => {
                            gsap.to(material, {
                                opacity: 0,
                            });
                        });
                    } else {
                        gsap.to(node.material, {
                            opacity: 0,
                        });
                    }
                }
            });
            //when finished 
            galaxy.traverse((node) => {
                if (node.isMesh) {
                    if (Array.isArray(node.material)) {
                        node.material.forEach(material => {
                            material.opacity = 0;
                        });
                    } else {
                        node.material.opacity = 0;
                    }
                }
            });
        }
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

gsap.registerPlugin(ScrollTrigger)


gsap.to(camera.position, {
    z: 14,
    scrollTrigger: {
        trigger: document.getElementById('containerGalaxy'),
        start: "bottom 80%",
        end: "700%",
        pin: true,
        pinSpacing: false,
        scrub: 1,
    },
    
});
gsap.to(document.body, {
    backgroundColor: "black",
    scrollTrigger: {
        trigger: document.getElementById('containerGalaxy'),
        start: "top 30rem",
        end: "100%",
        scrub: 1,
    },
});

const leftPostHolder = document.querySelector('.leftPostHolder');
const rightPostHolder = document.querySelector('.rightPostHolder');
const postsLeft = Array.from(leftPostHolder.children);
const postsRight = Array.from(rightPostHolder.children);


let offset =0;

postsLeft.forEach((post, index) => {
    offset+=50
    post.style.transform = `scale(0)`;
    gsap.to(post, {
        scale: 3,
        x: gsap.utils.random(-600, -200),
        y: gsap.utils.random(-100, 300),
        opacity: 0,
        transformOrigin: "center center",
        scrollTrigger: {
            trigger:document.getElementById('postTrigger'),
            start: `top ${offset}%`,
            end: `bottom 25 + ${offset} %`,
            scrub: 1,
        },
    });
});

offset =0;

postsRight.forEach((post, index) => {
    offset+=25
    post.style.transform = `scale(0)`;
    gsap.to(post, {
        scale: 3,
        x: gsap.utils.random(200, 600),
        y: gsap.utils.random(-300, 300),   
        opacity: 0,
        transformOrigin: "center center",
        scrollTrigger: {
            trigger:document.getElementById('postTrigger'),
            start: `top ${15 + offset+25}%`,
            end: `bottom 25 + ${offset} %`,
            scrub: 1,
        },
    });
});
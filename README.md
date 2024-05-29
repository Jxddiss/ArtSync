## PlateformeDeCollaborationArtistiqueNicholsonAliMartinVanettaSpring
Projet pour le cours 420-G26-RO - Applications Web 2 - H2024
Présenté à : Dini Ahamada
Type de Projet : Platforme de collaboration artistique

# Lien du site https://artsync.tech

##  Présentation de l'application
Notre application est une plateforme de collaboration artistique, intégrant des fonctionnalités variées inspirées de logiciels populaires tels qu'Instagram, GitLab, Twitch et Pinterest. Conçue pour favoriser la créativité et l'interaction, elle propose une multitude de services pour répondre aux besoins des utilisateurs.

Parmi les fonctionnalités principales, on retrouve :

    Publications : Partagez vos idées et créations avec la communauté.
    Personnalisation du profil et du portfolio : Affichez et organisez vos œuvres de manière unique.
    Forums : Participez à des discussions thématiques et échangez avec d'autres créateurs.
    Intelligence artificielle : Utilisez des outils de génération d'idées et d'images pour stimuler votre créativité.
    Appels vidéo : Communiquez en face à face ou en groupe grâce à des appels vidéo de haute qualité.
    Livestreaming : Diffusez vos sessions créatives en direct et interagissez avec votre audience en temps réel.
    Gestion de projets artistiques : Créez et gérez vos projets avec des dépôts de fichiers intégrés.
    Classement des meilleures publications : Découvrez et soyez inspiré par les œuvres les plus appréciées.
    Messagerie instantanée : Restez connecté avec vos contacts grâce à une messagerie réactive et intuitive.
    Système de notification en live : Recevez des mises à jour en temps réel pour ne rien manquer.
    Réseautage social : Élargissez votre réseau en connectant avec des créateurs et professionnels du monde entier.
    Cette plateforme tout-en-un est idéale pour les artistes, créateurs de contenu, développeurs et toute personne souhaitant collaborer et innover dans un environnement dynamique et inspirant.
    Administration : Gérez le contenus des utilisateurs que ce soit les utilisateurs eux même que ce soit leurs publications,
    groupes, fichiers, forums, commentaires
    Optimisier pour mobile : Profitez d'une experience optimisié sur mobile

## Technologies Utilisées

# Front End

HTML, CSS, JavaScript : Technologies de base pour la structure et le style des pages web.
Angular : Framework de développement d'applications web côté client utilisant TypeScript.
TypeScript : Superset de JavaScript qui ajoute des types statiques, améliorant ainsi la robustesse et la maintenabilité du code.
GSAP (GreenSock Animation Platform) : Bibliothèque JavaScript puissante pour créer des animations fluides et performantes.
Three.js : Bibliothèque JavaScript pour créer et afficher des graphiques 3D dans le navigateur.
WebRTC : Technologie pour les communications en temps réel, utilisée pour les appels vidéo.

# Back End

Spring Boot : Framework Java pour développer des applications web robustes et scalables.
MySQL : Système de gestion de base de données relationnelle pour stocker les données de l'application.
Spring Security : Module de sécurité de Spring Framework pour gérer l'authentification et l'autorisation.
Java : Langage de programmation utilisé pour le développement côté serveur.

# Autres

API OpenAI : Utilisée pour générer des idées grâce à des modèles d'intelligence artificielle avancés.
Stability AI API : Utilisée pour la génération d'images basée sur l'intelligence artificielle.

## Installation et configuration

Pour installer et configurer ce projet Spring Boot, suivez les étapes ci-dessous :

# Pré-requis :

Assurez-vous d'avoir MySQL installé sur votre système.
Configuration de la base de données :

Créez une base de données MySQL nommée artsync. L'injection des tables sera automatique lors du lancement de l'application.

# Configuration des API :

API OpenAI (Générateur d'idée) :
Ajoutez votre clé API OpenAI dans le fichier openai.properties (remplacez [Insert nom fichier] par le nom exact du fichier).
API Stability AI (Générateur d'image) :
Ajoutez votre clé API dans le fichier application.properties.

# Lancer l'application :

Démarrez l'application sur intellij. Toutes les configurations nécessaires seront prises en compte et votre environnement sera prêt à être utilisé.

## Statut du projet

Le projet a débuté en février 2024 et s'est achevé le 30 mai 2024. Toutes les fonctionnalités prévues ont été implémentées et testées, et l'application est désormais prête à être utilisée.
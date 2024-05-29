-- phpMyAdmin SQL Dump
-- version 4.5.4.1
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Mer 29 Mai 2024 à 22:32
-- Version du serveur :  8.3.0
-- Version de PHP :  7.2.7
use artsync;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";



/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `artsync`
--

-- --------------------------------------------------------

--
-- Structure de la table `annonce`
--

CREATE TABLE `annonce` (
  `id` bigint NOT NULL,
  `date_creation` datetime(6) DEFAULT NULL,
  `message` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `projet_id` bigint DEFAULT NULL,
  `utilisateur_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `app_setting`
--

CREATE TABLE `app_setting` (
  `id` bigint NOT NULL,
  `boite_idee_active` bit(1) NOT NULL,
  `forge_image_active` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contenu de la table `app_setting`
--

INSERT INTO `app_setting` (`id`, `boite_idee_active`, `forge_image_active`) VALUES
(2, b'1', b'1');

-- --------------------------------------------------------

--
-- Structure de la table `chat`
--

CREATE TABLE `chat` (
  `id` bigint NOT NULL,
  `date_time_envoie` datetime(6) DEFAULT NULL,
  `message` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `url_media` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `conversation_id` bigint DEFAULT NULL,
  `projet_id` bigint DEFAULT NULL,
  `utilisateur_deux_id` bigint DEFAULT NULL,
  `utilisateur_un_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contenu de la table `chat`
--

INSERT INTO `chat` (`id`, `date_time_envoie`, `message`, `type`, `url_media`, `conversation_id`, `projet_id`, `utilisateur_deux_id`, `utilisateur_un_id`) VALUES
(35, '2024-04-16 18:24:50.056000', 'Allo', 'text', NULL, 11, NULL, 6, 1),
(39, '2024-04-16 18:58:42.836000', NULL, 'media', '/media/chat/nwa.png', 11, NULL, 6, 1),
(110, '2024-04-23 03:39:38.269000', 'Yoo', 'text', NULL, 11, NULL, 1, 6),
(111, '2024-04-23 03:39:48.289000', 'test', 'text', NULL, 11, NULL, 6, 1),
(112, '2024-04-23 03:39:49.450000', 'test', 'text', NULL, 11, NULL, 6, 1),
(113, '2024-04-23 03:39:52.964000', 'ca va', 'text', NULL, 11, NULL, 1, 6),
(114, '2024-04-23 03:57:30.334000', 'Yoo', 'text', NULL, 14, NULL, 13, 6),
(115, '2024-04-23 04:00:41.208000', 'Allo', 'text', NULL, 11, NULL, 1, 6),
(121, '2024-04-24 00:45:24.196000', 'T\'est là?', 'text', NULL, 11, NULL, 6, 1),
(122, '2024-04-24 00:45:34.547000', 'Répond moi ici', 'text', NULL, 11, NULL, 6, 1),
(123, '2024-04-24 00:46:16.820000', 'allo i guess', 'text', NULL, 12, NULL, 4, 1),
(139, '2024-04-25 03:38:58.706000', 'Ca va', 'text', NULL, 12, NULL, 4, 1),
(140, '2024-04-25 05:52:07.335000', 'allo', 'text', NULL, 11, NULL, 6, 1),
(141, '2024-04-25 05:52:13.647000', 'yoo', 'text', NULL, 11, NULL, 6, 1),
(142, '2024-04-25 05:52:31.180000', 'Ca va', 'text', NULL, 11, NULL, 6, 1),
(143, '2024-04-25 20:43:04.777000', 'Yo', 'text', NULL, 11, NULL, 6, 1),
(144, '2024-04-25 20:48:08.629000', 'Yoo', 'text', NULL, 11, NULL, NULL, 1),
(145, '2024-04-25 20:48:38.118000', 'K', 'text', NULL, 11, NULL, NULL, 1),
(146, '2024-04-25 20:48:43.310000', 'Allo', 'text', NULL, 11, NULL, NULL, 6),
(147, '2024-04-26 06:25:57.275000', 'Allo', 'text', NULL, 11, NULL, NULL, 6),
(148, '2024-04-26 06:27:20.916000', 'Allo', 'text', NULL, 11, NULL, NULL, 6),
(149, '2024-04-26 06:27:32.007000', 'K', 'text', NULL, 11, NULL, NULL, 6),
(150, '2024-04-26 06:30:35.253000', 'Yoo', 'text', NULL, 11, NULL, NULL, 6),
(151, '2024-04-26 06:30:43.645000', 'K', 'text', NULL, 11, NULL, NULL, 6),
(152, '2024-04-26 06:30:52.776000', 'K', 'text', NULL, 11, NULL, NULL, 6),
(153, '2024-04-26 06:33:06.203000', 'Test en live?', 'text', NULL, 11, NULL, NULL, 6),
(154, '2024-04-26 06:34:42.854000', 'Non j\'ai finis', 'text', NULL, 11, NULL, NULL, 1),
(155, '2024-04-26 06:35:34.935000', NULL, 'media', '/media/chat/a0d59759780eb78ae1f3d15b9f0a0a1af1a41379.gif', 11, NULL, NULL, 6),
(156, '2024-04-27 02:05:25.096000', 'Allo', 'text', NULL, 12, NULL, NULL, 1),
(157, '2024-04-27 02:05:37.255000', 'Yoo', 'text', NULL, 12, NULL, NULL, 1),
(158, '2024-04-27 02:05:50.299000', 'hey', 'text', NULL, 12, NULL, NULL, 4),
(163, '2024-04-28 02:22:56.004000', 'Allo', 'text', NULL, 11, NULL, NULL, 6),
(164, '2024-04-28 04:09:51.894000', 'Yoooo', 'text', NULL, 17, NULL, NULL, 3),
(165, '2024-04-28 04:38:43.211000', '<h1>Hi</h1>', 'text', NULL, 11, NULL, NULL, 1),
(166, '2024-04-28 04:39:09.904000', '<script>alert("hi")</script>', 'text', NULL, 11, NULL, NULL, 1),
(176, '2024-04-30 19:45:04.905000', 'allo', 'text', NULL, 11, NULL, NULL, 6),
(177, '2024-04-30 21:29:36.277000', 'allo', 'text', NULL, 11, NULL, NULL, 6),
(179, '2024-04-30 22:26:30.135000', 'Test', 'text', NULL, 11, NULL, NULL, 1),
(186, '2024-05-02 09:05:19.434000', 'Allo', 'text', NULL, 11, NULL, NULL, 6),
(188, '2024-05-04 01:16:15.507000', 'Allo', 'text', NULL, 11, NULL, NULL, 1),
(189, '2024-05-04 01:16:43.746000', 'Allo', 'text', NULL, 11, NULL, NULL, 1),
(190, '2024-05-04 01:20:16.450000', 'Allo', 'text', NULL, 11, NULL, NULL, 1),
(192, '2024-05-05 01:14:51.715000', 'aa', 'text', NULL, 11, NULL, NULL, 1),
(193, '2024-05-05 01:26:14.296000', 'ww', 'text', NULL, 11, NULL, NULL, 6),
(194, '2024-05-05 04:07:56.027000', NULL, 'media', '/media/chat/cat.jpg', 11, NULL, NULL, 1),
(195, '2024-05-05 04:42:34.547000', 'Yoo', 'text', NULL, 11, NULL, NULL, 6),
(196, '2024-05-05 04:52:57.931000', 'allo', 'text', NULL, 11, NULL, NULL, 1),
(197, '2024-05-05 05:04:21.081000', 'Allo', 'text', NULL, 11, NULL, NULL, 6),
(198, '2024-05-05 05:07:06.872000', 'Allo', 'text', NULL, 11, NULL, NULL, 1),
(199, '2024-05-05 05:09:02.322000', 'Allo', 'text', NULL, 11, NULL, NULL, 6),
(200, '2024-05-05 05:31:35.021000', 'Allo', 'text', NULL, 11, NULL, NULL, 6),
(201, '2024-05-05 03:37:44.976975', 'Yooo', 'text', NULL, 11, NULL, NULL, 1),
(202, '2024-05-05 03:38:18.141134', 'ca va', 'text', NULL, 11, NULL, NULL, 6),
(203, '2024-05-05 21:51:38.456806', 'allo', 'text', NULL, 11, NULL, NULL, 1),
(204, '2024-05-05 21:52:24.232201', 'Yoo', 'text', NULL, 11, NULL, NULL, 1),
(205, '2024-05-05 21:54:04.915445', 'j', 'text', NULL, 11, NULL, NULL, 1),
(206, '2024-05-05 22:36:28.022433', 'ca', 'text', NULL, 11, NULL, NULL, 1),
(207, '2024-05-05 22:36:40.718795', 'g', 'text', NULL, 11, NULL, NULL, 1),
(208, '2024-05-05 22:36:59.300132', 'test', 'text', NULL, 11, NULL, NULL, 1),
(210, '2024-05-05 23:49:54.279197', 'Allo', 'text', NULL, 12, NULL, NULL, 4),
(211, '2024-05-05 23:50:32.065367', 'yo', 'text', NULL, 17, NULL, NULL, 3),
(212, '2024-05-06 00:04:07.142573', 'Yoo', 'text', NULL, 11, NULL, NULL, 6),
(213, '2024-05-06 00:05:18.136570', 'sss', 'text', NULL, 11, NULL, NULL, 6),
(214, '2024-05-06 00:06:06.753284', 'aa', 'text', NULL, 11, NULL, NULL, 6),
(215, '2024-05-06 00:10:51.913998', 'ssss', 'text', NULL, 11, NULL, NULL, 1),
(216, '2024-05-06 00:22:03.223117', 'Yoo', 'text', NULL, 11, NULL, NULL, 1),
(217, '2024-05-06 00:53:18.403880', 'Hhhhhhhh gbhbgsjakghjk jkhgjkhjk jhkgbjkhgbk jkgbhjkhb jbjkbkjb jhbjkb kjbjkb', 'text', NULL, 11, NULL, NULL, 6),
(218, '2024-05-06 02:05:56.642239', 'Yoo', 'text', NULL, 11, NULL, NULL, 6),
(219, '2024-05-06 02:23:12.340071', 'Yoo', 'text', NULL, 11, NULL, NULL, 6),
(220, '2024-05-06 02:24:11.041457', 'gg', 'text', NULL, 11, NULL, NULL, 6),
(221, '2024-05-06 02:24:17.151488', 'gg', 'text', NULL, 11, NULL, NULL, 6),
(222, '2024-05-06 02:26:01.546598', 'vv', 'text', NULL, 11, NULL, NULL, 6),
(223, '2024-05-06 02:27:50.452519', 'c', 'text', NULL, 11, NULL, NULL, 6),
(224, '2024-05-07 19:06:41.273080', 'Allo', 'text', NULL, 11, NULL, NULL, 6),
(225, '2024-05-07 19:18:02.312718', 'yoo', 'text', NULL, 11, NULL, NULL, 6),
(226, '2024-05-07 19:23:06.587148', 'yooo', 'text', NULL, 11, NULL, NULL, 1),
(227, '2024-05-07 19:24:58.638965', 'Ca va', 'text', NULL, 11, NULL, NULL, 1),
(228, '2024-05-07 19:28:37.949552', 'ouii', 'text', NULL, 11, NULL, NULL, 6),
(229, '2024-05-07 19:30:25.803983', 'ss', 'text', NULL, 11, NULL, NULL, 6),
(230, '2024-05-07 19:31:33.377512', 'yoo', 'text', NULL, 11, NULL, NULL, 6),
(231, '2024-05-07 19:31:44.744154', 'allo', 'text', NULL, 11, NULL, NULL, 1),
(232, '2024-05-08 20:48:37.263494', 'allo', 'text', NULL, 11, NULL, NULL, 6),
(233, '2024-05-08 21:27:55.599684', 'll', 'text', NULL, 11, NULL, NULL, 6),
(234, '2024-05-08 21:29:06.528588', 'Yoo', 'text', NULL, 11, NULL, NULL, 6),
(235, '2024-05-08 21:30:20.396414', 'ss', 'text', NULL, 11, NULL, NULL, 6),
(236, '2024-05-08 21:30:48.377449', 'a', 'text', NULL, 11, NULL, NULL, 6),
(237, '2024-05-08 21:32:21.031505', 'allo', 'text', NULL, 11, NULL, NULL, 6),
(238, '2024-05-09 23:11:01.757142', NULL, 'media', '/media/chat/testBanGif.gif', 11, NULL, NULL, 1),
(239, '2024-05-14 22:36:01.651640', 'Yoo', 'text', NULL, 11, NULL, NULL, 1),
(240, '2024-05-15 00:02:51.666430', 'dskdsdfd', 'text', NULL, 11, NULL, NULL, 1),
(241, '2024-05-15 00:04:18.071086', 'yyo', 'text', NULL, 11, NULL, NULL, 1),
(242, '2024-05-15 00:06:42.598803', 'allo', 'text', NULL, 29, NULL, NULL, 1),
(243, '2024-05-15 00:06:51.950994', 'Allo', 'text', NULL, 29, NULL, NULL, 6),
(244, '2024-05-15 00:07:14.070052', 'sdfs', 'text', NULL, 29, NULL, NULL, 6),
(245, '2024-05-15 00:07:55.255173', 'asas', 'text', NULL, 29, NULL, NULL, 6),
(246, '2024-05-15 00:11:27.445502', 'efef', 'text', NULL, 29, NULL, NULL, 6),
(247, '2024-05-15 00:11:33.952561', 'efsef', 'text', NULL, 29, NULL, NULL, 6),
(248, '2024-05-15 00:13:10.333064', 'Yooo', 'text', NULL, 29, NULL, NULL, 6),
(249, '2024-05-15 00:14:09.199400', 'Yoo', 'text', NULL, 29, NULL, NULL, 6),
(250, '2024-05-15 00:14:14.954880', 'Yooo', 'text', NULL, 11, NULL, NULL, 6),
(251, '2024-05-15 00:16:04.075268', 'Yoo', 'text', NULL, 11, NULL, NULL, 1),
(252, '2024-05-15 00:16:22.407536', 'Yoo', 'text', NULL, 29, NULL, NULL, 1),
(253, '2024-05-15 00:19:15.868112', 'Yoo', 'text', NULL, 29, NULL, NULL, 1),
(254, '2024-05-15 00:20:20.793363', 'Yooo', 'text', NULL, 29, NULL, NULL, 1),
(255, '2024-05-15 00:23:11.681298', 'Yoo', 'text', NULL, 29, NULL, NULL, 1),
(256, '2024-05-15 00:26:37.147107', 'Yooo', 'text', NULL, 29, NULL, NULL, 1),
(257, '2024-05-15 00:28:18.090128', 'Yoo', 'text', NULL, 29, NULL, NULL, 1),
(258, '2024-05-15 14:39:38.836692', 'yooo', 'text', NULL, 11, NULL, NULL, 1),
(259, '2024-05-15 14:39:44.888227', 'yooo', 'text', NULL, 29, NULL, NULL, 1),
(260, '2024-05-15 14:43:54.500815', 'yoooo', 'text', NULL, 29, NULL, NULL, 1),
(261, '2024-05-15 14:45:30.889682', 'yoo', 'text', NULL, 29, NULL, NULL, 6),
(262, '2024-05-15 14:45:51.607246', 'Yoo', 'text', NULL, 29, NULL, NULL, 6),
(263, '2024-05-15 14:46:21.633696', 'gjjwehjwewq', 'text', NULL, 11, NULL, NULL, 1),
(264, '2024-05-15 15:00:59.572931', 'Yooo', 'text', NULL, 11, NULL, NULL, 1),
(265, '2024-05-15 15:01:06.718861', 'yOOO', 'text', NULL, 29, NULL, NULL, 1),
(266, '2024-05-15 22:20:27.052333', 'Yoooo', 'text', NULL, 11, NULL, NULL, 6),
(267, '2024-05-15 22:21:54.313410', 'Ca va ', 'text', NULL, 11, NULL, NULL, 6),
(268, '2024-05-15 22:22:00.019055', '?', 'text', NULL, 11, NULL, NULL, 6),
(269, '2024-05-15 22:22:47.863612', 'Yoo', 'text', NULL, 11, NULL, NULL, 1),
(270, '2024-05-27 14:47:32.895784', 'Yoooo', 'text', NULL, 11, NULL, NULL, 1),
(271, '2024-05-27 14:49:55.669052', 'Yooo', 'text', NULL, 11, NULL, NULL, 6),
(272, '2024-05-27 14:50:05.501242', 'Ca va?', 'text', NULL, 11, NULL, NULL, 1),
(273, '2024-05-27 14:50:11.567208', 'Ouiiiii', 'text', NULL, 11, NULL, NULL, 6),
(274, '2024-05-27 14:50:34.799909', 'Yoooo', 'text', NULL, 11, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Structure de la table `commentaire`
--

CREATE TABLE `commentaire` (
  `id` bigint NOT NULL,
  `message` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `forum_id` bigint DEFAULT NULL,
  `post_id` bigint DEFAULT NULL,
  `utilisateur_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contenu de la table `commentaire`
--

INSERT INTO `commentaire` (`id`, `message`, `forum_id`, `post_id`, `utilisateur_id`) VALUES
(2, 'Waaawwww 😲❤️', NULL, 27, 1),
(7, 'Alllo', NULL, 23, 1),
(9, 'Ouhhh', NULL, 1, 1),
(10, 'C\'est beau', NULL, 3, 1),
(11, 'Vraiment', NULL, 3, 1),
(12, 'C\'est vraiment beau', NULL, 13, 1),
(13, 'Yooooo', NULL, 23, 1),
(15, 'Test', NULL, 36, 1),
(16, 'Test', NULL, 39, 1),
(17, 'Damn', NULL, 5, 1),
(18, 'Test', NULL, 6, 1),
(20, 'Test', NULL, 7, 1),
(21, 'test', NULL, 7, 1),
(22, 'c\'est beau', NULL, 27, 1),
(23, 'C\'est trop cool', NULL, 57, 1),
(24, 'Test', NULL, 56, 1),
(25, 'test', NULL, 57, 1),
(26, 'Omoi', NULL, 58, 1),
(27, 'Test', NULL, 58, 1),
(28, 'Test', NULL, 58, 1),
(29, 'Test', NULL, 55, 1),
(30, 'Test', NULL, 55, 1),
(31, 'Test', NULL, 55, 1),
(32, 'Test', NULL, 43, 1),
(33, 'Test', NULL, 46, 1),
(34, 'Test', NULL, 3, 1),
(35, 'Test', NULL, 39, 1),
(36, 'Test', NULL, 1, 1),
(37, 'Test', NULL, 1, 1),
(38, 'test', NULL, 57, 1),
(39, 'Allo', NULL, 58, 1),
(40, 'Yooo', NULL, 53, 1),
(41, 'Test', NULL, 61, 1),
(54, 'Je vous attend!', 4, NULL, 16),
(57, 'adssdsd', 5, NULL, 1),
(58, 'sdaadsd', 5, NULL, 1),
(59, 'eeeeeee', 4, NULL, 1),
(60, 'Test 11111', NULL, 59, 6);

-- --------------------------------------------------------

--
-- Structure de la table `confirmation_token`
--

CREATE TABLE `confirmation_token` (
  `id` bigint NOT NULL,
  `date_expiration` datetime(6) NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contenu de la table `confirmation_token`
--

INSERT INTO `confirmation_token` (`id`, `date_expiration`, `token`, `type`, `user_id`) VALUES
(13, '2024-05-21 04:00:00.000000', '9f2d3774-56c4-424f-aa46-31f5bb6f6946', 'PASSWORD_RESET', 9),
(14, '2024-05-21 04:00:00.000000', 'c865a214-8d60-4c1c-963e-fc0b01169c0a', 'PASSWORD_RESET', 9);

-- --------------------------------------------------------

--
-- Structure de la table `conversation`
--

CREATE TABLE `conversation` (
  `id` bigint NOT NULL,
  `projet_id` bigint DEFAULT NULL,
  `utilisateur_deux_id` bigint DEFAULT NULL,
  `utilisateur_un_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contenu de la table `conversation`
--

INSERT INTO `conversation` (`id`, `projet_id`, `utilisateur_deux_id`, `utilisateur_un_id`) VALUES
(11, NULL, 1, 6),
(12, NULL, 4, 1),
(14, NULL, 13, 6),
(17, NULL, 3, 1),
(20, NULL, 1, 11),
(25, NULL, 6, 3),
(29, 6, NULL, 1);

-- --------------------------------------------------------

--
-- Structure de la table `demande`
--

CREATE TABLE `demande` (
  `id` bigint NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id_projet` bigint DEFAULT NULL,
  `id_utilisateur` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `demande_admin`
--

CREATE TABLE `demande_admin` (
  `id` bigint NOT NULL,
  `message` longtext COLLATE utf8mb4_unicode_ci,
  `user_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contenu de la table `demande_admin`
--

INSERT INTO `demande_admin` (`id`, `message`, `user_id`) VALUES
(5, 'S\'il vous plait débloque moi', 6);

-- --------------------------------------------------------

--
-- Structure de la table `fichier_general`
--

CREATE TABLE `fichier_general` (
  `id` bigint NOT NULL,
  `url_media` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `post_id` bigint DEFAULT NULL,
  `utilisateur_id` bigint DEFAULT NULL,
  `fichier_id` bigint DEFAULT NULL,
  `projet_id` bigint DEFAULT NULL,
  `forum_id` bigint DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contenu de la table `fichier_general`
--

INSERT INTO `fichier_general` (`id`, `url_media`, `post_id`, `utilisateur_id`, `fichier_id`, `projet_id`, `forum_id`, `type`) VALUES
(1, 'cat.png', 1, 1, NULL, NULL, NULL, 'image'),
(3, 'oil.png', 3, 3, NULL, NULL, NULL, 'image'),
(4, 'pikachu.gif', 4, 4, NULL, NULL, NULL, 'image'),
(5, 'digitalArt-1.jpg', 5, 6, NULL, NULL, NULL, 'image'),
(6, 'pixelart2.png', 6, 1, NULL, NULL, NULL, 'image'),
(7, 'art.jpg', 7, 1, NULL, NULL, NULL, 'image'),
(9, 'orc.png', 9, 3, NULL, NULL, NULL, 'image'),
(10, '3D-2.png', 10, 4, NULL, NULL, NULL, 'image'),
(11, 'nwa.png', 13, 4, NULL, NULL, NULL, 'image'),
(14, 'blossom.jpg', 20, 6, NULL, NULL, NULL, 'image'),
(17, 'nitro.jpg', 23, 6, NULL, NULL, NULL, 'image'),
(18, 'landscape.png', 24, 3, NULL, NULL, NULL, 'image'),
(19, 'onePunch.jpg', 25, 11, NULL, NULL, NULL, 'image'),
(21, 'japmar.jpg', 27, 4, NULL, NULL, NULL, 'image'),
(27, 'Space Background.png', 36, NULL, NULL, NULL, NULL, 'image'),
(28, 'test.png', 37, NULL, NULL, NULL, NULL, 'image'),
(30, 'pix.jpeg', 39, NULL, NULL, NULL, NULL, 'image'),
(32, 'bg_1040.jpg', 40, NULL, NULL, NULL, NULL, 'image'),
(33, 'bg_1073.png', 41, NULL, NULL, NULL, NULL, 'image'),
(35, 'DesignerPage.png', 43, NULL, NULL, NULL, NULL, 'image'),
(38, 'monkeydluffy.jpg', 46, NULL, NULL, NULL, NULL, 'image'),
(41, 'cyberPunkPixel.gif', 49, NULL, NULL, NULL, NULL, 'image'),
(42, 'art.jpg', 50, NULL, NULL, NULL, NULL, 'image'),
(43, 'cybercity.jpg', 51, NULL, NULL, NULL, NULL, 'image'),
(44, 'cyberpixelArt.jpg', 52, NULL, NULL, NULL, NULL, 'image'),
(45, 'characterCyber.jpg', 53, NULL, NULL, NULL, NULL, 'image'),
(46, 'aok.jpg', 54, NULL, NULL, NULL, NULL, 'image'),
(47, '3D.jpg', 55, NULL, NULL, NULL, NULL, 'image'),
(48, 'nicPfp.gif', 56, NULL, NULL, NULL, NULL, 'image'),
(49, 'zoro-pfp.gif', 57, NULL, NULL, NULL, NULL, 'image'),
(50, 'Omoi.jpg', 58, NULL, NULL, NULL, NULL, 'image'),
(51, 'cat.jpg', NULL, 1, NULL, 6, NULL, 'image'),
(59, 'test.docx', NULL, 1, NULL, 6, NULL, 'docx'),
(61, 'animationstickman.gif', 59, NULL, NULL, NULL, NULL, NULL),
(63, 'Document - Google Chrome 2024-03-30 21-58-53.mp4', 61, NULL, NULL, NULL, NULL, NULL),
(64, 'Vidéo sans titre – Réalisée avec Clipchamp.gif', 62, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `forum`
--

CREATE TABLE `forum` (
  `id` bigint NOT NULL,
  `contenu` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date_creation` datetime(6) DEFAULT NULL,
  `filtres` varchar(125) COLLATE utf8mb4_unicode_ci NOT NULL,
  `publique` bit(1) NOT NULL,
  `titre` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contenu de la table `forum`
--

INSERT INTO `forum` (`id`, `contenu`, `date_creation`, `filtres`, `publique`, `titre`, `user_id`) VALUES
(4, 'Il y\'a un live ce soir venez en grand nombre', '2024-05-27 16:52:56.020893', 'ANNONCE,', b'1', 'Live ce soir', 16),
(5, 'test', '2024-05-27 16:53:22.597184', 'CONSEIL,QUESTION,ANNONCE,', b'1', 'Test', 16);

-- --------------------------------------------------------

--
-- Structure de la table `live_stream`
--

CREATE TABLE `live_stream` (
  `id` bigint NOT NULL,
  `active` bit(1) NOT NULL,
  `pseudo_streamer` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `titre` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contenu de la table `live_stream`
--

INSERT INTO `live_stream` (`id`, `active`, `pseudo_streamer`, `titre`) VALUES
(5, b'0', 'quitta', 'fgfsdg'),
(6, b'0', 'mark09y', 'hfgh'),
(7, b'0', 'test', 'Ali test tu vois?');

-- --------------------------------------------------------

--
-- Structure de la table `notification`
--

CREATE TABLE `notification` (
  `id` bigint NOT NULL,
  `appel` bit(1) NOT NULL,
  `img_sender` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `message` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pseudo_sender` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `titre` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `url_notif` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lu` bit(1) NOT NULL,
  `id_dest` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contenu de la table `notification`
--

INSERT INTO `notification` (`id`, `appel`, `img_sender`, `message`, `pseudo_sender`, `titre`, `type`, `url_notif`, `lu`, `id_dest`) VALUES
(1, b'0', 'aok.jpg', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(2, b'0', 'aok.jpg', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(3, b'0', 'aok.jpg', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(4, b'0', 'profil9.png', 'Nouvelle mention jaime de quitta', 'quitta', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 1),
(5, b'0', 'bigben.jpg', 'bigbadben s\'est abonné à votre compte', 'bigbadben', 'Nouvelle abonné', 'relation', '/utilisateur/profil/bigbadben', b'1', 1),
(6, b'0', 'aok.jpg', 'mark09y s\'est abonné à votre compte', 'mark09y', 'Nouvelle abonné', 'relation', '/utilisateur/profil/mark09y', b'1', 10),
(7, b'0', 'bigben.jpg', 'Nouveau message de bigbadben', 'bigbadben', 'Nouveau message', 'info', '/utilisateur/conversation/26', b'1', 1),
(8, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 6),
(9, b'0', 'aok.jpg', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(10, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(11, b'0', 'aok.jpg', 'Nouveau message de ', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(12, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(13, b'0', 'aok.jpg', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(14, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 6),
(15, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(16, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(17, b'0', 'aok.jpg', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(18, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(19, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(20, b'0', 'aok.jpg', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(21, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(22, b'0', 'aok.jpg', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(23, b'0', 'aok.jpg', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(24, b'0', 'aok.jpg', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(25, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(26, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(27, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 6),
(28, b'0', 'aok.jpg', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(29, b'0', 'aok.jpg', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(30, b'0', 'aok.jpg', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(31, b'0', 'marianne.jpg', 'Nouveau message de aqualuna', 'aqualuna', 'Nouveau message', 'info', '/utilisateur/conversation/12', b'1', 1),
(32, b'0', 'oil.png', 'Nouveau message de test', 'test', 'Nouveau message', 'info', '/utilisateur/conversation/17', b'1', 1),
(33, b'0', 'aok.jpg', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 16),
(34, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(35, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(36, b'0', 'profil9.png', 'Nouvelle mention jaime de quitta', 'quitta', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 1),
(37, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(38, b'0', 'profil9.png', 'Nouvelle mention jaime de quitta', 'quitta', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 1),
(39, b'0', 'profil9.png', 'Nouvelle mention jaime de quitta', 'quitta', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 1),
(40, b'0', 'profil9.png', 'Nouvelle mention jaime de quitta', 'quitta', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 1),
(41, b'0', 'profil9.png', 'Nouvelle mention jaime de quitta', 'quitta', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 1),
(42, b'0', 'profil9.png', 'Nouvelle mention jaime de quitta', 'quitta', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 1),
(43, b'0', 'profil9.png', 'Nouvelle mention jaime de quitta', 'quitta', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 1),
(44, b'0', 'profil9.png', 'Nouvelle mention jaime de quitta', 'quitta', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 1),
(45, b'0', 'aok.jpg', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(46, b'0', 'profil9.png', 'Nouvelle mention jaime de quitta', 'quitta', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 1),
(47, b'0', 'profil9.png', 'Nouvelle mention jaime de quitta', 'quitta', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 1),
(48, b'0', 'aok.jpg', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(49, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(50, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(51, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(52, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(53, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(54, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(55, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(56, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(57, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/12', b'1', 4),
(58, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/12', b'1', 4),
(59, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 6),
(60, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 6),
(61, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(62, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 6),
(63, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(64, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 6),
(65, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(66, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(67, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 6),
(68, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(69, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(70, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(71, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(72, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(73, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(74, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(75, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(76, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(77, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(78, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(79, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(80, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(81, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(82, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 6),
(83, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(84, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 6),
(85, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 6),
(86, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 6),
(87, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 6),
(88, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 1),
(89, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11', b'1', 6),
(90, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(91, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(92, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(93, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(94, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(95, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(96, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(97, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(98, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(99, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(100, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(101, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(102, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(103, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(104, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(105, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(106, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(107, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(108, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(109, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(110, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(111, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(112, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(113, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(114, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(115, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(116, b'0', 'aok.jpg', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(117, b'0', 'aok.jpg', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(118, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(119, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(120, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(121, b'0', 'aok.jpg', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(122, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(123, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(124, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(125, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(126, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(127, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(128, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(129, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(130, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(131, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(132, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(133, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(134, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(135, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(136, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(137, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(138, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(139, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(140, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(141, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(142, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(143, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(144, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(145, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(146, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(147, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(148, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(149, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(150, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(151, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(152, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(153, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(154, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(155, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(156, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(157, b'1', 'aok.jpg', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(158, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(159, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(160, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(161, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(162, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(163, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(164, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(165, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(166, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(167, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(168, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(169, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(170, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(171, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(172, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(173, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(174, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(175, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(176, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(177, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(178, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(179, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(180, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(181, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(182, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(183, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(184, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(185, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(186, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(187, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(188, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(189, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(190, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(191, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(192, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(193, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(194, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(195, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(196, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(197, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(198, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(199, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(200, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(201, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(202, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(203, b'0', 'mark09y.gif', 'mark09y s\'est abonné à votre compte', 'mark09y', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y', b'1', 14),
(204, b'0', 'mark09y.gif', 'Nouvelle mention jaime de mark09y', 'mark09y', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 16),
(205, b'1', 'mark09y.gif', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(206, b'0', 'mark09y.gif', 'Nouveau message de ', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(207, b'0', 'mark09y.gif', 'mark09y s\'est abonné à votre compte', 'mark09y', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y', b'1', 9),
(208, b'0', 'mark09y.gif', 'mark09y s\'est abonné à votre compte', 'mark09y', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y', b'1', 9),
(209, b'1', 'mark09y.gif', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(210, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(211, b'0', 'mark09y.gif', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(212, b'0', 'mark09y.gif', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(213, b'0', 'mark09y.gif', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(214, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(215, b'0', 'mark09y.gif', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(216, b'0', 'mark09y.gif', 'Nouveau message de groupe', 'mark09y', 'Groupe Test', 'info', '/utilisateur/conversation/projet/29', b'1', 1),
(217, b'0', 'mark09y.gif', 'Nouveau message de groupe', 'mark09y', 'Groupe Test', 'info', '/utilisateur/conversation/projet/29', b'1', 6),
(218, b'0', 'mark09y.gif', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(219, b'0', 'mark09y.gif', 'Nouveau message de groupe', 'mark09y', 'Groupe Test', 'info', '/utilisateur/conversation/projet/29', b'1', 6),
(220, b'0', 'mark09y.gif', 'Nouveau message de groupe', 'mark09y', 'Groupe Test', 'info', '/utilisateur/conversation/projet/29', b'1', 6),
(221, b'0', 'profil9.png', 'Nouveau message de groupe', 'quitta', 'Groupe Test', 'info', '/utilisateur/conversation/projet/29', b'1', 6),
(222, b'0', 'profil9.png', 'Nouveau message de groupe', 'quitta', 'Groupe Test', 'info', '/utilisateur/conversation/projet/29', b'1', 6),
(223, b'0', 'mark09y.gif', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(224, b'0', 'mark09y.gif', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(225, b'0', 'mark09y.gif', 'Nouveau message de groupe', 'mark09y', 'Groupe Test', 'info', '/utilisateur/conversation/projet/29', b'1', 1),
(226, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(227, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(228, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(229, b'0', 'mark09y.gif', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(230, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 1),
(231, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'0', 3),
(232, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 1),
(233, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 1),
(234, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'0', 4),
(235, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 1),
(236, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 1),
(237, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 1),
(238, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 1),
(239, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 1),
(240, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 1),
(241, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 1),
(242, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 1),
(243, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 1),
(244, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 1),
(245, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'0', 3),
(246, b'0', 'mark09y.gif', 'Nouvelle mention jaime de mark09y', 'mark09y', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'0', 4),
(247, b'0', 'mark09y.gif', 'Nouvelle mention jaime de mark09y', 'mark09y', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'0', 3),
(248, b'0', 'mark09y.gif', 'Nouvelle mention jaime de mark09y', 'mark09y', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'0', 3),
(249, b'0', 'mark09y.gif', 'Nouvelle mention jaime de mark09y', 'mark09y', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 6),
(250, b'0', 'mark09y.gif', 'Nouvelle mention jaime de mark09y', 'mark09y', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 6),
(251, b'0', 'mark09y.gif', 'Nouvelle mention jaime de mark09y', 'mark09y', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 6),
(252, b'0', 'mark09y.gif', 'Nouvelle mention jaime de mark09y', 'mark09y', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 6),
(253, b'0', 'mark09y.gif', 'Nouvelle mention jaime de mark09y', 'mark09y', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 6),
(254, b'0', 'mark09y.gif', 'Nouvelle mention jaime de mark09y', 'mark09y', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 6),
(255, b'0', 'mark09y.gif', 'Nouvelle mention jaime de mark09y', 'mark09y', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 16),
(256, b'0', 'profil9.png', 'Nouveau post de quitta', 'quitta', 'Nouveau post', 'info', 'https://localhost:8443/feed', b'0', 13),
(257, b'0', 'profil9.png', 'Nouveau post de quitta', 'quitta', 'Nouveau post', 'info', 'https://localhost:8443/feed', b'1', 1),
(258, b'0', 'mark09y.gif', 'Nouvelle mention jaime de mark09y', 'mark09y', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 6),
(259, b'0', 'mark09y.gif', 'Nouvelle post de mark09y', 'mark09y', 'Nouveau post', 'info', 'https://localhost:8443/feed', b'0', 3),
(260, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 16),
(261, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 6),
(262, b'0', 'mark09y.gif', 'Nouveau commentaire de mark09y', 'mark09y', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 6),
(263, b'0', 'mark09y.gif', 'mark09y s\'est abonné à votre compte', 'mark09y', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y', b'1', 10),
(264, b'0', 'mark09y.gif', 'mark09y s\'est abonné à votre compte', 'mark09y', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y', b'1', 10),
(265, b'0', 'mark09y.gif', 'mark09y s\'est abonné à votre compte', 'mark09y', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y', b'1', 10),
(266, b'0', 'mark09y.gif', 'mark09y s\'est abonné à votre compte', 'mark09y', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y', b'1', 10),
(267, b'0', 'mark09y.gif', 'mark09y s\'est abonné à votre compte', 'mark09y', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y', b'1', 10),
(268, b'0', 'mark09y.gif', 'Nouvelle post de mark09y', 'mark09y', 'Nouveau post', 'info', 'https://localhost:8443/feed', b'1', 6),
(269, b'0', 'mark09y.gif', 'Nouvelle post de mark09y', 'mark09y', 'Nouveau post', 'info', 'https://localhost:8443/feed', b'0', 3),
(270, b'0', 'mark09y.gif', 'Nouvelle post de mark09y', 'mark09y', 'Nouveau post', 'info', 'https://localhost:8443/feed', b'1', 6),
(271, b'0', 'mark09y.gif', 'Nouveau post de mark09y', 'mark09y', 'Nouveau post', 'info', 'https://localhost:8443/feed', b'1', 16),
(272, b'0', 'mark09y.gif', 'Nouveau post de mark09y', 'mark09y', 'Nouveau post', 'info', 'https://localhost:8443/feed', b'1', 6),
(273, b'0', 'mark09y.gif', 'Nouveau post de mark09y', 'mark09y', 'Nouveau post', 'info', 'https://localhost:8443/feed', b'0', 4),
(274, b'0', 'mark09y.gif', 'Nouveau post de mark09y', 'mark09y', 'Nouveau post', 'info', 'https://localhost:8443/feed', b'1', 16),
(275, b'0', 'mark09y.gif', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(276, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(277, b'0', 'mark09y.gif', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(278, b'0', 'profil9.png', 'Nouveau message de quitta', 'quitta', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 1),
(279, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(280, b'1', 'mark09y.gif', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(281, b'0', 'mark09y.gif', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/11', b'1', 6),
(282, b'1', 'mark09y.gif', 'Appel de mark09y', 'mark09y', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 6),
(283, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(284, b'0', 'mark09y.gif', 'mark09y s\'est abonné à votre compte', 'mark09y', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y', b'0', 15),
(285, b'0', 'mark09y.gif', 'mark09y s\'est abonné à votre compte', 'mark09y', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y', b'0', 15),
(286, b'0', 'mark09y.gif', 'mark09y s\'est abonné à votre compte', 'mark09y', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y', b'0', 15),
(287, b'0', 'mark09y.gif', 'mark09y s\'est abonné à votre compte', 'mark09y', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y', b'0', 9),
(288, b'0', 'profil9.png', 'Nouveau commentaire de quitta', 'quitta', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 1),
(289, b'0', 'default.png', 'mark09y2 s\'est abonné à votre compte', 'mark09y2', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y2', b'1', 1),
(290, b'0', 'default.png', 'mark09y2 s\'est abonné à votre compte', 'mark09y2', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y2', b'0', 3),
(291, b'0', 'default.png', 'Nouveau commentaire de mark09y2', 'mark09y2', 'Nouveau commentaire', 'info', 'https://localhost:8443/feed', b'1', 1),
(292, b'0', 'default.png', 'Nouvelle mention jaime de mark09y2', 'mark09y2', 'Nouveau like', 'info', 'https://localhost:8443/feed', b'1', 1),
(293, b'0', 'mark09y.gif', 'mark09y s\'est abonné à votre compte', 'mark09y', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y', b'1', 30),
(294, b'0', 'mark09y.gif', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/30', b'1', 30),
(295, b'0', 'default.png', 'mark09y2 s\'est abonné à votre compte', 'mark09y2', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y2', b'1', 1),
(296, b'0', 'mark09y.gif', 'mark09y s\'est abonné à votre compte', 'mark09y', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y', b'1', 31),
(297, b'0', 'mark09y.gif', 'Nouveau message de mark09y', 'mark09y', 'Nouveau message', 'info', '/utilisateur/conversation/31', b'1', 31),
(298, b'0', 'default.png', 'Nouveau message de mark09y2', 'mark09y2', 'Nouveau message', 'info', '/utilisateur/conversation/31', b'1', 1),
(299, b'0', 'default.png', 'mark09y2 s\'est abonné à votre compte', 'mark09y2', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y2', b'1', 1),
(300, b'0', 'default.png', 'mark09y2 s\'est abonné à votre compte', 'mark09y2', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y2', b'1', 1),
(301, b'0', 'default.png', 'mark09y2 s\'est abonné à votre compte', 'mark09y2', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y2', b'0', 3),
(302, b'0', 'default.png', 'mark09y2 s\'est abonné à votre compte', 'mark09y2', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y2', b'1', 1),
(303, b'0', 'default.png', 'mark09y2 s\'est abonné à votre compte', 'mark09y2', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y2', b'1', 1),
(304, b'0', 'default.png', 'mark09y2 s\'est abonné à votre compte', 'mark09y2', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y2', b'1', 1),
(305, b'0', 'default.png', 'mark09y2 s\'est abonné à votre compte', 'mark09y2', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y2', b'1', 1),
(306, b'0', 'default.png', 'mark09y2 s\'est abonné à votre compte', 'mark09y2', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y2', b'1', 1),
(307, b'0', 'default.png', 'mark09y2 s\'est abonné à votre compte', 'mark09y2', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y2', b'1', 1),
(308, b'0', 'default.png', 'mark09y2 s\'est abonné à votre compte', 'mark09y2', 'Nouvelle abonné', 'info', '/utilisateur/profil/mark09y2', b'0', 3),
(309, b'1', 'profil9.png', 'Appel de quitta', 'quitta', 'Appel...', 'info', '/utilisateur/conversation/11?appel=true', b'1', 1),
(310, b'0', 'mark09y.gif', 'Nouveau post de mark09y', 'mark09y', 'Nouveau post', 'info', 'https://localhost:8443/feed', b'0', 3),
(311, b'0', 'default.png', 'mark09y2 s\'est abonné à votre compte', 'mark09y2', 'Nouvel abonné', 'info', '/utilisateur/profil/mark09y2', b'1', 1),
(312, b'0', 'mark09y.gif', 'mark09y s\'est abonné à votre compte', 'mark09y', 'Nouvel abonné', 'info', '/utilisateur/profil/mark09y', b'1', 43);

-- --------------------------------------------------------

--
-- Structure de la table `portfolio`
--

CREATE TABLE `portfolio` (
  `id` bigint NOT NULL,
  `code` longtext COLLATE utf8mb4_unicode_ci,
  `id_utilisateur` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contenu de la table `portfolio`
--

INSERT INTO `portfolio` (`id`, `code`, `id_utilisateur`) VALUES
(6, 'eyJiYWNrZ3JvdW5kIjoicmdiKDE3NCwgMjE3LCAyNTUsIDAuNSkiLCJib3JkZXJDb2xvciI6InJnYig1MiwgMTQwLCAyNTUsIDAuNSkiLCJvcGFjaXR5IjoxLCJib3JkZXJXaWR0aCI6IjRweCIsIml0ZW1zIjpbeyJiYWNrZ3JvdW5kIjoicmdiYSgyNTUsIDI1NSwgMTQ3LCAwLjUpIiwiYm9yZGVyQ29sb3IiOiJyZ2IoNTIsIDE0MCwgMjU1LCAwLjUpIiwib3BhY2l0eSI6MSwiYm9yZGVyV2lkdGgiOiIzcHgiLCJjbGFzc2VzIjoiY2FzZTR4MSBjYXNlIGRyb3BBcmVhIiwiaXRlbXMiOlt7InR5cGUiOiJpbWFnZSIsInNyYyI6Imh0dHBzOi8vbG9jYWxob3N0Ojg0NDMvbWVkaWEvaW1hZ2UvdGVzdC5wbmcifV19LHsiYmFja2dyb3VuZCI6InJnYmEoMjU1LCAxNDcsIDI1NSwgMC41KSIsImJvcmRlckNvbG9yIjoicmdiKDUyLCAxNDAsIDI1NSwgMC41KSIsIm9wYWNpdHkiOjEsImJvcmRlcldpZHRoIjoiM3B4IiwiY2xhc3NlcyI6ImNhc2UyeDIgY2FzZSBkcm9wQXJlYSIsIml0ZW1zIjpbeyJ0eXBlIjoiaW1hZ2UiLCJzcmMiOiJodHRwczovL2xvY2FsaG9zdDo4NDQzL21lZGlhL2ltYWdlL2NhdC5wbmcifSx7InR5cGUiOiJ0ZXh0IiwiZm9udEZhbWlseSI6IlwiQ29taXMgU2FucyBNU1wiIiwidGV4dEFsaWduIjoiIiwiY29sb3IiOiIiLCJmb250U2l6ZSI6IiIsInRleHRDb250ZW50IjoiQWxsbyJ9XX0seyJiYWNrZ3JvdW5kIjoicmdiYSgxNDcsIDI1NSwgMjU1LCAwLjUpIiwiYm9yZGVyQ29sb3IiOiJyZ2IoNTIsIDE0MCwgMjU1LCAwLjUpIiwib3BhY2l0eSI6MSwiYm9yZGVyV2lkdGgiOiIzcHgiLCJjbGFzc2VzIjoiY2FzZTF4MiBjYXNlIGRyb3BBcmVhIiwiaXRlbXMiOlt7InR5cGUiOiJpbWFnZSIsInNyYyI6Imh0dHBzOi8vbG9jYWxob3N0Ojg0NDMvbWVkaWEvaW1hZ2UvdGVzdC5wbmcifV19XX0=', 1);

-- --------------------------------------------------------

--
-- Structure de la table `post`
--

CREATE TABLE `post` (
  `id` bigint NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  `nb_likes` int NOT NULL,
  `nb_partages` int NOT NULL,
  `nb_vues` int NOT NULL,
  `profil_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pseudo_utilisateur` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `publique` bit(1) NOT NULL,
  `texte` longtext COLLATE utf8mb4_unicode_ci,
  `titre` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `utilisateur_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contenu de la table `post`
--

INSERT INTO `post` (`id`, `date`, `nb_likes`, `nb_partages`, `nb_vues`, `profil_url`, `pseudo_utilisateur`, `publique`, `texte`, `titre`, `type`, `utilisateur_id`) VALUES
(1, '2022-04-22 10:34:24.000000', 9, 1, 12, NULL, 'mark09y', b'1', 'Pixel art d\'un chat mignon 😍😍😍 #Art #PixelArt #cat #cutenessoverload', 'Pixel Cat', 'Pixel Art', 1),
(3, '2015-01-10 17:30:05.000000', -1, 0, 2, NULL, 'test', b'1', 'Peinture faite sur huile d\'une femme contemplant la beauté du monde qui nous entoure en sirotant une tasse de cappuccino dans son café préférée. Inspiré de mon voyage en Italie☕🛫#Art #Oil #cofee #cozy', 'Un café à rome', 'Art Traditionnel', 3),
(4, '2024-01-20 17:30:05.000000', 0, 0, 10, NULL, 'aqualuna', b'1', 'Un voyage vers le passée pour tout ceux qui sont nostalgique ⚡⚡⚡', 'PIKA PIKA', 'Animation', 4),
(5, '2024-01-19 17:30:05.000000', 20, 6, 34, NULL, 'quitta', b'1', 'Une époppée vers l\'espace, mon prochain jeu est en cours de développement!!!', 'No sky for man', 'Animation', 6),
(6, '2023-10-19 17:30:05.000000', 16, 4, 25, NULL, 'mark09y', b'1', 'Un soir, un bord de fenêtre, qu\'est ce qui je pourrait demander de plus', 'City of Stars', 'Pixel Art', 1),
(7, '2022-10-19 17:30:05.000000', 0, 3, 12, NULL, 'mark09y', b'1', 'Laisser cour a son esprit 👁️', 'Explosion de couleurs', 'Art Traditionnel', 1),
(9, '2018-01-10 17:30:05.000000', 10, 3, 2, NULL, 'test', b'1', 'Il est beau hein?👹👹', 'Nouveau modèle', 'Animation', 3),
(10, '2019-04-10 12:30:05.000000', 0, 2, 3, NULL, 'aqualuna', b'1', 'Vous pensez quoi de ce modèle ?', 'Tutoriel Blender', 'Animation', 4),
(13, '2024-03-25 21:50:36.000000', 0, 0, 0, NULL, 'aqualuna', b'1', 'Tellement contente d\'avoir pu travailler avec ces deux excellents artistes, merci!!🙇‍♀️🙇‍♂️🙇‍♀️🙇‍♂️', 'Ma Première pochette d\'album!!!!', 'Pixel art', 4),
(20, '2024-03-25 22:27:34.000000', 0, 0, 0, NULL, 'quitta', b'1', 'Une brume d\'été', 'Blossom', 'Art traditionnel', 6),
(23, '2024-03-25 22:56:39.000000', 2, 0, 0, NULL, 'quitta', b'1', 'BOOOOMMM', 'Nitro Force', 'Banniere', 6),
(24, '2024-03-25 23:00:13.000000', 0, 0, 0, NULL, 'test', b'1', 'I love nature', 'Beautiful eveniing ', 'Banniere', 3),
(25, '2024-03-26 15:33:54.000000', 0, 0, 0, NULL, 'gwuliano', b'1', 'dd', 'Nitro Force', 'Banniere', 11),
(27, '2024-03-26 22:38:37.000000', 1, 0, 0, NULL, 'aqualuna', b'1', '🎌 Cours, Culture et Aventure 🗺️ J’ai plongé dans la culture japonaise, suivi des cours passionnants et arpenté les ruelles animées de Tokyo. 📚🎨🏯\n 🍣 Sushis, Temples et Amitiés 🍜 J’ai savouré des sushis délicieux, médité dans des temples millénaires et tissé des liens avec des étudiants venus du monde entier. 🍣⛩️👫\n 📸 Capturez Chaque Instant 📷 J’ai immortalisé ces moments magiques avec mon appareil photo ! 🌸✨\n  Prêt(e) à embarquer pour cette aventure extraordinaire ? 🌟🎒', 'Un an au Pays du Soleil Levant ! 🌸', 'Banniere', 4),
(36, '2024-04-13 21:38:52.875680', 0, 0, 0, NULL, 'mark09y', b'1', 'Test', 'titre', 'texte', 1),
(37, '2024-04-17 03:31:34.718016', 0, 0, 0, NULL, 'mark09y', b'1', 'Test', 'titre', 'texte', 1),
(39, '2024-04-18 15:49:41.129184', 0, 0, 0, NULL, 'mark09y', b'1', 'Test Test', 'titre', 'texte', 1),
(40, '2024-04-22 23:54:00.119176', 0, 0, 0, NULL, 'assd', b'1', 'ceci est un test', 'titre', 'texte', 13),
(41, '2024-04-23 00:13:12.217600', 0, 0, 0, NULL, 'assd', b'1', 'ASASA', 'titre', 'texte', 13),
(43, '2024-04-26 14:05:56.119232', 0, 0, 0, NULL, 'mark09y', b'1', 'test', 'titre', 'texte', 1),
(46, '2024-04-26 14:28:35.420364', 0, 0, 0, NULL, 'mark09y', b'0', 'banniere', 'titre', 'Banniere', 1),
(49, '2024-04-30 14:57:54.966767', 1, 0, 0, NULL, 'ali_baba', b'0', 'banniere', 'titre', 'Banniere', 16),
(50, '2024-04-30 14:59:20.821993', 1, 0, 0, NULL, 'ali_baba', b'1', 'Abstract illustration', 'titre', 'texte', 16),
(51, '2024-04-30 15:00:46.159331', 1, 0, 0, NULL, 'ali_baba', b'1', 'Cyberpunk theme illustration', 'titre', 'texte', 16),
(52, '2024-04-30 15:03:16.627558', 1, 0, 0, NULL, 'ali_baba', b'1', 'Pixel art illustration', 'titre', 'texte', 16),
(53, '2024-04-30 15:04:35.457752', 2, 0, 0, NULL, 'ali_baba', b'1', 'Pixel art', 'titre', 'texte', 16),
(54, '2024-05-03 21:34:06.166228', 0, 0, 0, NULL, 'mark09y', b'0', 'ttt', 'titre', 'texte', 1),
(55, '2024-05-03 21:38:14.828323', 2, 0, 0, NULL, 'mark09y', b'0', 'tesr', 'titre', 'texte', 1),
(56, '2024-05-05 00:20:23.296635', 1, 0, 0, NULL, 'mark09y', b'0', 'Test', 'Test', 'Test', 1),
(57, '2024-05-05 00:26:22.917003', 2, 0, 0, NULL, 'mark09y', b'0', 'wrer', 'erew', 'werr', 1),
(58, '2024-05-09 00:49:04.898584', 0, 0, 0, NULL, 'mark09y', b'0', 'Test', 'Omoi', 'test', 1),
(59, '2024-05-16 21:28:17.164199', 0, 0, 0, NULL, 'mark09y', b'0', 'Petite animation pour un projet en android', 'Projet native', 'image', 1),
(61, '2024-05-16 21:31:11.406949', 1, 0, 0, NULL, 'quitta', b'0', 'Test d\'un post vidéo', 'Test video', 'video', 6),
(62, '2024-05-16 22:52:32.618734', 1, 0, 0, NULL, 'mark09y', b'1', 'Test char spécial', 'Test char spécial', 'image', 1);

-- --------------------------------------------------------

--
-- Structure de la table `projet`
--

CREATE TABLE `projet` (
  `id` bigint NOT NULL,
  `date_creation` datetime(6) DEFAULT NULL,
  `date_modification` datetime(6) DEFAULT NULL,
  `date_suppression` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nb_membre` int NOT NULL,
  `projet_photo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `publique` bit(1) NOT NULL,
  `titre` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `admin_user_id` bigint DEFAULT NULL,
  `conversation_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contenu de la table `projet`
--

INSERT INTO `projet` (`id`, `date_creation`, `date_modification`, `date_suppression`, `description`, `nb_membre`, `projet_photo`, `publique`, `titre`, `admin_user_id`, `conversation_id`) VALUES
(6, '2024-05-10 01:20:10.339262', '2024-05-10 01:20:10.339262', NULL, 'Test', 0, 'flower.jpg', b'1', 'Test', 1, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `projet_fichiers`
--

CREATE TABLE `projet_fichiers` (
  `projet_id` bigint NOT NULL,
  `fichiers_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `projet_utilisateur`
--

CREATE TABLE `projet_utilisateur` (
  `projet_id` bigint NOT NULL,
  `utilisateur_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contenu de la table `projet_utilisateur`
--

INSERT INTO `projet_utilisateur` (`projet_id`, `utilisateur_id`) VALUES
(6, 1),
(6, 6),
(6, 10);

-- --------------------------------------------------------

--
-- Structure de la table `tache`
--

CREATE TABLE `tache` (
  `id` bigint NOT NULL,
  `date_creation` datetime(6) DEFAULT NULL,
  `date_modification` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `etat` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `titre` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `projet_id` bigint DEFAULT NULL,
  `utilisateur_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id` bigint NOT NULL,
  `email` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` bit(1) NOT NULL,
  `nom` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `photo_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `prenom` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pseudo` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `specialisation` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `statut` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `utilisateur_id` bigint DEFAULT NULL,
  `background_color` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `background_texture` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contenu de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `email`, `is_active`, `nom`, `password`, `photo_url`, `prenom`, `pseudo`, `specialisation`, `statut`, `utilisateur_id`, `background_color`, `background_texture`, `role_name`) VALUES
(1, 'mmm@nn.ca', b'1', 'Jacques', '$2a$10$xPd7d6zeBFPTmiM0RCtdiuog3UT5DhNRZC2TIJ9yx2PWvW8agE2D6', 'mark09y.gif', 'Nicholson', 'mark09y', 'Traditionnel', 'offline', NULL, 'night-2', 'texture8', 'ROLE_ADMIN'),
(3, 'SS@HH.ca', b'1', 'bob', '$2a$10$Z.ehxzafXuXEHUrSkBDR/etLi9F0WSsQ7ejLt6QOKwStJc2t0wWES', 'oil.png', 'we', 'test', 'Modélisation 3D', 'offline', NULL, NULL, NULL, 'ROLE_USER'),
(4, 'nich02@jjj.com', b'1', 'Tally', '$2a$10$EiP8QD3oAX3VWUVLioKX8OCB7dNdSOdMy2m6xoxPvkU6/8F7Kh9x6', 'marianne.jpg', 'Marianna', 'aqualuna', 'Modélisation 3D', 'offline', NULL, 'light-3', 'texture12', 'ROLE_USER'),
(6, 'nich0@jjj.com', b'1', 'Goud', '$2a$10$hjZREgI04/eWaDeMm5K5z.Xwd2xCRLhoJ97wKYrddTos1KyrJPpPu', 'profil9.png', 'Test2', 'quitta', 'Animation', 'offline', NULL, 'light-5', 'texture9', 'ROLE_USER'),
(9, 'jaddiss2001@hotmail.com', b'1', 'Jacques', '$2a$10$/YiZ5GMUVBsu1/N.uJN3OuOomCeW/7XV4460PLc3zp7gQVZUGaojW', 'default.png', 'Nicholson', 'nichojay', 'Traditionnel', 'offline', NULL, NULL, NULL, 'ROLE_USER'),
(10, 'London@england.com', b'1', 'Ben', '$2a$10$6YaAP24qG.mbz7SR/xNy6uo9/sgmVsdcy/hHqYyVh/jpmsCzOqjw2', 'bigben.jpg', 'Big', 'bigbadben', 'Traditionnel', 'offline', NULL, NULL, NULL, 'ROLE_USER'),
(11, 'jamilfayad@hotmail.ca', b'1', 'Fayad', '$2a$10$PzkBfBuFrjd4boqd1fjMJenwUqvLqpf6j1QxX6zTpPD2eGrfNDDg2', 'onePunch.jpg', 'Jamil', 'gwuliano', 'Modélisation 3D', 'offline', NULL, NULL, NULL, 'ROLE_USER'),
(13, 'asdsad@g.com', b'1', 'aaa', '$2a$10$dSgMPlXV8QvAH41BAlUo3uFNuDuOzKK1obYPIy4fz64v1weSN/rSW', 'default.png', 'sasas', 'assd', 'Modélisation 3D', 'offline', NULL, NULL, NULL, 'ROLE_USER'),
(15, 'tedekeu@outlook.fr', b'1', 'Teddy', '$2a$10$NRDNrms7U9CfMYyFL8.y.OczfVh3RocH8xx7ywTXeg0BVA2HAvzZq', 'default.png', 'Cabrel', 'Teddy02', 'Animation', 'offline', NULL, NULL, NULL, 'ROLE_USER'),
(16, 'ali@gmail.com', b'1', 'Benkarrouch', '$2a$10$REyt2QMqraKBaobHTm9iHuEu0Xi44Mm1cxAnmnewD2EGBanbZGVgO', 'cyberpfp.gif', 'Ali', 'ali_baba', 'Numérique', 'offline', NULL, NULL, NULL, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateurs_amis`
--

CREATE TABLE `utilisateurs_amis` (
  `id` bigint NOT NULL,
  `utilisateur_un_id` bigint NOT NULL,
  `utilisateur_deux_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contenu de la table `utilisateurs_amis`
--

INSERT INTO `utilisateurs_amis` (`id`, `utilisateur_un_id`, `utilisateur_deux_id`) VALUES
(3, 6, 1),
(9, 6, 13),
(10, 13, 6),
(14, 3, 1),
(20, 11, 1),
(22, 3, 6),
(23, 6, 3),
(26, 1, 4),
(27, 1, 6),
(28, 1, 3),
(30, 1, 11),
(31, 4, 1);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateurs_relation`
--

CREATE TABLE `utilisateurs_relation` (
  `id` bigint NOT NULL,
  `utilisateur_deux_id` bigint NOT NULL,
  `utilisateur_un_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Contenu de la table `utilisateurs_relation`
--

INSERT INTO `utilisateurs_relation` (`id`, `utilisateur_deux_id`, `utilisateur_un_id`) VALUES
(29, 6, 13),
(30, 6, 13),
(33, 13, 6),
(34, 13, 6),
(37, 3, 6),
(38, 3, 6),
(63, 6, 11),
(64, 6, 11),
(71, 6, 3),
(72, 6, 3),
(79, 1, 6),
(80, 1, 3),
(82, 1, 11),
(83, 1, 16),
(85, 6, 1),
(86, 3, 1),
(88, 11, 1),
(89, 16, 1),
(91, 4, 1),
(93, 1, 4),
(108, 1, 10),
(109, 1, 10);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `annonce`
--
ALTER TABLE `annonce`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK33rkd0o8qelj48ho4o1h6ix0r` (`projet_id`),
  ADD KEY `FK7189vsun3jgblxqkayt825j95` (`utilisateur_id`);

--
-- Index pour la table `app_setting`
--
ALTER TABLE `app_setting`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `chat`
--
ALTER TABLE `chat`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgi1b8sl01jboyasp8260gk8pm` (`conversation_id`),
  ADD KEY `FK83pgexk315s9yq38gf4g40e3i` (`utilisateur_deux_id`),
  ADD KEY `FKq6a3aeushy8pb2fehcl1nco47` (`utilisateur_un_id`);

--
-- Index pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7eypuskkyk5s79u5da0f1bocy` (`forum_id`),
  ADD KEY `FK89e35c5okm3nkyeh1e00onwtj` (`post_id`),
  ADD KEY `FKfkx1pegfdsd6e3cp2wblsc5jf` (`utilisateur_id`);

--
-- Index pour la table `confirmation_token`
--
ALTER TABLE `confirmation_token`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_54wolcn5ryqshiq8gq0epvr3v` (`token`);

--
-- Index pour la table `conversation`
--
ALTER TABLE `conversation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKf8jiaex2nc48riikkrn0cd6lt` (`utilisateur_deux_id`),
  ADD KEY `FKa8ecmraphc5xetep67soljmvh` (`utilisateur_un_id`),
  ADD KEY `FK6h3lm8ssma8tr5i9y3vnmfgge` (`projet_id`);

--
-- Index pour la table `demande`
--
ALTER TABLE `demande`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKb059d4y82fx7wlpgh74kucbxm` (`id_utilisateur`),
  ADD KEY `FKstowloifnt22s1twwbufu6un7` (`id_projet`);

--
-- Index pour la table `demande_admin`
--
ALTER TABLE `demande_admin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_9s8shbuhfve90a99bko84cqyx` (`user_id`);

--
-- Index pour la table `fichier_general`
--
ALTER TABLE `fichier_general`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK28u32exjpus7vy6rfe281265q` (`post_id`),
  ADD KEY `FKo0lw3mepj7iaq217bnqaaii8s` (`utilisateur_id`),
  ADD KEY `FKhsaj7tmjd3t56vmil9u08m8k0` (`fichier_id`),
  ADD KEY `FKokd9vckva1bvd1hqvwa6in20v` (`projet_id`),
  ADD KEY `FKoiopo38m120t8bn8dow62xmrp` (`forum_id`);

--
-- Index pour la table `forum`
--
ALTER TABLE `forum`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmarb4a1bynlshdlvrmvmn54hd` (`user_id`);

--
-- Index pour la table `live_stream`
--
ALTER TABLE `live_stream`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `portfolio`
--
ALTER TABLE `portfolio`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_i0633cbsl29a71afn38neyb9g` (`id_utilisateur`);

--
-- Index pour la table `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKl0aq509q4619rr44kkgeq584l` (`utilisateur_id`);

--
-- Index pour la table `projet`
--
ALTER TABLE `projet`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_a1jgpdp7i95rvlalktaa74si0` (`titre`),
  ADD UNIQUE KEY `UK_fwbjxmv0cw9fp0vqtc1rggikv` (`conversation_id`),
  ADD KEY `FKmrtq0cem6ffaytbbkif2mfdd6` (`admin_user_id`);

--
-- Index pour la table `projet_fichiers`
--
ALTER TABLE `projet_fichiers`
  ADD UNIQUE KEY `UK_1x302xknw3xhri317loenom0b` (`fichiers_id`),
  ADD KEY `FK99k9dujn6n10m7jj2yp2l54b1` (`projet_id`);

--
-- Index pour la table `projet_utilisateur`
--
ALTER TABLE `projet_utilisateur`
  ADD KEY `FK9ml8xiexc2swkh9963s0bxojm` (`utilisateur_id`),
  ADD KEY `FKb6ddtekerapq08itn65cv07u` (`projet_id`);

--
-- Index pour la table `tache`
--
ALTER TABLE `tache`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKf97vcdrmyltn9s75dgh2tlw70` (`projet_id`),
  ADD KEY `FKiykbx3c0wod3ekrusf3njj4e1` (`utilisateur_id`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_rma38wvnqfaf66vvmi57c71lo` (`email`),
  ADD UNIQUE KEY `UK_cuxn7vi5kxoby13xbh75cesdc` (`pseudo`);

--
-- Index pour la table `utilisateurs_amis`
--
ALTER TABLE `utilisateurs_amis`
  ADD PRIMARY KEY (`id`,`utilisateur_un_id`,`utilisateur_deux_id`),
  ADD UNIQUE KEY `UKfwsy2viyficib64tt4vkv59jt` (`id`),
  ADD KEY `FK739395c4vvk8b81s79iihijhk` (`utilisateur_deux_id`),
  ADD KEY `FK3jkydjsoe7b1axfbpfpmhu8pv` (`utilisateur_un_id`);

--
-- Index pour la table `utilisateurs_relation`
--
ALTER TABLE `utilisateurs_relation`
  ADD PRIMARY KEY (`id`,`utilisateur_deux_id`,`utilisateur_un_id`),
  ADD UNIQUE KEY `UKhigi3o07x4dhe3kucjsir6ete` (`id`),
  ADD KEY `FK8efw2mfa5c3ev7589oxnx3ql1` (`utilisateur_un_id`),
  ADD KEY `FK15vttpcm0wg79lhn2cy0jfn6c` (`utilisateur_deux_id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `annonce`
--
ALTER TABLE `annonce`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `app_setting`
--
ALTER TABLE `app_setting`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT pour la table `chat`
--
ALTER TABLE `chat`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=278;
--
-- AUTO_INCREMENT pour la table `commentaire`
--
ALTER TABLE `commentaire`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=65;
--
-- AUTO_INCREMENT pour la table `confirmation_token`
--
ALTER TABLE `confirmation_token`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT pour la table `conversation`
--
ALTER TABLE `conversation`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;
--
-- AUTO_INCREMENT pour la table `demande`
--
ALTER TABLE `demande`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT pour la table `demande_admin`
--
ALTER TABLE `demande_admin`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `fichier_general`
--
ALTER TABLE `fichier_general`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=80;
--
-- AUTO_INCREMENT pour la table `forum`
--
ALTER TABLE `forum`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT pour la table `live_stream`
--
ALTER TABLE `live_stream`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT pour la table `notification`
--
ALTER TABLE `notification`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=313;
--
-- AUTO_INCREMENT pour la table `portfolio`
--
ALTER TABLE `portfolio`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT pour la table `post`
--
ALTER TABLE `post`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;
--
-- AUTO_INCREMENT pour la table `projet`
--
ALTER TABLE `projet`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT pour la table `tache`
--
ALTER TABLE `tache`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;
--
-- AUTO_INCREMENT pour la table `utilisateurs_amis`
--
ALTER TABLE `utilisateurs_amis`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;
--
-- AUTO_INCREMENT pour la table `utilisateurs_relation`
--
ALTER TABLE `utilisateurs_relation`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=154;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `annonce`
--
ALTER TABLE `annonce`
  ADD CONSTRAINT `FK33rkd0o8qelj48ho4o1h6ix0r` FOREIGN KEY (`projet_id`) REFERENCES `projet` (`id`),
  ADD CONSTRAINT `FK7189vsun3jgblxqkayt825j95` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `chat`
--
ALTER TABLE `chat`
  ADD CONSTRAINT `FK83pgexk315s9yq38gf4g40e3i` FOREIGN KEY (`utilisateur_deux_id`) REFERENCES `utilisateur` (`id`),
  ADD CONSTRAINT `FKgi1b8sl01jboyasp8260gk8pm` FOREIGN KEY (`conversation_id`) REFERENCES `conversation` (`id`),
  ADD CONSTRAINT `FKq6a3aeushy8pb2fehcl1nco47` FOREIGN KEY (`utilisateur_un_id`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD CONSTRAINT `FK7eypuskkyk5s79u5da0f1bocy` FOREIGN KEY (`forum_id`) REFERENCES `forum` (`id`),
  ADD CONSTRAINT `FK89e35c5okm3nkyeh1e00onwtj` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  ADD CONSTRAINT `FKfkx1pegfdsd6e3cp2wblsc5jf` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `conversation`
--
ALTER TABLE `conversation`
  ADD CONSTRAINT `FK6h3lm8ssma8tr5i9y3vnmfgge` FOREIGN KEY (`projet_id`) REFERENCES `projet` (`id`),
  ADD CONSTRAINT `FKa8ecmraphc5xetep67soljmvh` FOREIGN KEY (`utilisateur_un_id`) REFERENCES `utilisateur` (`id`),
  ADD CONSTRAINT `FKf8jiaex2nc48riikkrn0cd6lt` FOREIGN KEY (`utilisateur_deux_id`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `demande`
--
ALTER TABLE `demande`
  ADD CONSTRAINT `FKb059d4y82fx7wlpgh74kucbxm` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id`),
  ADD CONSTRAINT `FKstowloifnt22s1twwbufu6un7` FOREIGN KEY (`id_projet`) REFERENCES `projet` (`id`);



--
-- Contraintes pour la table `fichier_general`
--
ALTER TABLE `fichier_general`
  ADD CONSTRAINT `FK28u32exjpus7vy6rfe281265q` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  ADD CONSTRAINT `FKhsaj7tmjd3t56vmil9u08m8k0` FOREIGN KEY (`fichier_id`) REFERENCES `projet` (`id`),
  ADD CONSTRAINT `FKo0lw3mepj7iaq217bnqaaii8s` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`),
  ADD CONSTRAINT `FKoiopo38m120t8bn8dow62xmrp` FOREIGN KEY (`forum_id`) REFERENCES `forum` (`id`),
  ADD CONSTRAINT `FKokd9vckva1bvd1hqvwa6in20v` FOREIGN KEY (`projet_id`) REFERENCES `projet` (`id`);

--
-- Contraintes pour la table `forum`
--
ALTER TABLE `forum`
  ADD CONSTRAINT `FKmarb4a1bynlshdlvrmvmn54hd` FOREIGN KEY (`user_id`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `portfolio`
--
ALTER TABLE `portfolio`
  ADD CONSTRAINT `FKr60yljyrml0ib5bgp7rt94hi1` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `post`
--
ALTER TABLE `post`
  ADD CONSTRAINT `FKl0aq509q4619rr44kkgeq584l` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `projet`
--
ALTER TABLE `projet`
  ADD CONSTRAINT `FKii7v1u99wvcoa2rhye4agjv1n` FOREIGN KEY (`conversation_id`) REFERENCES `conversation` (`id`),
  ADD CONSTRAINT `FKmrtq0cem6ffaytbbkif2mfdd6` FOREIGN KEY (`admin_user_id`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `projet_fichiers`
--
ALTER TABLE `projet_fichiers`
  ADD CONSTRAINT `FK3gph5u5bm4s4ycu3iy9tqycnm` FOREIGN KEY (`fichiers_id`) REFERENCES `fichier_general` (`id`),
  ADD CONSTRAINT `FK99k9dujn6n10m7jj2yp2l54b1` FOREIGN KEY (`projet_id`) REFERENCES `projet` (`id`);

--
-- Contraintes pour la table `projet_utilisateur`
--
ALTER TABLE `projet_utilisateur`
  ADD CONSTRAINT `FK9ml8xiexc2swkh9963s0bxojm` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`),
  ADD CONSTRAINT `FKb6ddtekerapq08itn65cv07u` FOREIGN KEY (`projet_id`) REFERENCES `projet` (`id`);

--
-- Contraintes pour la table `tache`
--
ALTER TABLE `tache`
  ADD CONSTRAINT `FKf97vcdrmyltn9s75dgh2tlw70` FOREIGN KEY (`projet_id`) REFERENCES `projet` (`id`),
  ADD CONSTRAINT `FKiykbx3c0wod3ekrusf3njj4e1` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `utilisateurs_amis`
--
ALTER TABLE `utilisateurs_amis`
  ADD CONSTRAINT `FK3jkydjsoe7b1axfbpfpmhu8pv` FOREIGN KEY (`utilisateur_un_id`) REFERENCES `utilisateur` (`id`),
  ADD CONSTRAINT `FK739395c4vvk8b81s79iihijhk` FOREIGN KEY (`utilisateur_deux_id`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `utilisateurs_relation`
--
ALTER TABLE `utilisateurs_relation`
  ADD CONSTRAINT `FK15vttpcm0wg79lhn2cy0jfn6c` FOREIGN KEY (`utilisateur_deux_id`) REFERENCES `utilisateur` (`id`),
  ADD CONSTRAINT `FK8efw2mfa5c3ev7589oxnx3ql1` FOREIGN KEY (`utilisateur_un_id`) REFERENCES `utilisateur` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- phpMyAdmin SQL Dump
-- version 4.2.10
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 20, 2017 at 07:24 AM
-- Server version: 5.5.38-log
-- PHP Version: 5.6.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `d3football`
--

-- --------------------------------------------------------

--
-- Table structure for table `drives`
--

CREATE TABLE `drives` (
`id` int(11) NOT NULL,
  `game_id` int(11) NOT NULL,
  `team_id` int(11) NOT NULL,
  `location` tinyint(4) NOT NULL,
  `quarter` varchar(4) NOT NULL,
  `starttime` time NOT NULL,
  `points` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `field_goals`
--

CREATE TABLE `field_goals` (
  `play_id` int(11) NOT NULL,
  `distance` tinyint(4) NOT NULL,
  `success` enum('0','1') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `games`
--

CREATE TABLE `games` (
`id` int(11) NOT NULL,
  `home_id` int(11) NOT NULL,
  `road_id` int(11) NOT NULL,
  `home_score` tinyint(3) unsigned NOT NULL,
  `road_score` tinyint(3) unsigned NOT NULL,
  `date` datetime NOT NULL,
  `gamecode` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `goforit`
--

CREATE TABLE `goforit` (
  `play_id` int(11) NOT NULL,
  `success` enum('0','1') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `plays`
--

CREATE TABLE `plays` (
`id` int(10) unsigned NOT NULL,
  `drive_id` int(11) NOT NULL,
  `playnum` tinyint(4) NOT NULL,
  `down` enum('1','2','3','4') NOT NULL,
  `distance` tinyint(4) NOT NULL,
  `location` tinyint(4) NOT NULL,
  `quarter` enum('1','2','3','4','5') NOT NULL,
  `description` varchar(255) NOT NULL,
  `result` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `punts`
--

CREATE TABLE `punts` (
  `play_id` int(11) NOT NULL,
  `distance` tinyint(4) NOT NULL,
  `net` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `teams`
--

CREATE TABLE `teams` (
`id` int(11) NOT NULL,
  `school` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `drives`
--
ALTER TABLE `drives`
 ADD PRIMARY KEY (`id`), ADD KEY `game_id` (`game_id`,`team_id`);

--
-- Indexes for table `field_goals`
--
ALTER TABLE `field_goals`
 ADD PRIMARY KEY (`play_id`);

--
-- Indexes for table `games`
--
ALTER TABLE `games`
 ADD PRIMARY KEY (`id`), ADD KEY `home_id` (`home_id`,`road_id`,`gamecode`);

--
-- Indexes for table `goforit`
--
ALTER TABLE `goforit`
 ADD PRIMARY KEY (`play_id`);

--
-- Indexes for table `plays`
--
ALTER TABLE `plays`
 ADD PRIMARY KEY (`id`), ADD KEY `drive_id` (`drive_id`);

--
-- Indexes for table `punts`
--
ALTER TABLE `punts`
 ADD PRIMARY KEY (`play_id`);

--
-- Indexes for table `teams`
--
ALTER TABLE `teams`
 ADD PRIMARY KEY (`id`), ADD KEY `school` (`school`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `drives`
--
ALTER TABLE `drives`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `games`
--
ALTER TABLE `games`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `plays`
--
ALTER TABLE `plays`
MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `teams`
--
ALTER TABLE `teams`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

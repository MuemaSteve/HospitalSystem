-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 08, 2019 at 11:17 AM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `edoc_hospitals`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointments`
--

CREATE TABLE `appointments`
(
    `id`        int(11) NOT NULL,
    `PatientId` int(11) NOT NULL,
    `doctorId`  int(11) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `appointments`
--

INSERT INTO `appointments` (`id`, `PatientId`, `doctorId`)
VALUES (1, 4, 1),
       (2, 5, 2),
       (3, 6, 5),
       (4, 6, 6),
       (5, 6, 2),
       (6, 7, 5);

-- --------------------------------------------------------

--
-- Table structure for table `conditions`
--

CREATE TABLE `conditions`
(
    `id`             int(11) NOT NULL,
    `patientemail`   text    NOT NULL,
    `conditionName`  text    NOT NULL,
    `recommendation` text    NOT NULL,
    `date`           date    NOT NULL,
    `category`       text    NOT NULL,
    `description`    text    NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

-- --------------------------------------------------------

--
-- Table structure for table `hospital`
--

CREATE TABLE `hospital`
(
    `id`       int(11) NOT NULL,
    `name`     text    NOT NULL,
    `location` text    NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `hospital`
--

INSERT INTO `hospital` (`id`, `name`, `location`)
VALUES (1, 'JKUAT', 'JUJA'),
       (2, 'JKUAT', 'KISII'),
       (3, 'JKUAT', 'NAIROBI');

-- --------------------------------------------------------

--
-- Table structure for table `patients`
--

CREATE TABLE `patients`
(
    `id`             int(11)   NOT NULL,
    `name`           text      NOT NULL,
    `email`          text      NOT NULL,
    `phonenumber`    text      NOT NULL,
    `birthdate`      date      NOT NULL,
    `identifier`     text,
    `sex`            text      NOT NULL,
    `branch`         text      NOT NULL,
    `dateregistered` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `patients`
--

INSERT INTO `patients` (`id`, `name`, `email`, `phonenumber`, `birthdate`, `identifier`, `sex`, `branch`,
                        `dateregistered`)
VALUES (1, 'steve', 'snm@gmail.com', '0702653268', '2019-06-05', NULL, 'male', 'NAIROBI', '2019-06-14 08:33:47'),
       (2, 'aphonse', 'alphonsekiprop@gmail.com', '0725616871', '1997-06-16', NULL, 'male', 'NAIROBI',
        '2019-06-14 09:53:26'),
       (3, 'cvbcb', 'dfgd@fg.fghh', 'dfgdfg', '2019-06-12', NULL, 'male', 'NAIROBI', '2019-06-14 10:01:55'),
       (4, 'aphonse', 'aphonse@mail.com', '787876382', '2019-06-04', NULL, 'male', 'NAIROBI', '2019-06-14 10:48:09'),
       (5, 'aphonse', 'aphonse@yahoo.com', '7676732682', '2019-06-04', NULL, 'male', 'NAIROBI', '2019-06-14 10:51:48'),
       (6, 'francis', 'francismburu@gmail.com', '0707613360', '1998-06-05', NULL, 'female', 'NAIROBI',
        '2019-06-17 09:15:57'),
       (7, 'awdh.s,fj.sd', 'sjH@gmail.com', 'jkdhfkjdlvlxcjv', '1998-06-05', NULL, 'male', 'NAIROBI',
        '2019-06-17 09:16:33'),
       (8, 'mnmndmfnmm', 'nbmfn@gmail.com', '9898498594', '2019-05-28', NULL, 'male', 'NAIROBI', '2019-06-17 09:17:49'),
       (9, 'brian maringa wabai', 'brianmaringa19@gmail.com', '0722589720', '1997-09-06', NULL, 'male', 'NAIROBI',
        '2019-07-08 06:15:19'),
       (10, 'brian mwangi', 'bmwangi@gmail.com', '0712345678', '1997-09-06', NULL, 'male', 'NAIROBI',
        '2019-07-08 06:16:00');

-- --------------------------------------------------------

--
-- Table structure for table `prescriptions`
--

CREATE TABLE `prescriptions`
(
    `id`             int(11)   NOT NULL,
    `patientemail`   text      NOT NULL,
    `dateprescribed` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `illness`        text      NOT NULL,
    `prescription`   text      NOT NULL,
    `recommendation` text      NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

-- --------------------------------------------------------

--
-- Table structure for table `specialdisease`
--

CREATE TABLE `specialdisease`
(
    `id`             int(11) NOT NULL,
    `type`           text    NOT NULL,
    `prescription`   text    NOT NULL,
    `recommendation` text    NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users`
(
    `id`                    int(11)  NOT NULL,
    `name`                  text     NOT NULL,
    `email`                 text     NOT NULL,
    `password`              text     NOT NULL,
    `hospital`              text     NOT NULL,
    `status`                text     NOT NULL,
    `userclearancelevel`    text     NOT NULL,
    `certification`         longblob NOT NULL,
    `identification`        text     NOT NULL,
    `description`           text     NOT NULL,
    `numberoofappointments` int(11)  NOT NULL DEFAULT '0'
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `hospital`, `status`, `userclearancelevel`, `certification`,
                     `identification`, `description`, `numberoofappointments`)
VALUES (1, 'steve', 'muemasnyamai@gmail.com', 'steve', 'JKUAT', 'active', 'admin', '', '', '', 1),
       (2, 'snm', 'snm@gmail.com', 'snm@gmail.com', 'NAIROBI', 'active', 'DOCTOR', '', '12121212121', 'qwertyuiop', 2),
       (3, 'snm', 'snm1@gmail.com', 'snm1@gmail.com', 'NAIROBI', 'active', 'ADMIN', '', '12121212121', 'qwertyuiop', 0),
       (4, 'snm', 'snm2@gmail.com', 'snm2@gmail.com', 'NAIROBI', 'active', 'RECEPTIONIST', '', '12121212121',
        'qwertyuiop', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointments`
--
ALTER TABLE `appointments`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `conditions`
--
ALTER TABLE `conditions`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hospital`
--
ALTER TABLE `hospital`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `patients`
--
ALTER TABLE `patients`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `identifier number` (`identifier`(11));

--
-- Indexes for table `prescriptions`
--
ALTER TABLE `prescriptions`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `specialdisease`
--
ALTER TABLE `specialdisease`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
    ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointments`
--
ALTER TABLE `appointments`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 7;

--
-- AUTO_INCREMENT for table `conditions`
--
ALTER TABLE `conditions`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hospital`
--
ALTER TABLE `hospital`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 4;

--
-- AUTO_INCREMENT for table `patients`
--
ALTER TABLE `patients`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 11;

--
-- AUTO_INCREMENT for table `prescriptions`
--
ALTER TABLE `prescriptions`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `specialdisease`
--
ALTER TABLE `specialdisease`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;

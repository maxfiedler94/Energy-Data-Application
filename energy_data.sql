-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 14, 2017 at 03:57 PM
-- Server version: 10.1.26-MariaDB
-- PHP Version: 7.1.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `energy_data`
--

-- --------------------------------------------------------

--
-- Table structure for table `energy_aspect_electricity`
--

CREATE TABLE `energy_aspect_electricity` (
  `electricity_id` int(11) NOT NULL,
  `zone` varchar(64) NOT NULL,
  `level_1_consumer` varchar(64) NOT NULL,
  `level_2_consumer` varchar(64) NOT NULL,
  `level_3_consumer` varchar(64) NOT NULL,
  `input_date` date DEFAULT NULL,
  `connected_installed_power` double NOT NULL,
  `utilization_of_consumer` double NOT NULL,
  `anually_hours` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `energy_aspect_electricity`
--

INSERT INTO `energy_aspect_electricity` (`electricity_id`, `zone`, `level_1_consumer`, `level_2_consumer`, `level_3_consumer`, `input_date`, `connected_installed_power`, `utilization_of_consumer`, `anually_hours`) VALUES
(42, 'Production', 'Cooler', 'Steamer', 'xxxxxx', '2017-12-14', 3.5, 2.2, 22),
(43, 'Production', 'Cooler', 'Converter', 'PLC', '2017-12-14', 55.5, 33.1, 50),
(44, 'Production', 'Steamer', 'xxxxxx', 'Converter', '2017-12-18', 3.2, 1.12, 5),
(45, 'Processing', 'xxxxxx', 'xxxxxx', 'xxxxxx', '2017-12-12', 12.11, 44.2, 66),
(46, 'Processing', 'xxxxxx', 'xxxxxx', 'xxxxxx', '2017-12-13', 5.51, 2.22, 1),
(47, 'Processing', 'xxxxxx', 'xxxxxx', 'xxxxxx', '2017-12-05', 5.5, 1, 33);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `energy_aspect_electricity`
--
ALTER TABLE `energy_aspect_electricity`
  ADD PRIMARY KEY (`electricity_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `energy_aspect_electricity`
--
ALTER TABLE `energy_aspect_electricity`
  MODIFY `electricity_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

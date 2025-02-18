-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-01-2025 a las 14:01:48
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyectomvc`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `coches`
--

CREATE TABLE `coches` (
  `id` int(11) NOT NULL,
  `marca` varchar(50) NOT NULL,
  `modelo` varchar(50) NOT NULL,
  `matricula` varchar(7) NOT NULL,
  `anoMatriculacion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `coches`
--

INSERT INTO `coches` (`id`, `marca`, `modelo`, `matricula`, `anoMatriculacion`) VALUES
(1, 'Toyota', 'Corolla', '1234ABC', 2015),
(2, 'Ford', 'Focus', '2345BCD', 2018),
(3, 'Volkswagen', 'Golf', '3456CDE', 2017),
(4, 'Renault', 'Clio', '4567DEF', 2019),
(5, 'Honda', 'Civic', '5678EFG', 2016),
(6, 'Hyundai', 'Elantra', '6789FGH', 2020),
(7, 'Mazda', '3', '7890GHI', 2021),
(8, 'Chevrolet', 'Malibu', '8901HIJ', 2014),
(9, 'Nissan', 'Sentra', '9012IJK', 2013),
(10, 'BMW', 'Serie 3', '0123JKL', 2022),
(11, 'Audi', 'A3', '1243LMN', 2019),
(12, 'Toyota', 'Corolla', '2354NOP', 2015),
(13, 'Ford', 'Fiesta', '3465PQR', 2018),
(14, 'Peugeot', '308', '4576RST', 2020),
(15, 'Honda', 'Accord', '5687TUV', 2017),
(16, 'Mercedes', 'Clase C', '6798WXY', 2021),
(17, 'Kia', 'Sportage', '7899XYZ', 2016),
(18, 'Chevrolet', 'Malibu', '8910ABC', 2014),
(19, 'Renault', 'Megane', '9021BCD', 2015),
(20, 'Volkswagen', 'Passat', '0132CDE', 2018),
(21, 'Toyota', 'Yaris', '1244DEF', 2017),
(22, 'Ford', 'Focus', '2355EFG', 2018),
(23, 'Volkswagen', 'Golf', '3466FGH', 2017),
(24, 'Audi', 'Q5', '4577GHI', 2019),
(25, 'Honda', 'Civic', '5688HIJ', 2016),
(26, 'Hyundai', 'Tucson', '6799IJK', 2020),
(27, 'Mazda', 'CX-5', '7800JKL', 2021),
(28, 'Fiat', '500', '8911KLM', 2013),
(29, 'Nissan', 'Juke', '9022LMN', 2018),
(30, 'BMW', 'Serie 5', '0133MNO', 2022),
(31, 'Toyota', 'Corolla', '1245NOP', 2015),
(32, 'Ford', 'Focus', '2356PQR', 2018),
(33, 'Volkswagen', 'Golf', '3467RST', 2017),
(34, 'Peugeot', '208', '4578TUV', 2019),
(35, 'Honda', 'CR-V', '5689WXY', 2015),
(36, 'Mercedes', 'Clase E', '6700XYZ', 2020),
(37, 'Kia', 'Optima', '7801ABC', 2021),
(38, 'Chevrolet', 'Spark', '8912BCD', 2014),
(39, 'Nissan', 'Altima', '9023CDE', 2016),
(40, 'Audi', 'A4', '0134DEF', 2019),
(41, 'Toyota', 'Hilux', '1246EFG', 2015),
(42, 'Ford', 'Explorer', '2357FGH', 2018),
(43, 'Volkswagen', 'Jetta', '3468GHI', 2019),
(44, 'Renault', 'Captur', '4579HIJ', 2021),
(45, 'Honda', 'Jazz', '5690IJK', 2018),
(46, 'Hyundai', 'Santa Fe', '6701JKL', 2020),
(47, 'Mazda', '6', '7802KLM', 2017),
(48, 'Fiat', 'Punto', '8913LMN', 2014),
(49, 'Nissan', 'Qashqai', '9024MNO', 2015),
(50, 'BMW', 'X1', '0135NOP', 2022),
(51, 'Toyota', 'RAV4', '1247OPQ', 2015),
(52, 'Ford', 'Mustang', '2358PQR', 2018),
(53, 'Volkswagen', 'Tiguan', '3469RST', 2021),
(54, 'Peugeot', '2008', '4580TUV', 2020),
(55, 'Honda', 'HR-V', '5691WXY', 2019),
(56, 'Mercedes', 'GLA', '6702XYZ', 2022),
(57, 'Kia', 'Ceed', '7803ABC', 2016),
(58, 'Chevrolet', 'Cruze', '8914BCD', 2014),
(59, 'Nissan', 'Leaf', '9025CDE', 2017),
(60, 'Audi', 'Q7', '0136DEF', 2019),
(61, 'Toyota', 'Camry', '1248EFG', 2018),
(62, 'Ford', 'Edge', '2359FGH', 2019),
(63, 'Volkswagen', 'Polo', '3470GHI', 2017),
(64, 'Renault', 'Koleos', '4581HIJ', 2020),
(65, 'Honda', 'City', '5692IJK', 2016),
(66, 'Hyundai', 'i30', '6703JKL', 2018),
(67, 'Mazda', 'MX-5', '7804KLM', 2019),
(68, 'Fiat', 'Tipo', '8915LMN', 2021),
(69, 'Nissan', 'Murano', '9026MNO', 2015),
(70, 'BMW', 'Serie 7', '0137NOP', 2022),
(71, 'Toyota', 'Avalon', '1249OPQ', 2018),
(72, 'Ford', 'Ranger', '2360PQR', 2020),
(73, 'Volkswagen', 'Scirocco', '3471RST', 2016),
(74, 'Peugeot', '508', '4582TUV', 2021),
(75, 'Honda', 'Odyssey', '5693WXY', 2017),
(76, 'Mercedes', 'GLC', '6704XYZ', 2021),
(77, 'Kia', 'Stinger', '7805ABC', 2018),
(78, 'Chevrolet', 'Camaro', '8916BCD', 2016),
(79, 'Nissan', 'Pathfinder', '9027CDE', 2019),
(80, 'Audi', 'TT', '0138DEF', 2021),
(81, 'Toyota', 'Prius', '1250EFG', 2015),
(82, 'Ford', 'Bronco', '2361FGH', 2017),
(83, 'Volkswagen', 'Arteon', '3472GHI', 2021),
(84, 'Renault', 'Fluence', '4583HIJ', 2022),
(85, 'Honda', 'Fit', '5694IJK', 2019),
(86, 'Hyundai', 'Kona', '6705JKL', 2016),
(87, 'Mazda', 'CX-3', '7806KLM', 2020),
(89, 'Nissan', 'Rogue', '9028MNO', 2018),
(90, 'BMW', 'Z4', '0139NOP', 2020),
(91, 'Toyota', 'Land Cruiser', '1251OPQ', 2022),
(92, 'Ford', 'Transit', '2362PQR', 2018),
(93, 'Volkswagen', 'Touareg', '3473RST', 2020),
(94, 'Peugeot', '3008', '4584TUV', 2017),
(95, 'Honda', 'Passport', '5695WXY', 2021),
(96, 'Mercedes', 'GLS', '6706XYZ', 2018),
(97, 'Kia', 'Niro', '7807ABC', 2017),
(98, 'Chevrolet', 'Traverse', '8918BCD', 2019),
(99, 'Nissan', 'Maxima', '9029CDE', 2015),
(100, 'Audi', 'A5', '0140DEF', 2022),
(101, 'Volkswagen', 'Golf', '1234LJL', 2014),
(104, 'Ford', 'Focus', '1475ASD', 2016);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historialpropietarios`
--

CREATE TABLE `historialpropietarios` (
  `id` int(11) NOT NULL,
  `idCoche` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `fechaCambio` date NOT NULL,
  `fechaFin` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `historialpropietarios`
--

INSERT INTO `historialpropietarios` (`id`, `idCoche`, `idPersona`, `fechaCambio`, `fechaFin`) VALUES
(1, 4, 5, '2024-11-09', NULL),
(2, 9, 9, '2024-11-09', '2024-11-12'),
(3, 14, 16, '2024-11-10', NULL),
(4, 5, 6, '2024-11-10', '2024-11-12'),
(5, 11, 33, '2024-11-11', NULL),
(6, 8, 44, '2024-11-12', '2024-11-12'),
(7, 7, 1, '2024-11-12', '2024-11-13'),
(8, 8, 1, '2024-11-12', NULL),
(9, 9, 30, '2024-11-12', NULL),
(10, 8, 8, '2024-11-12', '2024-11-12'),
(11, 5, 1, '2024-11-12', '2024-11-12'),
(12, 9, 13, '2024-11-12', NULL),
(13, 4, 36, '2024-11-12', '2024-11-12'),
(14, 8, 4, '2024-11-12', NULL),
(15, 11, 3, '2024-11-12', '2024-11-12'),
(16, 13, 11, '2024-11-13', '2024-11-13'),
(17, 6, 45, '2024-11-13', '2024-11-13'),
(18, 3, 51, '2024-11-13', NULL),
(19, 8, 13, '2024-11-14', NULL),
(20, 5, 16, '2024-11-14', '2024-11-14'),
(21, 12, 8, '2024-12-17', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas`
--

CREATE TABLE `personas` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `sexo` int(11) DEFAULT NULL,
  `dni` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `personas`
--

INSERT INTO `personas` (`id`, `nombre`, `sexo`, `dni`) VALUES
(1, 'Juan Pérez', 1, '12345678A'),
(2, 'María López', 2, '23456789B'),
(3, 'Carlos Gómez', 1, '34567890C'),
(4, 'Ana Martínez', 2, '45678901D'),
(5, 'Luis Fernández', 1, '56789012E'),
(6, 'Laura Sánchez', 2, '67890123F'),
(7, 'Jorge Ruiz', 1, '78901234G'),
(8, 'Beatriz Torres', 2, '89012345H'),
(9, 'Pedro Ramírez', 1, '90123456J'),
(10, 'Lucía Herrera', 2, '01234567K'),
(11, 'Diego Alonso', 1, '11234567L'),
(12, 'Elena Núñez', 2, '22345678M'),
(13, 'Andrés Morales', 1, '33456789N'),
(14, 'Rocío Castro', 2, '44567890O'),
(15, 'Manuel Díaz', 1, '55678901P'),
(16, 'Clara Gil', 2, '66789012Q'),
(17, 'Francisco Gómez', 1, '77890123R'),
(18, 'Isabel Vargas', 2, '88901234S'),
(19, 'Héctor Ramos', 1, '99012345T'),
(20, 'Sofía Medina', 2, '00123456U'),
(21, 'Fernando Vega', 1, '11234567V'),
(22, 'Marta Ortiz', 2, '22345678W'),
(23, 'Alberto Soto', 1, '33456789X'),
(24, 'Verónica Robles', 2, '44567890Y'),
(25, 'Pablo Lozano', 1, '55678901Z'),
(27, 'Raúl Campos', 1, '77890123B'),
(28, 'Patricia Jiménez', 2, '88901234C'),
(29, 'Rubén Iglesias', 1, '99012345D'),
(30, 'Carla Guerra', 2, '00123456E'),
(31, 'Javier Escobar', 1, '11234567F'),
(32, 'Eva Márquez', 2, '22345678G'),
(33, 'Cristian Iglesias', 1, '33456789H'),
(34, 'Luciana Paredes', 2, '44567890I'),
(35, 'Miguel Peña', 1, '55678901J'),
(36, 'Alejandra Parra', 2, '66789012K'),
(37, 'Sergio Valdez', 1, '77890123L'),
(38, 'Natalia Roldán', 2, '88901234M'),
(39, 'Guillermo Benítez', 1, '99012345N'),
(40, 'Sara Arroyo', 2, '00123456O'),
(41, 'Tomás Herrera', 1, '11234567P'),
(42, 'Irene Salinas', 2, '22345678Q'),
(43, 'Óscar Vidal', 1, '33456789R'),
(44, 'Alicia Morales', 2, '44567890S'),
(45, 'Adrián Ferrer', 1, '55678901T'),
(46, 'Gabriela Cabrera', 2, '66789012U'),
(47, 'Gustavo Estrada', 1, '77890123V'),
(49, 'Félix Moreno', 1, '99012345X'),
(50, 'Amalia Silva', 2, '00123456Y'),
(51, 'Laura Juncos', 2, '49429523Q'),
(54, 'Carlos', 1, '12345678G');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `coches`
--
ALTER TABLE `coches`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `historialpropietarios`
--
ALTER TABLE `historialpropietarios`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idCoche` (`idCoche`),
  ADD KEY `idPersona` (`idPersona`);

--
-- Indices de la tabla `personas`
--
ALTER TABLE `personas`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `coches`
--
ALTER TABLE `coches`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;

--
-- AUTO_INCREMENT de la tabla `historialpropietarios`
--
ALTER TABLE `historialpropietarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `personas`
--
ALTER TABLE `personas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `historialpropietarios`
--
ALTER TABLE `historialpropietarios`
  ADD CONSTRAINT `historialpropietarios_ibfk_1` FOREIGN KEY (`idCoche`) REFERENCES `coches` (`id`),
  ADD CONSTRAINT `historialpropietarios_ibfk_2` FOREIGN KEY (`idPersona`) REFERENCES `personas` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

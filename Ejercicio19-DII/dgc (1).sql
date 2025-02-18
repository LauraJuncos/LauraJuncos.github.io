-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-01-2025 a las 21:33:08
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
-- Base de datos: `dgc`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alegacion`
--

CREATE TABLE `alegacion` (
  `Id_Alegacion` int(11) NOT NULL,
  `Fecha_Presentacion` date NOT NULL,
  `Contenido` text NOT NULL,
  `Resolucion` text DEFAULT NULL,
  `Codigo_Validacion` varchar(50) NOT NULL,
  `Estado` varchar(9) DEFAULT NULL,
  `Id_Usuario` int(11) NOT NULL,
  `Id_Proyecto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `alegacion`
--

INSERT INTO `alegacion` (`Id_Alegacion`, `Fecha_Presentacion`, `Contenido`, `Resolucion`, `Codigo_Validacion`, `Estado`, `Id_Usuario`, `Id_Proyecto`) VALUES
(1, '2024-01-15', 'Solicitud de modificación del proyecto solar.', 'Pendiente', 'VALID12345', 'Pendiente', 1, 1),
(2, '2024-02-05', 'Revisión de plano para la nueva biblioteca.', 'Aceptada', 'VALID23456', 'Aceptada', 1, 2),
(3, '2023-12-20', 'Alegación por problemas con la rehabilitación de viviendas.', 'Rechazada', 'VALID34567', 'Rechazada', 1, 1),
(4, '2024-03-12', 'Revisión del proyecto de ampliación del aeropuerto.', 'Pendiente', 'VALID45678', 'Pendiente', 4, 4),
(5, '2024-04-25', 'Propuesta de nuevos materiales para el parque urbano.', 'Pendiente', 'VALID56789', 'Pendiente', 5, 1),
(6, '2024-01-30', 'Discrepancia en los plazos de construcción de la biblioteca.', 'Rechazada', 'VALID67890', 'Rechazada', 6, 6),
(7, '2024-03-22', 'Cambio en los planos de la ampliación del aeropuerto.', 'Aceptada', 'VALID78901', 'Aceptada', 7, 7),
(8, '2024-02-10', 'Alegación por aumento de presupuesto en el proyecto solar.', 'Pendiente', 'VALID89012', 'Pendiente', 8, 8),
(9, '2023-12-15', 'Discrepancia en la entrega de materiales para la rehabilitación.', 'Rechazada', 'VALID90123', 'Rechazada', 9, 9),
(10, '2024-05-03', 'Solicitud de ampliación de plazo para el proyecto de parque urbano.', 'Pendiente', 'VALID01234', 'Pendiente', 10, 10),
(11, '2025-01-23', 'Proyecto Solar muy caro', 'Pendiente', 'ba952d5b-84ad-4c69-a5a8-d3af88fc8e1d', 'Pendiente', 12, 1),
(12, '2025-01-23', 'Biblioteca necesaria para alumnos de universidad', 'Pendiente', '4e15f61c-7bba-4932-8ede-524cc091d29b', 'Pendiente', 12, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `favoritos`
--

CREATE TABLE `favoritos` (
  `Id_Favoritos` int(11) NOT NULL,
  `Id_Usuario` int(11) NOT NULL,
  `Id_Proyecto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `favoritos`
--

INSERT INTO `favoritos` (`Id_Favoritos`, `Id_Usuario`, `Id_Proyecto`) VALUES
(1, 1, 1),
(2, 2, 3),
(3, 3, 4),
(4, 4, 2),
(5, 5, 1),
(6, 6, 5),
(7, 7, 7),
(8, 8, 9),
(9, 9, 8),
(10, 10, 6),
(11, 1, 2),
(12, 12, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `informe`
--

CREATE TABLE `informe` (
  `Id_Informe` int(11) NOT NULL,
  `Fecha_Creacion` date NOT NULL,
  `Autor` varchar(100) NOT NULL,
  `Contenido` text NOT NULL,
  `Estado` varchar(9) NOT NULL,
  `Tipo_Informe` varchar(50) NOT NULL,
  `Id_Proyecto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `informe`
--

INSERT INTO `informe` (`Id_Informe`, `Fecha_Creacion`, `Autor`, `Contenido`, `Estado`, `Tipo_Informe`, `Id_Proyecto`) VALUES
(1, '2025-01-10', 'Laura Gómez', 'Informe sobre el avance de la instalación de paneles solares.', 'Pendiente', 'Informe Técnico', 1),
(2, '2025-01-11', 'Carlos López', 'Informe sobre la construcción de la biblioteca pública.', 'Aprobado', 'Informe Final', 2),
(3, '2025-01-12', 'Ana Martínez', 'Informe sobre la rehabilitación energética de edificios.', 'Pendiente', 'Informe de Avance', 3),
(4, '2025-01-13', 'Luis Hernández', 'Informe sobre la ampliación del aeropuerto.', 'Pendiente', 'Informe de Revisión', 4),
(5, '2025-01-14', 'Miguel Díaz', 'Informe sobre la construcción del parque urbano.', 'Aprobado', 'Informe Ejecutivo', 5),
(6, '2025-01-15', 'Sara Martínez', 'Informe sobre la modernización de la red eléctrica.', 'Pendiente', 'Informe Técnico', 6),
(7, '2025-01-16', 'David Pérez', 'Informe sobre el puente nuevo y su fase de construcción.', 'Pendiente', 'Informe de Evaluación', 7),
(8, '2025-01-17', 'Marta Sánchez', 'Informe sobre la actualización del proyecto hidráulico.', 'Aprobado', 'Informe Final', 8),
(9, '2025-01-18', 'Carlos López', 'Informe sobre la ejecución de proyectos de vivienda en la ciudad.', 'Pendiente', 'Informe de Progreso', 9),
(10, '2025-01-19', 'Laura Gómez', 'Informe sobre el progreso de la planta solar en el sector industrial.', 'Aprobado', 'Informe Ejecutivo', 10),
(11, '2025-01-23', 'Nombre del autor', 'Contenido del informe', 'Pendiente', 'Informe Técnico', 2),
(12, '2025-01-23', 'Antonia', 'Prueba de informe por Antonia Leal', 'Pendiente', 'Informe Final', 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notificaciones`
--

CREATE TABLE `notificaciones` (
  `Id_Notificacion` int(11) NOT NULL,
  `Id_Usuario` int(11) NOT NULL,
  `Fecha` date NOT NULL,
  `Contenido` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `notificaciones`
--

INSERT INTO `notificaciones` (`Id_Notificacion`, `Id_Usuario`, `Fecha`, `Contenido`) VALUES
(1, 1, '2025-01-10', 'Su proyecto \"Proyecto Solar\" ha sido aprobado.'),
(2, 2, '2025-01-11', 'Por favor, actualice su información de contacto.'),
(3, 3, '2025-01-12', 'El proyecto \"Construcción Biblioteca\" está en revisión.'),
(4, 4, '2025-01-13', 'Se ha actualizado el estado del proyecto \"Ampliación Aeropuerto\".'),
(5, 5, '2025-01-14', 'Su solicitud de cambio de contraseña ha sido procesada.'),
(6, 6, '2025-01-15', 'Hay un nuevo comentario en el proyecto \"Modernización Red Eléctrica\".'),
(7, 7, '2025-01-16', 'El proyecto \"Puente Nuevo\" ha cambiado de fase a Construcción.'),
(8, 8, '2025-01-17', 'Notificación de vencimiento: revise su suscripción.'),
(9, 9, '2025-01-18', 'Se ha generado una nueva alegación en el proyecto \"Hospital Modular\".'),
(10, 10, '2025-01-19', 'El proyecto \"Proyecto Hidráulico\" ha sido finalizado.'),
(11, 6, '2025-01-23', 'Su proyecto \"Modernización Red Eléctrica\" ha sido finalizado.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proyecto`
--

CREATE TABLE `proyecto` (
  `Id_Proyecto` int(11) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Descripcion` text DEFAULT NULL,
  `Estado` varchar(10) NOT NULL,
  `Fase` varchar(12) NOT NULL,
  `Presupuesto` decimal(15,2) DEFAULT NULL,
  `Fecha_Inicio` date DEFAULT NULL,
  `Fecha_Fin` date DEFAULT NULL,
  `Ubicacion` text DEFAULT NULL,
  `Id_Gestor` int(11) NOT NULL,
  `Responsable` varchar(100) DEFAULT NULL,
  `Id_Usuario` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `proyecto`
--

INSERT INTO `proyecto` (`Id_Proyecto`, `Nombre`, `Descripcion`, `Estado`, `Fase`, `Presupuesto`, `Fecha_Inicio`, `Fecha_Fin`, `Ubicacion`, `Id_Gestor`, `Responsable`, `Id_Usuario`) VALUES
(1, 'Proyecto Solar', 'Instalación de paneles solares en un parque industrial.', 'Finalizado', 'Tramite', 500000.00, '2024-01-10', '2024-07-15', 'Parque Industrial Sur, Madrid', 1, 'Laura Gómez', 1),
(2, 'Construcción Biblioteca', 'Creación de una biblioteca pública en el centro urbano.', 'En tramite', 'Construccion', 750000.00, '2024-02-01', '2024-12-01', 'Calle Mayor, Sevilla', 2, 'Carlos López', 2),
(3, 'Rehabilitación Viviendas', 'Rehabilitación energética de edificios residenciales.', 'Finalizado', 'Construccion', 1200000.00, '2023-01-05', '2023-11-20', 'Barrio Norte, Valencia', 3, 'Ana Martínez', 3),
(4, 'Ampliación Aeropuerto', 'Ampliación de la terminal de carga del aeropuerto.', 'En tramite', 'Tramite', 15000000.00, '2024-03-15', '2025-08-30', 'Zona Aeropuerto, Barcelona', 0, 'Luis Hernández', 4),
(5, 'Parque Urbano', 'Construcción de un parque urbano sostenible.', 'Finalizado', 'Construccion', 3000000.00, '2023-06-10', '2024-02-28', 'Distrito Verde, Granada', 0, 'Marta Ruiz', 5),
(6, 'Modernización Red Eléctrica', 'Actualización de infraestructuras eléctricas en zona rural.', 'Finalizado', 'Tramite', 450000.00, '2024-04-01', '2024-10-15', 'Comarca Norte, Bilbao', 0, 'Javier Pérez', 6),
(7, 'Puente Nuevo', 'Construcción de un nuevo puente sobre el río principal.', 'En tramite', 'Construccion', 6000000.00, '2024-05-20', '2025-03-30', 'Río Central, Córdoba', 0, 'Sofía Romero', 7),
(8, 'Red de Ciclovías', 'Implementación de ciclovías en toda la ciudad.', 'Finalizado', 'Construccion', 850000.00, '2023-08-15', '2024-03-10', 'Zona Urbana, Málaga', 0, 'Diego Navarro', 8),
(9, 'Hospital Modular', 'Construcción de un hospital modular en zona rural.', 'En tramite', 'Construccion', 2500000.00, '2024-09-01', '2025-06-15', 'Sierra Alta, Zaragoza', 0, 'Clara Vega', 9),
(10, 'Proyecto Hidráulico', 'Desarrollo de infraestructura hidráulica para regadíos.', 'Finalizado', 'Construccion', 1800000.00, '2023-10-05', '2024-05-30', 'Zona Agrícola, Toledo', 0, 'Raúl Ortega', 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `test_entity`
--

CREATE TABLE `test_entity` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `test_entity`
--

INSERT INTO `test_entity` (`id`, `name`, `description`) VALUES
(1, 'Prueba', 'Esta es una descripción de prueba'),
(2, 'Prueba', 'Esta es una descripción de prueba');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `Id_Usuario` int(11) NOT NULL,
  `DNI` varchar(15) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Apellidos` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Contrasena` varchar(100) NOT NULL,
  `Telefono` varchar(15) DEFAULT NULL,
  `Calle` varchar(255) DEFAULT NULL,
  `Ciudad` varchar(100) DEFAULT NULL,
  `Codigo_Postal` varchar(15) DEFAULT NULL,
  `Provincia` varchar(100) DEFAULT NULL,
  `Pais` varchar(100) DEFAULT NULL,
  `Fecha_Alta` date NOT NULL,
  `Fecha_Ultimo_Acceso` datetime DEFAULT NULL,
  `Tipo_Usuario` enum('GESTOR','USUARIO','ADMINISTRACIONPUBLICA') NOT NULL,
  `Validado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`Id_Usuario`, `DNI`, `Nombre`, `Apellidos`, `Email`, `Contrasena`, `Telefono`, `Calle`, `Ciudad`, `Codigo_Postal`, `Provincia`, `Pais`, `Fecha_Alta`, `Fecha_Ultimo_Acceso`, `Tipo_Usuario`, `Validado`) VALUES
(1, '12345678A', 'Laura', 'Gómez', 'laura.gomez@email.com', 'securePass1', '600123456', 'Calle Falsa 123', 'Madrid', '28001', 'Madrid', 'España', '2024-01-01', '2025-01-23 21:21:01', 'GESTOR', 1),
(2, '87654321B', 'Carlos', 'López', 'carlos.lopez@email.com', 'securePass2', '620654321', 'Avenida Real 456', 'Sevilla', '41001', 'Sevilla', 'España', '2024-02-15', '2024-02-20 00:00:00', 'USUARIO', 0),
(3, '45678912C', 'Ana', 'Martínez', 'ana.martinez@email.com', 'securePass3', '610987654', 'Plaza Mayor 789', 'Valencia', '46001', 'Valencia', 'España', '2024-03-01', '2024-03-05 00:00:00', 'ADMINISTRACIONPUBLICA', 1),
(4, '98765432D', 'Luis', 'Hernández', 'luis.hernandez@email.com', 'securePass4', '630234567', 'Calle Luna 567', 'Barcelona', '08001', 'Barcelona', 'España', '2024-04-10', '2024-04-15 00:00:00', 'GESTOR', 1),
(5, '65432198E', 'Marta', 'Ruiz', 'marta.ruiz@email.com', 'securePass5', '640345678', 'Avenida Sol 321', 'Granada', '18001', 'Granada', 'España', '2024-05-20', '2024-05-25 00:00:00', 'USUARIO', 0),
(6, '32165487F', 'Javier', 'Pérez', 'javier.perez@email.com', 'securePass6', '650456789', 'Camino Verde 654', 'Bilbao', '48001', 'Vizcaya', 'España', '2024-06-05', '2024-06-10 00:00:00', 'ADMINISTRACIONPUBLICA', 1),
(7, '15975368G', 'Sofía', 'Romero', 'sofia.romero@email.com', 'securePass7', '660567890', 'Paseo Azul 987', 'Córdoba', '14001', 'Córdoba', 'España', '2024-07-15', '2024-07-20 00:00:00', 'GESTOR', 1),
(8, '75315984H', 'Diego', 'Navarro', 'diego.navarro@email.com', 'securePass8', '670678901', 'Calle Roja 123', 'Málaga', '29001', 'Málaga', 'España', '2024-08-25', '2024-08-30 00:00:00', 'USUARIO', 0),
(9, '25896317I', 'Clara', 'Vega', 'clara.vega@email.com', 'securePass9', '680789012', 'Avenida Blanca 456', 'Zaragoza', '50001', 'Zaragoza', 'España', '2024-09-10', '2024-09-15 00:00:00', 'ADMINISTRACIONPUBLICA', 1),
(10, '36985214J', 'Raúl', 'Ortega', 'raul.ortega@email.com', 'securePass10', '690890123', 'Plaza Verde 789', 'Toledo', '45001', 'Toledo', 'España', '2024-10-30', '2024-11-05 00:00:00', 'GESTOR', 1),
(11, '123456789L', 'Laura', 'Juncos Leal', 'laurajuncosleal@gmail.com', '1234', '643684121', 'Mahora', 'Madrigueras', '02230', 'Albacete', 'España', '2025-01-21', '2025-01-23 16:54:06', 'GESTOR', 0),
(12, '123456789P', 'Jesus', 'Martinez', 'jesusmartinez@hotmail.com', '12345', '673837121', 'Larga', 'Tarazona', '12345', 'Albacete', 'España', '2025-01-22', '2025-01-23 19:54:22', 'USUARIO', 1),
(14, '74504706R', 'Antonia', 'Leal', 'antonialeal@gmail.com', '1234', '625597722', 'Mahora', 'Madrigueras', '02230', 'Albacete', 'España', '2025-01-23', '2025-01-23 20:46:13', 'ADMINISTRACIONPUBLICA', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alegacion`
--
ALTER TABLE `alegacion`
  ADD PRIMARY KEY (`Id_Alegacion`),
  ADD UNIQUE KEY `Codigo_Validacion` (`Codigo_Validacion`),
  ADD KEY `Id_Usuario` (`Id_Usuario`),
  ADD KEY `Id_Proyecto` (`Id_Proyecto`);

--
-- Indices de la tabla `favoritos`
--
ALTER TABLE `favoritos`
  ADD PRIMARY KEY (`Id_Favoritos`),
  ADD KEY `Id_Usuario` (`Id_Usuario`),
  ADD KEY `Id_Proyecto` (`Id_Proyecto`);

--
-- Indices de la tabla `informe`
--
ALTER TABLE `informe`
  ADD PRIMARY KEY (`Id_Informe`),
  ADD KEY `Id_Proyecto` (`Id_Proyecto`);

--
-- Indices de la tabla `notificaciones`
--
ALTER TABLE `notificaciones`
  ADD PRIMARY KEY (`Id_Notificacion`),
  ADD KEY `Id_Usuario` (`Id_Usuario`);

--
-- Indices de la tabla `proyecto`
--
ALTER TABLE `proyecto`
  ADD PRIMARY KEY (`Id_Proyecto`),
  ADD KEY `fk_Usuario` (`Id_Usuario`),
  ADD KEY `fk_proyecto_gestor` (`Id_Gestor`);

--
-- Indices de la tabla `test_entity`
--
ALTER TABLE `test_entity`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`Id_Usuario`),
  ADD UNIQUE KEY `DNI` (`DNI`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `alegacion`
--
ALTER TABLE `alegacion`
  MODIFY `Id_Alegacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `favoritos`
--
ALTER TABLE `favoritos`
  MODIFY `Id_Favoritos` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `informe`
--
ALTER TABLE `informe`
  MODIFY `Id_Informe` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `notificaciones`
--
ALTER TABLE `notificaciones`
  MODIFY `Id_Notificacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `proyecto`
--
ALTER TABLE `proyecto`
  MODIFY `Id_Proyecto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `test_entity`
--
ALTER TABLE `test_entity`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `Id_Usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `alegacion`
--
ALTER TABLE `alegacion`
  ADD CONSTRAINT `alegacion_ibfk_1` FOREIGN KEY (`Id_Usuario`) REFERENCES `usuario` (`Id_Usuario`),
  ADD CONSTRAINT `alegacion_ibfk_2` FOREIGN KEY (`Id_Proyecto`) REFERENCES `proyecto` (`Id_Proyecto`);

--
-- Filtros para la tabla `favoritos`
--
ALTER TABLE `favoritos`
  ADD CONSTRAINT `favoritos_ibfk_1` FOREIGN KEY (`Id_Usuario`) REFERENCES `usuario` (`Id_Usuario`),
  ADD CONSTRAINT `favoritos_ibfk_2` FOREIGN KEY (`Id_Proyecto`) REFERENCES `proyecto` (`Id_Proyecto`);

--
-- Filtros para la tabla `informe`
--
ALTER TABLE `informe`
  ADD CONSTRAINT `informe_ibfk_1` FOREIGN KEY (`Id_Proyecto`) REFERENCES `proyecto` (`Id_Proyecto`);

--
-- Filtros para la tabla `notificaciones`
--
ALTER TABLE `notificaciones`
  ADD CONSTRAINT `notificaciones_ibfk_1` FOREIGN KEY (`Id_Usuario`) REFERENCES `usuario` (`Id_Usuario`);

--
-- Filtros para la tabla `proyecto`
--
ALTER TABLE `proyecto`
  ADD CONSTRAINT `fk_Usuario` FOREIGN KEY (`Id_Usuario`) REFERENCES `usuario` (`Id_Usuario`),
  ADD CONSTRAINT `fk_proyecto_gestor` FOREIGN KEY (`Id_Gestor`) REFERENCES `usuario` (`Id_Usuario`),
  ADD CONSTRAINT `proyecto_ibfk_1` FOREIGN KEY (`Id_Gestor`) REFERENCES `usuario` (`Id_Usuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

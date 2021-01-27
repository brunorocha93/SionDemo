-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-01-2021 a las 17:44:19
-- Versión del servidor: 10.4.11-MariaDB
-- Versión de PHP: 7.4.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sion_demo`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `apellidos` varchar(200) NOT NULL,
  `edad` int(4) NOT NULL,
  `correo` varchar(200) NOT NULL,
  `nacionalidad` varchar(200) NOT NULL,
  `ci` varchar(15) NOT NULL,
  `idUsuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`idCliente`, `nombre`, `apellidos`, `edad`, `correo`, `nacionalidad`, `ci`, `idUsuario`) VALUES
(1, 'Daniela', 'Rocha Vargas', 21, 'srocha@gmail.com', 'Argentina', '', 3),
(3, 'Diego', 'Rocha Hurtado', 12, 'drocha@gmail.com', 'Boliviana', '', 3),
(8, 'Juan Carlos', 'Salvatierra', 36, 'jcsalvatierra@gmail.com', 'Alemana', '55887744', 4),
(9, 'José Antonio', 'Gutiérrez', 87, 'jagutierrez@gmail.com', 'Paraguayo', '553890', 6),
(11, 'Melissa', 'Guzmán Rosales', 88, 'holanda@gmail.com', 'Holandesa', '75893', 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `apellidos` varchar(200) NOT NULL,
  `correo` varchar(200) NOT NULL,
  `contrasena` varchar(200) NOT NULL,
  `claveApi` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `nombre`, `apellidos`, `correo`, `contrasena`, `claveApi`) VALUES
(1, 'Pedro', 'Lopez', 'plopez@gmail.com', '$2y$10$D3Bf08D3F7NGwZIlYE03t.U5X8yTNxFfSgJvI0jfbF1UvWko/UuZm', '1fba2ee2066125297aaadc5fee23112d'),
(2, 'Ana', 'Flores', 'aflores@gmail.com', '$2y$10$Nw9FO3VdEPAKuqfjlcwBkuWONOIEdnC.Jnnw53Pa5hVMZzVTFHmqq', '5992856cfa6a0d7736aab5954a6be1be'),
(3, 'Bruno', 'Rocha', 'brocha@gmail.com', '$2y$10$xk3JOjaFUtDgBrYp0Hcml.ezIv4igcy5zIOVfWaRM/p2VJkfc8OOO', '8cdbb7ed1f2340d50062b4d6c44fba4d'),
(4, 'Melissa', 'Ortiz', 'mortiz@gmail.com', '$2y$10$KQJfZe52QWFi0DwbLtcYb.VjqShETyzS0n0w10QseIlWSjFg8K2VW', '7d2313bacfe8ec79b38dd3086968a1f6'),
(5, 'Severino', 'Hurtado', 'shurtado@gmail.com', '$2y$10$DPyWtsMFBq2UJMUTK4vJsOXuyK/wqyg2KVws1wwc8hso90EiDZ7R.', 'bfb2e1059aac51989439c1ddecd3e896'),
(6, 'Brian', 'Castro', 'bcastro@gmail.com', '$2y$10$TucxbfNpTA9HnKwC/MrIJOZ3flTXK9q7kxvos9CLtDacuHpMb5o.W', 'd9d0497d8a751861145b7b8e4214e0b0');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`idCliente`),
  ADD KEY `pk_idUsuario` (`idUsuario`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `idCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `pk_idUsuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

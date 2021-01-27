<?php

class clientes
{

    const NOMBRE_TABLA = "cliente";
    const ID_CLIENTE = "idCliente";
    const NOMBRE = "nombre";
    const APELLIDOS = "apellidos";
    const EDAD = "edad";
    const CORREO = "correo";
    const NACIONALIDAD = "nacionalidad";
    const CI = "ci";
    const ID_USUARIO = "idUsuario";

    const CODIGO_EXITO = 1;
    const ESTADO_EXITO = 1;
    const ERROR = 2;
    const ERROR_BD = 3;
    const ERROR_PARAMETROS = 4;
    const NO_ENCONTRADO = 5;

    public static function get($peticion)
    {
        $obj = new clientes();
        $idUsuario = usuarios::autorizar();

        if (empty($peticion[1])) {
            return $obj->obtenerClientes($idUsuario);
        } else {
            return $obj->obtenerClientes($idUsuario, $peticion[0]);
        }

    }

    public static function post()
    {
        $obj = new clientes();
        $idUsuario = usuarios::autorizar();

        $body = file_get_contents('php://input');
        $contacto = json_decode($body);

        $idContacto = $obj->crear($idUsuario, $contacto);

        http_response_code(200);
        return array("estado" => true, "mensaje" => "Cliente creado correctamente");
    }

    public static function put($peticion)
    {
        $obj = new clientes();
        $idUsuario = usuarios::autorizar();

        if (!empty($peticion[1])) {
            $body = file_get_contents('php://input');
            $contacto = json_decode($body);

            if ($obj->actualizar($contacto, $peticion[1]) > 0) {
                http_response_code(200);
                return array("estado" => true, "mensaje" => "Cliente actualizado correctamente");
            } else {
                return array("estado" => false, "mensaje" => "Error al actualizar");
            }
        } else {
            return array("estado" => false, "mensaje" => "Error al actualizar");
        }
    }

    public static function delete($peticion)
    {
        $obj = new clientes();
        $idUsuario = usuarios::autorizar();

        if (!empty($peticion[1])) {
            if ($obj->eliminar($idUsuario, $peticion[1]) > 0) {
                http_response_code(200);
                return array("estado" => true, "mensaje" => "Cliente eliminado correctamente");
            } else {
                return array("estado" => false, "mensaje" => "Error al eliminar el cliente");
            }
        } else {
            return array("estado" => false, "mensaje" => "Error al eliminar el cliente. Faltan parametros");
        }

    }

    private function obtenerClientes($idUsuario, $idContacto = null)
    {
        try {
            if (!$idContacto) {
                $comando = "SELECT * FROM " . self::NOMBRE_TABLA;

                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                $sentencia->bindParam(1, $idUsuario, PDO::PARAM_INT);

            } else {
                $comando = "SELECT * FROM " . self::NOMBRE_TABLA .
                " WHERE " . self::ID_CLIENTE . "=? AND " .
                self::ID_USUARIO . "=?";

                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);

                $sentencia->bindParam(1, $idContacto, PDO::PARAM_INT);
                $sentencia->bindParam(2, $idUsuario, PDO::PARAM_INT);
            }

            if ($sentencia->execute()) {
                http_response_code(200);
                // return $sentencia->fetchAll(PDO::FETCH_ASSOC);
                return array("estado" => true, "mensaje" => "Cargado con éxito", "clientes" => $sentencia->fetchAll(PDO::FETCH_ASSOC));
            } else {
                throw new ExcepcionApi(self::ERROR, "Se ha producido un error");
            }

        } catch (PDOException $e) {
            throw new ExcepcionApi(self::ERROR_BD, $e->getMessage());
        }
    }

    private function crear($idUsuario, $cliente)
    {
        if ($cliente) {
            try {

                $pdo = ConexionBD::obtenerInstancia()->obtenerBD();

                $comando = "INSERT INTO " . self::NOMBRE_TABLA . " ( " .
                self::NOMBRE . "," .
                self::APELLIDOS . "," .
                self::EDAD . "," .
                self::CORREO . "," .
                self::NACIONALIDAD . "," .
                self::CI . "," .
                self::ID_USUARIO . ")" .
                    " VALUES(?,?,?,?,?,?,?)";

                $sentencia = $pdo->prepare($comando);

                $nombre = $cliente->nombre;
                $apellidos = $cliente->apellidos;
                $edad = $cliente->edad;
                $correo = $cliente->correo;
                $nacionalidad = $cliente->nacionalidad;
                $ci = $cliente->ci;

                $sentencia->bindParam(1, $nombre);
                $sentencia->bindParam(2, $apellidos);
                $sentencia->bindParam(3, $edad);
                $sentencia->bindParam(4, $correo);
                $sentencia->bindParam(5, $nacionalidad);
                $sentencia->bindParam(6, $ci);
                $sentencia->bindParam(7, $idUsuario);

                $sentencia->execute();

                return $pdo->lastInsertId();

            } catch (PDOException $e) {
                throw new ExcepcionApi(self::ERROR_BD, $e->getMessage());
            }
        } else {
            throw new ExcepcionApi(
                self::ERROR_PARAMETROS,
                utf8_encode("Error en existencia o sintaxis de parámetros"));
        }

    }

    private function actualizar($cliente, $idCliente)
    {
        try {
            $consulta = "UPDATE " . self::NOMBRE_TABLA .
            " SET " . self::NOMBRE . "=?," .
            self::APELLIDOS . "=?," .
            self::EDAD . "=?," .
            self::CORREO . "=?, " .
            self::NACIONALIDAD . "=?, " .
            self::CI . "=? " .
            " WHERE " . self::ID_CLIENTE . "=?";

            $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($consulta);

            $nombre = $cliente->nombre;
            $apellidos = $cliente->apellidos;
            $edad = $cliente->edad;
            $correo = $cliente->correo;
            $nacionalidad = $cliente->nacionalidad;
            //$ci = $cliente->ci;

            $sentencia->bindParam(1, $nombre);
            $sentencia->bindParam(2, $apellidos);
            $sentencia->bindParam(3, $edad);
            $sentencia->bindParam(4, $correo);
            $sentencia->bindParam(5, $nacionalidad);
            $sentencia->bindParam(6, $ci);
            $sentencia->bindParam(7, $idCliente);
            $sentencia->execute();

            return $sentencia->rowCount();

        } catch (PDOException $e) {
            throw new ExcepcionApi(self::ERROR_BD, $e->getMessage());
        }
    }

    private function eliminar($idUsuario, $idCliente)
    {
        try {
            $comando = "DELETE FROM " . self::NOMBRE_TABLA .
            " WHERE " . self::ID_CLIENTE . "=?";

            $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);

            $sentencia->bindParam(1, $idCliente);

            $sentencia->execute();

            return $sentencia->rowCount();

        } catch (PDOException $e) {
            throw new ExcepcionApi(self::ERROR_BD, $e->getMessage());
        }
    }

}

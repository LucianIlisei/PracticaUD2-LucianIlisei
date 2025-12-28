package com.lucian.conexion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Conexion {
    private String ip;
    private String user;
    private String password;
    private String adminPassword;

    private Connection conexion;

    void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://"+ip+"3306/hospital",user,password);
        } catch (SQLException e) {
            try {
                conexion = DriverManager.getConnection("jdbc:mysql://"+ip+"3306",user,password);
                PreparedStatement statement = null;

                String code = leerFichero();
                String[] query = code.split("--");
                for (String aQuery : query) {
                    statement = conexion.prepareStatement(aQuery);
                    statement.executeUpdate();
                }
                assert statement != null;
                statement.close();
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private String leerFichero() throws IOException {
        String linea;
        StringBuilder stringBuilder = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader("basedatos.sql"))) {
            while((linea = reader.readLine()) != null){
                stringBuilder.append(linea);
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    void desconectar() {
        try {
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

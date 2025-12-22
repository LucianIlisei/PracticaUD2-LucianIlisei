package com.lucian.conexion;

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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}

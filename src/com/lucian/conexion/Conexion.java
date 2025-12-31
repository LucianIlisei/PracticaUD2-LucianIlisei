package com.lucian.conexion;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
    private String ip;
    private String user;
    private String password;
    private String adminPassword;

    public Conexion() { getPropValues(); }

    public String getIp() { return ip; }
    public String getUser() { return user; }
    public String getPassword() { return password; }
    public String getAdminPassword() { return adminPassword; }

    private Connection conexion;

    public void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://"+ip+":3306/hospital",user,password);
        } catch (SQLException e) {
            try {
                conexion = DriverManager.getConnection("jdbc:mysql://"+ip+":3306",user,password);
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
        try(BufferedReader reader = new BufferedReader(new FileReader("basedatos_java.sql"))) {
            while((linea = reader.readLine()) != null){
                stringBuilder.append(linea);
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    public void desconectar() {
        try {
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getPropValues() {
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = new FileInputStream(propFileName);

            prop.load(inputStream);
            ip = prop.getProperty("ip");
            user = prop.getProperty("user");
            password = prop.getProperty("pass");
            adminPassword = prop.getProperty("admin");
        } catch (FileNotFoundException e) {
            System.out.println("Exception: " + e);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
                try {
                    if (inputStream != null) inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    public void setPropValues(String ip, String user, String pass, String adminPass) {
        try {
            Properties prop = new Properties();
            prop.setProperty("ip", ip);
            prop.setProperty("user", user);
            prop.setProperty("pass", pass);
            prop.setProperty("admin", adminPass);
            OutputStream out = new FileOutputStream("config.properties");
            prop.store(out, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.ip = ip;
        this.user = user;
        this.password = pass;
        this.adminPassword = adminPass;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author c0649005
 */
public class Credentials {
    
//    public static void main(String[] args) {
//       getConnection();
//    }
     public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://localhost/ashish";
            String user = "root";
            String pass = "";
            conn = DriverManager.getConnection(jdbc, user, pass);
            String query = "SELECT * FROM product";

//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
//            
//            while (rs.next()){
//            System.out.println(rs.getInt("productID") + "\t" + rs.getString("name") + "\t" + rs.getString("description")+ "\t" + rs.getInt("quantity"));
//        } 
        }
            catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Credentials.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
     }

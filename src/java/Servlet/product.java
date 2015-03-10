/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import database.Credentials;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.json.simple.JSONValue;

/**
 *
 * @author c0649005
 */
@Path("/product")
public class product {

    @GET
    @Produces("application/json")

    public String doGet() {
        String result = getResults("SELECT * FROM PRODUCT");
        return result;
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public String doGet(@PathParam("id") String id) {

        String result = getResults("SELECT * FROM PRODUCT where productID = ?", id);
        return result;

    }

    @POST
    @Consumes("application/json")
    public void doPost(String str) {
        JsonParser parser = Json.createParser(new StringReader(str));
        Map<String, String> map = new HashMap<>();
        String name = "", value;

        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case KEY_NAME:
                    name = parser.getString();
                    break;
                case VALUE_STRING:
                    value = parser.getString();
                    break;
                case VALUE_NUMBER:
                    value = Integer.toString(parser.getInt());
                    break;
            }
        }
        System.out.println(map);

        String na = map.get("name");
        String des = map.get("name");
        String qunt = map.get("quantity");

        doUpdate("INSERT INTO PRODUCT(name,description,quantity)VALUES (?,?,?)", na, des, qunt);
    }

    @PUT
    @Consumes("application/json")
    public void doPut(String str) {
        JsonParser parser = Json.createParser(new StringReader(str));
        Map<String, String> map = new HashMap<>();
        String name = "", value;

        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case KEY_NAME:
                    name = parser.getString();
                    break;
                case VALUE_STRING:
                    value = parser.getString();
                    break;
                case VALUE_NUMBER:
                    value = Integer.toString(parser.getInt());
                    break;
            }
        }
        System.out.println(map);

        String na = map.get("name");
        String des = map.get("name");
        String qunt = map.get("quantity");

        doUpdate("INSERT INTO PRODUCT(name,description,quantity)VALUES (?,?,?)", na, des, qunt);
    }

    @DELETE
    @Path("{id}")
    public void doDelete(@PathParam("id") String id, String str) {

        doUpdate("DELETE FROM PRODUCT WHERE productID = ?", id);

    }

    private int doUpdate(String query, String... params) {
        int numChanges = 0;
        try (Connection conn = Credentials.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                pstmt.setString(i, params[i - 1]);
            }
            numChanges = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(product.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numChanges;
    }

    private String getResults(String query, String... params) {
        StringBuilder sb = new StringBuilder();
        String json = "";
        try (Connection conn = Credentials.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                pstmt.setString(i, params[i - 1]);
            }
            ResultSet rs = pstmt.executeQuery();
            List li = new LinkedList();
            while (rs.next()) {
                //sb.append(String.format("%s\t%s\t%s\t%s\n", rs.getInt("productID"), rs.getString("name"), rs.getString("description"), rs.getInt("quantity")));
                //for conversion to json https://code.google.com/p/json-simple/downloads/detail?name=json-simple-1.1.1.jar
                Map m1 = new LinkedHashMap();
                m1.put("ProductID", rs.getInt("ProductID"));
                m1.put("name", rs.getString("name"));
                m1.put("description", rs.getString("description"));
                m1.put("quantity", rs.getInt("quantity"));
                li.add(m1);
            }
            json = JSONValue.toJSONString(li);
        } catch (SQLException ex) {
            Logger.getLogger(product.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json.replace("},", "},\n");
    }
}

package com.expleo.project4;

import com.google.gson.JsonObject;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;
import org.hamcrest.CoreMatchers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

public class JSONDBConnector {

    private static Connection connection;
    private static File fileDatabase;

    private static JsonObject jsonObject;
    private static RequestSpecification request;
    private static String  url;
    private static Response response; //ws

    public static JsonObject getJsonObject() {
        return jsonObject;
    }

    public static RequestSpecification getRequest() {
        return request;
    }

    public static String getUrl() {
        return url;
    }

    public static Response getResponse() {
        return response;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static String checkJSONDBFileExist(String fileName) {
        String message = "";
        if(ChinookConnector.isExited() == false)
        {
            File file = new File(fileName + ".json");

            String filePath = new File("").getAbsolutePath();
            filePath.concat(" path to the property file");
            try {
                assertThat(file.exists(), equalTo(true));
                message = "db.json database exist in " + filePath + " directory";
                fileDatabase = file;
            } catch (AssertionError ex) {

                message += fileName + ".db file does not exist in " + filePath + " directory";
                ChinookConnector.setExit(true);


            }

        }


        return message;
    }

    // Perform initialisation processing

    public static String CheckForJSONConnection(String _url) {
        String message="";
        if(ChinookConnector.isExited() == false) {
            jsonObject = new JsonObject();
            request = given().contentType("application/json");
            try
            {
                assertThat("http://localhost:3000".equals(_url), equalTo(true));
                url = _url;
                message += "Correct URL to connect to db.json database";

            }catch(AssertionError ex)
            {
                message += "Invalid url: "+_url;
                ChinookConnector.setExit(true);
            }


        }
        return message;
    }






}

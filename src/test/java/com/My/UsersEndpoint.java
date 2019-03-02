package com.My;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.LogConfig;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsersEndpoint {

    private Response response;
    public static PrintStream stream;

    public static void outputLog() {
        String logPath = "src/test/java/com/My/Logs/logfile.log";
        try {
            stream = new PrintStream(logPath);
        } catch (FileNotFoundException e) {
            new File(logPath);
        }
    }

    public void restAssuredConfig() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/test/resources/config.properties");
            prop.load(input);
            RestAssured.port = Integer.parseInt(prop.getProperty("port"));
            RestAssured.basePath = prop.getProperty("endpoint");
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<HashMap<String, String>> parseBody() {
        String body = response.getBody().asString();
        List <HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        Pattern pattern = Pattern.compile("(\\{ID=.*?, FIRSTNAME=.*?, LASTNAME=.*?\\})");
        Matcher matcher = pattern.matcher(body);
        while (matcher.find()) {
            HashMap <String, String> map = new HashMap<String, String>();
            String user = matcher.group(1);
            Pattern p = Pattern.compile("\\{ID=(.*?), FIRSTNAME=(.*?), LASTNAME=(.*?)\\}");
            Matcher m = p.matcher(user);
            m.find();
            map.put("ID", m.group(1));
            map.put("FIRSTNAME", m.group(2));
            map.put("LASTNAME", m.group(3));
            list.add(map);
        }
        return list;
    }

    public List<String> getUserIdList() {
        List<HashMap<String, String>> listUsers = parseBody();
        List <String> list = new ArrayList<String>();
        for (HashMap user : listUsers) {
            list.add(user.get("ID").toString());
        }
        return list;
    }

    public RequestSpecification logConfig() {
        return RestAssured.given().config(RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(stream))).log().all();
    }

    public UsersEndpoint get() {
        stream.print("\nGet users data:\n\n");
        response = logConfig().get();
        stream.println("Response body:");
        response.then().log().body();
        return this;
    }

    public UsersEndpoint get(String id) {
        stream.print("\nGet user data for id=" + id + ":\n\n");
        response = logConfig().get("/" + id);
        stream.println("Response body:");
        response.then().log().body();
        return this;
    }

    public UsersEndpoint post(HashMap<String, String> userData) {
        stream.print("\nCreate user " + userData + ":\n\n");
        response = logConfig().given()
                .formParam("firstName", userData.get("FIRSTNAME"))
                .formParam("lastName", userData.get("LASTNAME"))
                .when()
                .post();
        stream.println("Response body:");
        response.then().log().body();
        return this;
    }

    public UsersEndpoint put(String id, HashMap<String, String> userData) {
        stream.print("\nModify user data for id=" + id + " to " + userData + ":\n\n");
        response = logConfig().given()
                .formParam("firstName", userData.get("FIRSTNAME"))
                .formParam("lastName", userData.get("LASTNAME"))
                .when()
                .put("/" + id);
        stream.println("Response body:");
        response.then().log().body();
        return this;
    }

    public UsersEndpoint put(String id, String firstName) {
        stream.print("\nModify user firstName for id=" + id + " to " + firstName + ":\n\n");
        response = logConfig().given()
                .formParam("firstName", firstName)
                .when()
                .put("/" + id);
        stream.println("Response body:");
        response.then().log().body();
        return this;
    }

    public UsersEndpoint delete(String id) {
        stream.print("\nDelete user for id=" + id + ":\n\n");
        response = logConfig().delete("/" + id);
        stream.println("Response body:");
        response.then().log().body();
        return this;
    }

    public UsersEndpoint validateStatusCode(int code) {
        response.then().statusCode(code);
        return this;
    }
}

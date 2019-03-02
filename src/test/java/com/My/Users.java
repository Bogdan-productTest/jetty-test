package com.My;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Random;

public class Users {

    private HashMap <String, String> userData = new HashMap<String, String>();
    private JsonArray jsonArray;

    public Users() {
        parseJson();
        newUser();
    }

    public Users newUser() {
        int count = jsonArray.size();
        JsonObject obj = jsonArray.get(new Random().nextInt(count)).getAsJsonObject();
        userData.put("FIRSTNAME", obj.get("firstName").getAsString());
        userData.put("LASTNAME", obj.get("lastName").getAsString());
        return this;
    }

    public HashMap <String, String> getUser() {
        return userData;
    }

    public void parseJson() {
        try {
            jsonArray = new JsonParser().parse(new FileReader("src/test/resources/users.json")).getAsJsonArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

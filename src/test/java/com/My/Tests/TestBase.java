package com.My.Tests;

import com.My.Users;
import com.My.UsersEndpoint;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.HashMap;
import java.util.List;

import static com.My.UsersEndpoint.outputLog;
import static com.My.UsersEndpoint.stream;

public class TestBase {
    protected static UsersEndpoint usersEndpoint;
    protected static Users user;

    protected static HashMap<String, String> createUser(HashMap <String, String> userData) {
        List <HashMap<String, String>> l = usersEndpoint.post(userData).parseBody();
        userData.put("ID", l.get(0).get("ID"));
        return userData;
    }

    @BeforeClass
    public static void setUp(){
        usersEndpoint = new UsersEndpoint();
        usersEndpoint.restAssuredConfig();
        outputLog();
    }

    @After
    public void deleteUsers() {
        List<String> users = usersEndpoint.get().getUserIdList();
        for (String id : users) {
            usersEndpoint.delete(id);
        }
        log("--------------------------------------------------------------------------\n\n");
    }

    @Before
    public void beforeTest() {
        updateUser();
    }

    public Users updateUser() {
        user = new Users();
        user.newUser();
        log("Run test: ");
        return user;
    }

    public void log(String message) {
        stream.print(message);
    }

    public void printMethodName (Object obj) {
        log(obj.getClass().getEnclosingMethod().getName() + "\n\n");
    }
}

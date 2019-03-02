package com.My.Tests;

import org.junit.Test;
import java.util.HashMap;

public class CheckStatusCodeTestsUsersEndpoint extends TestBase {

    @Test
    public void getUsers_statusCodeIs200() {
        printMethodName(new Object() {});
        log("Create user: " + user.getUser() + "\n");
        createUser(user.getUser());
        usersEndpoint.get().validateStatusCode(200);
    }

    @Test
    public void getValidUser_statusCodeIs200() {
        printMethodName(new Object() {});
        HashMap <String, String> userExpected = createUser(user.getUser());
        usersEndpoint.get(userExpected.get("ID")).validateStatusCode(200);
    }

    @Test
    public void getInvalidUser_statusCodeIs404() {
        printMethodName(new Object() {});
        HashMap <String, String> userExpected = createUser(user.getUser());
        usersEndpoint.get(userExpected.get("ID") + "100").validateStatusCode(404);
    }

    @Test
    public void createUser_statusCodeIs200() {
        printMethodName(new Object() {});
        createUser(user.getUser());
        usersEndpoint.validateStatusCode(200);
    }

    @Test
    public void modifyValidUser_statusCodeIs200() {
        printMethodName(new Object() {});
        HashMap <String, String> userExpected = createUser(user.getUser());
        updateUser();
        usersEndpoint.put(userExpected.get("ID"), user.getUser()).validateStatusCode(200);
    }

    @Test
    public void modifyInvalidUser_statusCodeIs404() {
        printMethodName(new Object() {});
        HashMap <String, String> userExpected = createUser(user.getUser());
        updateUser();
        String invalidId = String.valueOf(Integer.parseInt(userExpected.get("ID")) + 100);
        usersEndpoint.put(invalidId, user.getUser()).validateStatusCode(404);
    }

    @Test
    public void deleteUser_statusCodeIs200() {
        printMethodName(new Object() {});
        HashMap <String, String> userExpected = createUser(user.getUser());
        usersEndpoint.delete(userExpected.get("ID")).validateStatusCode(200);
    }

    @Test
    public void deleteInvalidUser_statusCodeIs404() {
        printMethodName(new Object() {});
        HashMap <String, String> userExpected = createUser(user.getUser());
        usersEndpoint.delete(userExpected.get("ID")).validateStatusCode(404);
    }
}

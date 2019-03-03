package com.My.Tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import org.junit.Test;
import java.util.HashMap;
import io.qameta.allure.Description;

@Epic("Status codes")
public class CheckStatusCodeTestsUsersEndpoint extends TestBase {

    @Test
    @Issue("432")
    @Description("Get /rs/users return code 200")
    public void getUsers_statusCodeIs200() {
        printMethodName(new Object() {});
        log("Create user: " + user.getUser() + "\n");
        createUser(user.getUser());
        usersEndpoint.get().validateStatusCode(201);
    }

    @Test
    @Description("Get /rs/users/{valid_id} return code 200")
    public void getValidUser_statusCodeIs200() {
        printMethodName(new Object() {});
        HashMap <String, String> userExpected = createUser(user.getUser());
        usersEndpoint.get(userExpected.get("ID")).validateStatusCode(200);
    }

    @Test
    @Description("Get /rs/users/{invalid_id} return code 404")
    public void getInvalidUser_statusCodeIs404() {
        printMethodName(new Object() {});
        HashMap <String, String> userExpected = createUser(user.getUser());
        usersEndpoint.get(userExpected.get("ID") + "100").validateStatusCode(404);
    }

    @Test
    @Description("Post /rs/users return code 200")
    public void createUser_statusCodeIs200() {
        printMethodName(new Object() {});
        createUser(user.getUser());
        usersEndpoint.validateStatusCode(200);
    }

    @Test
    @Description("Put /rs/users/{valid_id} return code 200")
    public void modifyValidUser_statusCodeIs200() {
        printMethodName(new Object() {});
        HashMap <String, String> userExpected = createUser(user.getUser());
        updateUser();
        usersEndpoint.put(userExpected.get("ID"), user.getUser()).validateStatusCode(200);
    }

    @Test
    @Description("Put /rs/users/{invalid_id} return code 404")
    public void modifyInvalidUser_statusCodeIs404() {
        printMethodName(new Object() {});
        HashMap <String, String> userExpected = createUser(user.getUser());
        updateUser();
        String invalidId = String.valueOf(Integer.parseInt(userExpected.get("ID")) + 100);
        usersEndpoint.put(invalidId, user.getUser()).validateStatusCode(404);
    }

    @Test
    @Description("Delete /rs/users/{valid_id} return code 200")
    public void deleteUser_statusCodeIs200() {
        printMethodName(new Object() {});
        HashMap <String, String> userExpected = createUser(user.getUser());
        usersEndpoint.delete(userExpected.get("ID")).validateStatusCode(200);
    }

    @Test
    @Description("Delete /rs/users/{invalid_id} return code 404")
    public void deleteInvalidUser_statusCodeIs404() {
        printMethodName(new Object() {});
        HashMap <String, String> userExpected = createUser(user.getUser());
        usersEndpoint.delete(userExpected.get("ID")).validateStatusCode(404);
    }
}

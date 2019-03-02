package com.My.Tests;

import com.My.Users;
import org.junit.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FunctionalTestsUsersEndpoint extends TestBase {

    @Test
    public void getUsers_listOfUsersIsCorrect() {
        printMethodName(new Object() {});
        List <HashMap<String, String>> expectedUsers = new ArrayList<HashMap<String, String>>();
        expectedUsers.add(createUser(user.getUser()));
        Users newUser = updateUser();
        expectedUsers.add(createUser(newUser.getUser()));
        List <HashMap<String, String>> actualUsers = usersEndpoint.get().parseBody();
        Assert.assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void getValidUserCyrillic_userIsCorrect() {
        printMethodName(new Object() {});
        HashMap <String, String> map = new HashMap<String, String>();
        map.put("FIRSTNAME", "имя");
        map.put("LASTNAME", "фамилия");
        HashMap <String, String> userExpected = createUser(map);
        HashMap <String, String> userActual = usersEndpoint.get(userExpected.get("ID")).parseBody().get(0);
        Assert.assertEquals(userExpected, userActual);
    }

    @Test
    public void getValidUserLatin_userIsCorrect() {
        printMethodName(new Object() {});
        HashMap <String, String> userExpected = createUser(user.getUser());
        HashMap <String, String> userActual = usersEndpoint.get(userExpected.get("ID")).parseBody().get(0);
        Assert.assertEquals(userExpected, userActual);
    }

    @Test
    public void createUser_userIsCorrect() {
        printMethodName(new Object() {});
        HashMap <String, String> userExpected = createUser(user.getUser());
        HashMap <String, String> userActual = usersEndpoint.get(userExpected.get("ID")).parseBody().get(0);
        Assert.assertEquals(userExpected, userActual);
    }

    @Test
    public void modifyAllParametersForValidUser_userIsCorrect() {
        printMethodName(new Object() {});
        HashMap <String, String> userExpected = createUser(user.getUser());
        Users newUser = updateUser();
        userExpected.put("FIRSTNAME", newUser.getUser().get("FIRSTNAME"));
        userExpected.put("LASTNAME", newUser.getUser().get("LASTNAME"));
        usersEndpoint.put(userExpected.get("ID"), newUser.getUser());
        HashMap <String, String> userActual = usersEndpoint.get(userExpected.get("ID")).parseBody().get(0);
        Assert.assertEquals(userExpected, userActual);
    }

    @Test
    public void modifyOneParameterForValidUser_userIsCorrect() {
        printMethodName(new Object() {});
        HashMap <String, String> userExpected = createUser(user.getUser());
        Users newUser = updateUser();
        String newName = newUser.getUser().get("FIRSTNAME");
        userExpected.put("FIRSTNAME", newName);
        usersEndpoint.put(userExpected.get("ID"), newName);
        HashMap <String, String> userActual = usersEndpoint.get(userExpected.get("ID")).parseBody().get(0);
        Assert.assertEquals(userExpected, userActual);
    }

    @Test
    public void deleteValidUser_userDeleted() {
        printMethodName(new Object() {});
        HashMap <String, String> userExpected = createUser(user.getUser());
        usersEndpoint.delete(userExpected.get("ID"));
        int countUsers = usersEndpoint.get(userExpected.get("ID")).parseBody().size();
        Assert.assertEquals(countUsers,0);
    }
}

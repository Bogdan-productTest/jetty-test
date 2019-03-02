package com.My.TestRunners;

import com.My.Tests.FunctionalTestsUsersEndpoint;

public class TestRunnerFunctional extends TestRunnerBase {
    public static void main(String[] args) {
        setProperties(args, FunctionalTestsUsersEndpoint.class);
    }
}

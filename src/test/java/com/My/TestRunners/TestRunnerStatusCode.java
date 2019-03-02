package com.My.TestRunners;

import  com.My.Tests.CheckStatusCodeTestsUsersEndpoint;

public class TestRunnerStatusCode extends TestRunnerBase {
    public static void main(String[] args) {
        setProperties(args, CheckStatusCodeTestsUsersEndpoint.class);
    }
} 
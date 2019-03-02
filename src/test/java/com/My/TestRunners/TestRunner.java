package com.My.TestRunners;

import com.My.Tests.TestSuite;

public class TestRunner extends TestRunnerBase {
    public static void main(String[] args) {
        TestRunnerBase.setProperties(args, TestSuite.class);
    }
}

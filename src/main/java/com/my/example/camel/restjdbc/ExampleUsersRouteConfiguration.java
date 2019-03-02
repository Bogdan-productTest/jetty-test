package com.my.example.camel.restjdbc;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class ExampleUsersRouteConfiguration extends RouteBuilder {

    private Processor headerMovingProcessor;

    public ExampleUsersRouteConfiguration(HeaderMovingProcessor headerMovingProcessor) {
        this.headerMovingProcessor = headerMovingProcessor;
    }

    @Override
    public void configure() {
        from("restlet:/users?restletMethod=POST")
                .process(headerMovingProcessor)
                .setBody(simple("insert into users(firstName, lastName) values('${headers.firstName}','${headers.lastName}')"))
                .to("jdbc:dataSource")
                .setBody(simple("select * from users where id in (select max(id) from users)"))
                .to("jdbc:dataSource");

        from("restlet:/users/{userId}?restletMethods=GET,PUT,DELETE")
                .process(headerMovingProcessor)
                .choice()
                .when(simple("${header.CamelHttpMethod} == 'GET'"))
                .setBody(simple("select * from users where id = ${header.userId}"))
                .when(simple("${header.CamelHttpMethod} == 'PUT'"))
                .setBody(simple("update users set firstName='${header.firstName}', lastName='${header.lastName}' where id = ${header.userId}"))
                .when(simple("${header.CamelHttpMethod} == 'DELETE'"))
                .setBody(simple("delete from users where id = ${header.userId}"))
                .otherwise()
                .stop()
                .end()
                .to("jdbc:dataSource");

        from("restlet:/users?restletMethod=GET")
                .setBody(simple("select * from users"))
                .to("jdbc:dataSource");
    }


}


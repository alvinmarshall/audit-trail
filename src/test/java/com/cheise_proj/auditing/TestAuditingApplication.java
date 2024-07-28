package com.cheise_proj.auditing;

import org.springframework.boot.SpringApplication;

public class TestAuditingApplication {

    public static void main(String[] args) {
        SpringApplication.from(AuditingApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}

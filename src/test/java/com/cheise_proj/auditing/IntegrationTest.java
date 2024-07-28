package com.cheise_proj.auditing;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = AuditingApplicationTests.class)
@EnableTestcontainers
@ActiveProfiles("test")
//lookup:https://github.com/testcontainers/testcontainers-java/discussions/7454#discussioncomment-7554534
public abstract class IntegrationTest {
}
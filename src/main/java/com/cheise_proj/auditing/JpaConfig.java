package com.cheise_proj.auditing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "customAuditProvider")
class JpaConfig {

    @Bean
    AuditorAware<String> customAuditProvider() {
        return new AuditAwareImpl();
    }
}
package com.cheise_proj.auditing;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("System");
    }
}
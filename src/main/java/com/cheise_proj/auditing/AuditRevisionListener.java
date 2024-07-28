package com.cheise_proj.auditing;

import org.hibernate.envers.RevisionListener;

class AuditRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        String currentUser = "System";
        AuditRevisionEntity audit = (AuditRevisionEntity) revisionEntity;
        audit.setUserName(currentUser);
    }
}
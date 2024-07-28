package com.cheise_proj.auditing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import java.util.*;

@Entity
@Table(name = "revision_info")
@RevisionEntity(AuditRevisionListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
class AuditRevisionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revision_seq")
    @SequenceGenerator(
            name = "revision_seq",
            sequenceName = "seq_revision_id"
    )
    @RevisionNumber
    private int id;

    @Column(name = "revision_date")
    @Temporal(TemporalType.TIMESTAMP)
    @RevisionTimestamp
    private Date date;

    @Column(name = "user_name")
    private String userName;

    static List<Map<String, Object>> toRevisionResults(List<Object> results) {
        List<Map<String, Object>> revisions = new ArrayList<>();

        for (Object result : results) {
            if (result instanceof Object[] resultArray) {
                Map<String, Object> revisionData = new HashMap<>();
                revisionData.put("entity", resultArray[0]);
                revisionData.put("revision", resultArray[1]);
                revisionData.put("revisionType", resultArray[2]);
                if (resultArray.length > 3) {
                    revisionData.put("changes", resultArray[3]);
                }
                revisions.add(revisionData);
            }
            else if (result instanceof Object resultMap) {
                Map<String, Object> revisionData = new HashMap<>();
                revisionData.put("revision", resultMap);
                revisions.add(revisionData);
            }
        }
        return revisions;
    }
}
package com.board.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private String created_at;

    private String created_by;

    public String getCreated_at() {
        return created_at;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void createdBy(String name) {
        this.created_by = name;
    }

    @PrePersist
    public void onPrePersist(){
        this.created_at = LocalDateTime.now()
                .format(DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}

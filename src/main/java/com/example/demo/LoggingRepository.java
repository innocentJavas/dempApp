package com.example.demo;

import com.example.demo.entity.AuditLogging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggingRepository extends JpaRepository<AuditLogging, Long> {
}

package com.sacavix.learn.sharerer.persistence;

import com.sacavix.learn.sharerer.persistence.entities.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Optional<Certificate> findFirstByCertificateHash(String certificateHash);
}

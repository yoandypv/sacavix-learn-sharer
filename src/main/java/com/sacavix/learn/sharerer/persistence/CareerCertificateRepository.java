package com.sacavix.learn.sharerer.persistence;

import com.sacavix.learn.sharerer.persistence.entities.CareerCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CareerCertificateRepository extends JpaRepository<CareerCertificate, Long> {
    Optional<CareerCertificate> findFirstByCertificateHash(String certificateHash);
}

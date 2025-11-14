package com.sacavix.learn.sharerer.service;

import com.sacavix.learn.sharerer.persistence.CertificateRepository;
import com.sacavix.learn.sharerer.persistence.entities.Certificate;
import com.sacavix.learn.sharerer.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CertificateContent implements Shareable {

    private final CertificateRepository certificateRepository;

    @Value("${cdn.url.static.certificate}")
    private String staticUrl;

    @Value("${cdn.url.static.certificates.base-path}")
    private String certificatesBasePath;

    public CertificateContent(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Override
    public ShareContent getContent(String id) {

        log.info("Get content for certificate id: {}", id);

        Optional<Certificate> certificate = certificateRepository.findFirstByCertificateHash(id);

        if (certificate.isPresent()) {
            var certificateContent = certificate.get();
            var imagePath = certificatesBasePath + certificateContent.getCertificateHash() + ".png";
            if (UrlUtil.exists(imagePath)) {
                imagePath = staticUrl;
            }

            return new ShareContent("Nuevo certificado alcanzado: " + certificateContent.getQuizTitle(),
                    "Logré este nuevo certificado usando SACAViX Learn, únete conmigo y aprendamos juntos.", imagePath);
        }
        return new ShareContent("He alcanzado un nuevo certificado.", "Logré un nuevo certificado usando SACAViX Learn, únete conmigo y aprendamos juntos.", staticUrl);
    }

    @Override
    public ShareableType getType() {
        return ShareableType.CERTIFICATE;
    }
}

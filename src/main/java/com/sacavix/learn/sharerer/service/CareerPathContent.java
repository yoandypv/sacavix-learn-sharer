package com.sacavix.learn.sharerer.service;

import com.sacavix.learn.sharerer.persistence.CareerCertificateRepository;
import com.sacavix.learn.sharerer.persistence.entities.CareerCertificate;
import com.sacavix.learn.sharerer.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CareerPathContent implements Shareable {

    private final CareerCertificateRepository careerCertificateRepository;

    @Value("${cdn.url.static.certificate}")
    private String staticUrl;

    @Value("${cdn.url.static.certificates.base-path}")
    private String certificatesBasePath;

    public CareerPathContent(CareerCertificateRepository careerCertificateRepository) {
        this.careerCertificateRepository = careerCertificateRepository;
    }

    @Override
    public ShareContent getContent(String id) {
        log.info("Get content for certificate id: {}", id);

        Optional<CareerCertificate> certificate = careerCertificateRepository.findFirstByCertificateHash(id);

        if (certificate.isPresent()) {
            var certificateContent = certificate.get();
            var imagePath = certificatesBasePath + certificateContent.getCertificateHash() + ".png";
            if (UrlUtil.exists(imagePath)) {
                imagePath = staticUrl;
            }

            return new ShareContent("He concluido la carrera: " + certificateContent.getCareerPathTitle(),
                    "Logré este nuevo certificado usando SACAViX Learn, únete conmigo y aprendamos juntos.", imagePath);
        }
        return new ShareContent("He alcanzado un nuevo certificado.", "Logré un nuevo certificado usando SACAViX Learn, únete conmigo y aprendamos juntos.", staticUrl);

    }

    @Override
    public ShareableType getType() {
        return ShareableType.CAREER_PATH;
    }
}

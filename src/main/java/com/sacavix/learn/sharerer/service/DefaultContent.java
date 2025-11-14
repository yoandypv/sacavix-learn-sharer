package com.sacavix.learn.sharerer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DefaultContent implements Shareable {

    @Value("${cdn.url.static.default-image}")
    private String staticUrlDefaultImage;

    @Override
    public ShareContent getContent(String id) {
        return new ShareContent("Aprende conmigo en SACAViX Learn",
                "Únete junto a mí y una gran comunidad en SACAViX Learn para aprender programación juntos. Resuelve quizzes, aprende con rutas de carreras y entrenamientos específicos. Una forma divertida y práctica de aprender programación.", staticUrlDefaultImage);

    }

    @Override
    public ShareableType getType() {
        return ShareableType.DEFAULT;
    }
}

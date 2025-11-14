package com.sacavix.learn.sharerer.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BotUtil {

    private static final String[] SOCIAL_MEDIA_BOTS = {
            "facebookexternalhit", // Facebook/WhatsApp
            "Twitterbot",         // Twitter/X
            "LinkedInBot",        // LinkedIn
            "Pinterestbot",       // Pinterest
            "TelegramBot",        // Telegram
            "Googlebot"           // Google
            // Puedes agregar mas segun sea necesario
    };

    public static boolean isSocialMediaBot(String userAgent) {
        if (userAgent == null) {
            return false;
        }

        log.info("userAgent:{}", userAgent);

        String lowerCaseAgent = userAgent.toLowerCase();
        for (String bot : SOCIAL_MEDIA_BOTS) {
            if (lowerCaseAgent.contains(bot.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}

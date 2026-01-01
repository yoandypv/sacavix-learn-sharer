package com.sacavix.learn.sharerer.controller;

import com.sacavix.learn.sharerer.service.ShareContent;
import com.sacavix.learn.sharerer.service.ShareHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.sacavix.learn.sharerer.util.BotUtil.isSocialMediaBot;

@RequestMapping("/sh")
@Controller
@Slf4j
public class ShareController {

    private final ShareHandler shareHandler;

    @Value("${sacavix.learn.web.url}")
    private String webUrl;

    public ShareController(ShareHandler shareHandler) {
        this.shareHandler = shareHandler;
    }

    @GetMapping("/{type}/{id}")
    public String shareContent(@PathVariable String type,
                               @PathVariable String id,
                               @RequestHeader(value = "User-Agent", required = false) String userAgent,
                               Model model) {

        if (isSocialMediaBot(userAgent)) {
            return "redirect:" + webUrl;
        }

        ShareContent content = shareHandler.getContent(type, id) ;
        model.addAttribute("title", content.title());
        model.addAttribute("description", content.description());
        model.addAttribute("pageUrl", webUrl );
        model.addAttribute("imageUrl", content.imageUrl());
        return "share";
    }

}

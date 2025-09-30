package com.sacavix.learn.sharerer.controller;

import com.sacavix.learn.sharerer.service.ShareContent;
import com.sacavix.learn.sharerer.service.ShareHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/share")
@Controller
public class ShareController {

    private final ShareHandler shareHandler;

    public ShareController(ShareHandler shareHandler) {
        this.shareHandler = shareHandler;
    }

    @GetMapping("{type}/{id}")
    public String shareContent(@PathVariable String type, @PathVariable String id, Model model) {
        ShareContent content = shareHandler.getContent(type, id) ;
        model.addAttribute("title", content.title());
        model.addAttribute("description", content.description());
        model.addAttribute("imageUrl", content.imageUrl());
        return "sharer";
    }
}

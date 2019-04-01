package com.github.binarywang.demo.wx.mp.common;

import com.github.binarywang.demo.wx.mp.service.IWeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {

    @Autowired
    private IWeChatService weChatService;
    @Override
    public void run(String... args) throws Exception {
        weChatService.init();
        System.out.print("asd");
    }
}

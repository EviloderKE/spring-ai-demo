package com.example.spring.ai.demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("ai")
public class DemoController {
    @Autowired
    private ChatClient chatClient;

    @RequestMapping("chat")
    public String chat(@RequestParam String prompt){
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    @RequestMapping(value = "/fluxChat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> fluxChat(@RequestParam String prompt, @RequestParam String conversationId) {
        return chatClient.prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .user(prompt)
                .stream()
                .content();
    }
}

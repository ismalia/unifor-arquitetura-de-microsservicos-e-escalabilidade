package com.br.wallet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
public class WalletController {
    @GetMapping("/hello")
    public String helloWallet() {
        return "Hi, I'm here.";
    }

    @GetMapping("/{name}")
    public String helloWithName(@PathVariable String name) {
        return "Hi, I'm " + name;
    }
}

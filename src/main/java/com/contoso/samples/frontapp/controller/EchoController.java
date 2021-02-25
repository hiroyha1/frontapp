package com.contoso.samples.frontapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.contoso.samples.echo.service.EchoService;

@RestController
@ComponentScan("com.contoso.samples.echo.service")
@RequestMapping(path = "echo")
public class EchoController {
    @Autowired
    EchoService echoService;

    @GetMapping()
    public String get(@RequestParam(name="message") String message) {
        return echoService.echo(message);
    }
}

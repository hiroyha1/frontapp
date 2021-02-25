package com.contoso.samples.frontapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.contoso.samples.echo.service.EchoService;


import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value=EchoController.class)
public class EchoControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private EchoService service;
    
    @Test
    public void whenValidParameter_thenStatus200() throws Exception
    {
        String message = "Hello World";
        when(service.echo(anyString())).thenReturn(message);
        
        mvc.perform(
                get("/echo")
                .param("message", message)
            )
            // .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
            .andExpect(content().string(message));

        ArgumentCaptor<String> anyString = ArgumentCaptor.forClass(String.class);
        verify(service, times(1)).echo(anyString.capture());
        assertEquals(message, anyString.getValue());
        reset(service);
    }

    @Test
    public void whenEmptyParameter_thenStatus400() throws Exception
    {
        mvc.perform(get("/echo"))
            // .andDo(print())
            .andExpect(status().is(400));
    }
}

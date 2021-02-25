package com.contoso.samples.frontapp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = FrontappApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration()
@AutoConfigureMockMvc
public class FrontappApplicationTests {
    @Autowired
    private MockMvc mvc;

    @Test
    public void  whenValidMessage_thenStatus200() throws Exception
    {
        String message = "Hello World";
        
        mvc.perform(
                get("/echo")
                .param("message", message)
            )
            // .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
            .andExpect(content().string(message));
    }

    @Test
    public void whenEmptyMessage_thenStatus400() throws Exception
    {
        mvc.perform(get("/echo"))
            // .andDo(print())
            .andExpect(status().is(400));
    }
}

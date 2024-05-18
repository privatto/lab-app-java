package com.jansouza.springboot.lab.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.jansouza.springboot.lab.controller.ApiController;

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {

    private final ApiController controller;
	private final MockMvc mockMvc;

    @Autowired
    public ApiControllerTest(ApiController controller, MockMvc mockMvc) {
        this.controller = controller;
        this.mockMvc = mockMvc;
    }

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
	void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/api")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("api")));
	}

}
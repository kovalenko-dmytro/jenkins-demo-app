package com.gmail.apach.jenkins_demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(
	classes = JenkinsDemoApplication.class,
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class AbstractControllerIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	protected MockMvc mvc;
	@Autowired
	protected ObjectMapper objectMapper;
}

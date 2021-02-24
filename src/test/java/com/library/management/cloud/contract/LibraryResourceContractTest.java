package com.library.management.cloud.contract;

import com.library.management.LibraryApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibraryApplication.class)
@AutoConfigureMockMvc
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = {"com.user.management:user"})
public class LibraryResourceContractTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllUsersContract() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username", is("Arup D Chanda")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].username", is("Sumit")));
    }
}

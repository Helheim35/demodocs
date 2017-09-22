package fr.netapsys.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.netapsys.Application;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static capital.scalable.restdocs.AutoDocumentation.responseFields;
import static capital.scalable.restdocs.jackson.JacksonResultHandlers.prepareJackson;
import static org.springframework.restdocs.cli.CliDocumentation.curlRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        Application.class
})
public class UserRestTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    protected MockMvc mockMvc;

    @Autowired private WebApplicationContext webApplicationContext;
    @Autowired protected ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .alwaysDo(prepareJackson(objectMapper))
                .alwaysDo(
                        document("{method-name}",
                            preprocessRequest(),
                            preprocessResponse(prettyPrint())
                        )
                )
                .apply(documentationConfiguration(restDocumentation)
                        .snippets()
                        .withDefaults(
                                curlRequest(),
                                httpRequest(),
                                httpResponse(),
                                responseFields()
                        ))
                .build();
    }

    @Test
    public void getUser() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
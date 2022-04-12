package me.minseok.ezfarmuser.documentation;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.minseok.ezfarmuser.vo.request.RequestLogin;
import me.minseok.ezfarmuser.vo.request.RequestUser;
import me.minseok.ezfarmuser.vo.response.ResponseUser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDocumentationTest {

  @Rule
  public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private ObjectMapper objectMapper;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
        .apply(documentationConfiguration(this.restDocumentation))
        .apply(springSecurity())
        .alwaysDo(document("{method-name}/{step}/"))
        .build();
  }

  @Transactional
  @Test
  public void createAUser() throws Exception {
    createUser();
  }

  @Transactional
  @Test
  public void loginTheService() throws Exception {
    createUser();
    login();
  }

  @Transactional
  @Test
  public void getAUser() throws Exception {
    ResponseUser createUser = createUser();
    getUser(createUser.getUserId());

  }

  private void getUser(String userId) throws Exception {
    this.mockMvc.perform(get("/users/{userId}", userId).header(
            HttpHeaders.AUTHORIZATION, "Bearer accessToken"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("userId", is(notNullValue())))
        .andExpect(jsonPath("email", is(notNullValue())))
        .andExpect(jsonPath("name", is(notNullValue())))
        .andReturn();

  }

  private ResponseUser createUser() throws Exception {
    RequestUser requestUser = RequestUser.builder()
        .email("yourEmail")
        .name("yourName")
        .pwd("yourPassword")
        .build();

    ResultActions resultActions = this.mockMvc
        .perform(
            post("/users").contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(requestUser)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(" {'email':  'yourEmail'} "));

    return objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), ResponseUser.class);
  }

  private void login() throws Exception {
    RequestLogin requestLogin = RequestLogin.builder()
        .email("yourEmail")
        .password("yourPassword")
        .build();

    this.mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestLogin)))
        .andExpect(status().isOk())
        .andExpect(header().exists("token"));
  }
}

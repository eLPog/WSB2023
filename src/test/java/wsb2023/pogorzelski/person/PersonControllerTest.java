package wsb2023.pogorzelski.person;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import wsb2023.pogorzelski.controllers.PersonController;
import wsb2023.pogorzelski.services.AuthorityService;
import wsb2023.pogorzelski.services.PersonService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class PersonControllerTest {

    // znajdz ten obiekt w projekcie i wstaw go tutaj - autowired to robi


    @Autowired
    MockMvc mockMvc;

    @Autowired
    private PersonController personController;

    @MockBean
    private AuthorityService authorityService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private PersonService personService;

    @WithMockUser(username = "testowy", roles = {"MANAGE_USERS"})
    @Test
    public void getRandomNumber() throws Exception {
        mockMvc.perform(get("/person/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }
//    @WithMockUser(username = "testowy")
    @Test
    public void isEndpointForbidden() throws Exception {
        mockMvc.perform(get("/person/all"))
                .andDo(print())
                .andExpect(status().isTemporaryRedirect());
    }

    @Test
    public void controllerExist() throws Exception{
        assertThat(personController).isNotNull();
    }
}

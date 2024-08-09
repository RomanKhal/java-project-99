package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.Model.User;
import hexlet.code.Repository.UserRepository;
import hexlet.code.dto.User.UserCreateDTO;
import hexlet.code.dto.User.UserUpdateDTO;
import hexlet.code.mapper.UserMapper;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Map;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserMapper mapper;

    @Test
    public void testCreate() throws Exception {
                var data = Instancio.of(UserCreateDTO.class)
                .supply(Select.field(UserCreateDTO::getEmail), () -> faker.internet().emailAddress())
                .create();

        var request = post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn();

        var byEmail = userRepository.findByEmail(data.getEmail()).get();
        assertThat(byEmail.getFirstName()).isEqualTo(data.getFirstName());
        assertThat(byEmail.getLastName()).isEqualTo(data.getLastName());
    }

    @Test
    public void testShow() throws Exception {
        var data = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .create();

        var user = userRepository.save(data);

        var request = get("/api/users/" + user.getId());

        var response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        var body = response.getResponse().getContentAsString();

        assertThatJson(body).isObject()
                .containsAllEntriesOf(Map.of(
                        "email", data.getEmail(),
                        "firstName", data.getFirstName(),
                        "lastName", data.getLastName()
                ));
    }

    @Test
    public void testIndex() throws Exception {
        var request = get("/api/users");

        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize((int) userRepository.count())))
                .andReturn();


        var body = result.getResponse().getContentAsString();

        assertThatJson(body).isArray().size().isEqualTo(userRepository.count());
    }

    @Test
    public void testUpdate() throws Exception {
        var data = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .create();

        var user = userRepository.save(data);

        var updateData = new UserUpdateDTO();
        updateData.setFirstName(JsonNullable.of("newName"));
        updateData.setLastName(JsonNullable.of("newLastName"));
        updateData.setEmail(JsonNullable.of("newEmail@gmail.com"));
        updateData.setPassword(JsonNullable.of("newPass"));

        var request = put("/api/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(updateData));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        user = userRepository.findById(user.getId()).get();

        assertThat(user.getEmail()).isEqualTo(updateData.getEmail().get());
    }

    @Test
    public void testDelete() throws Exception {
        var data = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .create();

        var user = userRepository.save(data);

        var request = delete("/api/users/" + user.getId());

        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

}

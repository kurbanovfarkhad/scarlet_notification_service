package scarlett.notification.org.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import scarlett.notification.org.application.BaseIntegrationTest;
import scarlett.notification.org.application.presentation.crud.model.UserPreferenceModel;
import scarlett.notification.org.application.presentation.crud.service.UserPreferenceService;
import scarlett.notification.org.common.model.enums.ChannelType;
import scarlett.notification.org.persistence.entity.UserPreferenceEntity;
import scarlett.notification.org.persistence.repository.UserPreferenceRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserPreferenceControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserPreferenceRepository repository;
    @Autowired
    private UserPreferenceService service;

    private static @NotNull UserPreferenceModel createModel() {
        UserPreferenceModel model = new UserPreferenceModel();
        model.setDefaultChannelType(ChannelType.EMAIL);
        model.setUserId(UUID.randomUUID());

        return model;
    }

    @Test
    void create() throws Exception {
        // given
        UserPreferenceModel model = createModel();
        // when
        mockMvc.perform(post("/preferences")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(model)))
               // then
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.userId").value(model.getUserId().toString()))
               .andExpect(jsonPath("$.id").exists())
               .andExpect(jsonPath("$.defaultChannelType").value(model.getDefaultChannelType().name()))
        ;
        List<UserPreferenceEntity> all = repository.findAll();
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals(all.get(0).getUserId(), model.getUserId());
        Assertions.assertEquals(all.get(0).getDefaultChannelType().name(), model.getDefaultChannelType().name());
        Assertions.assertTrue(Objects.nonNull(all.get(0).getId()));
    }

    @Test
    void getAll() throws Exception {
        // given
        UserPreferenceModel model = createModel();
        var db = service.create(model);
        // when
        mockMvc.perform(get("/preferences")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(model)))
               // then
               .andExpect(status().isOk())
        ;
        List<UserPreferenceEntity> all = repository.findAll();
        Assertions.assertFalse(all.isEmpty());
        UserPreferenceEntity entity = all.stream()
                                         .filter(e -> Objects.equals(e.getId(), db.getId()))
                                         .findFirst()
                                         .orElseThrow();
        Assertions.assertEquals(model.getUserId(), entity.getUserId());
        Assertions.assertEquals(model.getDefaultChannelType().name(), entity.getDefaultChannelType().name());
    }

    @Test
    void update() throws Exception {
        // given
        UserPreferenceModel eventModel = createModel();
        UserPreferenceModel db = service.create(eventModel);
        db.setDefaultChannelType(ChannelType.SMS);
        // when
        mockMvc.perform(patch("/preferences")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(db)))
               // then
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.userId").value(eventModel.getUserId().toString()))
               .andExpect(jsonPath("$.id").exists())
               .andExpect(jsonPath("$.defaultChannelType").value(db.getDefaultChannelType().name()))
        ;
        List<UserPreferenceEntity> all = repository.findAll();
        Assertions.assertFalse(all.isEmpty());
        UserPreferenceEntity eventEntity = all.stream()
                                              .filter(e -> Objects.equals(e.getId(), db.getId()))
                                              .findFirst()
                                              .orElseThrow();
        Assertions.assertEquals(eventEntity.getUserId(), db.getUserId());
        Assertions.assertEquals(eventEntity.getDefaultChannelType().name(), db.getDefaultChannelType().name());
    }
}

package scarlett.notification.org.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import scarlett.notification.org.application.BaseIntegrationTest;
import scarlett.notification.org.application.presentation.crud.model.EventModel;
import scarlett.notification.org.application.presentation.crud.service.EventService;
import scarlett.notification.org.common.model.enums.SerializationType;
import scarlett.notification.org.persistence.entity.EventEntity;
import scarlett.notification.org.persistence.repository.EventRepository;

import java.util.List;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventService eventService;

    private static @NotNull EventModel createModel() {
        EventModel eventModel = new EventModel();
        eventModel.setDescription("test");
        eventModel.setName("TEST_EVENT");
        eventModel.setSchemaType(SerializationType.AVRO);
        return eventModel;
    }

    @BeforeEach
    void setup() {
        eventRepository.deleteAll();
    }

    @Test
    void create() throws Exception {
        // given
        EventModel eventModel = createModel();
        // when
        mockMvc.perform(post("/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(eventModel)))
               // then
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value(eventModel.getName()))
               .andExpect(jsonPath("$.id").exists())
               .andExpect(jsonPath("$.description").value(eventModel.getDescription()))
        ;
        List<EventEntity> all = eventRepository.findAll();
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals(all.get(0).getName(), eventModel.getName());
        Assertions.assertEquals(all.get(0).getDescription(), eventModel.getDescription());
        Assertions.assertEquals(all.get(0).getSchemaType(), eventModel.getSchemaType());
        Assertions.assertTrue(Objects.nonNull(all.get(0).getId()));
    }

    @Test
    void getAll() throws Exception {
        // given
        EventModel eventModel = createModel();
        var db = eventService.create(eventModel);
        // when
        mockMvc.perform(get("/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(eventModel)))
               // then
               .andExpect(status().isOk())
        ;
        List<EventEntity> all = eventRepository.findAll();
        Assertions.assertFalse(all.isEmpty());
        EventEntity eventEntity = all.stream()
                                     .filter(e -> Objects.equals(e.getId(), db.getId()))
                                     .findFirst()
                                     .orElseThrow();
        Assertions.assertEquals(eventModel.getName(), eventEntity.getName());
        Assertions.assertEquals(eventModel.getDescription(), eventEntity.getDescription());
        Assertions.assertEquals(eventModel.getSchemaType(), eventEntity.getSchemaType());
    }

    @Test
    void update() throws Exception {
        // given
        EventModel eventModel = createModel();
        EventModel db = eventService.create(eventModel);
        db.setDescription("new description");
        // when
        mockMvc.perform(patch("/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(db)))
               // then
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value(eventModel.getName()))
               .andExpect(jsonPath("$.id").exists())
               .andExpect(jsonPath("$.description").value(db.getDescription()))
        ;
        List<EventEntity> all = eventRepository.findAll();
        Assertions.assertFalse(all.isEmpty());
        EventEntity eventEntity = all.stream()
                                     .filter(e -> Objects.equals(e.getId(), db.getId()))
                                     .findFirst()
                                     .orElseThrow();
        Assertions.assertEquals(eventEntity.getName(), db.getName());
        Assertions.assertEquals(eventEntity.getDescription(), db.getDescription());
        Assertions.assertEquals(eventEntity.getSchemaType(), db.getSchemaType());
    }
}

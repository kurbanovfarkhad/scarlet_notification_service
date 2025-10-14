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
import org.springframework.transaction.annotation.Transactional;
import scarlett.notification.org.application.BaseIntegrationTest;
import scarlett.notification.org.application.presentation.crud.model.TemplateModel;
import scarlett.notification.org.application.presentation.crud.model.TemplateTranslationModel;
import scarlett.notification.org.application.presentation.crud.service.TemplateService;
import scarlett.notification.org.common.model.enums.ChannelType;
import scarlett.notification.org.common.model.enums.LocaleEmbeddable;
import scarlett.notification.org.common.model.enums.Priority;
import scarlett.notification.org.persistence.entity.TemplateEntity;
import scarlett.notification.org.persistence.repository.TemplateRepository;

import java.util.List;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TemplateControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private TemplateService templateService;

    private static @NotNull TemplateModel createModel() {
        TemplateTranslationModel ru = new TemplateTranslationModel();
        ru.setBody("ru body");
        ru.setName("ru name");
        ru.setSubject("ru subject");
        ru.setLocale(LocaleEmbeddable.ru);

        TemplateModel templateModel = new TemplateModel();
        templateModel.setAllowedChannel(List.of(ChannelType.SMS, ChannelType.PUSH));
        templateModel.setTranslations(List.of(ru));
        templateModel.setDeliveryAttempts(3);
        templateModel.setDefaultChannel(ChannelType.EMAIL);
        templateModel.setPriority(Priority.HIGH);
        return templateModel;
    }

    @Test
    void create() throws Exception {
        // given
        TemplateModel templateModel = createModel();
        // when
        mockMvc.perform(post("/templates")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(templateModel)))
               // then
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.allowedChannel").isArray())
               .andExpect(jsonPath("$.allowedChannel").isNotEmpty())
               .andExpect(jsonPath("$.id").exists())
               .andExpect(jsonPath("$.deliveryAttempts").value(templateModel.getDeliveryAttempts()))
               .andExpect(jsonPath("$.translations[0].name").value(templateModel.getTranslations().get(0).getName()))
               .andExpect(jsonPath("$.translations[0].body").value(templateModel.getTranslations().get(0).getBody()))
               .andExpect(jsonPath("$.translations[0].locale").value(
                       templateModel.getTranslations().get(0).getLocale().name()))
               .andExpect(
                       jsonPath("$.translations[0].subject").value(templateModel.getTranslations().get(0).getSubject()))
        ;
        List<TemplateEntity> all = templateRepository.findAll();
        Assertions.assertEquals(1, all.size());
        Assertions.assertTrue(Objects.nonNull(all.get(0).getId()));
    }

    @Transactional
    @Test
    void getAll() throws Exception {
        // given
        TemplateModel templateModel = createModel();
        var db = templateService.create(templateModel);
        // when
        mockMvc.perform(get("/templates")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(templateModel)))
               // then
               .andExpect(status().isOk())
        ;
        List<TemplateEntity> all = templateRepository.findAll();
        Assertions.assertFalse(all.isEmpty());
        TemplateEntity templateEntity = all.stream()
                                           .filter(e -> Objects.equals(e.getId(), db.getId()))
                                           .findFirst()
                                           .orElseThrow();
        Assertions.assertEquals(templateEntity.getTranslations().size(), db.getTranslations().size());
        Assertions.assertEquals(templateEntity.getAllowedChannel().size(), db.getAllowedChannel().size());
    }

    @Transactional
    @Test
    void update() throws Exception {
        // given
        TemplateModel templateModel = createModel();
        TemplateModel db = templateService.create(templateModel);
        // when
        mockMvc.perform(patch("/templates")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(db)))
               // then
               .andExpect(status().isOk())
        ;
        List<TemplateEntity> all = templateRepository.findAll();
        Assertions.assertFalse(all.isEmpty());
        TemplateEntity templateEntity = all.stream()
                                           .filter(e -> Objects.equals(e.getId(), db.getId()))
                                           .findFirst()
                                           .orElseThrow();
        Assertions.assertEquals(templateEntity.getTranslations().size(), db.getTranslations().size());
    }
}

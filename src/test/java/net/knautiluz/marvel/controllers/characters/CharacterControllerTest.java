package net.knautiluz.marvel.controllers.characters;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.knautiluz.marvel.models.character.Character;
import net.knautiluz.marvel.repository.characters.CharacterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CharacterController.class)
public class CharacterControllerTest {

    @Autowired
    private MockMvc simulatedRequest;

    @MockBean
    private CharacterRepository mockedRepository;

    @Test
    public void givenSomeCharacterWhenCharacterNotExistsThenMustReturnNotFound() throws Exception {
        MvcResult result = simulatedRequest.perform(get("/characters/1")).andExpect(status().isNotFound()).andReturn();
        Assertions.assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    public void givenCharacterRequestWhenCharacterExistThenMustReturnCharacter() throws Exception {
        Optional<Character> character = Optional.ofNullable(Character.builder().id(1).name("Thanos").build());
        Mockito.when(mockedRepository.findOne(Mockito.any(Specification.class))).thenReturn(character);
        MvcResult result = simulatedRequest.perform(get("/characters/1")).andExpect(status().isOk()).andReturn();
        JsonElement json = new JsonParser().parse(result.getResponse().getContentAsString());
        String charName = json.getAsJsonObject().get("name").getAsString();
        Assertions.assertEquals(character.get().getName(), charName);
    }

    @Test
    public void givenCharacterPostWhenPostIsSuccessfullyThenShouldReturnRightStatus() throws Exception {
        Character character = Character.builder().id(1).name("Thanos").build();
        final String characterGson = new Gson().toJson(character);
        Mockito.when(mockedRepository.save(Mockito.any())).thenReturn(character);
        MvcResult result = simulatedRequest
                .perform(post("/characters").contentType(MediaType.APPLICATION_JSON).content(characterGson))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    public void givenCharacterPostWhenCharactersNameExistThenShouldReturnConflict() throws Exception {
        Optional<Character> character = Optional.ofNullable(Character.builder().id(1).name("Thanos").build());
        final String characterGson = new Gson().toJson(character.get());
        Mockito.when(mockedRepository.findOne(Mockito.any(Specification.class))).thenReturn(character);
        MvcResult result = simulatedRequest
                .perform(post("/characters").contentType(MediaType.APPLICATION_JSON).content(characterGson))
                .andExpect(status().isConflict()).andReturn();
    }

    @Test
    public void givenCharacterPutWhenPutIsSuccessfullyThenShouldReturnNoContentStatus() throws Exception {
        Character character = Character.builder().id(1).name("Thanos").build();
        final String characterGson = new Gson().toJson(character);
        Mockito.when(mockedRepository.findOne(Mockito.any(Specification.class))).thenReturn(Optional.of(character));
        Mockito.when(mockedRepository.save(Mockito.any())).thenReturn(character);
        MvcResult result = simulatedRequest
                .perform(put("/characters/1").contentType(MediaType.APPLICATION_JSON).content(characterGson))
                .andExpect(status().isNoContent()).andReturn();
    }

    @Test
    public void givenCharacterPutWhenCharacterNotExistsThenShouldReturnNotFoundStatus() throws Exception {
        Character character = Character.builder().id(1).name("Thanos").build();
        final String characterGson = new Gson().toJson(character);
        Mockito.when(mockedRepository.save(Mockito.any())).thenReturn(character);
        MvcResult result = simulatedRequest
                .perform(put("/characters/1").contentType(MediaType.APPLICATION_JSON).content(characterGson))
                .andExpect(status().isNotFound()).andReturn();
    }

    @Test
    public void givenCharacterDeleteWhenIdIsFoundThenReturnOkStatus() throws Exception {
        Character character = Character.builder().id(1).name("Thanos").build();
        final String characterGson = new Gson().toJson(character);
        Mockito.when(mockedRepository.findOne(Mockito.any(Specification.class))).thenReturn(Optional.of(character));
        MvcResult result = simulatedRequest
                .perform(delete("/characters/1").contentType(MediaType.APPLICATION_JSON).content(characterGson))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void givenCharacterDeleteWhenIdIsNotFoundThenReturnNotFoundStatus() throws Exception {
        Character character = Character.builder().id(1).name("Thanos").build();
        final String characterGson = new Gson().toJson(character);
        MvcResult result = simulatedRequest
                .perform(delete("/characters/1").contentType(MediaType.APPLICATION_JSON).content(characterGson))
                .andExpect(status().isNotFound()).andReturn();
    }

}

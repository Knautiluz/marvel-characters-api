package net.knautiluz.marvel.controllers.characters;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        JsonElement json = new JsonParser().parse(result.getResponse().getContentAsString());
        String status = json.getAsJsonObject().get("status").getAsString();
        Assertions.assertEquals("Character not found.", status);
    }

    @Test
    public void givenSomeCharacterWhenCharacterExistsThenMustBeOneCharacterPresent() throws Exception {
        final int characterId = 1;
        Optional<Character> character = Optional.ofNullable(Character.builder().id(characterId).name("Thanos").build());
        Mockito.when(mockedRepository.findOne(Mockito.any(Specification.class))).thenReturn(character);
        MvcResult result = simulatedRequest.perform(get("/characters/1")).andExpect(status().isOk()).andReturn();
        JsonElement json = new JsonParser().parse(result.getResponse().getContentAsString());
        String charName = json.getAsJsonObject().get("name").getAsString();
        Assertions.assertEquals(character.get().getName(), charName);
    }

}

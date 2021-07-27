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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CharactersController.class)
public class CharactersControllerTest {

    @Autowired
    private MockMvc simulatedRequest;

    @MockBean
    private CharacterRepository mockedRepository;

    @Test
    public void givenFullRequestWhenNoFiltersIsPresentThenMustReturnAllContent() throws Exception {
        List<Character> characters = new ArrayList<>();
        characters.add(Character.builder().name("Thanos").build());
        characters.add(Character.builder().name("Thanatos").build());
        characters.add(Character.builder().name("Hades").build());
        Page<Character> chars = new PageImpl<>(characters);
        Mockito.when(mockedRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(chars);
        MvcResult result = simulatedRequest.perform(get("/characters")).andExpect(status().isOk()).andReturn();
        JsonElement json = new JsonParser().parse(result.getResponse().getContentAsString());
        int returnedChars = json.getAsJsonObject().get("data").getAsJsonObject().get("results").getAsJsonArray().size();
        Assertions.assertEquals(3, returnedChars);
    }

    @Test
    public void givenFullRequestWhenFilterLimitIsOneThenShouldReturn200OK() throws Exception {
        List<Character> characters = new ArrayList<>();
        characters.add(Character.builder().name("Thanos").build());
        Page<Character> chars = new PageImpl<>(characters);
        Mockito.when(mockedRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(chars);
        simulatedRequest.perform(get("/characters").param("limit", "1")).andExpect(status().isOk());
    }

    @Test
    public void givenFullRequestWhenFilterNameEqualsThenShouldReturn200OK() throws Exception {
        List<Character> characters = new ArrayList<>();
        characters.add(Character.builder().name("Thanos").build());
        Page<Character> chars = new PageImpl<>(characters);
        Mockito.when(mockedRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(chars);
        simulatedRequest.perform(get("/characters").param("name", "Thanos")).andExpect(status().isOk());
    }

    @Test
    public void givenFullRequestWhenFilterNameEqualsAndIsEmptyThenShouldReturn409() throws Exception {
        List<Character> characters = new ArrayList<>();
        characters.add(Character.builder().name("Thanos").build());
        Page<Character> chars = new PageImpl<>(characters);
        Mockito.when(mockedRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(chars);
        MvcResult result = simulatedRequest.perform(get("/characters").param("name", "")).andExpect(status().isConflict()).andReturn();
        JsonElement json = new JsonParser().parse(result.getResponse().getContentAsString());
        Assertions.assertEquals("Empty parameter for name.", json.getAsJsonObject().get("status").getAsString());
    }

    @Test
    public void givenFullRequestWhenFilterNameStartWithThenShouldReturn200OK() throws Exception {
        List<Character> characters = new ArrayList<>();
        characters.add(Character.builder().name("Thanos").build());
        Page<Character> chars = new PageImpl<>(characters);
        Mockito.when(mockedRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(chars);
        simulatedRequest.perform(get("/characters").param("nameStartWith", "Tha")).andExpect(status().isOk());
    }

    @Test
    public void givenFullRequestWhenFilterNameStartWithAndEmptyThenShouldReturn409() throws Exception {
        List<Character> characters = new ArrayList<>();
        characters.add(Character.builder().name("Thanos").build());
        Page<Character> chars = new PageImpl<>(characters);
        Mockito.when(mockedRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(chars);
        MvcResult result = simulatedRequest.perform(get("/characters").param("nameStartWith", "")).andExpect(status().isConflict()).andReturn();
        JsonElement json = new JsonParser().parse(result.getResponse().getContentAsString());
        Assertions.assertEquals("Empty parameter for nameStartWith.", json.getAsJsonObject().get("status").getAsString());
    }

    @Test
    public void givenFullRequestWhenFilterLimitBelowOneThenShouldReturn409() throws Exception {
        List<Character> characters = new ArrayList<>();
        characters.add(Character.builder().name("Thanos").build());
        Page<Character> chars = new PageImpl<>(characters);
        Mockito.when(mockedRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(chars);
        MvcResult result = simulatedRequest.perform(get("/characters").param("limit", "0")).andExpect(status().isConflict()).andReturn();
        JsonElement json = new JsonParser().parse(result.getResponse().getContentAsString());
        Assertions.assertEquals("Limit invalid or below 1.", json.getAsJsonObject().get("status").getAsString());
    }

    @Test
    public void givenFullRequestWhenFilterLimitAbove100ThenShouldReturn409() throws Exception {
        List<Character> characters = new ArrayList<>();
        characters.add(Character.builder().name("Thanos").build());
        Page<Character> chars = new PageImpl<>(characters);
        Mockito.when(mockedRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(chars);
        MvcResult result = simulatedRequest.perform(get("/characters").param("limit", "101")).andExpect(status().isConflict()).andReturn();
        JsonElement json = new JsonParser().parse(result.getResponse().getContentAsString());
        Assertions.assertEquals("Limit greater than 100.", json.getAsJsonObject().get("status").getAsString());
    }

    @Test
    public void givenReversedCharacterNamesListWhenFilterOrderByNameIsPresentThenMustReturnOrderedContent() throws Exception {
        List<Character> characters = new ArrayList<>();
        characters.add(Character.builder().name("Zantetsuken").modified(new Date()).build());
        characters.add(Character.builder().name("Barognar").modified(new Date()).build());
        characters.add(Character.builder().name("Abimael").modified(new Date()).build());
        Page<Character> chars = new PageImpl<>(characters);
        Mockito.when(mockedRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(chars);
        MvcResult result = simulatedRequest.perform(get("/characters").param("orderBy", "name")).andExpect(status().isOk()).andReturn();
        JsonElement json = new JsonParser().parse(result.getResponse().getContentAsString());
        String charOneName = json.getAsJsonObject().get("data").getAsJsonObject().get("results").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
        String charTwoName = json.getAsJsonObject().get("data").getAsJsonObject().get("results").getAsJsonArray().get(1).getAsJsonObject().get("name").getAsString();
        String charThreeName = json.getAsJsonObject().get("data").getAsJsonObject().get("results").getAsJsonArray().get(2).getAsJsonObject().get("name").getAsString();
        Assertions.assertEquals("Abimael", charOneName);
        Assertions.assertEquals("Barognar", charTwoName);
        Assertions.assertEquals("Zantetsuken", charThreeName);
    }

    @Test
    public void givenOrderedCharacterNamesListWhenFilterOrderByNameReverseIsPresentThenMustReturnRevertedContent() throws Exception {
        List<Character> characters = new ArrayList<>();
        characters.add(Character.builder().name("Abimael").modified(new Date()).build());
        characters.add(Character.builder().name("Barognar").modified(new Date()).build());
        characters.add(Character.builder().name("Zantetsuken").modified(new Date()).build());
        Page<Character> chars = new PageImpl<>(characters);
        Mockito.when(mockedRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(chars);
        MvcResult result = simulatedRequest.perform(get("/characters").param("orderBy", "-name")).andExpect(status().isOk()).andReturn();
        JsonElement json = new JsonParser().parse(result.getResponse().getContentAsString());
        String charOneName = json.getAsJsonObject().get("data").getAsJsonObject().get("results").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
        String charTwoName = json.getAsJsonObject().get("data").getAsJsonObject().get("results").getAsJsonArray().get(1).getAsJsonObject().get("name").getAsString();
        String charThreeName = json.getAsJsonObject().get("data").getAsJsonObject().get("results").getAsJsonArray().get(2).getAsJsonObject().get("name").getAsString();
        Assertions.assertEquals("Zantetsuken", charOneName);
        Assertions.assertEquals("Barognar", charTwoName);
        Assertions.assertEquals("Abimael", charThreeName);
    }

    @Test
    public void givenOrderedCharacterModifiedListWhenFilterOrderByModifiedIsPresentThenMustReturnOrderedContent() throws Exception {
        List<Character> characters = new ArrayList<>();
        characters.add(Character.builder().name("Abimael").modified(Date.from(Instant.ofEpochMilli(100001))).build());
        characters.add(Character.builder().name("Barognar").modified(Date.from(Instant.ofEpochMilli(100002))).build());
        characters.add(Character.builder().name("Zantetsuken").modified(Date.from(Instant.ofEpochMilli(100003))).build());
        Page<Character> chars = new PageImpl<>(characters);
        Mockito.when(mockedRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(chars);
        MvcResult result = simulatedRequest.perform(get("/characters").param("orderBy", "modified")).andExpect(status().isOk()).andReturn();
        JsonElement json = new JsonParser().parse(result.getResponse().getContentAsString());
        String charOneName = json.getAsJsonObject().get("data").getAsJsonObject().get("results").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
        String charTwoName = json.getAsJsonObject().get("data").getAsJsonObject().get("results").getAsJsonArray().get(1).getAsJsonObject().get("name").getAsString();
        String charThreeName = json.getAsJsonObject().get("data").getAsJsonObject().get("results").getAsJsonArray().get(2).getAsJsonObject().get("name").getAsString();
        Assertions.assertEquals("Abimael", charOneName);
        Assertions.assertEquals("Barognar", charTwoName);
        Assertions.assertEquals("Zantetsuken", charThreeName);
    }

    @Test
    public void givenOrderedCharacterModifiedListWhenFilterOrderByModifiedIsPresentThenMustReturnReversedContent() throws Exception {
        List<Character> characters = new ArrayList<>();
        characters.add(Character.builder().name("Abimael").modified(Date.from(Instant.ofEpochMilli(100001))).build());
        characters.add(Character.builder().name("Barognar").modified(Date.from(Instant.ofEpochMilli(100002))).build());
        characters.add(Character.builder().name("Zantetsuken").modified(Date.from(Instant.ofEpochMilli(100003))).build());
        Page<Character> chars = new PageImpl<>(characters);
        Mockito.when(mockedRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(chars);
        MvcResult result = simulatedRequest.perform(get("/characters").param("orderBy", "-modified")).andExpect(status().isOk()).andReturn();
        JsonElement json = new JsonParser().parse(result.getResponse().getContentAsString());
        String charOneName = json.getAsJsonObject().get("data").getAsJsonObject().get("results").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
        String charTwoName = json.getAsJsonObject().get("data").getAsJsonObject().get("results").getAsJsonArray().get(1).getAsJsonObject().get("name").getAsString();
        String charThreeName = json.getAsJsonObject().get("data").getAsJsonObject().get("results").getAsJsonArray().get(2).getAsJsonObject().get("name").getAsString();
        Assertions.assertEquals("Zantetsuken", charOneName);
        Assertions.assertEquals("Barognar", charTwoName);
        Assertions.assertEquals("Abimael", charThreeName);
    }

    @Test
    public void givenRequestWhenFilterInvalidOrderByIsPresentThenMustReturn409() throws Exception {
        List<Character> characters = new ArrayList<>();
        characters.add(Character.builder().name("Abimael").modified(Date.from(Instant.ofEpochMilli(100001))).build());
        Page<Character> chars = new PageImpl<>(characters);
        Mockito.when(mockedRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(chars);
        MvcResult result = simulatedRequest.perform(get("/characters").param("orderBy", "-fuck")).andExpect(status().isConflict()).andReturn();
        JsonElement json = new JsonParser().parse(result.getResponse().getContentAsString());
        Assertions.assertEquals("Invalid or unrecognized ordering parameter.", json.getAsJsonObject().get("status").getAsString());
    }

    @Test
    public void givenOffsetParameterWhenTheOffsetParameterIsTwoThenShouldIgonreTwoFirstResults() throws Exception {
        List<Character> characters = new ArrayList<>();
        characters.add(Character.builder().name("Abimael").modified(new Date()).build());
        characters.add(Character.builder().name("Barognar").modified(new Date()).build());
        characters.add(Character.builder().name("Zantetsuken").modified(new Date()).build());
        Page<Character> chars = new PageImpl<>(characters);
        Mockito.when(mockedRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(chars);
        MvcResult result = simulatedRequest.perform(get("/characters").param("offset", "2")).andExpect(status().isOk()).andReturn();
        JsonElement json = new JsonParser().parse(result.getResponse().getContentAsString());
        String charOneName = json.getAsJsonObject().get("data").getAsJsonObject().get("results").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
        int returnedChars = json.getAsJsonObject().get("data").getAsJsonObject().get("results").getAsJsonArray().size();
        Assertions.assertEquals(1, returnedChars);
        Assertions.assertEquals(characters.get(2).getName(), charOneName);
    }
}

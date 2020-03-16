package net.knautiluz.marvel.controllers.characters;

import net.knautiluz.marvel.models.character.Character;
import net.knautiluz.marvel.repository.characters.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static net.knautiluz.marvel.matchers.CharacterMatchers.idEquals;

@SuppressWarnings("unused")
@RestController
public class CharacterController {

    @Autowired
    private CharacterRepository repo;

    @GetMapping(value = "/characters/{characterId}")
    public ResponseEntity<Object> getCharacter(@PathVariable int characterId) {
        final Optional<Character> character = repo.findOne(Specification.where(idEquals(characterId)));
        return validateCharacterResponse(character);
    }

    private ResponseEntity<Object> validateCharacterResponse(Optional<Character> character) {
        if(!character.isPresent()) {
            final Map<String, Object> notFound = new HashMap<>();
            notFound.put("code", HttpStatus.NOT_FOUND.value());
            notFound.put("status", "Character not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(character);
        }
    }

}

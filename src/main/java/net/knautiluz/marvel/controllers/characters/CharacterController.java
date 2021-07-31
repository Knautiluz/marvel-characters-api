package net.knautiluz.marvel.controllers.characters;

import net.knautiluz.marvel.matchers.CharacterMatchers;
import net.knautiluz.marvel.models.character.Character;
import net.knautiluz.marvel.repository.characters.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.Optional;

import static net.knautiluz.marvel.matchers.CharacterMatchers.idEquals;

@SuppressWarnings("unused")
@RestController
public class CharacterController {

    @Autowired
    private CharacterRepository repo;

    @GetMapping(value = "/characters/{characterId}", produces = "application/json")
    public ResponseEntity<Object> getCharacter(@PathVariable int characterId) {
        final Optional<Character> character = repo.findOne(Specification.where(idEquals(characterId)));
        return character.<ResponseEntity<Object>>map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/characters", consumes = "application/json")
    public ResponseEntity<Object> createCharacter(@RequestBody Character character) {
        final Optional<Character> optCharacter = repo.findOne(CharacterMatchers.nameIs(character.getName()));
        if(optCharacter.isPresent())
            return ResponseEntity.status(409).build();
        character.setModified(new Date());
        Character result = repo.save(character);
        return ResponseEntity.created(
            UriComponentsBuilder.fromUriString(String.format("https://knautiluz-characters.herokuapp.com/characters/%d", result.getId()))
            .build()
            .toUri()
        ).build();
    }

    @PutMapping(value = "/characters/{characterId}", consumes = "application/json")
    public ResponseEntity<Object> createCharacter(@RequestBody Character character, @PathVariable int characterId) {
        final Optional<Character> optCharacter = repo.findOne(Specification.where(idEquals(characterId)));
        if(!optCharacter.isPresent())
            return ResponseEntity.notFound().build();
        character.setId(characterId);
        repo.save(character);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/characters/{characterId}")
    public ResponseEntity<Object> deleteCharacter(@PathVariable int characterId) {
        final Optional<Character> character = repo.findOne(Specification.where(idEquals(characterId)));
            if(!character.isPresent())
                return ResponseEntity.notFound().build();
        repo.deleteById(characterId);
        return ResponseEntity.ok().build();
    }

}

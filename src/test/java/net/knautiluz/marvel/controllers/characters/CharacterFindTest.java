package net.knautiluz.marvel.controllers.characters;

import net.knautiluz.marvel.matchers.CharacterMatchers;
import net.knautiluz.marvel.models.character.Character;
import net.knautiluz.marvel.repository.characters.CharacterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CharacterFindTest {

    @Autowired
    private CharacterRepository repository;

    @Test
    public void givenCharacterSearchWhenParameterIsNameStartWithThenShouldReturnTwo() {
        List<Character> characters = new ArrayList<>();
        characters.add(Character.builder().name("Thanos").build());
        characters.add(Character.builder().name("Thanatos").build());
        characters.add(Character.builder().name("Hades").build());
        characters.forEach(ch -> repository.save(ch));
        List<Character> result = repository.findAll(Specification.where(CharacterMatchers.nameStartWith("Tha")));
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void givenCharacterSearchWhenParameterIsNameEqualsThenShouldReturnOne() {
        List<Character> characters = new ArrayList<>();
        characters.add(Character.builder().name("Thanos").build());
        characters.add(Character.builder().name("Thanatos").build());
        characters.add(Character.builder().name("Hades").build());
        characters.forEach(ch -> repository.save(ch));
        List<Character> result = repository.findAll(Specification.where(CharacterMatchers.nameIs("Thanatos")));
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void givenCharacterSearchWhenParameterIsModifiedSincePastThenShouldReturnOne() {
        Character chara = Character.builder().name("Thanos").modified(new Date()).build();
        repository.save(chara);
        List<Character> result = repository.findAll(Specification.where(CharacterMatchers.modifiedSince(Date.from(Instant.EPOCH))));
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void givenCharacterSearchWhenParameterIsModifiedSinceNowThenShouldReturnZero() {
        Character chara = Character.builder().name("Thanos").modified(new Date()).build();
        repository.save(chara);
        List<Character> result = repository.findAll(Specification.where(CharacterMatchers.modifiedSince(new Date())));
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void givenSearchSince1970WhenTheLimitIsOneThenShouldReturnOne() {
        List<Character> characters = new ArrayList<>();
        characters.add(Character.builder().name("Thanos").modified(new Date()).build());
        characters.add(Character.builder().name("Thanatos").modified(new Date()).build());
        characters.add(Character.builder().name("Hades").modified(new Date()).build());
        characters.forEach(ch -> repository.save(ch));
        Page<Character> result = repository.findAll(Specification.where(CharacterMatchers.modifiedSince(Date.from(Instant.EPOCH))), PageRequest.of(0, 1));
        Assertions.assertEquals(1, result.toList().size());
    }
}

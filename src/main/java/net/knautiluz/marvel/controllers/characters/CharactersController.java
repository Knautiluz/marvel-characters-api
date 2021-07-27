package net.knautiluz.marvel.controllers.characters;

import com.mysql.jdbc.StringUtils;
import net.knautiluz.marvel.models.character.Character;
import net.knautiluz.marvel.repository.characters.CharacterRepository;
import net.knautiluz.marvel.wrapper.builder.character.CharacterDataContainer;
import net.knautiluz.marvel.wrapper.builder.character.CharacterDataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static net.knautiluz.marvel.matchers.CharacterMatchers.*;
import static org.apache.tomcat.util.security.ConcurrentMessageDigest.digest;
import static org.apache.tomcat.util.security.MD5Encoder.encode;

@SuppressWarnings("unused")
@RestController
public class CharactersController {

    @Autowired
    private CharacterRepository repo;

    @GetMapping(path = "/characters", produces = "application/json")
    public ResponseEntity<Object> getCharacters
    (
        @RequestParam(required = false, defaultValue = "20") Integer limit,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String nameStartWith,
        @RequestParam(required = false) Date modifiedSince,
        @RequestParam(required = false, defaultValue = "name") String orderBy,
        @RequestParam(required = false, defaultValue = "0") Integer offset
    ) {
        final ResponseEntity<Object> validation = validateParameters(orderBy, limit, name, nameStartWith);
        if (validation != null) return validation;
        Page<Character> characters = repo.findAll(Specification
                .where(nameIs(name))
                .and(nameStartWith(nameStartWith))
                .and(modifiedSince(modifiedSince)), PageRequest.of(0, limit));
        CharacterDataContainer characterContainer = buildCharacterDataContainer(characters.toList(), orderBy, limit, offset);
        return ResponseEntity.status(200).body(buildCharacterDataContainer(characterContainer));
    }

    private ResponseEntity<Object> validateParameters(String orderBy, Integer limit, String name, String nameStartWith) {
        final Map<String, Object> errorMap = new HashMap<>();
        final String status = "status";
        final String code = "code";
        if(!(orderBy.equals("name") || orderBy.equals("-name") || orderBy.equals("modified") || orderBy.equals("-modified"))) {
            errorMap.put(code, HttpStatus.CONFLICT.value());
            errorMap.put(status, "Invalid or unrecognized ordering parameter.");
        }
        if(name != null && StringUtils.isEmptyOrWhitespaceOnly(name)) {
            errorMap.put(code, HttpStatus.CONFLICT.value());
            errorMap.put(status, "Empty parameter for name.");
        }
        if(nameStartWith != null && StringUtils.isEmptyOrWhitespaceOnly(nameStartWith)) {
            errorMap.put(code, HttpStatus.CONFLICT.value());
            errorMap.put(status, "Empty parameter for nameStartWith.");
        }
        if(limit < 1) {
            errorMap.put(code, HttpStatus.CONFLICT.value());
            errorMap.put(status, "Limit invalid or below 1.");
        }
        if(limit > 100) {
            errorMap.put(code, HttpStatus.CONFLICT.value());
            errorMap.put(status, "Limit greater than 100.");
        }
        if(errorMap.isEmpty()) {
            return null;
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMap);
        }
    }

    private CharacterDataWrapper buildCharacterDataContainer(CharacterDataContainer characterContainer) {
        CharacterDataWrapper builder = CharacterDataWrapper.builder()
                .code(200)
                .status("OK")
                .attributionText("Data provided by Jackson Jones. © 2020 Knautiluz")
                .attributionHTML("<a href='http://marvel.com'>Data provided by Jackson Jones. © 2020 Knautiluz</a>")
                .copyright("© 2020 Knautiluz")
                .data(characterContainer)
                .build();
        builder.setEtag(encode(digest("MD5", builder.getData().toString().getBytes())));
        return  builder;
    }

    private CharacterDataContainer buildCharacterDataContainer(List<Character> characters, String orderBy, Integer limit, Integer offset) {
        List<Character> offsetedChars = characters.stream().skip(offset).collect(Collectors.toList());
        if(orderBy != null && orderBy.equals("name")) {
            offsetedChars.sort(Comparator.comparing(Character::getName));
        }
        if(orderBy != null && orderBy.equals("-name")) {
            offsetedChars.sort(Comparator.comparing(Character::getName).reversed());
        }
        if(orderBy != null && orderBy.equals("modified")) {
            offsetedChars.sort(Comparator.comparing(Character::getModified));
        }
        if(orderBy != null && orderBy.equals("-modified")) {
            offsetedChars.sort(Comparator.comparing(Character::getModified).reversed());
        }
        return CharacterDataContainer.builder()
                .count(characters.size())
                .limit(limit)
                .offset(offset)
                .results(offsetedChars)
                .total(characters.size())
                .build();
    }

}

package net.knautiluz.marvel.repository.characters;

import net.knautiluz.marvel.models.character.Character;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<Character, Integer>, JpaSpecificationExecutor<Character> {

}

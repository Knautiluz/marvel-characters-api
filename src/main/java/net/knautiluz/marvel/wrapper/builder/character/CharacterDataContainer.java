package net.knautiluz.marvel.wrapper.builder.character;

import lombok.Builder;
import lombok.Data;
import net.knautiluz.marvel.models.character.Character;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class CharacterDataContainer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * (int, optional): The requested offset (number of skipped results) of the call.
     */
    private int offset;

    /**
     *  (int, optional): The requested result limit.
     */
    private int limit;

    /**
     *  (int, optional): The total number of resources available given the current filter set.
     */
    private int total;

    /**
     *  (int, optional): The total number of results returned by this call.,
     */
    private int count;

    /**
     *  (Array[Character], optional): The list of characters returned by the call.
     */
    private List<Character> results;
}

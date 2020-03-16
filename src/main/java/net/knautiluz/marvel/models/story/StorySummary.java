package net.knautiluz.marvel.models.story;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StorySummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private int id;


    /**
     * @deprecated até encontrar uma forma de remove-lo do builder do lombok
     */
    @ManyToOne
    @JsonIgnore
    @Deprecated
    private StoryList storyList;

    /**
     *  (string, optional): The path to the individual story resource.
     */
    private String resourceURI;

    /**
     * (string, optional): The canonical name of the story.
     */
    private String name;

    /**
     *  (string, optional): The type of the story (interior or cover).
     */
    private String type;

}

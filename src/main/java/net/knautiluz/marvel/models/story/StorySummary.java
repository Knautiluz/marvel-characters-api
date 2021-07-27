package net.knautiluz.marvel.models.story;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StorySummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Builder
    public StorySummary(int id, String resourceURI, String name, String type) {
        this.id = id;
        this.resourceURI = resourceURI;
        this.name = name;
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "story_summary_seq")
    @JsonIgnore
    private int id;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
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

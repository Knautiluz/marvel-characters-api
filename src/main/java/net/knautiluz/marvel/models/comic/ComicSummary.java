package net.knautiluz.marvel.models.comic;

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
public class ComicSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Builder
    public ComicSummary(int id, String resourceURI, String name) {
        this.id = id;
        this.resourceURI = resourceURI;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comic_summary_seq")
    @JsonIgnore
    private int id;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ComicList comic;

    /**
     *  (string, optional): The path to the individual comic resource.
     */
    private String resourceURI;

    /**
     *  (string, optional): The canonical name of the comic.
     */
    private String name;
}

package net.knautiluz.marvel.models.comic;

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
public class ComicSummary implements Serializable {

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

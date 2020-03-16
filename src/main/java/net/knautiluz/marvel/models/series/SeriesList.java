package net.knautiluz.marvel.models.series;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import net.knautiluz.marvel.models.character.Character;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeriesList implements Serializable {

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
    private Character character;

    /**
     *  (int, optional): The number of total available series in this list. Will always be greater than or equal to the "returned" value.
     */
    private int available;

    /**
     *  (int, optional): The number of series returned in this collection (up to 20).
     */
    private int returned;

    /**
     *  (string, optional): The path to the full list of series in this collection.
     */
    private String collectionURI;

    /**
     * (Array[SeriesSummary], optional): The list of returned series in this collection.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "series_list_id")
    private List<SeriesSummary> items;

}

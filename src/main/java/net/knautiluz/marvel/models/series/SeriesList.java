package net.knautiluz.marvel.models.series;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import net.knautiluz.marvel.models.character.Character;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@SuppressWarnings("unused")
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SeriesList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Builder
    public SeriesList(int id, String collectionURI, Set<SeriesSummary> items) {
        this.id = id;
        this.collectionURI = collectionURI;
        this.items = items;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "series_list_seq")
    @JsonIgnore
    private int id;

    @ManyToMany(mappedBy = "series")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Character> character;

    /**
     *  (int, optional): The number of total available series in this list. Will always be greater than or equal to the "returned" value.
     */
    private int available;

    /**
     *  (int, optional): The number of series returned in this collection (up to 20).
     */
    private int returned;


    public int getAvailable() {
        return this.items.size();
    }

    public int getReturned() {
        return this.items.size();
    }

    /**
     *  (string, optional): The path to the full list of series in this collection.
     */
    private String collectionURI;

    /**
     * (Array[SeriesSummary], optional): The list of returned series in this collection.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "series_list_id")
    private Set<SeriesSummary> items;

}

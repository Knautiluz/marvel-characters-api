package net.knautiluz.marvel.models.comic;

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
public class ComicList implements Serializable {

    //TODO CRIAR OU VERIFICAR NECESSIDADE DE CAMPO ÚNICO PARA MAPEAR A BUSCA DOS CHARACTERS NAS COMICS ONDE ELE APARECE "ENDPOINT/CHARACTERS"

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comic_list_seq")
    @JsonIgnore
    private int id;

    @Builder
    public ComicList(int id, String collectionURI, Set<ComicSummary> items) {
        this.id = id;
        this.collectionURI = collectionURI;
        this.items = items;
    }

    @ManyToMany(mappedBy = "comics")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Character> character;

    /**
     * (int, optional): The number of total available issues in this list. Will always be greater than or equal to the "returned" value.
     */
    @Transient
    @Getter(AccessLevel.NONE)
    private int available;

    /**
     *  (int, optional): The number of issues returned in this collection (up to 20).
     */
    @Transient
    @Getter(AccessLevel.NONE)
    private int returned;

    public int getAvailable() {
        return this.items.size();
    }

    public int getReturned() {
        return this.items.size();
    }

    /**
     *  (string, optional): The path to the full list of issues in this collection.
     */
    private String collectionURI;

    /**
     *  (Array[ComicSummary], optional): The list of returned issues in this collection.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "comic_id")
    private Set<ComicSummary> items;

}

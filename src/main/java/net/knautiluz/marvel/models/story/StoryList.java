package net.knautiluz.marvel.models.story;

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
public class StoryList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Builder
    public StoryList(int id, String collectionURI, Set<StorySummary> items) {
        this.id = id;
        this.collectionURI = collectionURI;
        this.items = items;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "story_list_seq")
    @JsonIgnore
    private int id;

    @ManyToMany(mappedBy = "series")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Character> character;

    /**
     *  (int, optional): The number of total available stories in this list. Will always be greater than or equal to the "returned" value.
     */
    @Transient
    @Getter(AccessLevel.NONE)
    private int available;

    /**
     * (int, optional): The number of stories returned in this collection (up to 20).,
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
     * (string, optional): The path to the full list of stories in this collection.
     */
    private String collectionURI;

    /**
     * (Array[StorySummary], optional): The list of returned stories in this collection.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "story_list_id")
    private Set<StorySummary> items;

}

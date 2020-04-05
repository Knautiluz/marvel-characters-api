package net.knautiluz.marvel.models.character;

import lombok.*;
import net.knautiluz.marvel.models.comic.ComicList;
import net.knautiluz.marvel.models.event.EventList;
import net.knautiluz.marvel.models.series.SeriesList;
import net.knautiluz.marvel.models.story.StoryList;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "marvelCharacter")
public class Character implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The unique ID of the character resource.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "marvel_character_seq")
    private int id;

    /**
     * The name of the character.
     */
    private String name;

    /**
     * A short bio or description of the character.
     */
    private String description;

    /**
     * The date the resource was most recently modified.
     */
    private Date modified;

    /**
     * The canonical URL identifier for this resource.
     */
    private String resourceURI;

    /**
     * A set of public web site URLs for the resource.,
     */
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Url> urls;

    /**
     * The representative image for this character.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "character_id")
    private Image thumbnail;

    /**
     * (ComicList, optional): A resource list containing comics which feature this character.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "character_id")
    private Set<ComicList> comics;

    /**
     * (StoryList, optional): A resource list of stories in which this character appears.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "character_id")
    private Set<StoryList> stories;

    /**
     * (EventList, optional): A resource list of events in which this character appears.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "character_id")
    private Set<EventList> events;

    /**
     * (SeriesList, optional): A resource list of series in which this character appears
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "character_id")
    private Set<SeriesList> series;

}

package net.knautiluz.marvel.models.event;

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
public class EventSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Builder
    public EventSummary(int id, EventList eventList, String resourceURI, String name) {
        this.id = id;
        this.eventList = eventList;
        this.resourceURI = resourceURI;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_summary_seq")
    @JsonIgnore
    private int id;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private EventList eventList;

    /**
     * (string, optional): The path to the individual event resource.
     */
    private String resourceURI;

    /**
     *  (string, optional): The name of the event.
     */
    private String name;

}

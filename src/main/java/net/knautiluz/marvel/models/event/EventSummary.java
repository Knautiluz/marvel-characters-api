package net.knautiluz.marvel.models.event;

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
public class EventSummary implements Serializable {

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

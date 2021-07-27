package net.knautiluz.marvel.models.series;

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
public class SeriesSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Builder
    public SeriesSummary(int id, String resourceURI, String name) {
        this.id = id;
        this.resourceURI = resourceURI;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "series_summary_seq")
    @JsonIgnore
    private int id;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private SeriesList seriesList;

    /**
     *  (string, optional): The path to the individual series resource.
     */
    private String resourceURI;

    /**
     *  (string, optional): The canonical name of the series.
     */
    private String name;

}

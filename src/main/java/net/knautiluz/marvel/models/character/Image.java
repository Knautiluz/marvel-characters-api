package net.knautiluz.marvel.models.character;

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
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Builder
    public Image(int id, String path, String extension) {
        this.id = id;
        this.path = path;
        this.extension = extension;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_seq")
    @JsonIgnore
    private int id;

    @OneToOne(mappedBy = "thumbnail")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Character character;

    /**
     *  (string, optional): The directory path of to the image.
     */
    private String path;

    /**
     *  (string, optional): The file extension for the image.
     */
    private String extension;
}

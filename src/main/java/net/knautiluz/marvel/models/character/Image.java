package net.knautiluz.marvel.models.character;

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
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private int id;

    /**
     * @deprecated até encontrar uma forma de remove-lo do builder do lombok
     */
    @OneToOne
    @JsonIgnore
    @Deprecated
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

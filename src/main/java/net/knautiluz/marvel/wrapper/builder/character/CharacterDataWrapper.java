package net.knautiluz.marvel.wrapper.builder.character;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CharacterDataWrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * (int, optional): The HTTP status code of the returned result.,
     */
    private int code;

    /**
     * (string, optional): A string description of the call status.
     */
    private String status;


    /**
     *  (string, optional): The copyright notice for the returned result.
     */
    private String copyright;

    /**
     * (string, optional): The attribution notice for this result. Please display either this notice or the contents of the attributionHTML field on all screens which contain data from the Marvel Comics API.
     */
    private String attributionText;

    /**
     * (string, optional): An HTML representation of the attribution notice for this result. Please display either this notice or the contents of the attributionText field on all screens which contain data from the Marvel Comics API.
     */
    private String attributionHTML;

    /**
     * (CharacterDataContainer, optional): The results returned by the call.
     */
    private CharacterDataContainer data;

    /**
     * (string, optional): A digest value of the content returned by the call.
     */
    private String etag;

}

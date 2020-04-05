package net.knautiluz.marvel.controllers.dextra.initialization;

import net.knautiluz.marvel.models.character.Character;
import net.knautiluz.marvel.models.character.Image;
import net.knautiluz.marvel.models.character.Url;
import net.knautiluz.marvel.models.comic.ComicList;
import net.knautiluz.marvel.models.comic.ComicSummary;
import net.knautiluz.marvel.repository.characters.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@SuppressWarnings("unused")
@RestController
public class DextraInitController {

    @Autowired
    private CharacterRepository repository;

    /** DATABASE INITIALIZATION **/

    @PostMapping(path = "/start")
    @ResponseStatus(code = HttpStatus.OK)
    public void generateData() {
        /* oracle */
        Set<ComicSummary> oracleSummaries = new HashSet<>();
        oracleSummaries.add(createSummary("OracleDay", "OracleDay"));
        oracleSummaries.add(createSummary("OracleEnd", "OracleEnd"));
        Set<ComicList> oracleComics = new HashSet<>();
        oracleComics.add(createComicsList(oracleSummaries, 1000));
        Character oracle = createCharacters("Oracle", "Destroyer of Java", "Oracle", Date.from(Instant.ofEpochMilli(100001)), oracleComics);
        Set<Url> urlList = new HashSet<>();
        urlList.add(createUrl("wiki", "https://knautiluz.net/Oracle"));
        urlList.add(createUrl("comic", "https://knautiluz.net/OracleEnemies"));
        oracle.setUrls(urlList);
        repository.save(oracle);

        /* jakarta */
        Set<ComicSummary> jakartaSummaries = new HashSet<>();
        jakartaSummaries.add(createSummary("Jakarta Kills Oracle", "JakartaMassacre"));
        jakartaSummaries.add(createSummary("Oracle Killed By Jakarta", "JakartaTriumph"));
        Set<ComicSummary> jakartaSummariesB = new HashSet<>();
        jakartaSummariesB.add(createSummary("Oracle Killed By Jakarta", "OracleDie"));
        jakartaSummariesB.add(createSummary("Oracle is fine in heaven.", "OracleRestInPiece"));
        Set<ComicList> jakartaComics = new HashSet<>();
        jakartaComics.add(createComicsList(jakartaSummaries, 2000));
        oracleComics.add(createComicsList(jakartaSummariesB, 2000));
        Character jakarta = createCharacters("Jakarta", "Oracle Enemy", "Jakarta", Date.from(Instant.ofEpochMilli(200005)), jakartaComics);
        repository.save(jakarta);
    }

    private Url createUrl(String type, String url) {
        return Url.builder().type(type).url(url).build();
    }

    private Image createThumbNail(String charName) {
        return Image.builder().extension(".png").path("https://knautiluz.net/images/characters/" + charName).build();
    }

    private Character createCharacters(String charName, String charDescription, String charEndURI, Date modified, Set<ComicList> characterComics) {
        return Character.builder()
                .name(charName)
                .description(charDescription)
                .resourceURI("https://knautiluz.net/characters/" + charEndURI)
                .modified(modified)
                .comics(characterComics)
                .thumbnail(createThumbNail(charName)).build();
    }

    private ComicList createComicsList(Set<ComicSummary> summaries, int comicsID) {
        return ComicList.builder()
                .collectionURI("https://knautiluz.net/comics/" + comicsID)
                .items(summaries).build();
    }

    private ComicSummary createSummary(String name, String summaryNameURI) {
        return ComicSummary.builder().name(name).resourceURI("https://knautiluz/net/comics/summaries/" + summaryNameURI).build();
    }

    @PostMapping(path = "/new", consumes = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createCharacter(@RequestBody Character character) {
        repository.save(character);
    }

}

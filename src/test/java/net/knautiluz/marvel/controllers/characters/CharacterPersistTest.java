package net.knautiluz.marvel.controllers.characters;

import net.knautiluz.marvel.models.character.Character;
import net.knautiluz.marvel.models.character.Image;
import net.knautiluz.marvel.models.character.Url;
import net.knautiluz.marvel.models.comic.ComicList;
import net.knautiluz.marvel.models.comic.ComicSummary;
import net.knautiluz.marvel.models.event.EventList;
import net.knautiluz.marvel.models.event.EventSummary;
import net.knautiluz.marvel.models.series.SeriesList;
import net.knautiluz.marvel.models.series.SeriesSummary;
import net.knautiluz.marvel.models.story.StoryList;
import net.knautiluz.marvel.models.story.StorySummary;
import net.knautiluz.marvel.repository.characters.CharacterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static net.knautiluz.marvel.matchers.CharacterMatchers.idEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CharacterPersistTest {

    @Autowired
    private CharacterRepository repository;

    @Test
    public void givenCharacterFullDataWhenSaveInRepoThenSaveAndReturnTheSame() {
        Image thumbnail = Image.builder().id(1).extension(".png").path("https://knautiluz.net/Thanos").build();
        List<ComicList> comics = generateComics();
        List<StoryList>  stories = generateStories();
        List<SeriesList> series = generateSeries();
        List<EventList> events = generateEvents();
        List<Url> urls = new ArrayList<>();
        urls.add(Url.builder().type("wiki").url("https://knautiluz/marvel/wiki/thanos").build());
        final int charID = 1;
        Character character = Character.builder()
                .id(charID)
                .name("Thanos")
                .description("XPTO Description")
                .modified(new Date())
                .thumbnail(thumbnail)
                .resourceURI("https://knautiluz.net/marvel/thanos")
                .comics(comics)
                .stories(stories)
                .series(series)
                .events(events)
                .urls(urls)
                .build();
        repository.save(character);
        Iterable<Character> result = repository.findAll();
        if(result.iterator().hasNext()) {
            Assertions.assertEquals(result.iterator().next().getName(), character.getName());
            Assertions.assertNotNull(result.iterator().next().getThumbnail());
            Assertions.assertFalse(result.iterator().next().getComics().isEmpty());
            Assertions.assertFalse(result.iterator().next().getStories().isEmpty());
            Assertions.assertFalse(result.iterator().next().getSeries().isEmpty());
            Assertions.assertFalse(result.iterator().next().getEvents().isEmpty());
            Assertions.assertFalse(result.iterator().next().getUrls().isEmpty());
        } else {
            Assertions.fail("Model was not saved...");
        }
    }

    private List<EventList> generateEvents() {
        List<EventList> events = new ArrayList<>();
        List<EventSummary> summaries = new ArrayList<>();
        EventSummary summary = EventSummary.builder().name("Thanos Meetup").resourceURI("https://knautiluz.net/marvel/ThanosMeetup").build();
        summaries.add(summary);
        EventList story = EventList.builder().items(summaries).collectionURI("https://knautiluz.net/marvel/thanos/summaries/events").build();
        events.add(story);
        return events;
    }

    private List<SeriesList> generateSeries() {
        List<SeriesList> series = new ArrayList<>();
        List<SeriesSummary> summaries = new ArrayList<>();
        SeriesSummary summary = SeriesSummary.builder().name("Um dia sem o Java").resourceURI("https://knautiluz.net/marvel/OneDayWithoutJava").build();
        summaries.add(summary);
        SeriesList story = SeriesList.builder().items(summaries).collectionURI("https://knautiluz.net/marvel/thanos/summaries/series").build();
        series.add(story);
        return series;
    }

    private List<StoryList> generateStories() {
        List<StoryList> stories = new ArrayList<>();
        List<StorySummary> summaries = new ArrayList<>();
        StorySummary summary = StorySummary.builder().name("Um dia com o Java").resourceURI("https://knautiluz.net/marvel/OneDayWithJava").type("comics").build();
        summaries.add(summary);
        StoryList story = StoryList.builder().items(summaries).collectionURI("https://knautiluz.net/marvel/thanos/summaries/stories").build();
        stories.add(story);
        return stories;
    }

    private List<ComicList> generateComics() {
        List<ComicList> comics = new ArrayList<>();
        ComicSummary comicSummary = ComicSummary.builder().name("As aventuras do Thanos Javeiro").resourceURI("https://knautiluz.net/marvel/ThanosJaveiro").build();
        ComicList comicList = ComicList.builder().items(Collections.singletonList(comicSummary)).collectionURI("https://knautiluz.net/marvel/ThanosComics").build();
        comics.add(comicList);
        return comics;
    }

}

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
        Set<ComicList> comics = generateComics();
        Set<StoryList>  stories = generateStories();
        Set<SeriesList> series = generateSeries();
        Set<EventList> events = generateEvents();
        Set<Url> urls = new HashSet<>();
        urls.add(Url.builder().id(1).type("wiki").url("https://knautiluz/marvel/wiki/thanos").build());
        Character character = Character.builder()
                .id(1)
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
            Assertions.assertEquals(character.getName(), result.iterator().next().getName(), character.getName());
            Assertions.assertEquals(character.getThumbnail(), result.iterator().next().getThumbnail());
            Assertions.assertEquals(character.getComics(), result.iterator().next().getComics());
            Assertions.assertEquals(character.getStories(), result.iterator().next().getStories());
            Assertions.assertEquals(character.getSeries(), result.iterator().next().getSeries());
            Assertions.assertEquals(character.getEvents(), result.iterator().next().getEvents());
            Assertions.assertEquals(character.getUrls(), result.iterator().next().getUrls());
            Assertions.assertEquals(character.toString(), result.iterator().next().toString());
        } else {
            Assertions.fail("Model was not saved...");
        }
    }

    private Set<EventList> generateEvents() {
        Set<EventList> events = new HashSet<>();
        Set<EventSummary> summaries = new HashSet<>();
        EventSummary summary = EventSummary.builder().id(1).name("Thanos Meetup").resourceURI("https://knautiluz.net/marvel/ThanosMeetup").build();
        summaries.add(summary);
        EventList story = EventList.builder().id(1).items(summaries).collectionURI("https://knautiluz.net/marvel/thanos/summaries/events").build();
        events.add(story);
        return events;
    }

    private Set<SeriesList> generateSeries() {
        Set<SeriesList> series = new HashSet<>();
        Set<SeriesSummary> summaries = new HashSet<>();
        SeriesSummary summary = SeriesSummary.builder().id(1).name("Um dia sem o Java").resourceURI("https://knautiluz.net/marvel/OneDayWithoutJava").build();
        summaries.add(summary);
        SeriesList story = SeriesList.builder().id(1).items(summaries).collectionURI("https://knautiluz.net/marvel/thanos/summaries/series").build();
        series.add(story);
        return series;
    }

    private Set<StoryList> generateStories() {
        Set<StoryList> stories = new HashSet<>();
        Set<StorySummary> summaries = new HashSet<>();
        StorySummary summary = StorySummary.builder().id(1).name("Um dia com o Java").resourceURI("https://knautiluz.net/marvel/OneDayWithJava").type("comics").build();
        summaries.add(summary);
        StoryList story = StoryList.builder().id(1).items(summaries).collectionURI("https://knautiluz.net/marvel/thanos/summaries/stories").build();
        stories.add(story);
        return stories;
    }

    private Set<ComicList> generateComics() {
        Set<ComicList> comics = new HashSet<>();
        ComicSummary comicSummary = ComicSummary.builder().id(1).name("As aventuras do Thanos Javeiro").resourceURI("https://knautiluz.net/marvel/ThanosJaveiro").build();
        ComicList comicList = ComicList.builder().id(1).items(Collections.singleton(comicSummary)).collectionURI("https://knautiluz.net/marvel/ThanosComics").build();
        comics.add(comicList);
        return comics;
    }

}

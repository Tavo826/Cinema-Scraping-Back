package co.com.sofka.cinema.usecases;

import co.com.sofka.cinema.domain.cinema.Cinema;
import co.com.sofka.cinema.domain.cinema.command.AddMovies;
import co.com.sofka.cinema.domain.generic.DomainEvent;
import co.com.sofka.cinema.domain.generic.EventStoreRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.enterprise.context.Dependent;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Dependent
public class ExtractMoviesUseCase implements Function<AddMovies, List<DomainEvent>> {

    private static final String URL_BASE = "https://pelisplus.so/estrenos";
    private final EventStoreRepository repository;

    public ExtractMoviesUseCase(EventStoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DomainEvent> apply(AddMovies command) {

        var events = repository.getEventsBy("cinema", command.getCinemaId());
        var cinema = Cinema.from(command.getCinemaId(), events);

        Map<String, List<List<String>>> movies;

        try {

            Document doc1 = Jsoup.connect(URL_BASE).get();
            Document doc2 = Jsoup.connect(URL_BASE + "?page=2").get();

            Map<String, List<List<String>>>  movies1 = getMovieData(doc1);
            Map<String, List<List<String>>>  movies2 = getMovieData(doc2);

            (movies = new HashMap<>(movies1)).putAll(movies2);

        } catch (IOException e) {
            throw new ExtractMoviesException();
        }

        cinema.addMovies(movies);
        return cinema.getUncommittedChanges();
    }

    private List<String> getLinks(Document doc) {
        List<String> links = new ArrayList<>();
        doc.getElementsByClass("main-peliculas").select("a[href]").forEach(a -> links.add(a.attr("abs:href")));
        return links;
    }

    private Map<String, List<List<String>>> getMovieData(Document doc) {

        Map<String, List<List<String>>>  movies = new HashMap<>();

        String nameSelect = "body > div.container > div.main > div > div.pelicula-info > div.pi-right > div > p:nth-child(2) > span:nth-child(2)";
        String imgSelect = "#cover";
        String yearSelect = "body > div.container > div.main > div > div.pelicula-info > div.pi-right > div > p:nth-child(3) > span:nth-child(2)";
        String durationSelect = "body > div.container > div.main > div > div.pelicula-info > div.pi-right > div > p:nth-child(4) > span:nth-child(2)";
        String linksLatinoSelect = "#level2_latino > li";
        String linksSubtituladoSelect = "#level2_subtitulado > li";
        String linksCastellanoSelect = "#level2_castell > li";


        getLinks(doc).forEach(link -> {
            try {

                var document = Jsoup.connect(link).get();

                List<List<String>> movieData = new ArrayList<>();
                List<String> movieBasicInfo = new ArrayList<>();
                List<String> linksLatino = new ArrayList<>();
                List<String> linksSubtitulado = new ArrayList<>();
                List<String> linksCastellano = new ArrayList<>();

                movieBasicInfo.add(document.select(nameSelect).text());
                movieBasicInfo.add(document.select(imgSelect).attr("src"));
                movieBasicInfo.add(document.select(yearSelect).text());
                movieBasicInfo.add(document.select(durationSelect).text());

                document.select(linksLatinoSelect).forEach(latino -> linksLatino.add(latino.attr("data-video")));

                document.select(linksSubtituladoSelect).forEach(subtitulado -> linksSubtitulado.add(subtitulado.attr("data-video")));

                document.select(linksCastellanoSelect).forEach(latino -> linksCastellano.add(latino.attr("data-video")));

                movieData.add(movieBasicInfo);
                movieData.add(linksLatino);
                movieData.add(linksSubtitulado);
                movieData.add(linksCastellano);

                movies.put(document.select(nameSelect).text(), movieData);

            } catch (IOException e) {
                throw new ExtractMoviesException();
            }

        });

        return movies;

    }

}

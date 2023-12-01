package org.example;
import parsers.Completed;
import parsers.NewData;
import parsers.ekvus.EkvusParser;
import parsers.ekvus.EkvusSettings;
import parsers.ekvus.model.Afisha;
import parsers.habr.HabrParser;
import parsers.habr.HabrSettings;
import parsers.habr.model.Article;
import parsers.theatre.TheatreParser;
import parsers.theatre.TheatreSettings;
import parsers.theatre.model.Poster;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        ParserWorker<ArrayList<Afisha>> parser = new ParserWorker<>(new EkvusParser());
        parser.setParserSettings(new EkvusSettings());
        parser.onCompletedList.add(new Completed());
        parser.onNewDataList.add(new NewData());

        parser.start();
        Thread.sleep(10000);
        parser.abort();
    }
}
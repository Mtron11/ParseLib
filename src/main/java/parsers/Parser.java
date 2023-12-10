package parsers;

import org.example.ParserWorker;
import org.jsoup.nodes.Document;
import parsers.habr.model.Article;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public interface Parser<T> {
    T Parse(Document document, ParserWorker.OnNewDataHandler handler) throws IOException, ParseException;
}


package parsers;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.ParseException;

public interface Parser<T> {
    T Parse(Document document) throws IOException, ParseException;
}


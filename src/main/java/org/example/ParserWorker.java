package org.example;

import lombok.Getter;
import lombok.Setter;
import org.jsoup.nodes.Document;
import parsers.Parser;
import parsers.ParserSettings;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class ParserWorker <T>{
    @Getter
    @Setter
    Parser<T> parser;
    @Getter
    ParserSettings parserSettings;
    HtmlLoader loader;
    boolean isActive;
    public ArrayList<OnNewDataHandler> onNewDataList = new ArrayList<>();
    public ArrayList<OnCompleted> onCompletedList = new ArrayList<>();
    public ParserWorker(Parser<T> parser, ParserSettings parserSettings) {
        this.parser = parser;
        this.parserSettings = parserSettings;
        loader = new HtmlLoader(parserSettings);
    }

    private void worker() throws IOException, ParseException {
        for (int i = parserSettings.getStartPoint(); i <= parserSettings.getEndPoint(); i++) {
            if (!isActive) {
                onCompletedList.get(0).onCompleted(this);
                return;
            }
            Document document = loader.GetSourceByPageId(i);
            T result = parser.Parse(document, onNewDataList.get(0));
            onNewDataList.get(0).onNewData(this,result);
        }
        onCompletedList.get(0).onCompleted(this);
        isActive = false;
    }

    public void start() throws IOException, ParseException {
        isActive = true;
        worker();
    }
    public void abort() {
        isActive = false;
    }

    public interface OnNewDataHandler<T> {
        void onNewData(Object sender, T e) throws IOException;
    }
    public interface OnCompleted {
        void onCompleted(Object sender);
    }
}
package org.example;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class ParserWorker <T>{

    Parser<T> parser;
    ParserSettings parserSettings;
    HtmlLoader loader;
    boolean isActive;
    ArrayList<OnNewDataHandler> onNewDataList = new ArrayList<>();
    ArrayList<OnCompleted> onCompletedList = new ArrayList<>();
    public ParserWorker(Parser<T> parser) {
        this.parser = parser;
    }

    private void worker() throws IOException, ParseException {
        for (int i = parserSettings.getStartPoint(); i <= parserSettings.getEndPoint(); i++) {
            if (!isActive) {
                onCompletedList.get(0).onCompleted(this);
                return;
            }
            Document document = loader.GetSourceByPageId(i);
            T result = parser.Parse(document);
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

    public void setParser(Parser<T> parser) {
        this.parser = parser;
    }

    public void setParserSettings(ParserSettings parserSettings) {
        this.parserSettings = parserSettings;
        loader = new HtmlLoader(parserSettings);
    }

    public ParserSettings getParserSettings() {
        return parserSettings;
    }

    public Parser<T> getParser() {
        return parser;
    }
    public interface OnNewDataHandler<T> {
        void onNewData(Object sender, T e);
    }
    public interface OnCompleted {
        void onCompleted(Object sender);
    }



}

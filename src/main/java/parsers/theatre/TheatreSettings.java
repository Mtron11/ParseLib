package parsers.theatre;

import org.example.ParserSettings;

public class TheatreSettings extends ParserSettings {

    public TheatreSettings(int start, int end){
        startPoint = start;
        endPoint = end;
        BASE_URL = "https://kirovdramteatr.ru/afisha";
        PREFIX = "page{CurrentId}";
    }
}

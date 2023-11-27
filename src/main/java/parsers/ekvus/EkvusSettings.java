package parsers.ekvus;

import org.example.ParserSettings;

public class EkvusSettings extends ParserSettings {
    public EkvusSettings(int start, int end){
        startPoint = start;
        endPoint = end;
        BASE_URL = "https://ekvus-kirov.ru/afisha";
        PREFIX = "page{CurrentId}";
    }
}

package parsers.ekvus;

import parsers.ParserSettings;

public class EkvusSettings extends ParserSettings {
    public EkvusSettings(){
        startPoint = 1;
        endPoint = 1;
        BASE_URL = "https://ekvus-kirov.ru/afisha";
        PREFIX = "";
    }
}

package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import parsers.ParserSettings;

import java.io.IOException;

public class HtmlLoader {

    String url;

    public HtmlLoader(ParserSettings settings)
    {
        url = settings.BASE_URL+"/"+ settings.PREFIX;
    }

    public Document GetSourceByPageId(int id) throws IOException {
        String currentUrl = url.replace("{CurrentId}", Integer.toString(id));
        return Jsoup.connect(currentUrl).get();
    }
}


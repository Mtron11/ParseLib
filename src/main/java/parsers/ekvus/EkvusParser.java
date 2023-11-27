package parsers.ekvus;

import org.example.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parsers.ekvus.model.Afisha;

import java.io.IOException;
import java.util.ArrayList;

public class EkvusParser implements Parser<ArrayList<Afisha>> {
    @Override
    public ArrayList<Afisha> Parse(Document document) throws IOException {
        ArrayList<Afisha> posters = new ArrayList<>();
        Elements afishaTable = document.select("table.myafisha");
        Elements postersElements = afishaTable.select(".tr:has(a)");

        for(Element poster : postersElements){
            String date = poster.select(".td:eq(0) font").text();
            String title = poster.select(".td:eq(1) a").text();
            String duration = poster.select(".td:eq(2)").text(); // Продолжительность
            String ageLimit = poster.select(".td:eq(1) span.al_s").text(); // Ограничение


            posters.add(new Afisha(title, date, duration, ageLimit));

        }
        return posters;
    }
}

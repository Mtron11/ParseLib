package parsers.theatre;

import org.example.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parsers.theatre.model.Poster;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.ArrayList;

public class TheatreParser implements Parser<ArrayList<Poster>> {
    @Override
    public ArrayList<Poster> Parse(Document document) throws IOException, ParseException {
        ArrayList<Poster> posters = new ArrayList<>();
        Elements postersElements = document.select("div.t_afisha");

        String folderPath = "C:/Users/Mtron/Desktop/Java/ParseLib/images";
        try{
            Files.createDirectories(Paths.get(folderPath));
        }
        catch(Exception e){
            e.printStackTrace();
        }

        for(Element poster : postersElements){

            String duration = poster.select(".td3 .td2 .td1 div").first().textNodes().get(0).text();


            Element td2Element = poster.select(".td2").first();
            String imageUrl = td2Element.select("img").first().absUrl("src");

            Element tInfoAfishaElement = poster.select(".t_info_afisha").first();
            String date = tInfoAfishaElement.select(".td1 .date_afisha").text();
            String title = tInfoAfishaElement.select("h3 a").textNodes().get(0).text();
            String ageLimit = tInfoAfishaElement.select(".value_limit").text();

            posters.add(new Poster(title, imageUrl, date, duration, ageLimit));

            URL url = new URL(imageUrl);

            // поток скачать
            try (InputStream in = url.openStream()) {
                // берем имя файла (после последнего слэша)
                String fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);

                // путь изображения
                Path imagePath = Paths.get(folderPath, fileName);

                // Сохранение
                Files.copy(in, imagePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return posters;
    }
}

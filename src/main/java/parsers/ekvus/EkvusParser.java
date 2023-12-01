package parsers.ekvus;

import org.jsoup.Jsoup;
import parsers.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parsers.ekvus.model.Afisha;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class EkvusParser implements Parser<ArrayList<Afisha>> {
    @Override
    public ArrayList<Afisha> Parse(Document document) throws IOException {
        ArrayList<Afisha> posters = new ArrayList<>();
        Elements postersElements = document.getElementsByClass("page_box").get(0).getElementsByTag("table").get(0).getElementsByTag("tr");

        String folderPath = "C:/Users/Mtron/Desktop/ParseLib/images";
        try {
            Files.createDirectories(Paths.get(folderPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Element poster : postersElements){
            if (poster.getElementsByTag("td").size() < 2){
                continue;
            }
            String date = poster.getElementsByTag("font").text();
            String title = poster.select("td:eq(1)").select("a").textNodes().get(0).text();
            String ageLimit = poster.select("[class=\"al_s\"], [class=\"al\"]").text();

            Document posterDocument = loadPerfomance(poster);
            String duration = getDuration(posterDocument);
            String imageUrl = getImageUrl(posterDocument);

            posters.add(new Afisha(title, date, ageLimit, duration, imageUrl));
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

    private static Document loadPerfomance( Element afisha) throws IOException {
        String href = afisha.getElementsByTag("a").get(1).attr("href");
        return Jsoup.connect("https://ekvus-kirov.ru"+href).get();
    }
    private static String getDuration(Document doc){
        String dur = "Продолжительность спектакля:";
        Elements durations = doc.getElementsMatchingText(dur);
        String duration="";
        if (!durations.isEmpty()){
            duration = durations.get(8).text().substring(dur.length());
        }
        return duration;
    }
    private static String getImageUrl(Document doc){
        String imageUrl="";
        Element image = doc.getElementById("photo_osnova");
        if (image != null){
            imageUrl=image.absUrl("src");
        }
        else if (doc.getElementsByClass("img_right").first() != null){
            imageUrl = doc.getElementsByClass("img_right").first().absUrl("src");
        }
        return imageUrl;
    }
}

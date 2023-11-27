package parsers.habr;

import parsers.habr.model.Article;
import org.example.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class HabrParser implements Parser<ArrayList<Article>> {
    @Override
    public ArrayList<Article> Parse(Document document) throws IOException {
        ArrayList<Article> articles = new ArrayList<>();
        Elements articleElements = document.select("article");

        String folderPath = "C:/Users/Mtron/Desktop/Java/ParseLib/images";
        try {
            Files.createDirectories(Paths.get(folderPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Element articleElement : articleElements) {
            String title = articleElement.select("h2").text();
            String text = articleElement.select("div.article-formatted-body").text();
            String imageUrl = articleElement.select("img.tm-article-snippet__lead-image").attr("src");
            articles.add(new Article(title, text, imageUrl));
            if (imageUrl.startsWith("https")) {
                // объект URL для изображения
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
        }

        return articles;
    }
}

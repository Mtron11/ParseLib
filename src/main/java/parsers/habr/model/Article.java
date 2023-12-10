package parsers.habr.model;


import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

@Getter
public class Article {
    private final String title;
    private final String text;
    private final String imageUrl;
    private Image image=null;

    public Article(String title, String text, String imageUrl) throws IOException {
        this.title = title;
        this.text = text;
        this.imageUrl = imageUrl;
        if (!imageUrl.isEmpty()) {
            tools.ImageDownloader.download(!imageUrl.startsWith("https:") ? "https:" + imageUrl : imageUrl);
            String imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            image = new ImageIcon("images/" + imageName).getImage();
        }
    }

    @Override
    public String toString() {
        return  "title=" + title + "\n" +
                "text=" + text + "\n" +
                "imageUrl=" + imageUrl + "\n" +
                "\n";

    }
}

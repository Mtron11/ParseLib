package tools;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public abstract class ImageDownloader {

    private static String savePath = "C:/Users/Mtron/Desktop/PraseLib/images";

    public static void setSavePath(String savePath) {
        ImageDownloader.savePath = savePath;
        if (new File(ImageDownloader.savePath).mkdirs()) {
            System.out.println("Директория для сохранения изображений успешно создана\n");
        }
    }

    public static void download(String imageUrl) throws IOException {
        BufferedImage input = ImageIO.read(new URL(imageUrl));
        String imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        String imageExtension = imageName.substring(imageName.lastIndexOf(".") + 1);
        File output = new File(savePath + imageName);
        try{
            ImageIO.write(input, imageExtension, output);
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении изображения  " + imageName + ": " + e.getMessage());
        }
    }
}

package gui;

import org.example.ParserWorker;
import parsers.Completed;
import parsers.NewData;
import parsers.habr.HabrParser;
import parsers.habr.HabrSettings;
import parsers.habr.model.Article;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GUI extends JFrame implements ParserWorker.OnNewDataHandler<Article>, ParserWorker.OnCompleted {
    private ParserWorker<ArrayList<Article>> parser;
    private Thread downloadThread;
    private final JPanel panel = new JPanel();
    private int count = 0;
    public GUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        setTitle("Наконец-то дали контакты");
        setIconImage(new ImageIcon("icons/HeronWater.png").getImage());
        setSize(1200, 800);
        setMinimumSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel rightPanel = new JPanel();
        GridLayout layout = new GridLayout(10, 1, 5, 12);
        rightPanel.setLayout(layout);
        add(rightPanel, BorderLayout.EAST);

        JLabel jlStart = new JLabel("Первая страница");
        jlStart.setHorizontalAlignment(SwingConstants.CENTER);
        jlStart.setVerticalAlignment(SwingConstants.CENTER);
        rightPanel.add(jlStart);

        JTextField jtfStart = new JTextField();
        jtfStart.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(jtfStart);

        JLabel jlEnd = new JLabel("Последняя страница");
        jlEnd.setHorizontalAlignment(SwingConstants.CENTER);
        jlEnd.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(jlEnd);

        JTextField jtfEnd = new JTextField();
        jtfEnd.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(jtfEnd);

        JButton startButton = new JButton("Старт");
        JButton abortButton = new JButton("Стоп");
        startButton.setSize(50, 20);
        rightPanel.add(startButton);
        rightPanel.add(abortButton);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JScrollPane jsp = new JScrollPane(panel);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(jsp);
        startButton.addActionListener(e -> {
            if (!isCorrectInputText(jtfStart.getText(), jtfEnd.getText())) {
                return;
            }

            panel.removeAll();

            int start = Integer.parseInt(jtfStart.getText());
            int end = Integer.parseInt(jtfEnd.getText());

            parser = new ParserWorker<>(new HabrParser(), new HabrSettings(start, end));

            parser.onCompletedList.add(GUI.this);
            parser.onNewDataList.add(GUI.this);

            parser.onCompletedList.add(new Completed());
            parser.onNewDataList.add(new NewData());

            tools.ImageDownloader.setSavePath("src/images/");

            System.out.println("\nЗагрузка началась\n");

            downloadThread = new Thread(() -> {
                try {
                    parser.start();
                } catch (Exception thrown) {
                    thrown.printStackTrace();
                }
            });
            downloadThread.start();
        });

        abortButton.addActionListener(e -> {
            if (downloadThread != null && downloadThread.isAlive()) {
                parser.abort();
                downloadThread.interrupt();
            }
        });
    }
    private boolean isCorrectInputText(@NotNull String startText, @NotNull String endText) {
        if (startText.isEmpty()) {
            showError("Введите начало пагинации");
            return false;
        }

        if (endText.isEmpty()) {
            showError("Введите конец пагинации");
            return false;
        }

        int start, end;
        try {
            start = Integer.parseInt(startText);
            end = Integer.parseInt(endText);
        } catch (NumberFormatException e) {
            showError("Введите целочисленное значение");
            return false;
        }

        if (start < 1) {
            showError("Введено неккоректное значение (start < 1)");
            return false;
        }

        if (end < 1) {
            showError("Введено неккоректное значение (end < 1)");
            return false;
        }

        if (end < start) {
            showError("Введено неккоректное значение (start > end)");
            return false;
        }

        return true;
    }

    private void showError(@NotNull String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void onNewData(Object sender, @NotNull Article article) throws IOException {

            JTextArea textArea = getInitTextArea();
            textArea.append("Статья " + ++count + "\n" + article.getTitle() + "\n" + article.getText() + "\n");

            /*JLabel label = new JLabel();
            if (article.getImage() != null) {
                String imageName = article.getImageUrl().substring(article.getImageUrl().lastIndexOf("/") + 1);
                BufferedImage bufImage = ImageIO.read(new File("C:/Users/Mtron/Desktop/ParseLib/images/" + imageName));
                Image image = bufImage.getScaledInstance(600, 400, Image.SCALE_DEFAULT);
                label.setIcon(new ImageIcon(image));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.CENTER);
            } else {
                textArea.append("Изображение отсутствует\n");
            }*/

            panel.add(textArea);
            //panel.add(label);
            panel.updateUI();

    }

    private @NotNull JTextArea getInitTextArea() {
        JTextArea textArea = new JTextArea();

        textArea.setFont(new Font("Dialog", Font.PLAIN, 14));
        textArea.setSize(panel.getWidth(), panel.getHeight());
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);

        return textArea;
    }

    @Override
    public void onCompleted(Object sender) {
        JOptionPane.showMessageDialog(this, "Загрузка закончена");
    }

}
package parsers.theatre.model;

public class Poster {
    private final String title;
    private final String imageUrl;
    private final String date;
    private final String duration;
    private final String ageLimit;


    public Poster(String title, String imageUrl, String date, String duration, String ageLimit) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.date = date;
        this.duration = duration;
        this.ageLimit = ageLimit;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }
    public String getDate(){
        return date;
    }
    public String getDuration(){
        return duration;
    }

    public String getAgeLimit(){
        return ageLimit;
    }

    @Override
    public String toString() {
        return  "title= " + title + "\n" +
                "imageUrl= " + imageUrl + "\n" +
                "date= " + date + "\n" +
                "duration=  " + duration + "\n" +
                "ageLimit= " + ageLimit + "\n" +
                "\n";
    }
}

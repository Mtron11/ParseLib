package parsers.ekvus.model;

public class Afisha {
    private final String title;
    private final String date;
    private final String ageLimit;
    private final String imageUrl;
    private final String duration;


    public Afisha(String title, String date, String ageLimit, String duration, String imageUrl) {
        this.title = title;
        this.date = date;
        this.ageLimit = ageLimit;
        this.imageUrl = imageUrl;
        this.duration = duration;
    }


    public String getTitle() {
        return title;
    }
    public String getDate(){
        return date;
    }

    public String getAgeLimit(){
        return ageLimit;
    }

    @Override
    public String toString() {
        return  "title= " + title + "\n" +
                "date= " + date + "\n" +
                "imageUrl= " + imageUrl + "\n" +
                "duration= " + duration + "\n" +
                "ageLimit= " + ageLimit + "\n" +
                "\n";

    }
}

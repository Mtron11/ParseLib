package parsers.ekvus.model;

public class Afisha {
    private final String title;
    private final String date;
    private final String duration;
    private final String ageLimit;


    public Afisha(String title, String date, String duration, String ageLimit) {
        this.title = title;
        this.date = date;
        this.duration = duration;
        this.ageLimit = ageLimit;
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
                "date= " + date + "\n" +
                "duration=  " + duration + "\n" +
                "ageLimit= " + ageLimit + "\n" +
                "\n";

    }
}

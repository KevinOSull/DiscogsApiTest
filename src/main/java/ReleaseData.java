public class ReleaseData {

    private String artist;
    private String title;
    private String year;

    public ReleaseData(String artist,String title,String year){
        this.artist = artist;
        this.title = title;
        this.year = year;
    }

    public String getArtist(){
        return this.artist;
    }

    public String getTitle(){
        return this.title;
    }

    public String getYear(){
        return this.year;
    }
}

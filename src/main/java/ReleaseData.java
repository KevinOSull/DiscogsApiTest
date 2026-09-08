public class ReleaseData {

    private String title;
    private String year;

    public ReleaseData(String title,String year){
        this.title = title;
        this.year = year;
    }

    public String getTitle(){
        return this.title;
    }

    public String getYear(){
        return this.year;
    }
}

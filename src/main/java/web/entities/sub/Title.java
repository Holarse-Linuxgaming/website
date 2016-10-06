package web.entities.sub;

public class Title {
 
    private TitleType type;
    private String title;

    public Title() {
    }

    public Title(TitleType type, String title) {
        this.type = type;
        this.title = title;
    }
    
    public TitleType getType() {
        return type;
    }

    public void setType(TitleType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
   
}

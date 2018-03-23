package dreamline91.naver.com.checker.util.object;

/**
 * Created by dream on 2018-03-23.
 */

public class RandomText {
    private String string_title;
    private String string_link;
    private String string_image;
    private String string_content;

    public RandomText(String title, String link, String image, String content){
        this.string_title = title;
        this.string_link = link;
        this.string_image = image;
        this.string_content = content;
    }

    public String getTitle(){
        return string_title;
    }

    public String getLink(){
        return string_link;
    }

    public String getImage(){
        return string_image;
    }

    public String getContent(){
        return string_content;
    }
}

package dreamline91.naver.com.checker.util.object;

/**
 * Created by dream on 2018-03-18.
 */

public class Kakao {
    private long time;
    private String title;
    private String content;

    public Kakao(long time, String title, String content){
        this.time = time;
        this.title = title;
        this.content = content;
    }

    public long getTime(){
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}

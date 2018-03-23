package dreamline91.naver.com.checker.util.object;

/**
 * Created by dream on 2018-03-18.
 */

public class Kakao {
    private long long_time;
    private String string_title;
    private String string_content;

    public Kakao(long time, String title, String content){
        this.long_time = time;
        this.string_title = title;
        this.string_content = content;
    }

    public long getTime(){
        return long_time;
    }

    public String getTitle() {
        return string_title;
    }

    public String getContent() {
        return string_content;
    }
}

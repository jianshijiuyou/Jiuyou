package info.jiuyou.jiuyou.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * ==========================================
 * <p>
 * 版   权 ：jianshijiuyou(c) 2017
 * <br/>
 * 作   者 ：wq
 * <br/>
 * 版   本 ：1.0
 * <br/>
 * 创建日期 ：2017/6/2 0002  17:32
 * <br/>
 * 描   述 ：
 * <br/>
 * 修订历史 ：
 * </p>
 * ==========================================
 */
@Entity(nameInDb = "emotions")
public class Emotions {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "phrase")
    private String phrase;
    @Property(nameInDb = "type")
    private String type;
    @Property(nameInDb = "url")
    private String url;
    @Generated(hash = 1497483463)
    public Emotions(Long id, String phrase, String type, String url) {
        this.id = id;
        this.phrase = phrase;
        this.type = type;
        this.url = url;
    }
    @Generated(hash = 1770639217)
    public Emotions() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPhrase() {
        return this.phrase;
    }
    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}

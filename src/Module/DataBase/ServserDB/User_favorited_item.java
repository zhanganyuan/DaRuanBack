package Module.DataBase.ServserDB;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by anyuan on 2016/11/10.
 */
public class User_favorited_item implements Serializable{
    private String user_ID;
    private String title;
    private  String description;
    private String link;
    private Date pubDate;
    private int channel_ID;

    /**
     * constructors
     */
    public User_favorited_item() {
    }

    public User_favorited_item(String user_ID, String link) {
        this.user_ID = user_ID;
        this.link = link;
    }

    public User_favorited_item(String user_ID, String link, int channel_ID) {
        this.user_ID = user_ID;
        this.link = link;
        this.channel_ID = channel_ID;
    }

    /**
     * gets & sets
     */

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public int getChannel_ID() {
        return channel_ID;
    }

    public void setChannel_ID(int channel_ID) {
        this.channel_ID = channel_ID;
    }
}

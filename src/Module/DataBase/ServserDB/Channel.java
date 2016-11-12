package Module.DataBase.ServserDB;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by anyuan on 2016/11/10.
 */
public class Channel implements Serializable{
    private int channel_ID;
    private String link;
    private String title;
    private String description;
    private Date lastBuildDate;

    /**
     * constructors
     */
    public Channel() {
    }

    public Channel(int channel_ID, String link) {
        this.channel_ID = channel_ID;
        this.link = link;
    }

    /**
     * gets & sets
     */
    public int getChannel_ID() {
        return channel_ID;
    }

    public void setChannel_ID(int channel_ID) {
        this.channel_ID = channel_ID;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public Date getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(Date lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }
}

package com.whu.Module.DataBase.ServserDB;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

/**
 * Created by anyuan on 2016/11/10.
 */
public class User_info implements Serializable {
    private String user_ID;
    private String nickName;
    private byte[] user_img;

    /**
     * constructors
     */
    public User_info() {
    }

    public User_info(String user_ID, String nickName, byte[] user_img) {
        this.user_ID = user_ID;
        this.nickName = nickName;
        this.user_img = user_img;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public byte[] getUser_img() {
        return user_img;
    }

    public void setUser_img(byte[] user_img) {
        this.user_img = user_img;
    }

    public Image getTrueImage() throws SQLException, IOException {
        byte[] userImage = user_img;
        //byte[]转化成图片
        ByteArrayInputStream bis = new ByteArrayInputStream(userImage);//注意这里默认一个参数就可以，否则得到的是null
        BufferedImage img = ImageIO.read(bis);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(img,"jpg",bos);
        return  img;
    }
}

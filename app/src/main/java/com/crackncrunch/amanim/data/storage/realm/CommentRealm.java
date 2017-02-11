package com.crackncrunch.amanim.data.storage.realm;

import com.crackncrunch.amanim.data.managers.DataManager;
import com.crackncrunch.amanim.data.managers.PreferencesManager;
import com.crackncrunch.amanim.data.network.res.CommentRes;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CommentRealm extends RealmObject implements Serializable {
    @PrimaryKey
    private String id;
    private String userName;
    private String avatar;
    private float rating;
    private Date commentDate;
    private String comment;
    private boolean active;

    // Required for Realm
    public CommentRealm() {
    }

    public CommentRealm(float rating, String comment) {
        this.id = String.valueOf(this.hashCode());
        final PreferencesManager pm = DataManager.getInstance()
                .getPreferencesManager();
        this.userName = pm.getUserName();
        this.avatar = pm.getUserAvatar();
        this.rating = rating;
        this.commentDate = new Date();
        this.comment = comment;
        this.active = true;
    }

    public CommentRealm(CommentRes commentRes) {
        this.id = commentRes.getId();
        this.userName = commentRes.getUserName();
        this.avatar = commentRes.getAvatar();
        this.rating = commentRes.getRating();
        this.commentDate = commentRes.getCommentDate();
        this.comment = commentRes.getComment();
        this.active = commentRes.isActive();
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public float getRating() {
        return rating;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public String getComment() {
        return comment;
    }

    public boolean isActive() {
        return active;
    }
}

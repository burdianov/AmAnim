package com.crackncrunch.amanim.mvp.models;

import com.crackncrunch.amanim.data.network.res.CommentRes;
import com.crackncrunch.amanim.data.storage.realm.CommentRealm;
import com.crackncrunch.amanim.jobs.SendMessageJob;

public class DetailModel extends AbstractModel {
    public void saveComment(String productId,
                            CommentRes commentRes) {
        mDataManager.saveCommentToNetworkAndRealm(productId, commentRes);
    }

    public void sendComment(String id, CommentRealm commentRealm) {
        SendMessageJob job = new SendMessageJob(id, commentRealm);
        mJobManager.addJobInBackground(job);
    }
}

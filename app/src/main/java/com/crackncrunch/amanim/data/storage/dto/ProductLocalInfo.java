package com.crackncrunch.amanim.data.storage.dto;

public class ProductLocalInfo {
    private int remoteId;
    private boolean favorite;
    private int count;

    public ProductLocalInfo() {

    }

    public ProductLocalInfo(int remoteId, boolean favorite, int count) {
        this.remoteId = remoteId;
        this.favorite = favorite;
        this.count = count;
    }

    public int getRemoteId() {
        return remoteId;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void addCount() {
        count++;
    }

    public void deleteCount() {
        count--;
    }

    public void setRemoteId(int remoteId) {
        this.remoteId = remoteId;
    }
}

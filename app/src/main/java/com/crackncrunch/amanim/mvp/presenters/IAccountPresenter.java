package com.crackncrunch.amanim.mvp.presenters;

public interface IAccountPresenter {
    void clickOnAddress();
    void switchViewState();
    void takePhoto();
    void chooseCamera();
    void chooseGallery();
    void removeAddress(int position);
    void editAddress(int position);
}

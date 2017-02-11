package com.crackncrunch.amanim.data.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.crackncrunch.amanim.data.storage.dto.ProductDto;
import com.crackncrunch.amanim.utils.ConstantsManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreferencesManager {

    public static String PROFILE_FULL_NAME_KEY = "PROFILE_FULL_NAME_KEY";
    public static String PROFILE_AVATAR_KEY = "PROFILE_AVATAR_KEY";
    public static String PROFILE_PHONE_KEY = "PROFILE_PHONE_KEY";

    public static String NOTIFICATION_ORDER_KEY = "NOTIFICATION_ORDER_KEY";
    public static String NOTIFICATION_PROMO_KEY = "NOTIFICATION_PROMO_KEY";

    public static String PRODUCT_LAST_UPDATE_KEY = "PRODUCT_LAST_UPDATE_KEY";
    public static String MOCK_PRODUCT_LIST = "MOCK_PRODUCT_LIST";

    private final SharedPreferences mSharedPreferences;

    public PreferencesManager(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    //region ==================== User Profile Info ===================

    public void saveProfileInfo(Map<String, String> userProfileInfo) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PROFILE_FULL_NAME_KEY, userProfileInfo.get(PROFILE_FULL_NAME_KEY));
        editor.putString(PROFILE_AVATAR_KEY, userProfileInfo.get(PROFILE_AVATAR_KEY));
        editor.putString(PROFILE_PHONE_KEY, userProfileInfo.get(PROFILE_PHONE_KEY));
        editor.apply();
    }

    public Map<String, String> getUserProfileInfo() {
        Map<String, String> mapProfileInfo = new HashMap<>();
        mapProfileInfo.put(PROFILE_PHONE_KEY, mSharedPreferences.getString
                (PROFILE_PHONE_KEY, ""));
        mapProfileInfo.put(PROFILE_FULL_NAME_KEY, mSharedPreferences.getString
                (PROFILE_FULL_NAME_KEY, ""));
        mapProfileInfo.put(PROFILE_AVATAR_KEY, mSharedPreferences.getString
                (PROFILE_AVATAR_KEY, ""));
        return mapProfileInfo;
    }

    public String getUserName() {
        return mSharedPreferences.getString(PROFILE_FULL_NAME_KEY, "NoName");
    }

    public String getUserAvatar() {
        return mSharedPreferences.getString(PROFILE_AVATAR_KEY,
                "http://www.topglobus.ru/forum/images/avatars/gallery/filmy/Rambo.jpg");
    }

    public void saveUserAvatar(String avatarUrl) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PROFILE_AVATAR_KEY, avatarUrl);
        editor.apply();
    }

    //endregion

    //region ==================== User Settings ===================

    public Map<String, Boolean> getUserSettings() {
        Map<String, Boolean> settings = new HashMap<>();
        settings.put(NOTIFICATION_ORDER_KEY, mSharedPreferences.getBoolean
                (NOTIFICATION_ORDER_KEY, false));
        settings.put(NOTIFICATION_PROMO_KEY, mSharedPreferences.getBoolean
                (NOTIFICATION_PROMO_KEY, false));
        return settings;
    }

    public void saveSetting(String notificationKey, boolean isChecked) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(notificationKey, isChecked);
        editor.apply();
    }

    //endregion

    //region ==================== Products ===================

    public String getLastProductUpdate() {
        return mSharedPreferences.getString(PRODUCT_LAST_UPDATE_KEY,
                ConstantsManager.UNIX_EPOCH_TIME);
    }

    public void saveLastProductUpdate(String lastModified) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PRODUCT_LAST_UPDATE_KEY, lastModified);
        editor.apply();
    }

    public List<ProductDto> getProductList() {
        String products = mSharedPreferences.getString(MOCK_PRODUCT_LIST, null);
        if (products != null) {
            Gson gson = new Gson();
            ProductDto[] productList = gson.fromJson(products, ProductDto[].class);
            return Arrays.asList(productList);
        }
        return null;
    }

    //endregion

    //region ==================== Misc ===================

    public void saveAuthToken(String authToken) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantsManager.AUTH_TOKEN_KEY, authToken);
        editor.apply();
    }

    public String getAuthToken() {
        return mSharedPreferences.getString(ConstantsManager.AUTH_TOKEN_KEY,
                ConstantsManager.INVALID_TOKEN);
    }

    //endregion
}
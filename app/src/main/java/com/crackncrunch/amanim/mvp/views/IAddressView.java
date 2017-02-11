package com.crackncrunch.amanim.mvp.views;

import com.crackncrunch.amanim.data.storage.dto.UserAddressDto;

public interface IAddressView extends IView {
    void showInputError();
    UserAddressDto getUserAddress();
}

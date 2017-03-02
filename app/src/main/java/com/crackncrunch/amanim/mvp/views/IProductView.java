package com.crackncrunch.amanim.mvp.views;

import com.crackncrunch.amanim.data.storage.dto.ProductDto;

public interface IProductView extends IView {
    void showProductView(ProductDto product);
    void updateProductCountView(ProductDto product);
    boolean isExpanded();
}

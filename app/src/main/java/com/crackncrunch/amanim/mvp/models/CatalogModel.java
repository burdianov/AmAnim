package com.crackncrunch.amanim.mvp.models;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.crackncrunch.amanim.data.storage.dto.ProductDto;
import com.crackncrunch.amanim.data.storage.realm.ProductRealm;

import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class CatalogModel extends AbstractModel {

    private BehaviorSubject<List<ProductDto>> mProductListObs = BehaviorSubject
            .create();

    public CatalogModel() {
        mProductListObs.onNext(getProductList());
    }

    //region ==================== Products ===================

    public List<ProductDto> getProductList() {
        return mDataManager.getPreferencesManager().getProductList();
    }

    //endregion

    public boolean isUserAuth() {
        return mDataManager.isAuthUser();
    }

    public Observable<ProductRealm> getProductObs() {
        Observable<ProductRealm> network = fromNetwork();
        //Observable<ProductRealm> network = Observable.empty();
        Observable<ProductRealm> disk = fromDisk();
        return Observable.mergeDelayError(disk, network)
                .distinct(ProductRealm::getId);
    }

    @RxLogObservable
    public Observable<ProductRealm> fromNetwork() {
        return mDataManager.getProductsObsFromNetwork();
    }

    @RxLogObservable
    public Observable<ProductRealm> fromDisk() {
        return mDataManager.getProductFromRealm();
    }
}

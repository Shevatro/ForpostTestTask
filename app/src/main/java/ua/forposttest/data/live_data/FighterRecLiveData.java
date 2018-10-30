package ua.forposttest.data.live_data;

import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.forposttest.data.FighterRecStorage;
import ua.forposttest.data.model.Fighter;
import ua.forposttest.data.model.FighterRec;

public class FighterRecLiveData extends LiveData<List<Fighter>> {
    private CompositeDisposable compositeDisposable;
    private List<FighterRec> fightRecord;

    public FighterRecLiveData(FighterRecStorage storage) {
        this.fightRecord = storage.getRec();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onActive() {
        init();
    }

    @Override
    protected void onInactive() {
        compositeDisposable.dispose();
    }

    private void init() {
        Disposable disposable = Flowable.zip(Flowable.just(fightRecord).flatMap(Flowable::fromIterable), Flowable.interval(1, 1, TimeUnit.SECONDS), (obs, timer) -> obs)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        users -> postValue(users.getFighters()),
                        // onError
                        throwable -> {

                        });
        compositeDisposable.add(disposable);
    }
}

package ua.forposttest.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import ua.forposttest.data.FighterRecStorage;
import ua.forposttest.data.live_data.FighterRecLiveData;
import ua.forposttest.data.live_data.FirebaseQueryLiveData;
import ua.forposttest.data.model.Fighter;

public class MainViewModel extends ViewModel {
    private final FighterRecStorage recStorage = new FighterRecStorage();

    private FirebaseQueryLiveData liveData;
    private FighterRecLiveData liveDataHistory;
    private boolean flagLastFight;

    public void init(boolean flagLastFight) {
        this.flagLastFight = flagLastFight;
    }

    @NonNull
    public LiveData<List<Fighter>> getListOfFightersLiveData() {
        if (flagLastFight) {
            return getDataHistory();
        } else {
            return getLiveData();
        }
    }

    private LiveData<List<Fighter>> getDataHistory() {
        if (liveDataHistory == null) {
            liveDataHistory = new FighterRecLiveData(recStorage);
        }
        return liveDataHistory;
    }

    private LiveData<List<Fighter>> getLiveData() {
        if (liveData == null) {
            liveData = new FirebaseQueryLiveData(FirebaseDatabase.getInstance().getReference().child("fighters"));
        }
        return liveData;
    }

    public void addHistory(List<Fighter> fighters) {
        Log.d("test", "addHistory");
        recStorage.add(fighters);
    }

    public void saveHistory() {
        Log.d("test", "saveHistory");
        //если это не просмотр последней битвы
        if (!flagLastFight) {
            recStorage.setRec();
        }
    }

}

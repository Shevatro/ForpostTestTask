package ua.forposttest.data.live_data;

import android.arch.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import timber.log.Timber;
import ua.forposttest.data.model.Fighter;

public class FirebaseQueryLiveData extends LiveData<List<Fighter>> {

    private final Query query;
    private MyValueEventListener listener;

    public FirebaseQueryLiveData(Query query) {
        this.query = query;
    }

    public FirebaseQueryLiveData(DatabaseReference ref) {
        this.query = ref;
        listener = new MyValueEventListener();
    }

    @Override
    protected void onActive() {
        query.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        query.removeEventListener(listener);
    }

    private class MyValueEventListener implements ValueEventListener {
        final GenericTypeIndicator<List<Fighter>> indicator = new GenericTypeIndicator<List<Fighter>>() {
        };

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot != null) {
                setValue(dataSnapshot.getValue(indicator));
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Timber.e(databaseError.toException(), "Can't listen to query %s", query);
        }
    }
}

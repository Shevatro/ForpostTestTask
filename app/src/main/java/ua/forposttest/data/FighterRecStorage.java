package ua.forposttest.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ua.forposttest.data.model.Fighter;
import ua.forposttest.data.model.FighterRec;

public class FighterRecStorage {
    private List<FighterRec> fightRecord = new ArrayList<>();
    private long currTimeTMP;

    public void add(List<Fighter> fighters) {
        if (fighters == null) return;
        long currTime = System.currentTimeMillis();
        if (currTimeTMP == 0)
            currTimeTMP = currTime;
        FighterRec fighterRec = new FighterRec(fighters, currTime - currTimeTMP);
        fightRecord.add(fighterRec);
        currTimeTMP = currTime;
    }

    public List<FighterRec> getRec() {
        final String unknown = "Unknown";
        String json = PersistentStorage.getProperty(PersistentStorage.FIELD_FIGHTER_RECORD, unknown);
        if (!json.equals(unknown)) {
            Type listType = new TypeToken<List<FighterRec>>() {
            }.getType();
            fightRecord = new Gson().fromJson(json, listType);
        }
        return fightRecord;
    }

    public void setRec() {
        PersistentStorage.addProperty(PersistentStorage.FIELD_FIGHTER_RECORD, new Gson().toJson(fightRecord));
    }
}

package ua.forposttest.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import ua.forposttest.R;
import ua.forposttest.ui.fragment.ChronologyFragment;
import ua.forposttest.ui.fragment.MapFragment;
import ua.forposttest.data.model.Fighter;

// @author Sergio Shevatro
public class MainActivity extends AppCompatActivity {
    private static final String BOOT_MODE = "BOOT_MODE";
    private boolean mFlagLastFight;
    private boolean mFlagInitSuccessful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null && savedInstanceState.containsKey(BOOT_MODE)) {
            init(savedInstanceState.getBoolean(BOOT_MODE));
        } else {
            showStartDialog();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //если пользователь сделал выбор в showStartDialog
        if (mFlagInitSuccessful) {
            savedInstanceState.putBoolean(BOOT_MODE, mFlagLastFight);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        ViewModelProviders.of(this).get(MainViewModel.class).saveHistory();
        super.onDestroy();
    }

    private void initModel(boolean flagLastFight) {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.init(flagLastFight);
        //записываем трек, если это не режим просмотра прошлой битвы
        if (!flagLastFight) {
            LiveData<List<Fighter>> liveData = viewModel.getListOfFightersLiveData();
            liveData.observe(this, viewModel::addHistory);
        }

    }

    private void showFragments() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_map, MapFragment.newInstance())
                .replace(R.id.container_chronology, ChronologyFragment.newInstance())
                .commit();
    }

    private void showStartDialog() {
        new MaterialDialog.Builder(this)
                .content(getString(R.string.description_start_dialog))
                .negativeText(R.string.last_fight)
                .positiveText(R.string.current_status)
                .cancelable(false)
                .onNegative((dialog, which) -> init(true))
                .onPositive((dialog, which) -> init(false))
                .show();
    }

    private void init(boolean state) {
        mFlagLastFight = state;
        mFlagInitSuccessful = true;
        initModel(state);
        showFragments();
    }
}

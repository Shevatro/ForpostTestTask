package ua.forposttest.ui.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import ua.forposttest.data.model.Fighter;
import ua.forposttest.ui.adapter.FighterAdapter;
import ua.forposttest.ui.MainViewModel;
import ua.forposttest.R;

public class ChronologyFragment extends Fragment {

    private FighterAdapter mAdapter;
    private RecyclerView mFightersList;

    public static ChronologyFragment newInstance() {
        return new ChronologyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chronology, container, false);
        mFightersList = root.findViewById(R.id.rv_fighters);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initModel();
    }

    private void initModel() {
        MainViewModel viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        LiveData<List<Fighter>> liveData = viewModel.getListOfFightersLiveData();
        liveData.observe(this, this::update);
    }

    private void update(List<Fighter> fighters) {
        if (fighters == null) return;
        if (mAdapter == null) {
            initAdapter(fighters);
        } else {
            updateAdapter(fighters);
        }
    }

    private void initAdapter(List<Fighter> fighters) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mFightersList.setLayoutManager(layoutManager);
        mFightersList.setHasFixedSize(true);
        mAdapter = new FighterAdapter(fighters);
        mFightersList.setAdapter(mAdapter);
    }

    private void updateAdapter(List<Fighter> fighters) {
        mAdapter.update(fighters);
    }
}

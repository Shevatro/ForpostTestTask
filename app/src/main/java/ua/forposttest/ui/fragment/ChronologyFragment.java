package ua.forposttest.ui.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;
import java.util.Objects;

import ua.forposttest.data.model.Fighter;
import ua.forposttest.ui.adapter.FighterAdapter;
import ua.forposttest.ui.MainViewModel;
import ua.forposttest.R;

public class ChronologyFragment extends Fragment {

    private ListView fightersList;
    private FighterAdapter adapter;

    public static ChronologyFragment newInstance() {
        return new ChronologyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chronology, container, false);
        fightersList = root.findViewById(R.id.fc_fighters_list);

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        MainViewModel viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        LiveData<List<Fighter>> liveData = viewModel.getListOfFightersLiveData();
        liveData.observe(this, this::update);
    }

    private void update(List<Fighter> fighters) {
        if (adapter == null) {
            initAdapter(fighters);
        } else {
            adapter.update(fighters);
        }
    }

    private void initAdapter(List<Fighter> fighters) {
        if (fighters == null) return;

        adapter = new FighterAdapter(getContext(), fighters);
        fightersList.setAdapter(adapter);
    }
}

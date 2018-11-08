package ua.forposttest.ui.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.List;
import java.util.Objects;

import ua.forposttest.ImageUtile;
import ua.forposttest.MapConst;
import ua.forposttest.data.model.Fighter;
import ua.forposttest.ui.MainViewModel;
import ua.forposttest.R;

public class MapFragment extends Fragment {

    private MapView mapView;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        initModel();
        return root;
    }

    //All the lifecycle methods that need to be overridden
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    private void initModel() {
        MainViewModel viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        LiveData<List<Fighter>> liveData = viewModel.getListOfFightersLiveData();
        liveData.observe(this, this::reDrawMarkers);
    }

    private void reDrawMarkers(List<Fighter> fighters) {
        mapView.getMapAsync(mapboxMap -> {
            mapboxMap.clear();
            addMarkers(mapboxMap, fighters);
            setCamera(mapboxMap, fighters.get(0).getPosition_lat(), fighters.get(0).getPosition_lon());
        });
    }

    private void addMarkers(MapboxMap mapboxMap, List<Fighter> fighters) {
        IconFactory iconFactory = IconFactory.getInstance(getContext());
        for (Fighter fighter : fighters) {
            Bitmap bm = ImageUtile.getBitmapFromVectorDrawable(getActivity(), chooseImageByType(fighter.getType()));
            ImageUtile.changeColor(bm, fighter.getTeam());
            Icon icon = iconFactory.fromBitmap(bm);
            mapboxMap.addMarker(new MarkerOptions()
                    .position(new LatLng(fighter.getPosition_lat(), fighter.getPosition_lon()))
                    .title(String.valueOf(fighter.getId()))
                    .icon(icon)
                    .snippet(buildFightInfo(fighter)));
        }
    }

    private String buildFightInfo(Fighter fighter) {
        return "team " + fighter.getTeam() + "\n" +
                "health " + fighter.getHealth() + "\n" +
                "type " + fighter.getType() + "\n" +
                "clips " + fighter.getClips() + "\n" +
                "ammo " + fighter.getAmmo();
    }

    private void setCamera(MapboxMap mapboxMap, double lat, double lon) {
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(lat, lon))
                .zoom(MapConst.zoomDefault)
                .tilt(MapConst.tiltDefault)
                .build();
        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
    }

    private int chooseImageByType(int type) {
        switch (type) {
            case 0:
                return R.drawable.ic_human;
            case 1:
                return R.drawable.ic_technic;
            default:
                return R.drawable.ic_other;
        }
    }
}

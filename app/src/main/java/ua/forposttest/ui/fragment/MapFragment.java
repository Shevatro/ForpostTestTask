package ua.forposttest.ui.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.List;
import java.util.Objects;

import ua.forposttest.ImageUtile;
import ua.forposttest.data.model.Fighter;
import ua.forposttest.ui.MainViewModel;
import ua.forposttest.R;

public class MapFragment extends Fragment {

    private MapView mapView;
    private List<Marker> markers;
    private CameraPosition position;
    private MapboxMap mapboxMap;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        Mapbox.getInstance(Objects.requireNonNull(getContext()), getString(R.string.access_token));
        mapView = root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initModel();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    private void initModel() {
        MainViewModel viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        LiveData<List<Fighter>> liveData = viewModel.getListOfFightersLiveData();
        liveData.observe(this, this::addMarkers);
    }

    private void getDialog(List<Fighter> fighters) {
        mapboxMap.setOnMarkerClickListener(marker -> {
            for (Fighter fighter : fighters) {
                if (Integer.parseInt(marker.getTitle()) == fighter.id) {
                    String message = "health " + fighter.health + "\n" +
                            "type " + fighter.type + "\n" +
                            "clips " + fighter.clips + "\n" +
                            "ammo " + fighter.ammo;

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(message)
                            .setTitle("team " + fighter.team);
                    builder.setPositiveButton("OK", (dialog, id) -> {

                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return false;
                }
            }
            return false;
        });
    }

    public int chooseImageByType(int type) {
        switch (type) {
            case 0:
                return R.drawable.ic_human;
            case 1:
                return R.drawable.ic_technic;
            default:
                return R.drawable.ic_other;
        }
    }

    public void addMarkers(List<Fighter> fighters) {
        mapView.getMapAsync(mapboxMap -> {
            MapFragment.this.mapboxMap = mapboxMap;
            getDialog(fighters);
            markers = mapboxMap.getMarkers();
            IconFactory iconFactory = IconFactory.getInstance(getActivity());
            Bitmap bm;
            Icon icon;
            if (fighters != null) {
                for (Fighter fighter : fighters) {
                    boolean sign = true;

                    for (Marker m : markers) {
                        if (Integer.parseInt(m.getTitle()) == fighter.id) {
                            m.setPosition(new LatLng(fighter.position_lat, fighter.position_lon));

                            sign = false;
                            break;
                        }
                    }

                    if (sign) {
                        bm = ImageUtile.getBitmapFromVectorDrawable(getActivity(), chooseImageByType(fighter.type));
                        ImageUtile.changeColor(bm, fighter.team);
                        icon = iconFactory.fromBitmap(bm);
                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(fighter.position_lat, fighter.position_lon))
                                .title(String.valueOf(fighter.id))
                                .icon(icon)
                        );
                    }
                }

                position = new CameraPosition.Builder()
                        .target(new LatLng(fighters.get(0).position_lat, fighters.get(0).position_lon))
                        .zoom(13)
                        .tilt(20)
                        .build();
            } else {
                position = new CameraPosition.Builder()
                        .target(new LatLng(49.989822, 36.356495))
                        .zoom(13)
                        .tilt(20)
                        .build();
            }
            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
        });
    }

}

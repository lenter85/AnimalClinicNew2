package com.example.myapplication.clinic.fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.network.ClinicNetwork;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddressCheckFragment extends Fragment {
    private LocationManager locationManager;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private EditText editText;
    private ImageButton imageButton;
    private ListView listView;
    private Button btnRegister;

    public AddressCheckFragment() {
        // Required empty public constructor
        //커밋 테스트
        //크하하
        //하하
        //수정아
        //되나
        //되나22
        //커밋
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address_check, container, false);


        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                AddressCheckFragment.this.googleMap = googleMap;
                startLocationService();
                showCurrentLocation(37.50472555248105, 126.95383390834913);  // y(latitude), x(longitude) 순서다
            }
        });

        editText = (EditText) view.findViewById(R.id.editText);
        imageButton = (ImageButton) view.findViewById(R.id.imageButton);
        listView = (ListView) view.findViewById(R.id.listView);
        btnRegister = (Button) view.findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.clinicLocation = editText.getText().toString();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, MainActivity.registerClinicFragment)
                        .commit();
            }
        });
        imageButton.setOnClickListener(onClickListener);

        return view;
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ClinicNetwork clinicNetwork = new ClinicNetwork();
            clinicNetwork.setContext(getContext());
            clinicNetwork.setGoogleMap(googleMap);
            clinicNetwork.getLocation(editText.getText().toString());
            Log.i("mylog", "editText 출력 : " + editText.getText().toString());






            //showCurrentLocation(Double.parseDouble(registerLocation.getLatitude()),Double.parseDouble(registerLocation.getLongitude()));

        }
    };



    private void startLocationService() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    1
            );
            return;
        }
    }

        /*locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                10000,
                0,
                gpsListener
        );

        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                10000,
                0,
                gpsListener
        );

        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastLocation != null) {
            Double latitude = lastLocation.getLatitude();
            Double longitude = lastLocation.getLongitude();
            showCurrentLocation(latitude, longitude);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /*private LocationListener gpsListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            showCurrentLocation(latitude, longitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };*/

    private void showCurrentLocation(Double latitude, Double longitude) {
        LatLng curPoint = new LatLng(latitude, longitude);
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //googleMap.setMyLocationEnabled(true);

        showAbbBankItems(latitude, longitude);
    }

   /* @Override
    public void onDestroy() {
        super.onDestroy();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.removeUpdates(gpsListener);
    }*/

    private void showAbbBankItems (Double latitude, Double longitude) {
        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(latitude, longitude));
        marker.title("지점명 : 중앙대학교");
        marker.snippet("주소 : 동작구");
        marker.draggable(true);
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.woman));

        googleMap.addMarker(marker);


    }

}

package com.example.googlemapproject.Views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.googlemapproject.Interfaces.MapsInterface;
import com.example.googlemapproject.Models.MapModel;
import com.example.googlemapproject.Presenters.MapsPresenter;
import com.example.googlemapproject.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, MapsInterface.MapsView {

    private GoogleMap mMap;
    MapsPresenter presenter;
    private ArrayList<LatLng>listLatLng = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        presenter = new MapsPresenter(this  , this);



       


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        UiSettings settings = mMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        settings.setCompassEnabled(true);
        settings.setMyLocationButtonEnabled(true);
        settings.setMapToolbarEnabled(true);
        presenter.loadlatandlngfrommode();
        presenter.drawingroutes();
        presenter.drawingpolygons();
        presenter.drawingcircles();
        presenter.drawingroutes2();


    }

    @Override
    public void SetLocationsOnMap(ArrayList<MapModel> modelArrayList) {
        if(modelArrayList.size() > 0 ){
            for (MapModel model : modelArrayList){
                if(model.getLat()>0 && model.getLan()>0){
                   mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(model.getLat(),model.getLan()))
                            .title(model.getTitle())
//                            .snippet(bean.getSnippet())
                            .icon(model.getIcon()));
                    listLatLng.add(new LatLng(model.getLat() , model.getLan()));
                }
            }
            SetZoomlevel(listLatLng);
        }

    }

    @Override
    public void addpolylinetomap(PolylineOptions polylineOptions) {
       Polyline polyline =  mMap.addPolyline(polylineOptions);
        List<PatternItem> pattern = Arrays.<PatternItem>asList(
                new Dot(), new Gap(10), new Dash(20), new Gap(10));
       polyline.setPattern(pattern);
//       polyline.setJointType(JointType.ROUND);

    }

    @Override
    public void addpolylinetomap2(PolylineOptions polylineOptions) {
        mMap.addPolyline(polylineOptions);

    }

    @Override
    public void addpolygonetomap(PolygonOptions polygonOptions) {
       Polygon pol =  mMap.addPolygon(polygonOptions);
//       pol.setStrokeJointType(JointType.ROUND);
//        List<PatternItem> pattern = Arrays.<PatternItem>asList(
//                new Dot(), new Gap(10), new Dash(20), new Gap(10));
//        pol.setStrokePattern(pattern);

    }

    @Override
    public void addCircletomap(CircleOptions circleOptions) {

        mMap.addCircle(circleOptions);
//                List<PatternItem> pattern = Arrays.<PatternItem>asList(
//                new Dot(), new Gap(10), new Dash(20), new Gap(10));
//                circle.setStrokePattern(pattern);
    }


    public void  SetZoomlevel(ArrayList<LatLng> listLatLng)
    {
        if (listLatLng != null && listLatLng.size() == 1)
        {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(listLatLng.get(0), 10));
        }
        else if (listLatLng != null && listLatLng.size() > 1)
        {
            final LatLngBounds.Builder builder = LatLngBounds.builder();
            for (int i = 0; i < listLatLng.size(); i++)
            {
                builder.include(listLatLng.get(i));
            }

            @SuppressLint("ResourceType")
            final ViewTreeObserver treeObserver = findViewById(R.id.rlMapLayout).getViewTreeObserver();
            treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
            {
                @SuppressLint("ResourceType")
                @SuppressWarnings("deprecation")
                @Override
                public void onGlobalLayout()
                {
                    if(mMap != null){
                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), findViewById(R.id.map)
                                .getWidth(), findViewById(R.id.map).getHeight(), 80));
                        findViewById(R.id.rlMapLayout).getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });

        }
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

}

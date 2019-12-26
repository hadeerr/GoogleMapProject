package com.example.googlemapproject.Models;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.googlemapproject.R;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapModel {
    BitmapDescriptor icon ;
    double lat , lan ;
    String title;
    Context context;

    public MapModel(Context context) {
        this.context = context;
    }

    public MapModel(BitmapDescriptor icon, double lat, double lan , String title) {
        this.icon = icon;
        this.lat = lat;
        this.lan = lan;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BitmapDescriptor getIcon() {
        return icon;
    }

    public void setIcon(BitmapDescriptor icon) {
        this.icon = icon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLan() {
        return lan;
    }

    public void setLan(double lan) {
        this.lan = lan;
    }

    public ArrayList<MapModel> LoadLocations(){
        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).
                inflate(R.layout.marker_icon, null);
        View marker2 = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).
                inflate(R.layout.marker_icon2, null);

        ArrayList<MapModel> models = new ArrayList<>();
        models.add(new MapModel(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE) ,
                30.084478 , 31.328669 , "first"));
        models.add(new MapModel(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN) ,
                30.088120 , 31.329870 , "second"));
        models.add(new MapModel(BitmapDescriptorFactory.fromResource(R.drawable.baseline_opacity_black),
                30.088120 , 31.323970 , "third"));
        models.add(new MapModel(BitmapDescriptorFactory.fromResource(R.drawable.baseline_pan_tool_black) ,
                30.09962 , 31.339812 , "fourth"));
        models.add(new MapModel(BitmapDescriptorFactory.fromResource(R.drawable.baseline_room_black) ,
                30.09555 , 31.335833 , "fifth"));
        models.add(new MapModel(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(context , marker)) ,
                30.087449 , 31.341709 , "six"));
        models.add(new MapModel(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(context ,marker2) ),
                30.082398 , 31.324551 , "seven"));
         models.add(new MapModel(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE) ,
                 30.097251 , 31.335583  , "eight"));


         return models;

    }
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    public PolylineOptions  getpolyline(){

        PolylineOptions polylineOptions = new PolylineOptions()

                .add(new LatLng(30.084478 , 31.328669 ))
                .add(new LatLng(30.088120 , 31.329870 ))
                .add(new LatLng(30.09962 , 31.339812 ))
                .add(new LatLng(30.088120, 31.323970 ))
                .color(Color.BLUE )
                ;

//                .add(new LatLng(30.088120 , 31.323970 ))
//                .add(new LatLng(30.09962 , 31.339812 ))
//                .add(new LatLng(30.09555 , 31.335833 ))
//                .add(new LatLng(30.087449 , 31.341709 ))
//                .add(new LatLng(30.082398 , 31.324551 ))
//                .add(new LatLng(30.097251 , 31.335583 ));



        return polylineOptions;
    }

    public PolylineOptions  getpolyline2() {
        PolylineOptions polylineOptions = new PolylineOptions()
                .add(new LatLng(30.09962 , 31.339812 ))
                .add(new LatLng(30.087449 , 31.341709 ))
                .add(new LatLng(30.097251 , 31.335583 ))
                .color(Color.RED )
                ;
        return polylineOptions;
    }

        public PolygonOptions getpolygone(){
        Iterable<LatLng> latLngs = new ArrayList<>();
        ((ArrayList<LatLng>) latLngs).add(new LatLng(30.09555 ,31.335833 ));
        ((ArrayList<LatLng>) latLngs).add(new LatLng(30.087449 ,31.341709 ));
        PolygonOptions polygonOptions = new PolygonOptions()

                .add(new LatLng(30.09555 , 31.335833 ))
                .add(new LatLng(30.087449 , 31.341709 ))
                .add(new LatLng(30.082398 , 31.324551 ))
//                .add(new LatLng(30.088120, 31.323970 ))
//                .strokeColor(Color.GREEN)
//                .fillColor(Color.RED)
                .addHole(latLngs);

        return polygonOptions;
    }

    public CircleOptions getcircle(){
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(30.097251 , 31.335583 ))
                .radius(500)
                .strokeColor(Color.GRAY)
                .fillColor(Color.LTGRAY)
                ;
        return circleOptions;
    }
}

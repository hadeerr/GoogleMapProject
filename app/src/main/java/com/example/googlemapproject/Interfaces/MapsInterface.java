package com.example.googlemapproject.Interfaces;

import com.example.googlemapproject.Models.MapModel;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsInterface {

    public interface MapsView{

        public void SetLocationsOnMap(ArrayList<MapModel> modelArrayList);
        public void addpolylinetomap(PolylineOptions polylineOptions);
        public void addpolylinetomap2(PolylineOptions polylineOptions);
        public void addpolygonetomap(PolygonOptions polygonOptions);
        public void addCircletomap(CircleOptions circleOptions);


    }

    public interface MapsPresenter{
       public void loadlatandlngfrommode();
       public void drawingroutes();
       public void drawingroutes2();
       public void drawingpolygons();
       public void drawingcircles();

    }
}

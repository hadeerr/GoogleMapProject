package com.example.googlemapproject.Presenters;

import android.content.Context;

import com.example.googlemapproject.Interfaces.MapsInterface;
import com.example.googlemapproject.Models.MapModel;

public class MapsPresenter implements MapsInterface.MapsPresenter {
    MapsInterface.MapsView view ;
    MapModel model;


    public MapsPresenter(MapsInterface.MapsView view , Context context) {
        this.view = view;
        this.model = new MapModel(context);
    }


    @Override
    public void loadlatandlngfrommode() {

        view.SetLocationsOnMap( model.LoadLocations());



    }

    @Override
    public void drawingroutes() {
        view.addpolylinetomap(model.getpolyline());
    }

    @Override
    public void drawingroutes2() {
        view.addpolylinetomap2(model.getpolyline2());

    }

    @Override
    public void drawingpolygons() {
        view.addpolygonetomap(model.getpolygone());

    }

    @Override
    public void drawingcircles() {
        view.addCircletomap(model.getcircle());
    }
}

package vb.refugeehelpvb.helpers;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

import vb.refugeehelpvb.R;

/**
 * Created by Seb on 01.01.2016.
 */
public class MapBuilder extends MapView {

    final ResourceProxy mResourceProxy;

    ArrayList<OverlayItem> mapMarkers = new ArrayList<OverlayItem>();

    public MapBuilder(Context context) {
        super(context);

        mResourceProxy = new DefaultResourceProxyImpl(context);

        setClickable(true);
        setMaxZoomLevel(16);
        setMinZoomLevel(16);
        setTileSource(new XYTileSource("MapquestOSM", 0, 18, 256, ".jpg", new String[]{}));
        getController().setZoom(16);

        setUseDataConnection(false);
        setMultiTouchControls(true);


    }

    public void setGeoPoint(double lat, double lon){

        OverlayItem flag = new OverlayItem("Here", "SampleDescription", new GeoPoint(lat, lon));
        flag.setMarker(getResources().getDrawable(R.drawable.ic_destination));
        mapMarkers.add(flag);

    }

    public void centerMap(double lat, double lon){
        if(lat > 0 || lon > 0){
            GeoPoint geo = new GeoPoint(lat, lon);
            getController().setCenter(geo);
        }else{
            GeoPoint geo = new GeoPoint(50.7507624,9.265958);
            getController().setCenter(geo);
        }
    }

    public MapView drawMap(){

        ItemizedIconOverlay mMyLocationOverlay = new ItemizedIconOverlay<OverlayItem>(mapMarkers,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index,
                                                     final OverlayItem item) {

                        return true; // We 'handled' this event.
                    }
                    @Override
                    public boolean onItemLongPress(final int index,
                                                   final OverlayItem item) {
                        return false;
                    }
                }, mResourceProxy);

        getOverlays().add(mMyLocationOverlay);
        invalidate();

        return this;
    }



}

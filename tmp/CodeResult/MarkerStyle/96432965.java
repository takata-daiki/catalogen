package com.nutiteq.advancedmap.activity;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.nutiteq.MapView;
import com.nutiteq.advancedmap.R;
import com.nutiteq.components.Components;
import com.nutiteq.components.Envelope;
import com.nutiteq.components.MapPos;
import com.nutiteq.components.Options;
import com.nutiteq.filepicker.FilePickerActivity;
import com.nutiteq.layers.raster.GdalDatasetInfo;
import com.nutiteq.layers.raster.GdalMapLayer;
import com.nutiteq.log.Log;
import com.nutiteq.projections.EPSG3857;
import com.nutiteq.rasterdatasources.HTTPRasterDataSource;
import com.nutiteq.rasterdatasources.RasterDataSource;
import com.nutiteq.rasterlayers.RasterLayer;
import com.nutiteq.utils.UnscaledBitmapLoader;






// Isil
import android.graphics.Bitmap;

import java.util.ArrayList;

import com.nutiteq.style.MarkerStyle;
import com.nutiteq.style.PointStyle;
import com.nutiteq.style.LineStyle;
import com.nutiteq.style.PolygonStyle;
import com.nutiteq.style.StyleSet;
import com.nutiteq.ui.DefaultLabel;
import com.nutiteq.ui.Label;
import com.nutiteq.vectorlayers.GeometryLayer;
import com.nutiteq.vectorlayers.MarkerLayer;
import com.nutiteq.geometry.*;

/**
 * 
 * Demonstrates GdalMapLayer layer, whic uses GDAL native library
 * 
 * Requires GDAL native library with JNI wrappers, and raster data file (e.g. GeoTIFF) in SDCard
 * 
 * GDAL is used to load map tiles, and tiles are stored to persistent cache for faster loading later.
 * 
 * See https://github.com/nutiteq/hellomap3d/wiki/Gdal-layer for details
 * 
 * @author jaak
 *
 */
public class RasterFileMapActivity extends Activity implements FilePickerActivity {

    private MapView mapView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // spinner in status bar, for progress indication
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.main);

        // enable logging for troubleshooting - optional
        Log.enableAll();
        Log.setTag("gdal");

        // 1. Get the MapView from the Layout xml - mandatory
        mapView = (MapView) findViewById(R.id.mapView);

        // Optional, but very useful: restore map state during device rotation,
        // it is saved in onRetainNonConfigurationInstance() below
        Components retainObject = (Components) getLastNonConfigurationInstance();
        if (retainObject != null) {
            // just restore configuration, skip other initializations
            mapView.setComponents(retainObject);
            return;
        } else {
            // 2. create and set MapView components - mandatory
            Components components = new Components();
            // set stereo view: works if you rotate to landscape and device has HTC 3D or LG Real3D
            mapView.setComponents(components);
        }


        RasterDataSource dataSource = new HTTPRasterDataSource(new EPSG3857(), 0, 18, "http://otile1.mqcdn.com/tiles/1.0.0/osm/{zoom}/{x}/{y}.png");
        RasterLayer mapLayer = new RasterLayer(dataSource, 0);
        mapView.getLayers().setBaseLayer(mapLayer);

        // read filename from extras
        Bundle b = getIntent().getExtras();
        String file = b.getString("selectedFile");

        try {
            GdalMapLayer gdalLayer = new GdalMapLayer(new EPSG3857(), 0, 18, 9, file, mapView, true);
            gdalLayer.setShowAlways(true);
            mapView.getLayers().addLayer(gdalLayer);
            Map<Envelope, GdalDatasetInfo> dataSets = gdalLayer.getDatasets();
            if(!dataSets.isEmpty()){
                GdalDatasetInfo firstDataSet = (GdalDatasetInfo) dataSets.values().toArray()[0];

                MapPos centerPoint = new MapPos((firstDataSet.envelope.maxX+firstDataSet.envelope.minX)/2,
                        (firstDataSet.envelope.maxY+firstDataSet.envelope.minY)/2);


                Log.debug("found extent "+firstDataSet.envelope+", zoom "+firstDataSet.bestZoom+", centerPoint "+centerPoint);

                mapView.setFocusPoint(centerPoint);
                mapView.setZoom((float) firstDataSet.bestZoom);
            }else{
                Log.debug("no dataset info");
                Toast.makeText(this, "No dataset info", Toast.LENGTH_LONG).show();

                mapView.setFocusPoint(new MapPos(0,0));
                mapView.setZoom(1.0f);

            }

            // rotation - 0 = north-up
            mapView.setMapRotation(0f);
            // tilt means perspective view. Default is 90 degrees for "normal" 2D map view, minimum allowed is 30 degrees.
            mapView.setTilt(90.0f);

            // Activate some mapview options to make it smoother - optional
            mapView.getOptions().setPreloading(false);
            mapView.getOptions().setSeamlessHorizontalPan(true);
            mapView.getOptions().setTileFading(false);
            mapView.getOptions().setKineticPanning(true);
            mapView.getOptions().setDoubleClickZoomIn(true);
            mapView.getOptions().setDualClickZoomOut(true);

            // set sky bitmap - optional, default - white
            mapView.getOptions().setSkyDrawMode(Options.DRAW_BITMAP);
            mapView.getOptions().setSkyOffset(4.86f);
            mapView.getOptions().setSkyBitmap(
                    UnscaledBitmapLoader.decodeResource(getResources(),
                            R.drawable.sky_small));

            // Map background, visible if no map tiles loaded - optional, default - white
            mapView.getOptions().setBackgroundPlaneDrawMode(Options.DRAW_BITMAP);
            mapView.getOptions().setBackgroundPlaneBitmap(
                    UnscaledBitmapLoader.decodeResource(getResources(),
                            R.drawable.background_plane));
            mapView.getOptions().setClearColor(Color.WHITE);

            // configure texture caching - optional, suggested
            mapView.getOptions().setTextureMemoryCacheSize(20 * 1024 * 1024);
            mapView.getOptions().setCompressedMemoryCacheSize(8 * 1024 * 1024);

            // define online map persistent caching - optional, suggested. Default - no caching
            // mapView.getOptions().setPersistentCachePath(this.getDatabasePath("mapcache").getPath());
            // set persistent raster cache limit to 100MB
            mapView.getOptions().setPersistentCacheSize(100 * 1024 * 1024);
            
            
            // BEGIN Isil
            // Add a clickable marker
            //
            // define marker style (image, size, color)
            Bitmap pointMarker = UnscaledBitmapLoader.decodeResource(getResources(), R.drawable.olmarker);
            MarkerStyle markerStyle = MarkerStyle.builder().setBitmap(pointMarker).setSize(0.5f).setColor(Color.WHITE).build();
            // define label what is shown when you click on marker
            Label markerLabel = new DefaultLabel("San Francisco", "Here is a marker");

            // define location of the marker, it must be converted to base map coordinate system
            MapPos markerLocation = mapLayer.getProjection().fromWgs84(-122.06816f, 37.40686f);

            // create layer and add object to the layer, finally add layer to the map. 
            // All overlay layers must be same projection as base layer, so we reuse it
            MarkerLayer markerLayer = new MarkerLayer(mapLayer.getProjection());
            markerLayer.add(new Marker(markerLocation, markerLabel, markerStyle, markerLayer));
            mapView.getLayers().addLayer(markerLayer);
            
            // END Isil
            
            // BEGIN Isil
            // Creating a Geometry Layer and drawing polylines
            //
            // Define Styles
            // define minimum zoom for vector style visibility. If 0, then objects are visible with any zoom.
            int minZoom = 10;

            // load bitmaps for vector elements. You can get the images from Hellomap3D project res/drawable
            // these are simple anti-aliased bitmaps which can change colour, should be good for most cases
            Bitmap pointMarker2 = UnscaledBitmapLoader.decodeResource(getResources(), R.drawable.point);
            Bitmap lineMarker = UnscaledBitmapLoader.decodeResource(getResources(), R.drawable.line);

            // set styles for all 3 object types: point, line and polygon
            StyleSet<PointStyle> pointStyleSet = new StyleSet<PointStyle>();
            PointStyle pointStyle = PointStyle.builder().setBitmap(pointMarker2).setSize(0.1f).setColor(Color.GREEN).build();
            pointStyleSet.setZoomStyle(minZoom,pointStyle);

            // We reuse here pointStyle for Line. This is used for line caps, useful for nicer polylines
            // Also do not forget to set Bitmap for Line. This allows to have fancy styles for lines.
            StyleSet<LineStyle> lineStyleSet = new StyleSet<LineStyle>();
            lineStyleSet.setZoomStyle(minZoom, LineStyle.builder().setBitmap(lineMarker).setWidth(0.1f).setColor(Color.GREEN).setPointStyle(pointStyle).build());
            PolygonStyle polygonStyle = PolygonStyle.builder().setColor(Color.BLUE).build();
            StyleSet<PolygonStyle> polygonStyleSet = new StyleSet<PolygonStyle>(null);
            polygonStyleSet.setZoomStyle(minZoom, polygonStyle);
            
            // Add geometry layer
            GeometryLayer geomLayer = new GeometryLayer(mapLayer.getProjection());
            mapView.getLayers().addLayer(geomLayer);
            
            geomLayer.add(new Point(mapLayer.getProjection().fromWgs84(-122.06816f, 37.40696f),  new DefaultLabel("Isil: Geometry point"), pointStyle, null));
            
            // Add line with predefined coordinates
            // define 2 lines as WGS84 coordinates in an array.
            double[][][] lCoordss = {{{-122.06f,37.40f},{-122.05f,37.41},{-122.04f,37.42f}},{{-121.1f, 38.0f},{-121.2f,38.1f},{-121.3f,38.2f}}};

            // create two lines with these coordinates
            // if your line is in basemap projection coordinates, no need to use conversion
            for(double[][] lCoords:lCoordss){
                ArrayList<MapPos> lPoses =  new ArrayList<MapPos>();
                for(double[] coord:lCoords){
                    lPoses.add(mapLayer.getProjection().fromWgs84((float)coord[0],(float)coord[1]));
                }
                geomLayer.add(new Line(lPoses, new DefaultLabel("Line"), lineStyleSet, null));
            }
                        
            // add polygon with a hole. Inner hole coordinates must be entirely within.
            //double[][] pCoordsOuter = {{0,0},{0,51},{22,51},{0,0}}; // outer ring
            double[][] pCoordsOuter = {{-122,37},{-122,37.5},{-120,37.5},{-122,37}}; // outer ring
            //double[][] pCoordsInner = {{1,10},{1,50},{10,50},{1,10}}; // inner ring
            double[][] pCoordsInner = {{-121,37.2},{-121,37.4},{-121.5,37.4},{-121,37.2}}; // inner ring

            ArrayList<MapPos> outerPoses =  new ArrayList<MapPos>();
            for(double[] coord:pCoordsOuter){
              outerPoses.add(mapLayer.getProjection().fromWgs84((float)coord[0],(float)coord[1]));
            }

            ArrayList<MapPos> innerPoses =  new ArrayList<MapPos>();
            for(double[] coord:pCoordsInner){
                innerPoses.add(mapLayer.getProjection().fromWgs84((float)coord[0],(float)coord[1]));
            }
            // we need to create List of holes, as one polygon can have several holes
            // here we have just one. You can have nil there also.
            List<List<MapPos>> holes = new ArrayList<List<MapPos>>();
            holes.add(innerPoses);

            geomLayer.add(new Polygon(outerPoses, holes, new DefaultLabel("Polygon"), polygonStyleSet, null));
                        
            // END Isil

            // 4. zoom buttons using Android widgets - optional
            // get the zoomcontrols that was defined in main.xml
            ZoomControls zoomControls = (ZoomControls) findViewById(R.id.zoomcontrols);
            // set zoomcontrols listeners to enable zooming
            zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
                public void onClick(final View v) {
                    mapView.zoomIn();
                }
            });
            zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
                public void onClick(final View v) {
                    mapView.zoomOut();
                }
            });

        } catch (IOException e) {
            Toast.makeText(this, "ERROR "+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onStart() {
        mapView.startMapping();
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.stopMapping();
    }

    public MapView getMapView() {
        return mapView;
    }

    @Override
    public String getFileSelectMessage() {
        return "Select a raster file (.tif etc)";
    }

    @Override
    public FileFilter getFileFilter() {
        return new FileFilter() {
            @Override
            public boolean accept(File file) {
                String fileExtension = file.getName().substring(file.getName().lastIndexOf(".")+1).toLowerCase();
                Vector<String> exts = GdalMapLayer.getExtensions();
                return (file.isDirectory() || exts.contains(fileExtension));
            }
        };
    }

}


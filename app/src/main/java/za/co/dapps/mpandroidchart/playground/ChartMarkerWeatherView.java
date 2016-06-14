package za.co.dapps.mpandroidchart.playground;

import android.content.Context;
import android.graphics.Canvas;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.pwittchen.weathericonview.WeatherIconView;

public class ChartMarkerWeatherView extends MarkerView {

    private int uiScreenWidth;
    private int uiScreenHeight;

    private WeatherIconView weatherIconView;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     */
    public ChartMarkerWeatherView(Context context) {
        super(context, R.layout.layout_chart_marker_icon_view);
        weatherIconView = (WeatherIconView) findViewById(R.id.ivWeatherIcon);

        uiScreenWidth = getResources().getDisplayMetrics().widthPixels;
        uiScreenHeight = getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        final DataPoint dataPoint = (DataPoint) e.getData();
        if (dataPoint != null) {
            weatherIconView.setIconResource(dataPoint.getWeatherIcon());
        } else {
            weatherIconView.setText(null);
        }
    }

    @Override
    public int getXOffset(float xpos) {
        // this will center the marker-view horizontally
        return 0;
    }

    @Override
    public int getYOffset(float ypos) {
        // this will cause the marker-view to be above the selected value
        return -getHeight()/2;
    }

    @Override
    public void draw(Canvas canvas, float posx, float posy) {
        posx = uiScreenWidth - getWidth();
        posy += getYOffset(posy);

        // translate to the correct position and draw
        canvas.translate(posx, posy);
        draw(canvas);
        canvas.translate(-posx, -posy);
    }
}

package za.co.dapps.mpandroidchart.playground;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lineChartData)
    LineChart lineChartData;
    @BindView(R.id.lineChartWeather)
    LineChart lineChartWeather;
    @BindView(R.id.barChartData)
    HorizontalBarChart barChartData;

    private Random rand = new Random();
    private CoupleChartGestureListener coupleChartGestureListener;

    private final int NUM_POINTS_HOURS = 132; // 5.5days

    private final int WEATHER_ICON_COUNT = 8;
    private int[] weatherIcons = new int[] { R.string.wi_day_sunny,
                                             R.string.wi_day_rain,
                                             R.string.wi_day_fog,
                                             R.string.wi_day_showers,
                                             R.string.wi_day_snow,
                                             R.string.wi_day_sprinkle,
                                             R.string.wi_day_snow_thunderstorm,
                                             R.string.wi_day_cloudy};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // add data
        setLineData(lineChartData, NUM_POINTS_HOURS);
        setBarData(barChartData, NUM_POINTS_HOURS);
        setWeatherData(lineChartWeather, NUM_POINTS_HOURS);

        setEvents(lineChartWeather, NUM_POINTS_HOURS / 10);

        coupleChartGestureListener = new CoupleChartGestureListener(
                barChartData, new Chart[]{lineChartData, lineChartWeather});

        barChartData.setOnChartGestureListener(coupleChartGestureListener);
    }

    @Override
    public void onStart() {
        super.onStart();

        barChartData.postDelayed(new Runnable() {
            @Override
            public void run() {
                barChartData.zoom(0f, 4f, 0, 0);
                coupleChartGestureListener.syncCharts();
            }
        }, 100);
    }

    private void setLineData(LineChart mChart, int count) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < 4000; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        int i = 0;
        for (i = 0; i < count - 1; i++) {
            yVals.add(new Entry(i, randInt(500, 2000)));
        }

        yVals.add(new Entry(i, 0));

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");

            set1.setFillAlpha(255);
            set1.setLineWidth(1f);
            set1.setDrawCircleHole(false);
            set1.setDrawCircles(false);
            set1.setDrawFilled(true);
            set1.setDrawValues(false);
            set1.setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            set1.setFillColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            set1.setHighlightEnabled(false);

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(xVals, dataSets);


            final YAxis axisLeft = mChart.getAxisLeft();
            axisLeft.setLabelCount(NUM_POINTS_HOURS, true);
            axisLeft.setAxisMinValue(0);
            axisLeft.setAxisMaxValue(NUM_POINTS_HOURS);
            axisLeft.setDrawGridLines(false);
            // TEMP
            axisLeft.setDrawLabels(true);
            axisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
            axisLeft.setEnabled(false);
//            axisLeft.setInverted(true);
//            axisLeft.setAvoidFirstLastClipping(true);

            final YAxis axisRight = mChart.getAxisRight();
            axisRight.setEnabled(false);
            axisRight.setDrawAxisLine(false);
            axisRight.setDrawGridLines(false);


            // set data
            mChart.setData(data);
            mChart.setViewPortOffsets(0f, 0f, 0f, 0f);
            mChart.setPadding(0, 0, 30, 0);
            mChart.setDescription("");
            mChart.getLegend().setEnabled(false);

            mChart.notifyDataSetChanged();
        }
    }

    private void setBarData(final HorizontalBarChart chart, int count) {

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(String.valueOf(i));
        }


        int r = 1;
        for (int i = 0; i < count; i++) {
            if (i > (r*24 - 12) && i < r*24) {
                yVals1.add(new BarEntry(randInt(500, 3800), i));
            } else {
                if (i > r*24) {
                    r++;
                }

                yVals1.add(new BarEntry(0, i));
            }
        }

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet 1");
        set1.setDrawValues(false);
        set1.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        set1.setHighLightColor(ContextCompat.getColor(this, R.color.colorPrimary));
        set1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                if (value > 0) {
                    return String.valueOf(value);
                }
                return "";
            }
        });

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);


        //xAxis is a inverted yAxis since the graph is horizontal
        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setAxisMaxValue(NUM_POINTS_HOURS);
        xAxis.setAxisMinValue(0);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setLabelsToSkip(0);

        //yAxis is an inverted xAxis since the graph is horizontal
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setEnabled(false);
        yAxisLeft.setDrawLabels(false);
        yAxisLeft.setAxisMinValue(0);
        yAxisLeft.setAxisMaxValue(4000);
        yAxisLeft.setDrawTopYLabelEntry(false);


        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setEnabled(false);
        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawTopYLabelEntry(false);

        chart.setMinOffset(0f);
        chart.setViewPortOffsets(0f, 0f, 0f, 0f);
        chart.setPadding(0, 0, 0, 0);
        chart.setDescription("");
        chart.getLegend().setEnabled(false);
        chart.setExtraOffsets(0f, 0f, 0f, 0f);

        //mChart.notifyDataSetChanged();

        // Finally, set the data
        BarData data = new BarData(xVals, dataSets);
        chart.setData(data);

        chart.post(new Runnable() {
            @Override
            public void run() {
                chart.invalidate();
            }
        });
    }

    public int randInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public int randInt(int max) {
        return rand.nextInt(max);
    }

    private void setWeatherData(LineChart mChart, int count) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            DataPoint d = null;

            if (i % 4 == 0) {
                d = new DataPoint(DateTime.now(), i,
                        getString(getRandomWeather()));
            }

            yVals.add(new Entry(i, i, d));
        }

        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        set1.setDrawValues(false);
        set1.setDrawCircleHole(false);
        set1.setDrawCircles(false);
        set1.setHighlightEnabled(false);
        set1.setColor(ContextCompat.getColor(this, R.color.colorTransparent));

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        final XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawLabels(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setEnabled(false);
        final YAxis axisLeft = mChart.getAxisLeft();
        axisLeft.setLabelCount(NUM_POINTS_HOURS, true);
        axisLeft.setAxisMinValue(0);
        axisLeft.setAxisMaxValue(NUM_POINTS_HOURS);
        axisLeft.setDrawGridLines(false);
        axisLeft.setDrawLabels(false);
        axisLeft.setEnabled(false);
        final YAxis axisRight = mChart.getAxisRight();
        axisRight.setEnabled(false);
        axisRight.setDrawAxisLine(false);

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        data.setHighlightEnabled(false);

        // set the marker to the chart
        mChart.setMarkerView(new ChartMarkerWeatherView(this));
        mChart.setDrawMarkerViews(true);
        mChart.setDrawGridBackground(false);

        // set data
        mChart.setData(data);
        mChart.setViewPortOffsets(0f, 0f, 0f, 0f);
        mChart.setPadding(0, 0, 0, 0);
        mChart.setDescription("");

        //High all of it!
        Highlight[] highlights = new Highlight[count / 2];
        int pos = 0;
        for (int i = 0; i < count; i++) {
            if (i % 2 == 0) {
                highlights[pos] = new Highlight(i, 0);
                pos++;
            }
        }
        mChart.highlightValues(highlights);
        mChart.notifyDataSetChanged();
    }

    private void setEvents(LineChart chart, int count) {
        final YAxis axisLeft = chart.getAxisLeft();

        // Limit Lines
        for (int i = 0; i < count; i++) {
            final int pos = randInt(NUM_POINTS_HOURS);

            LimitLine ll1 = new LimitLine(pos, String.format(Locale.ENGLISH, "Event %d", i));
            ll1.setLineColor(Color.GREEN);
            ll1.enableDashedLine(25f, 15f, 0f);
            ll1.setLineWidth(2.0f);
            ll1.setTextColor(Color.BLACK);
            ll1.setTextSize(12f);
            axisLeft.addLimitLine(ll1);
        }
    }

    private int getRandomWeather() {
        return weatherIcons[randInt(WEATHER_ICON_COUNT)];
    }
}

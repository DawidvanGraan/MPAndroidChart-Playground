package za.co.dapps.mpandroidchart.playground;

import org.joda.time.DateTime;

public class DataPoint {

    private DateTime dateTime;
    private int value;
    private String weatherIcon;

    public DataPoint(final DateTime dateTime,
                     final int value,
                     final String weatherIcon) {
        this.dateTime = dateTime;
        this.value = value;
        this.weatherIcon = weatherIcon;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public int getValue() {
        return value;
    }
}

package com.example.rbt_praksa_android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AirVironment implements Parcelable {

    protected AirVironment(Parcel in) {
        id = in.readInt();
        if (in.readByte() == 0) {
            temperature = null;
        } else {
            temperature = in.readDouble();
        }
        if (in.readByte() == 0) {
            humidity = null;
        } else {
            humidity = in.readDouble();
        }
        if (in.readByte() == 0) {
            pollution = null;
        } else {
            pollution = in.readDouble();
        }
        timestamp = in.readString();
    }

    public static final Creator<AirVironment> CREATOR = new Creator<AirVironment>() {
        @Override
        public AirVironment createFromParcel(Parcel in) {
            return new AirVironment(in);
        }

        @Override
        public AirVironment[] newArray(int size) {
            return new AirVironment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        if (temperature == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(temperature);
        }
        if (humidity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(humidity);
        }
        if (pollution == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(pollution);
        }
        dest.writeString(timestamp);
    }

    @SerializedName("id")
    private int id;

    @SerializedName("temperature")
    private Double temperature;

    @SerializedName("humidity")
    private Double humidity;

    @SerializedName("pollution")
    private Double pollution;

    @SerializedName("timestamp")
    private String timestamp;

    public AirVironment(int id, Double temperature, Double humidity, Double pollution, String timestamp) {
        this.id = id;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pollution = pollution;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getPollution() {
        return pollution;
    }

    public void setPollution(Double pollution) {
        this.pollution = pollution;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "AirVironment{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", pollution=" + pollution +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}

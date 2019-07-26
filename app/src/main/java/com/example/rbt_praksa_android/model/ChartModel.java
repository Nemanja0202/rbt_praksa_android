package com.example.rbt_praksa_android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ChartModel implements Parcelable {
    private float value;

    public ChartModel(float value) {
        this.value = value;
    }

    protected ChartModel(Parcel in) {
        value = in.readFloat();
    }

    public static final Creator<ChartModel> CREATOR = new Creator<ChartModel>() {
        @Override
        public ChartModel createFromParcel(Parcel in) {
            return new ChartModel(in);
        }

        @Override
        public ChartModel[] newArray(int size) {
            return new ChartModel[size];
        }
    };

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(value);
    }
}

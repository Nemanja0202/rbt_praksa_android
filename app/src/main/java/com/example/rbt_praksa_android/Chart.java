package com.example.rbt_praksa_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;

import com.example.rbt_praksa_android.model.Temperature;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Chart extends View {

    Paint line = new Paint();
    Paint text = new Paint();
    private static List<Temperature> listParams;
    private float minTemp, maxTemp;

    public Chart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float min = listParams.get(0).getTemperature();
        float max = listParams.get(0).getTemperature();

        int y = canvas.getHeight();
        int x = canvas.getWidth();
        line.setColor(Color.BLACK);
        line.setStrokeWidth(5);

        canvas.drawLine(x*0.1f, y*0.1f, x*0.1f, y*0.9f, line);
        canvas.drawLine(x*0.1f, y*0.9f, x*0.9f, y*0.9f, line);

        line.setStrokeWidth(2);
        canvas.drawLine(x*0.1f, y*0.7f, x*0.9f, y*0.7f, line);
        canvas.drawLine(x*0.1f, y*0.5f, x*0.9f, y*0.5f, line);
        canvas.drawLine(x*0.1f, y*0.3f, x*0.9f, y*0.3f, line);
        canvas.drawLine(x*0.3f, y*0.1f, x*0.3f, y*0.9f, line);
        canvas.drawLine(x*0.5f, y*0.1f, x*0.5f, y*0.9f, line);
        canvas.drawLine(x*0.7f, y*0.1f, x*0.7f, y*0.9f, line);

        for (int i=0; i<listParams.size(); i++) {
            if (listParams.get(i).getTemperature() > max) {
                max = listParams.get(i).getTemperature();
            }

            if (listParams.get(i).getTemperature() < min) {
                min = listParams.get(i).getTemperature();
            }
        }

        text.setColor(Color.BLACK);
        text.setTextSize(20);

        DecimalFormat numberFormat = new DecimalFormat("#.00");
        max = max*1.1f;
        min = min*0.9f;
        max = Float.parseFloat(numberFormat.format(max));
        min = Float.parseFloat(numberFormat.format(min));

        float textCoefY = (max-min)/4;
        float line1Y = Float.parseFloat(numberFormat.format(min+textCoefY));
        float line2Y = Float.parseFloat(numberFormat.format(min+(textCoefY*2)));
        float line3Y = Float.parseFloat(numberFormat.format(max-textCoefY));

        canvas.drawText(Float.toString(min), x*0.01f, y*0.9f, text);
        canvas.drawText(Float.toString(line1Y), x*0.01f, y*0.7f, text);
        canvas.drawText(Float.toString(line2Y), x*0.01f, y*0.5f, text);
        canvas.drawText(Float.toString(line3Y), x*0.01f, y*0.3f, text);
        canvas.drawText(Float.toString(max), x*0.01f, y*0.1f, text);

        long timeCoef = listParams.get(listParams.size()-1).getTimestamp()+listParams.get(0).getTimestamp();

        Date startDate = new Date(listParams.get(0).getTimestamp());
        Date endDate = new Date(listParams.get(listParams.size()-1).getTimestamp());
        Date line1X = new Date(listParams.get(0).getTimestamp()+timeCoef);
        Date line2X = new Date(timeCoef/2);
        Date line3X = new Date(listParams.get(0).getTimestamp()+3*timeCoef);

        //SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");


        canvas.drawText(df.format(startDate), x*0.01f, y*0.99f, text);
        //canvas.drawText(df.format(line1X), x*0.25f, y*0.99f, text);
        canvas.drawText(df.format(line2X), x*0.41f, y*0.99f, text);
        //canvas.drawText(df.format(line3X), x*0.65f, y*0.99f, text);
        canvas.drawText(df.format(endDate), x*0.77f, y*0.99f, text);

        float coefY = y*0.8f/(max-min);
        float coefX = x*0.8f/listParams.size();

        float start = x*0.1f;
        line.setColor(Color.RED);

        for (int i=0; i<listParams.size()-1; i++) {
            canvas.drawLine(start, y*0.1f+((max-listParams.get(i).getTemperature())*coefY), start+coefX, y*0.1f+((max-listParams.get(i+1).getTemperature())*coefY), line);
            start += coefX;
        }

    }

    private void drawChart(Canvas canvas) {
//        paint.setColor(Color.WHITE);
//        canvas.drawRect(100, 100, 200, 200, paint);
    }

    public static void setParameters(List<Temperature> lista) {
        listParams = lista;

    }
}

package com.example.rbt_praksa_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Chart extends View {

    Paint line = new Paint();
    Paint text = new Paint();
    private static List<Pair<Long, Float>> listParams;
    private float minTemp, maxTemp;

    public Chart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        long minX = listParams.get(0).first;
        long maxX = listParams.get(0).first;

        float minY = listParams.get(0).second;
        float maxY = listParams.get(0).second;

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
            if (listParams.get(i).first > maxX) {
                maxX = listParams.get(i).first;
            }

            if (listParams.get(i).first < minX) {
                minX = listParams.get(i).first;
            }

            if (listParams.get(i).second > maxY) {
                maxY = listParams.get(i).second;
            }

            if (listParams.get(i).second < minY) {
                minY = listParams.get(i).second;
            }
        }

        //text.setColor(Color.BLACK);
        //text.setTextSize(20);

        //DecimalFormat numberFormat = new DecimalFormat("#.00");
        minY = minY*0.9f;
        maxY = maxY*1.1f;
//        maxY = Float.parseFloat(numberFormat.format(maxY));
//        minY = Float.parseFloat(numberFormat.format(minY));

//        float textCoefY = (maxY-minY)/4;
//        float line1Y = Float.parseFloat(numberFormat.format(minY+textCoefY));
//        float line2Y = Float.parseFloat(numberFormat.format(minY+(textCoefY*2)));
//        float line3Y = Float.parseFloat(numberFormat.format(maxY-textCoefY));

//        canvas.drawText(Float.toString(minY), x*0.01f, y*0.9f, text);
//        canvas.drawText(Float.toString(line1Y), x*0.01f, y*0.7f, text);
//        canvas.drawText(Float.toString(line2Y), x*0.01f, y*0.5f, text);
//        canvas.drawText(Float.toString(line3Y), x*0.01f, y*0.3f, text);
//        canvas.drawText(Float.toString(maxY), x*0.01f, y*0.1f, text);

        //long timeCoef = listParams.get(listParams.size()-1).second+listParams.get(0).second;




        //canvas.drawText(df.format(startDate), x*0.01f, y*0.99f, text);
        //canvas.drawText(df.format(line1X), x*0.25f, y*0.99f, text);
        //canvas.drawText(df.format(line2X), x*0.41f, y*0.99f, text);
        //canvas.drawText(df.format(line3X), x*0.65f, y*0.99f, text);
        //canvas.drawText(df.format(endDate), x*0.77f, y*0.99f, text);

        float coefX = x*0.8f/listParams.size();
        float coefY = y*0.8f/(maxY-minY);

        float start = x*0.1f;
        line.setColor(Color.RED);

        float test = listParams.get(1).second-listParams.get(0).second;


        for (int i=0; i<listParams.size()-1; i++) {
            canvas.drawLine(x*0.1f+(coefX*(listParams.get(i+1).second-listParams.get(i).second)), y*0.1f+((maxY-listParams.get(i).first)*coefY), x*0.1f+(listParams.get(i+1).first*coefX), y*0.1f+((maxY-listParams.get(i+1).second)*coefY), line);
            //start += coefX;
        }

    }

    public static void setParameters(List<Pair<Long, Float>> lista) {
        listParams = lista;

    }
}

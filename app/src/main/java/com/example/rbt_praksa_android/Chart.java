package com.example.rbt_praksa_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Chart extends View {

    private static Paint line = new Paint();
    private static Paint text = new Paint();
    private Paint graphLines = new Paint();
    private long minX;
    private long maxX;
    private float minY;
    private float maxY;
    private static List<Pair<Long, Float>> listParams;
    private static ArrayList<String> labelX = new ArrayList<>();
    private static ArrayList<String> labelY = new ArrayList<>();


    public Chart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (listParams == null || listParams.size() == 1)
            return;

        minX = listParams.get(0).first;
        maxX = listParams.get(0).first;
        minY = listParams.get(0).second;
        maxY = listParams.get(0).second;

        int y = canvas.getHeight();
        int x = canvas.getWidth();

        graphLines.setColor(Color.BLACK);
        graphLines.setStrokeWidth(5);
        //crtam grafik
        canvas.drawLine(x * 0.1f, y * 0.1f, x * 0.1f, y * 0.9f, graphLines);
        canvas.drawLine(x * 0.1f, y * 0.9f, x * 0.9f, y * 0.9f, graphLines);

        for (int i = 0; i < listParams.size(); i++) {
            if (listParams.get(i).first > maxX)
                maxX = listParams.get(i).first;
            if (listParams.get(i).first < minX)
                minX = listParams.get(i).first;
            if (listParams.get(i).second > maxY)
                maxY = listParams.get(i).second;
            if (listParams.get(i).second < minY)
                minY = listParams.get(i).second;
        }

        minY = minY * 0.9f;
        maxY = maxY * 1.1f;

        text.setTextSize(20);
        graphLines.setStrokeWidth(2);

        if (labelX.size() <= 2) {
            switch (labelX.size()) {
                case 1:
                    canvas.drawText(labelX.get(0), x * 0.08f, y * 0.95f, text);
                    break;
                case 2:
                    canvas.drawText(labelX.get(0), x * 0.08f, y * 0.95f, text);
                    canvas.save();
                    canvas.rotate(-45,x * 0.88f, y * 0.95f);
                    canvas.drawText(labelX.get(labelX.size() - 1), x * 0.88f, y * 0.95f, text);
                    canvas.restore();                    break;
                default:
                    break;
            }
        } else {
            canvas.drawText(labelX.get(0), x * 0.08f, y * 0.95f, text);
            canvas.save();
            canvas.rotate(-45,x * 0.88f, y * 0.95f);
            canvas.drawText(labelX.get(labelX.size() - 1), x * 0.88f, y * 0.95f, text);
            canvas.restore();
            int numLines = labelX.size() - 2;
            float coefLine = x * 0.8f / (numLines + 1);
            float coef = coefLine;
            for (int i = 0; i < numLines; i++) {
                canvas.drawText(labelX.get(i + 1), x * 0.08f + coef, y * 0.95f, text);
                canvas.drawLine(x * 0.1f + coef, y * 0.1f, x * 0.1f + coef, y * 0.91f, graphLines);
                coef += coefLine;
            }
        }

        if (labelY.size() <= 2) {
            switch (labelY.size()) {
                case 1:
                    canvas.drawText(labelY.get(0), x * 0.09f, y * 0.9f, text);
                    break;
                case 2:
                    canvas.drawText(labelY.get(0), x * 0.09f, y * 0.9f, text);
                    canvas.drawText(labelY.get(1), x * 0.09f, y * 0.1f, text);
                    break;
                default:
                    break;
            }
        } else {
            canvas.drawText(labelY.get(0), x * 0.02f, y * 0.9f, text);
            canvas.drawText(labelY.get(labelY.size() - 1), x * 0.02f, y * 0.12f, text);
            int numLines = labelY.size() - 2;
            float coefLine = y * 0.8f / (numLines + 1);
            float coef = coefLine;
            for (int i = 0; i < numLines; i++) {
                canvas.drawText(labelY.get(i + 1), x * 0.02f, y * 0.9f - coef, text);
                canvas.drawLine(x * 0.09f, y * 0.9f - coef, x * 0.9f, y * 0.9f - coef, graphLines);
                coef += coefLine;
            }
        }
        float coefX = x * 0.8f / (float)(maxX - minX);
        float coefY = y * 0.8f / (maxY - minY);
        float start = x * 0.1f;

        for (int i = 0; i < listParams.size() - 1; i++) {
            float x1 = start + (coefX * (float)(listParams.get(i).first - minX));
            float y1 = start + ((maxY - listParams.get(i).second) * coefY);
            float x2 = start + ((float)(listParams.get(i + 1).first - minX) * coefX);
            float y2 = start + ((maxY - listParams.get(i + 1).second) * coefY);
            canvas.drawLine(x1, y1, x2, y2, line);

            if (i + 2 < listParams.size() - 1) {
                float x3 = start + ((float)(listParams.get(i + 2).first - minX) * coefX);
                if (x2 == x3) {
                    float y3 = start + ((maxY - listParams.get(i + 2).second) * coefY);
                    canvas.drawLine(x2, y2, x3, y3, line);
                }
            }
        }

    }

    public static void setParameters(List<Pair<Long, Float>> lista, ArrayList<String> labelx, ArrayList<String> labely) {
        setParameters(lista, labelx, labely, Color.BLACK, Color.BLACK);
    }

    public static void setParameters(List<Pair<Long, Float>> lista, ArrayList<String> labelx, ArrayList<String> labely, int textColor, int lineColor) {
        listParams = lista;
        Collections.sort(listParams, new Comparator<Pair<Long, Float>>() {
            @Override
            public int compare(Pair<Long, Float> t1, Pair<Long, Float> t2) {
                return (int) compareValues(t1.first, t2.first);
            }

            public long compareValues(long f1, long f2) {
                return f1 - f2;
            }
        });
        text.setColor(textColor);
        line.setColor(lineColor);
        line.setStrokeWidth(3);
        labelY = labely;
        labelX = labelx;

    }
}

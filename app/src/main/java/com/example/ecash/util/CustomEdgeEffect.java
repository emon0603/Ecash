package com.example.ecash.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.EdgeEffect;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomEdgeEffect extends RecyclerView.EdgeEffectFactory {
    private final Context context;

    public CustomEdgeEffect(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    protected EdgeEffect createEdgeEffect(@NonNull RecyclerView view, int direction) {
        return new EdgeEffect(view.getContext()) {
            private final Paint paint = new Paint() {{
                setColor(Color.parseColor("#2AFF4081")); // আপনার পছন্দমতো রঙ দিন
                setStyle(Style.FILL);
            }};


            @Override
            public boolean draw(Canvas canvas) {
                int width = view.getWidth();
                int height = view.getHeight();

                if (direction == DIRECTION_TOP) {
                    // উপর থেকে টানলে Ripple Effect দেখাবে
                    RectF rect = new RectF(0, -120, width, 120);
                    canvas.drawOval(rect, paint);
                } else if (direction == DIRECTION_BOTTOM) {
                    // নিচ থেকে টানলে Ripple Effect দেখাবে
                    RectF rect = new RectF(0, height - 1300, width, 130);
                    canvas.drawOval(rect, paint);
                }


                return super.draw(canvas);
            }
        };
    }
}


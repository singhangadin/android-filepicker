package com.github.angads25.filepicker.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * <p>
 * Created by Angad on 19-05-2017.
 * </p>
 */

public class MaterialCheckbox extends View {
    private int width, height, minDim;
    private Paint paint;
    private RectF bounds;
    private boolean checked;
    private View.OnClickListener onClickListener;
    private OnCheckedChangeListener onCheckedChangeListener;

    public MaterialCheckbox(Context context) {
        super(context);
        initView();
    }

    public MaterialCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MaterialCheckbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        checked = false;
        paint = new Paint();
        paint.setAntiAlias(true);
        bounds = new RectF();

        onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                setChecked(!checked);
                onCheckedChangeListener.onCheckedChanged(MaterialCheckbox.this, isChecked());
            }
        };

        setOnClickListener(onClickListener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawRoundRect(bounds, 3, 4, paint);

        if(checked) {
            canvas.drawColor(Color.parseColor("#FF0000"));
        }
        else {
            canvas.drawColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getMeasuredHeight();
        width = getMeasuredWidth();
        minDim = Math.min(width, height);
        setMeasuredDimension(width, height);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        invalidate();
    }

    public void addOnCheckedChangedListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }
}

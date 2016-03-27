package kienme.movies;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import kienme.movies.R;

/**
 * Created by Ravikiran on 20/2/16.
 *
 * Custom ImageView to handle aspect ratio
 *
 */

public class ScaledImageView extends ImageView{
    public ScaledImageView(Context context) {
        super(context);
    }

    public ScaledImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaledImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight()*getMeasuredWidth()/(int)getResources().getDimension(R.dimen.poster_width));
    }
}

package kienme.movies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ravikiran on 8/2/16.
 *
 * Adapter to handle data for the GridView
 *
 */

public class PosterGridViewAdapter extends ArrayAdapter<PosterGridItem> /* BaseAdapter*/{
    View row;
    private Context context;
    private int layoutResource;
    private ArrayList<PosterGridItem> gridData = new ArrayList<>();
    boolean firstRun = true;

    public PosterGridViewAdapter(Context context, int layoutResource, ArrayList<PosterGridItem> gridData) {
        super(context, layoutResource, gridData);
        this.context = context;
        this.gridData = gridData;
        this.layoutResource = layoutResource;
    }

    public void setGridData(ArrayList<PosterGridItem> gridData) {
        this.gridData = gridData;
        notifyDataSetChanged();
        Log.d("DEBUG", "setGridData");
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        row = convertView;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResource, parent, false);
        }

        final PosterGridItem item = gridData.get(position);
        Log.d("DEBUG1", "Position: "+position);

        ScaledImageView imageView = (ScaledImageView)(row.findViewById(R.id.imageView));
        Picasso.with(context).load(item.getImage()).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.d("DEBUGP", "Success: "+position);
                if(firstRun) {
                    notifyDataSetChanged();
                    MainActivity.setProgressBarVisibility(View.GONE);
                    firstRun = false;
                }
            }

            @Override
            public void onError() {
                Log.d("DEBUGP", "Error: "+position);
            }
        });

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startDetailsActivity = new Intent(context, DetailsActivity.class);
                startDetailsActivity.putExtra("name", item.getName());
                startDetailsActivity.putExtra("image", item.getImage());
                startDetailsActivity.putExtra("release", item.getRelease());
                startDetailsActivity.putExtra("rating", item.getRating());
                startDetailsActivity.putExtra("overview", item.getOverview());
                startDetailsActivity.putExtra("headerImage", item.getHeaderImage());
                context.startActivity(startDetailsActivity);
            }
        });

        return row;
    }

}

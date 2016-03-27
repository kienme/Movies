package kienme.movies;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    String name, image, release, rating, overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getMovieData();
        setMovieData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Favourite - Coming soon!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getMovieData() {
        Bundle data = getIntent().getExtras();
        name = data.getString("name");
        image = data.getString("image");
        release = data.getString("release");
        rating = data.getString("rating");
        overview = data.getString("overview");
    }

    private void setMovieData() {
        final CollapsingToolbarLayout layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        ImageView imageView = (ImageView) findViewById(R.id.details_image);
        TextView releaseView = (TextView) findViewById(R.id.details_release);
        TextView ratingView = (TextView) findViewById(R.id.details_rating);
        TextView overviewView = (TextView) findViewById(R.id.details_overview);

        layout.setTitle(name);
        Picasso.with(this).load(image).into(imageView);
        releaseView.setText("Release date: " + release);
        ratingView.setText("Rating: " + rating + "/10");
        overviewView.setText(overview);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailsActivity.this, layout.getTitle(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

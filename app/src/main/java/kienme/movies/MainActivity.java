package kienme.movies;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String LINK = "https://api.themoviedb.org/3/discover/movie?";
    String SORT = "sort_by=popularity.desc";
    String KEY;
    ArrayList<PosterGridItem> gridData;
    PosterGridViewAdapter posterGridViewAdapter;
    static ProgressBar progressBar;
    GridView gridView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KEY = "&api_key="+getResources().getString(R.string.api_key);

        context = this;
        gridData = new ArrayList<>();
        gridView = (GridView) findViewById(R.id.gridView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        new FetchData().execute();

        posterGridViewAdapter = new PosterGridViewAdapter(this, R.layout.grid_item, gridData);
        posterGridViewAdapter.setGridData(gridData);
        gridView.setAdapter(posterGridViewAdapter);
    }

    public class FetchData extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            Boolean success = false;
            StringBuilder data = new StringBuilder("");
            try {
                Log.d("DEBUG", "inside try");
                URL url = new URL(LINK+SORT+KEY);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = bufferedReader.readLine()) != null) {
                    data.append(line);
                    Log.d("DEBUG", line);
                }

                if(inputStream != null) {
                    inputStream.close();
                    parseResult(data.toString());
                    Log.d("DEBUG", "input stream not null");
                    success = true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(success) {
                posterGridViewAdapter = new PosterGridViewAdapter(context, R.layout.grid_item, gridData);
                posterGridViewAdapter.setGridData(gridData);
                gridView.setAdapter(posterGridViewAdapter);
                Log.d("DEBUG", "post execute success");
            }
            else {
                Toast.makeText(context, "Failed to load images", Toast.LENGTH_LONG).show();
                Log.d("DEBUG", "post execute fail");
            }
        }
    }

    void parseResult(String data) {
        String imageBase = "http://image.tmdb.org/t/p/w185";
        try {
            JSONObject response = new JSONObject(data);
            JSONArray results = response.getJSONArray("results");

            for(int i = 0; i<results.length(); ++i) {
                JSONObject object= results.getJSONObject(i);
                String imagePath = object.get("poster_path").toString();
                PosterGridItem item = new PosterGridItem();

                item.setImage(imageBase+imagePath+"&api_key="+KEY);
                item.setName(object.get("title").toString());
                item.setRelease(object.get("release_date").toString());
                item.setRating(object.get("vote_average").toString());
                item.setOverview(object.get("overview").toString());
                Log.d("DEBUG", "Image path:" + imageBase+imagePath+"&api_key="+KEY);
                gridData.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static void setProgressBarVisibility(int v) {
        progressBar.setVisibility(v);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //menu.setGroupCheckable(R.id.menu_group, true, true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.rating:
                item.setChecked(true);
                SORT = "sort_by=vote_average.desc";
                gridData.clear();
                new FetchData().execute();
                return false;

            case R.id.popularity:
                item.setChecked(true);
                SORT = "sort_by=popularity.desc";
                gridData.clear();
                new FetchData().execute();
                return true;

            case R.id.favourites:
                item.setChecked(true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
package kienme.movies;

/**
 * Created by Ravikiran on 8/2/16.
 *
 * Element of the GridView. Here it is a single image.
 *
 */

public class PosterGridItem {
    private String name, image, release, rating, overview;

    public PosterGridItem() {
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getRelease() {
        return release;
    }

    public String getRating() {
        return rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}

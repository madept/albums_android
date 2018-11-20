package albums.krish.com.albums;

import android.app.Activity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.TextView;

import albums.krish.com.albums.viewmodels.AlbumsViewModel;

/**
 *  Acivity class , as MVVM patter is used, there is very less code in activity class
 */
public class Albums extends Activity {

    TextView title;
    RecyclerView albums;
    AlbumsViewModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_albums);
        title = (TextView)findViewById(R.id.title);
        albums = ( RecyclerView) findViewById(R.id.albums);

         model = new AlbumsViewModel(this,albums);
         model.loadAlbums();

    }




}

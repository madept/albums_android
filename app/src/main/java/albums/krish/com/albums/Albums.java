package albums.krish.com.albums;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.TextView;

import java.util.List;

import albums.krish.com.albums.adp.AlbumsAdapter;
import albums.krish.com.albums.models.Album;
import albums.krish.com.albums.viewmodels.AlbumsViewModel;

/**
 *  Acivity class , as MVVM pattern is used, ViewModel is being used and it observes of chnages to update UI
 */
public class Albums extends AppCompatActivity {

    RecyclerView albumsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_albums);
        albumsView = ( RecyclerView) findViewById(R.id.albums);
        AlbumsViewModel model = ViewModelProviders.of(Albums.this).get(AlbumsViewModel.class);
        model.setContext(Albums.this);
        model.getAlbums().observe(Albums.this, albums -> {
           updatealbumList(albums);
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // outState.putParcelable("model", Parcels.wrap(model));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        AlbumsViewModel model = ViewModelProviders.of(Albums.this).get(AlbumsViewModel.class);
        updatealbumList(model.getAlbums().getValue());
    }

    private void updatealbumList(List<Album> albums){

        AlbumsAdapter adp = new AlbumsAdapter(Albums.this, albums);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Albums.this);
        albumsView.setLayoutManager(layoutManager);
        albumsView.setAdapter(adp);
    }


}

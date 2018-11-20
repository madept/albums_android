package albums.krish.com.albums.viewmodels;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import albums.krish.com.albums.Util.Constants;
import albums.krish.com.albums.Util.JSONParser;
import albums.krish.com.albums.Util.Util;
import albums.krish.com.albums.adp.AlbumsAdapter;
import albums.krish.com.albums.models.Album;

/**
 *  ViewModel, to implement MVVM so that activity load can we reduced ( no hard wired coupling which makes testing easy)
 */

public class AlbumsViewModel  {

     ArrayList<Album> albums;
     RecyclerView albumsView;
     Activity context;
     public AlbumsViewModel(Activity context,RecyclerView albumsView){
         this.context = context ;
         this.albumsView = albumsView ;
     }

    /**
     *  Loads albums from network, if no netword try to load from load
     */
    public  void loadAlbums(){

        if(!Util.isNetworkAvailable(context)){
            loadLocalData();
        }else{
            sendAlbumsRequest();
        }


    }

    private void updateAlbumList(){

        AlbumsAdapter adp = new AlbumsAdapter(context,albums);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        albumsView.setLayoutManager(layoutManager);
        albumsView.setAdapter(adp);
    }

    private  void loadLocalData(){
        String response = Util.getPersistanceData(context, Constants.ALBUMS_PERSIST_FILE_NAME);
        if( !"".equals(response)){
            albums = JSONParser.parseAlbums(response);
            sortAlbums();
            updateAlbumList();
        }else{
            Toast.makeText(context,"No local data avaialble",Toast.LENGTH_SHORT).show();
        }
    }
    private void sortAlbums(){
        Collections.sort(albums, new Comparator<Album>() {
            @Override
            public int compare(Album album, Album t1) {
                return album.title.compareTo(t1.title);
            }
        });
    }

    private  void sendAlbumsRequest(){
         Util.showProgressDialog(context);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //String Request initialized
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.ALBUMS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Util.dismissProgressDialog();
                albums = JSONParser.parseAlbums(response);
                Util.savePersistanceData(context, Constants.ALBUMS_PERSIST_FILE_NAME,response);
                sortAlbums();
                updateAlbumList();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.dismissProgressDialog();
            }
        });
        requestQueue.add(stringRequest);
    }

}

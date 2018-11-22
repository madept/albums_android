package albums.krish.com.albums.viewmodels;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
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
import java.util.List;

import albums.krish.com.albums.Util.Constants;
import albums.krish.com.albums.Util.JSONParser;
import albums.krish.com.albums.Util.Util;
import albums.krish.com.albums.adp.AlbumsAdapter;
import albums.krish.com.albums.models.Album;

/**
 *  ViewModel, to implement MVVM . ViewModel notify its owner when it has updated data
 */

public class AlbumsViewModel extends ViewModel {


     Activity context;
     MutableLiveData<List<Album>> albums;
     public AlbumsViewModel(){
     }

     public void setContext(Activity context){
         this.context = context ;
     }

    /**
     *  Loads albums from network, if no netword try to load from local data
     */
    public LiveData<List<Album>> getAlbums(){
        if( albums == null)
            albums = new MutableLiveData<List<Album>>();
        if(!Util.isNetworkAvailable(context)){
            List<Album> albms = loadLocalData();
            sortAlbums(albms);
            albums.setValue(albms);

        }else{
            sendAlbumsRequest();
        }

        return albums;
    }

    /**
     * Loads local data, if no data then it returns empty list
     * @return Album list loaded from local data
     */
    private  List<Album> loadLocalData(){
        String response = Util.getPersistanceData(context, Constants.ALBUMS_PERSIST_FILE_NAME);
        if( !"".equals(response)){
            return JSONParser.parseAlbums(response);
        }else{
            Toast.makeText(context,"No local data avaialble",Toast.LENGTH_SHORT).show();
        }
        return new ArrayList<Album>();
    }

    /**
     * Sorts loaded albums by title
     * @param albms
     */
    private void sortAlbums(List<Album> albms){
        Collections.sort(albms, new Comparator<Album>() {
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
                Util.savePersistanceData(context, Constants.ALBUMS_PERSIST_FILE_NAME,response);
                List<Album> albms = JSONParser.parseAlbums(response);
                albums.setValue(albms);

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

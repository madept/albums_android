package albums.krish.com.albums.Util;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import albums.krish.com.albums.models.Album;

/**
 *  Class responsible for parsing album json
 */
public class JSONParser {

    /**
     * Parse and return list of albums
     * @param data json String data
     * @return List of Albums
     */
    public static List<Album> parseAlbums(String data){
        List<Album> albumsList = new ArrayList<Album>();
        try {
            JSONArray albumsArray = new JSONArray(data);
            int len = albumsArray.length();
            for(int i =0 ; i<len;i++){
                Album album = parseAlbum(albumsArray.getJSONObject(i));
                if( album.title != null)
                        albumsList.add(album);
            }
        }catch(JSONException ex){

        }
        return albumsList;

    }

    private static Album parseAlbum(JSONObject obj){
        Album album = new Album();
        try {

            album.id = obj.getInt("id");
            album.userID = obj.getInt("userId");
            album.title = obj.getString("title");

        }catch(JSONException ex){

        }

        return album;
    }

}

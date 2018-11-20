package albums.krish.com.albums.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import albums.krish.com.albums.models.Album;

/**
 *  Class responsible for parsing album json
 */
public class JSONParser {

    public static ArrayList<Album> parseAlbums(String data){
        ArrayList<Album> albums = new ArrayList<Album>();
        try {
            JSONArray albumsArray = new JSONArray(data);
            int len = albumsArray.length();
            for(int i =0 ; i<len;i++){
                Album album = parseAlbum(albumsArray.getJSONObject(i));
                if( album.title != null)
                        albums.add(album);
            }
        }catch(JSONException ex){

        }
    return albums;
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

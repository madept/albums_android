package albums.krish.com.albums.adp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


import albums.krish.com.albums.R;
import albums.krish.com.albums.models.Album;

/**
 *  Adapter for Album list Along with holder
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>  {

    List<Album> albumList;
    Context ctx;
    public AlbumsAdapter(Context ctx, List<Album> albumlist) {
        this.albumList = albumlist;
        //  this.sorting = sorting;
        this.ctx = ctx;
    }

    /**
     * Called when ViewHolder is created
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public AlbumsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_list_row, parent, false);

        return new AlbumsViewHolder(itemView);
    }

    /**
     *  binding view holder with data
     * @param holder view holder to bind with data
     * @param position row position
     */

    @Override
    public void onBindViewHolder(AlbumsViewHolder holder, final int position) {
        final  Album album = albumList.get(position);
        holder.title.setText(album.title);
        holder.row.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(AlbumsAdapter.this.ctx,"You clicked on album with ID " + album.id ,Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     *  Tells total number of rows
     * @return  tolal number of rows
     */
    @Override
    public int getItemCount() {
        return albumList.size();
    }

    /**
     *  Albums View holder so that views can be reused and only data binding changes when user scroll in the list
     */
    public class AlbumsViewHolder extends RecyclerView.ViewHolder {


        public TextView title;
        public LinearLayout row;

        public AlbumsViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            row = (LinearLayout) view.findViewById(R.id.album_row);

        }
    }
}

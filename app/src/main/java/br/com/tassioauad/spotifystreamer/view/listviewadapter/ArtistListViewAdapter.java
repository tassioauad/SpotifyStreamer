package br.com.tassioauad.spotifystreamer.view.listviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.tassioauad.spotifystreamer.R;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;

public class ArtistListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Artist> artistList;

    public ArtistListViewAdapter(Context context, List<Artist> artistList) {
        this.context = context;
        this.artistList = artistList;
    }

    @Override
    public int getCount() {
        return artistList.size();
    }

    @Override
    public Object getItem(int position) {
        return artistList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listviewitem_artist, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageViewPhoto = (ImageView) convertView.findViewById(R.id.imageview_artist_photo);
            viewHolder.textViewName = (TextView) convertView.findViewById(R.id.textview_artist_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Artist artist = (Artist) getItem(position);
        viewHolder.clear();

        viewHolder.textViewName.setText(artist.getName());
        String imageUrl = artist.getSmallImageUrl() == null ? artist.getImageUrl() : artist.getSmallImageUrl();
        Glide.with(context)
                .load(artist.getSmallImageUrl())
                .centerCrop()
                .placeholder(R.drawable.icon_oldmic)
                .crossFade()
                .into(viewHolder.imageViewPhoto);

        return convertView;
    }

    private class ViewHolder {
        TextView textViewName;
        ImageView imageViewPhoto;

        public void clear() {
            textViewName.setText("");
            imageViewPhoto.setImageBitmap(null);
        }
    }
}

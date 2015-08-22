package br.com.tassioauad.spotifystreamer.view.listviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.tassioauad.spotifystreamer.R;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.model.entity.Track;

public class TrackListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Track> trackList;

    public TrackListViewAdapter(Context context, List<Track> trackList) {
        this.context = context;
        this.trackList = trackList;
    }

    @Override
    public int getCount() {
        return trackList.size();
    }

    @Override
    public Object getItem(int position) {
        return trackList.get(position);
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
            convertView = layoutInflater.inflate(R.layout.listviewitem_track, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageViewAlbumPhoto = (ImageView) convertView.findViewById(R.id.imageview_album_photo);
            viewHolder.textViewTrackName = (TextView) convertView.findViewById(R.id.textview_track_name);
            viewHolder.textViewAlbumName = (TextView) convertView.findViewById(R.id.textview_album_name);
            viewHolder.textViewArtistsName = (TextView) convertView.findViewById(R.id.textview_artists_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Track track = (Track) getItem(position);
        viewHolder.clear();

        viewHolder.textViewTrackName.setText(track.getName());
        viewHolder.textViewAlbumName.setText(track.getAlbum().getName());
        String imageUrl = track.getAlbum().getSmallImageUrl() == null ? track.getAlbum().getImageUrl() : track.getAlbum().getSmallImageUrl();
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.icon_oldmic)
                .into(viewHolder.imageViewAlbumPhoto);
        StringBuilder stringBuilder = new StringBuilder();
        for (Artist artist : track.getArtistList()) {
            stringBuilder.append(artist.getName());
            stringBuilder.append(" - ");
        }
        viewHolder.textViewArtistsName.setText(stringBuilder.toString().substring(0, stringBuilder.toString().length() - 3));

        return convertView;
    }

    private class ViewHolder {
        TextView textViewTrackName;
        TextView textViewAlbumName;
        TextView textViewArtistsName;
        ImageView imageViewAlbumPhoto;

        public void clear() {
            textViewTrackName.setText("");
            textViewAlbumName.setText("");
            textViewArtistsName.setText("");
            imageViewAlbumPhoto.setImageBitmap(null);
        }
    }
}

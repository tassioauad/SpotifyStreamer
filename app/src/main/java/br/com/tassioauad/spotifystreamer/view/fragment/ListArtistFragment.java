package br.com.tassioauad.spotifystreamer.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.tassioauad.spotifystreamer.R;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.view.activity.SearchArtistActivity;
import br.com.tassioauad.spotifystreamer.view.activity.SearchTopTrackActivity;
import br.com.tassioauad.spotifystreamer.view.listviewadapter.ArtistListViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ListArtistFragment extends Fragment {

    private static final String ARTIST_LIST_BUNDLE_KEY = "artistlistbundlekey";

    private List<Artist> artistList = new ArrayList<>();
    private ListArtistListener listArtistListener;
    @Bind(R.id.linearlayout_notfound) LinearLayout linearLayoutNotFound;
    @Bind(R.id.listview_artist) ListView listViewArtist;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ListArtistListener) {
            listArtistListener = (ListArtistListener) activity;
        } else {
            throw new RuntimeException("Activity must implement ListArtistFragment");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listartistfragment, container, false);
        ButterKnife.bind(this, view);

        Artist[] artistArray = null;
        if (savedInstanceState != null) {
            artistArray = (Artist[]) savedInstanceState.getParcelableArray(ARTIST_LIST_BUNDLE_KEY);
        } else if(getArguments() != null) {
            artistArray = (Artist[]) getArguments().getParcelableArray(ARTIST_LIST_BUNDLE_KEY);
        }

        if (artistArray == null || artistArray.length == 0) {
            linearLayoutNotFound.setVisibility(View.VISIBLE);
            listViewArtist.setVisibility(View.GONE);
        } else {
            artistList = Arrays.asList(artistArray);
            linearLayoutNotFound.setVisibility(View.GONE);
            listViewArtist.setVisibility(View.VISIBLE);
            listViewArtist.setAdapter(new ArtistListViewAdapter(getActivity(), artistList));
            listViewArtist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listArtistListener.onArtistSelected((Artist) parent.getAdapter().getItem(position));
                }
            });
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (artistList != null && artistList.size() > 0) {
            outState.putParcelableArray(ARTIST_LIST_BUNDLE_KEY,
                    artistList.toArray(new Artist[artistList.size()]));
        }
        super.onSaveInstanceState(outState);
    }

    public static ListArtistFragment newInstance(Context context, List<Artist> artistList) {
        ListArtistFragment listArtistFragment = new ListArtistFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(ARTIST_LIST_BUNDLE_KEY, artistList.toArray(new Artist[artistList.size()]));
        listArtistFragment.setArguments(bundle);
        return listArtistFragment;
    }

    public static ListArtistFragment newInstance(Context context) {
        ListArtistFragment listArtistFragment = new ListArtistFragment();
        return listArtistFragment;
    }

    public interface ListArtistListener {
        public void onArtistSelected(Artist artist);
    }
}

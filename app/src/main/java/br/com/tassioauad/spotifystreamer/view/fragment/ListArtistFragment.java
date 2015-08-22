package br.com.tassioauad.spotifystreamer.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import br.com.tassioauad.spotifystreamer.R;
import br.com.tassioauad.spotifystreamer.SpotifyStreamerApplication;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.presenter.ListArtistPresenter;
import br.com.tassioauad.spotifystreamer.utils.dagger.ListArtistModule;
import br.com.tassioauad.spotifystreamer.view.ListArtistView;
import br.com.tassioauad.spotifystreamer.view.listviewadapter.ArtistListViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ListArtistFragment extends Fragment implements ListArtistView {

    private static final String ARTIST_LIST_BUNDLE_KEY = "artistlistbundlekey";
    private static final String ARTIST_NAME_BUNDLE_KEY = "artistnamebundlekey";
    @Inject
    ListArtistPresenter presenter;
    private List<Artist> artistList = new ArrayList<>();
    private ListArtistListener listArtistListener;
    @Bind(R.id.linearlayout_notfound) LinearLayout linearLayoutNotFound;
    @Bind(R.id.listview_artist) ListView listViewArtist;
    @Bind(R.id.linearlayout_lostconnection) LinearLayout linearLayoutLostConnection;
    @Bind(R.id.progressbar) ProgressBar progressBar;

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
        ((SpotifyStreamerApplication) getActivity().getApplication()).getObjectGraph().plus(new ListArtistModule(this)).inject(this);

        if (savedInstanceState != null) {
            Artist[] artistArray = (Artist[]) savedInstanceState.getParcelableArray(ARTIST_LIST_BUNDLE_KEY);
            if (artistArray == null || artistArray.length == 0) {
                anyArtistFounded();
            } else {
                artistList = Arrays.asList(artistArray);
                showArtists(artistList);
            }
        } else if(getArguments() != null) {
            String artistName =  getArguments().getString(ARTIST_NAME_BUNDLE_KEY, null);
            presenter.searchByName(artistName);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (artistList != null && artistList.size() > 0) {
            outState.putParcelableArray(ARTIST_LIST_BUNDLE_KEY, artistList.toArray(new Artist[artistList.size()]));
        }
        super.onSaveInstanceState(outState);
    }

    public static ListArtistFragment newInstance(Context context, String artistName) {
        ListArtistFragment listArtistFragment = new ListArtistFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARTIST_NAME_BUNDLE_KEY, artistName);
        listArtistFragment.setArguments(bundle);
        return listArtistFragment;
    }

    @Override
    public void anyArtistFounded() {
        linearLayoutLostConnection.setVisibility(View.GONE);
        listViewArtist.setVisibility(View.GONE);
        linearLayoutNotFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void showArtists(List<Artist> artistList) {
        this.artistList = artistList;
        linearLayoutLostConnection.setVisibility(View.GONE);
        listViewArtist.setVisibility(View.VISIBLE);
        linearLayoutNotFound.setVisibility(View.GONE);
        listViewArtist.setAdapter(new ArtistListViewAdapter(getActivity(), artistList));
        listViewArtist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listArtistListener.onArtistSelected((Artist) parent.getAdapter().getItem(position));
            }
        });
    }

    @Override
    public void showLoadingWarn() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingWarn() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void lostConnection() {
        linearLayoutLostConnection.setVisibility(View.VISIBLE);
        listViewArtist.setVisibility(View.GONE);
        linearLayoutNotFound.setVisibility(View.GONE);
    }

    public interface ListArtistListener {
        public void onArtistSelected(Artist artist);
    }
}

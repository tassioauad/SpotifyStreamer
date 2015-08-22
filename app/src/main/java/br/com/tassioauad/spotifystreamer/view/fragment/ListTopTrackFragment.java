package br.com.tassioauad.spotifystreamer.view.fragment;

import android.app.Activity;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import br.com.tassioauad.spotifystreamer.R;
import br.com.tassioauad.spotifystreamer.SpotifyStreamerApplication;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.model.entity.Track;
import br.com.tassioauad.spotifystreamer.presenter.ListTopTrackPresenter;
import br.com.tassioauad.spotifystreamer.utils.dagger.ListTopTrackModule;
import br.com.tassioauad.spotifystreamer.view.ListTopTrackView;
import br.com.tassioauad.spotifystreamer.view.listviewadapter.TrackListViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ListTopTrackFragment extends Fragment implements ListTopTrackView {

    private final String TRACK_LIST_BUNDLE_KEY = "tracklistbundlekey";
    private static final String ARTIST_BUNDLE_KEY = "trackbundlekey";

    @Inject
    ListTopTrackPresenter presenter;
    private List<Track> trackList = new ArrayList<>();

    @Bind(R.id.linearlayout_lostconnection) LinearLayout linearLayoutLostConnection;
    @Bind(R.id.linearlayout_notfound) LinearLayout linearLayoutNotFound;
    @Bind(R.id.listview_track) ListView listViewTrack;
    @Bind(R.id.progressbar) ProgressBar progressBar;
    private ListTopTrackListener listTopTrackListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ListTopTrackListener) {
            listTopTrackListener = (ListTopTrackListener) activity;
        } else {
            throw new RuntimeException("Activity must implement ListTopTrackListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SpotifyStreamerApplication) getActivity().getApplication())
                .getObjectGraph().plus(new ListTopTrackModule(this)).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listtoptrack, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            Track[] trackArray = (Track[]) savedInstanceState.getParcelableArray(TRACK_LIST_BUNDLE_KEY);
            if (trackArray != null) {
                showTracks(Arrays.asList(trackArray));
                trackList = Arrays.asList(trackArray);
            }
        } else {
            Artist artist = getArguments().getParcelable(ARTIST_BUNDLE_KEY);
            if (artist == null) {
                anyTrackFounded();
            } else {
                presenter.searchByArtist(artist);
            }
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (trackList != null && trackList.size() > 0) {
            outState.putParcelableArray(TRACK_LIST_BUNDLE_KEY,
                    trackList.toArray(new Track[trackList.size()]));
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showTracks(final List<Track> trackList) {
        this.trackList = trackList;
        linearLayoutNotFound.setVisibility(View.GONE);
        linearLayoutLostConnection.setVisibility(View.GONE);
        listViewTrack.setVisibility(View.VISIBLE);
        listViewTrack.setAdapter(new TrackListViewAdapter(getActivity(), trackList));
        listViewTrack.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listTopTrackListener.onTrackSelected(position, trackList);
            }
        });
    }

    @Override
    public void anyTrackFounded() {
        linearLayoutNotFound.setVisibility(View.VISIBLE);
        linearLayoutLostConnection.setVisibility(View.GONE);
        listViewTrack.setVisibility(View.GONE);
        Toast toast = Toast.makeText(getActivity(), getString(R.string.toptrack_toast_anytrackwasfound), Toast.LENGTH_SHORT);
        toast.getView().setBackgroundColor(getResources().getColor(R.color.green));
        toast.show();
    }

    @Override
    public void lostConnection() {
        linearLayoutNotFound.setVisibility(View.GONE);
        linearLayoutLostConnection.setVisibility(View.VISIBLE);
        listViewTrack.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingWarn() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingWarn() {
        progressBar.setVisibility(View.GONE);
    }

    public static ListTopTrackFragment newInstance(Artist artist) {
        ListTopTrackFragment listTopTrackFragment = new ListTopTrackFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARTIST_BUNDLE_KEY, artist);
        listTopTrackFragment.setArguments(bundle);

        return listTopTrackFragment;
    }

    public interface ListTopTrackListener {
        void onTrackSelected(int position, List<Track> trackList);
    }

}

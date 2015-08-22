package br.com.tassioauad.spotifystreamer.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.ShareActionProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import br.com.tassioauad.spotifystreamer.R;
import br.com.tassioauad.spotifystreamer.SpotifyStreamerApplication;
import br.com.tassioauad.spotifystreamer.model.entity.Artist;
import br.com.tassioauad.spotifystreamer.model.entity.Track;
import br.com.tassioauad.spotifystreamer.presenter.PlayerPresenter;
import br.com.tassioauad.spotifystreamer.utils.dagger.PlayerModule;
import br.com.tassioauad.spotifystreamer.view.PlayerView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayerFragment extends DialogFragment implements MediaPlayer.OnPreparedListener, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener, PlayerView {

    @Inject
    PlayerPresenter playerPresenter;
    public static final String EXTRA_TRACKS = "extra_tracks";
    public static final String EXTRA_SELECTED_POSITION = "actual_position";
    private static final int TRACK_NOTIFICATION_ID = 501;
    private static final String ACTION_PREVIOUS = "br.com.tassioauad.spotifystreamer.ACTION_PREVIOUS";
    private static final String ACTION_PLAY = "br.com.tassioauad.spotifystreamer.ACTION_PLAY";
    private static final String ACTION_PAUSE = "br.com.tassioauad.spotifystreamer.ACTION_STOP";
    private static final String ACTION_NEXT = "br.com.tassioauad.spotifystreamer.ACTION_NEXT";
    private ShareActionProvider shareActionProvider;
    private double timeElapsed = 0;
    private double finalTime = 0;
    private MediaPlayer mediaPlayer;
    private Track track;
    private PlayerListener playerListener;
    private Handler durationHandler = new Handler();
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_PREVIOUS:
                    playerPresenter.loadPrevious();
                    break;
                case ACTION_PLAY:
                    playAndPause();
                    break;
                case ACTION_PAUSE:
                    playAndPause();
                    break;
                case ACTION_NEXT:
                    playerPresenter.loadNext();
                    break;
            }
        }
    };

    //UI Elements
    @Bind(R.id.textview_artistname)
    TextView textViewArtistName;
    @Bind(R.id.textview_albumname)
    TextView textViewAlbumName;
    @Bind(R.id.imageview_albumimage)
    ImageView imageViewAlbumImage;
    @Bind(R.id.textview_trackname)
    TextView textViewTrackName;
    @Bind(R.id.seekbar_trackprogress)
    SeekBar seekBar;
    @Bind(R.id.textview_trackactualtime)
    TextView textViewActualTime;
    @Bind(R.id.textview_tracktime)
    TextView textViewTime;
    @Bind(R.id.imagebutton_trackprevious)
    ImageButton imageButtonPreviousTrack;
    @Bind(R.id.imagebutton_trackplay)
    ImageButton imageButtonPlayTrack;
    @Bind(R.id.imagebutton_tracknext)
    ImageButton imageButtonNextTrack;
    @Bind(R.id.progressbar)
    ProgressBar progressBar;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof PlayerListener) {
            playerListener = (PlayerListener) activity;
        } else {
            throw new RuntimeException("The activity must implement PlayerListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SpotifyStreamerApplication) getActivity().getApplication()).getObjectGraph().plus(new PlayerModule(this)).inject(this);
        setHasOptionsMenu(true);
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(ACTION_PREVIOUS));
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(ACTION_PLAY));
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(ACTION_PAUSE));
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(ACTION_NEXT));
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(PlayerFragment.this);
            mediaPlayer.setOnCompletionListener(PlayerFragment.this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        ButterKnife.bind(this, view);

        imageButtonPreviousTrack.setEnabled(false);
        imageButtonPreviousTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerPresenter.loadPrevious();
            }
        });
        imageButtonPlayTrack.setEnabled(false);
        imageButtonPlayTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAndPause();
            }
        });
        imageButtonNextTrack.setEnabled(false);
        imageButtonNextTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerPresenter.loadNext();
            }
        });
        seekBar.setOnSeekBarChangeListener(this);

        Track[] trackArray = (Track[]) getArguments().getParcelableArray(EXTRA_TRACKS);
        int actualPosition = getArguments().getInt(EXTRA_SELECTED_POSITION, 0);

        if (trackArray != null) {
            List<Track> trackList = Arrays.asList(trackArray);
            playerPresenter.init(trackList, actualPosition);
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_track, menu);
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        if (track != null) {
            setShareIntent(track);
        }
    }

    public static PlayerFragment newInstance(int position, List<Track> trackList) {
        PlayerFragment playerFragment = new PlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_SELECTED_POSITION, position);
        bundle.putParcelableArray(EXTRA_TRACKS, trackList.toArray(new Track[trackList.size()]));
        playerFragment.setArguments(bundle);

        return playerFragment;
    }

    @Override
    public void play(Track track, Boolean hasNext, Boolean hasPrevious) {
        this.track = track;
        playerListener.onPlay(track);
        imageButtonPreviousTrack.setEnabled(hasPrevious);
        imageButtonPlayTrack.setEnabled(false);
        imageButtonNextTrack.setEnabled(hasNext);
        if(hasNext) {
            imageButtonNextTrack.setBackgroundColor(getResources().getColor(R.color.purple800));
        } else {
            imageButtonNextTrack.setBackgroundColor(getResources().getColor(R.color.purple800_alfa));
        }
        if(hasPrevious) {
            imageButtonPreviousTrack.setBackgroundColor(getResources().getColor(R.color.purple800));
        } else {
            imageButtonPreviousTrack.setBackgroundColor(getResources().getColor(R.color.purple800_alfa));
        }
        seekBar.setEnabled(false);
        seekBar.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        textViewActualTime.setText("");
        textViewTime.setText("");

        StringBuilder stringBuilder = new StringBuilder();
        for (Artist artist : track.getArtistList()) {
            stringBuilder.append(artist.getName());
            stringBuilder.append(" - ");
        }
        textViewArtistName.setText(stringBuilder.toString().substring(0, stringBuilder.toString().length() - 3));
        textViewAlbumName.setText(track.getAlbum().getName());
        if (!TextUtils.isEmpty(track.getAlbum().getImageUrl())) {
            Picasso.with(getActivity()).load(track.getAlbum().getImageUrl()).placeholder(R.drawable.icon_oldmic).into(imageViewAlbumImage);
        }
        textViewTrackName.setText(track.getName());

        prepareMusic(track.getPreviewUrl());

        if (shareActionProvider != null) {
            setShareIntent(track);
        }
    }

    private void prepareMusic(String previewUrl) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

        mediaPlayer.reset();

        try {
            mediaPlayer.setDataSource(previewUrl);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(broadcastReceiver);
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        NotificationManager mNotificationManager =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(TRACK_NOTIFICATION_ID);
        super.onDestroy();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        finalTime = mediaPlayer.getDuration();
        seekBar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        seekBar.setMax((int) finalTime);
        textViewTime.setText(String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime))));
        imageButtonPlayTrack.setEnabled(true);
        seekBar.setEnabled(true);
        playAndPause();
    }

    private void playAndPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            imageButtonPlayTrack.setImageResource(android.R.drawable.ic_media_play);
        } else {
            mediaPlayer.start();
            imageButtonPlayTrack.setImageResource(android.R.drawable.ic_media_pause);
            timeElapsed = mediaPlayer.getCurrentPosition();
            seekBar.setProgress((int) timeElapsed);
            durationHandler.postDelayed(updateSeekBarTime, 100);
        }
        createNotification();
    }


    private Runnable updateSeekBarTime = new Runnable() {
        @Override
        public void run() {
            if ((mediaPlayer != null) && mediaPlayer.isPlaying()) {
                timeElapsed = mediaPlayer.getCurrentPosition();
                seekBar.setProgress((int) timeElapsed);
                textViewActualTime.setText(String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes((long) timeElapsed),
                        TimeUnit.MILLISECONDS.toSeconds((long) timeElapsed) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeElapsed))));
                durationHandler.postDelayed(this, 100);
            }
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            timeElapsed = progress;
            mediaPlayer.seekTo((int) timeElapsed);
            textViewActualTime.setText(String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes((long) timeElapsed),
                    TimeUnit.MILLISECONDS.toSeconds((long) timeElapsed) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeElapsed))));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playerPresenter.loadNext();
    }

    private void setShareIntent(Track track) {
        if (shareActionProvider != null) {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);

            sendIntent.setType("text/plain");

            StringBuilder stringBuilder = new StringBuilder();
            for (Artist artist : track.getArtistList()) {
                stringBuilder.append(artist.getName());
                stringBuilder.append(" - ");
            }

            String textShare = getActivity().getString(R.string.playerfragmemt_sharetext);
            textShare = String.format(textShare, track.getName(),
                    stringBuilder.toString().substring(0, stringBuilder.toString().length() - 3), track.getPreviewUrl());
            sendIntent.putExtra(Intent.EXTRA_TEXT, textShare);

            shareActionProvider.setShareIntent(sendIntent);
        }
    }

    public void createNotification() {
        NotificationCompat.MediaStyle mediaStyle = new NotificationCompat.MediaStyle();
        final NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(getActivity())
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setStyle(mediaStyle)
                .addAction(generateAction(android.R.drawable.ic_media_previous, getActivity().getString(R.string.playerfragment_previous), ACTION_PREVIOUS));
        if (mediaPlayer.isPlaying()) {
            builder.addAction(generateAction(android.R.drawable.ic_media_pause, getActivity().getString(R.string.playerfragment_pause), ACTION_PAUSE));
        } else {
            builder.addAction(generateAction(android.R.drawable.ic_media_play, getActivity().getString(R.string.playerfragment_play), ACTION_PLAY));
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Artist artist : track.getArtistList()) {
            stringBuilder.append(artist.getName());
            stringBuilder.append(" - ");
        }
        builder.addAction(generateAction(android.R.drawable.ic_media_next, getActivity().getString(R.string.playerfragment_next), ACTION_NEXT))
                .setContentTitle(track.getName())
                .setContentText(stringBuilder.toString().substring(0, stringBuilder.toString().length() - 3) + "\n" + track.getAlbum().getName());

        Picasso.with(getActivity()).load(track.getAlbum().getImageUrl()).placeholder(R.drawable.icon_oldmic).into(new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                builder.setLargeIcon(bitmap);
                NotificationManager notificationManager =
                        (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(TRACK_NOTIFICATION_ID, builder.build());
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                NotificationManager notificationManager =
                        (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(TRACK_NOTIFICATION_ID, builder.build());
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    private NotificationCompat.Action generateAction(int icon, String title, String intentAction) {
        Intent intent = new Intent(intentAction);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(),
                TRACK_NOTIFICATION_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return new NotificationCompat.Action.Builder(icon, title, pendingIntent).build();
    }

    public interface PlayerListener {
        void onPlay(Track track);
    }
}

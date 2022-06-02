package com.depauw.jukebox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.depauw.jukebox.databinding.ActivityPlayerBinding;

import java.io.IOException;

public class PlayerActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {


    private ActivityPlayerBinding binding;

    // variables for tracking background color
    private static int RED_VALUE = 0;
    private static int GREEN_VALUE = 0;
    private static int BLUE_VALUE = 0;

    // member variable for media player
    private MediaPlayer player;

    // variables for ratings
    private static float RATING_ONE = 0;
    private static float RATING_TWO = 0;
    private static float RATING_THREE = 0;
    private static int VOTES_ONE = 0;
    private static int VOTES_TWO = 0;
    private static int VOTES_THREE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.seekbarRed.setOnSeekBarChangeListener(this);
        binding.seekbarGreen.setOnSeekBarChangeListener(this);
        binding.seekbarBlue.setOnSeekBarChangeListener(this);
        // play the first song when app is started
        playSong();
        binding.radiogroupSongs.setOnCheckedChangeListener(this);
        binding.seekbarSongPosition.setOnSeekBarChangeListener(this);
        binding.buttonCastVote.setOnClickListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch(seekBar.getId()) {
            // when user seeks song
            case R.id.seekbar_song_position:{
                // making sure player is instantiated
                if(player != null)
                {
                    player.pause();
                    player.seekTo((i * player.getDuration())/100);
                    player.start();
                }
                break;
            }
            // cases to handle all seekbars for changing background color
            case R.id.seekbar_red: {
                RED_VALUE = i;
                binding.textviewRed.setText(String.valueOf(i));
                binding.constraintlayout.setBackgroundColor(Color.rgb(RED_VALUE, GREEN_VALUE, BLUE_VALUE));
                break;
            }
            case R.id.seekbar_green: {
                GREEN_VALUE = i;
                binding.textviewGreen.setText(String.valueOf(i));
                binding.constraintlayout.setBackgroundColor(Color.rgb(RED_VALUE, GREEN_VALUE, BLUE_VALUE));
                break;
            }
            case R.id.seekbar_blue: {
                BLUE_VALUE = i;
                binding.textviewBlue.setText(String.valueOf(i));
                binding.constraintlayout.setBackgroundColor(Color.rgb(RED_VALUE, GREEN_VALUE, BLUE_VALUE));
                break;
            }
        }
    }

    public void playSong()
    {
        int trackNumber = 0;
        switch(binding.radiogroupSongs.getCheckedRadioButtonId())
        {
            case R.id.radio_song1: {
                trackNumber = R.raw.track1;
                binding.imageviewAlbumCover.setImageResource(R.drawable.track1);
                break;
            }
            case R.id.radio_song2:{
                trackNumber = R.raw.track2;
                binding.imageviewAlbumCover.setImageResource(R.drawable.track2);
                break;
            }
            case R.id.radio_song3:{
                trackNumber = R.raw.track3;
                binding.imageviewAlbumCover.setImageResource(R.drawable.track3);
                break;
            }
        }

        //starting MediaPlayer with the selected song
        player = MediaPlayer.create(this, trackNumber);
        player.start();
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        player.stop();

        // releasing resources used by the player
        player.release();
        playSong();

        // resetting progress of rating bar each time new song is picked
        binding.ratingbarVoterRating.setProgress(0);

        // resetting progress of the song seeking bar each time new song picked
        binding.seekbarSongPosition.setProgress(0);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.button_cast_vote:{
                if(binding.radioSong1.isChecked())
                {
                    // calculating votes and ratings
                    float totalRating = RATING_ONE * VOTES_ONE;
                    totalRating += binding.ratingbarVoterRating.getRating();
                    VOTES_ONE += 1;
                    RATING_ONE = totalRating / VOTES_ONE;

                    // updating views
                    binding.progressbarAverageRating1.setProgress(Math.round(RATING_ONE));
                    binding.textviewNumVotes1.setText(String.valueOf(VOTES_ONE));
                    binding.ratingbarVoterRating.setProgress(0);
                }
                else if(binding.radioSong2.isChecked())
                {
                    // calculating votes and ratings
                    float totalRating = RATING_TWO * VOTES_TWO;
                    totalRating += binding.ratingbarVoterRating.getRating();
                    VOTES_TWO += 1;
                    RATING_TWO = totalRating / VOTES_TWO;

                    // updating views
                    binding.progressbarAverageRating2.setProgress(Math.round(RATING_TWO));
                    binding.textviewNumVotes2.setText(String.valueOf(VOTES_TWO));
                    binding.ratingbarVoterRating.setProgress(0);
                }
                else
                {
                    // calculating votes and ratings
                    float totalRating = RATING_THREE * VOTES_THREE;
                    totalRating += binding.ratingbarVoterRating.getRating();
                    VOTES_THREE += 1;
                    RATING_THREE = totalRating / VOTES_THREE;

                    // updating views
                    binding.progressbarAverageRating3.setProgress(Math.round(RATING_THREE));
                    binding.textviewNumVotes3.setText(String.valueOf(VOTES_THREE));
                    binding.ratingbarVoterRating.setProgress(0);
                }
            }
        }
    }

    // extra method to pause music when app is in background :)
    @Override
    protected void onStop()
    {
        super.onStop();
        player.pause();
    }

    // extra method to continue playing music when app is in background :)
    @Override
    protected void onStart()
    {
        super.onStart();
        player.start();
    }

    // below methods not used
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}
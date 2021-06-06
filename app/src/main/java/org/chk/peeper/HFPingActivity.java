package org.chk.peeper;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;

public class HFPingActivity extends AppCompatActivity {

    double duration = 1;            // 1 seconds
    double freqOfTone = 1000;       // 1000 hz
    int sampleRate = 8000;          // 8000 a number



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hf_ping);

        TextView textViewFrequency = findViewById(R.id.textViewFrequency);
        textViewFrequency.setText(String.valueOf(freqOfTone));

        SeekBar seekBarFrequency = (SeekBar) findViewById(R.id.seekBarFrequency);
        seekBarFrequency.setProgress( ((Double) freqOfTone ).intValue());
        seekBarFrequency.setMax(30000);
        seekBarFrequency.setMin(1);
        seekBarFrequency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    updateTextViewFrequency(progress);
                    freqOfTone = progress;
                SoundPlayer sp = new SoundPlayer();
                sp.execute();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        TextView textViewSamples = findViewById(R.id.textViewSamples);
        textViewSamples.setText(String.valueOf(sampleRate));


        SeekBar seekBarSamples = (SeekBar) findViewById(R.id.seekBarSamples);
        seekBarSamples.setProgress( sampleRate);
        seekBarSamples.setMax(30000);
        seekBarSamples.setMin(1);
        seekBarSamples.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    updateTextViewSamples(progress);
                    sampleRate = progress;
                SoundPlayer sp = new SoundPlayer();
                sp.execute();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    private void updateTextViewFrequency(Integer value){
        TextView textViewFrequency  = (TextView) findViewById(R.id.textViewFrequency);
        textViewFrequency.setText( String.valueOf(value) + "hz");
    }

    private void updateTextViewSamples(Integer value){
        TextView textViewSamples  = (TextView)findViewById(R.id.textViewSamples);
        textViewSamples.setText( String.valueOf(value));
    }

    public void testButtonClick(View view){
        Toast.makeText(this, "Clicked on Button", Toast.LENGTH_LONG).show();

        SeekBar seekBarFrequency = (SeekBar)findViewById(R.id.seekBarFrequency);
        freqOfTone = seekBarFrequency.getProgress();

        SeekBar seekBarSamples = (SeekBar)findViewById(R.id.seekBarSamples);
        sampleRate = seekBarSamples.getProgress();

        SoundPlayer sp = new SoundPlayer();
        sp.execute();
    }


    private class SoundPlayer extends AsyncTask<Void, Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            playSound();
            return null;
        }

        private boolean playSound() {



            double dnumSamples = duration * sampleRate;
            dnumSamples = Math.ceil(dnumSamples);
            int numSamples = (int) dnumSamples;
            double sample[] = new double[numSamples];
            byte generatedSnd[] = new byte[2 * numSamples];


            for (int i = 0; i < numSamples; ++i) {    // Fill the sample array
                sample[i] = Math.sin(freqOfTone * 2 * Math.PI * i / (sampleRate));
            }

// convert to 16 bit pcm sound array
// assumes the sample buffer is normalized.
// convert to 16 bit pcm sound array
// assumes the sample buffer is normalised.
            int idx = 0;
            int i = 0;

            int ramp = numSamples / 20;                                     // Amplitude ramp as a percent of sample count


            for (i = 0; i < ramp; ++i) {                                      // Ramp amplitude up (to avoid clicks)
                double dVal = sample[i];
                // Ramp up to maximum
                final short val = (short) ((dVal * 32767 * i / ramp));
                // in 16 bit wav PCM, first byte is the low order byte
                generatedSnd[idx++] = (byte) (val & 0x00ff);
                generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
            }


            for (i = i; i < numSamples - ramp; ++i) {                         // Max amplitude for most of the samples
                double dVal = sample[i];
                // scale to maximum amplitude
                final short val = (short) ((dVal * 32767));
                // in 16 bit wav PCM, first byte is the low order byte
                generatedSnd[idx++] = (byte) (val & 0x00ff);
                generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
            }

            for (i = i; i < numSamples; ++i) {                                // Ramp amplitude down
                double dVal = sample[i];
                // Ramp down to zero
                final short val = (short) ((dVal * 32767 * (numSamples - i) / ramp));
                // in 16 bit wav PCM, first byte is the low order byte
                generatedSnd[idx++] = (byte) (val & 0x00ff);
                generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
            }

            AudioTrack audioTrack = null;// Get audio track
            try {
                audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                        sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                        AudioFormat.ENCODING_PCM_16BIT, (int) numSamples * 2,
                        AudioTrack.MODE_STATIC);
                audioTrack.write(generatedSnd, 0, generatedSnd.length);        // Load the track
                audioTrack.play();                                             // Play the track
            } catch (Exception e) {
                //RunTimeError("Error: " + e);

                return false;
            }

            int x = 0;
            do {                                                              // Monitor playback to find when done
                if (audioTrack != null)
                    x = audioTrack.getPlaybackHeadPosition();
                else
                    x = numSamples;
            } while (x < numSamples);

            if (audioTrack != null) audioTrack.release();

            return true;
        }

    }


}
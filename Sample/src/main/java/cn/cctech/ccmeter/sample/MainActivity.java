package cn.cctech.ccmeter.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;
import cn.cctech.ccmeter.Meter;


public class MainActivity extends AppCompatActivity {

    private final int MAX_VALUE = 300;
    private final int START_ANGLE = 0;
    private final int END_ANGLE = 250;
    private Meter mMeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mMeter = (Meter) findViewById(R.id.meter);
        mMeter.setAngleRange(START_ANGLE, END_ANGLE);
        mMeter.setMaxValue(MAX_VALUE);
        mMeter.setMeterBackground(R.drawable.meter_bg);
        mMeter.setMeterNeedle(R.drawable.meter_needle);

        TextView startText = (TextView) findViewById(R.id.start_text);
        startText.setText("0");
        TextView endText = (TextView) findViewById(R.id.end_text);
        endText.setText(String.valueOf(MAX_VALUE));
        SeekBar seekBar = (SeekBar) findViewById(R.id.seek_bar);
        seekBar.setMax(MAX_VALUE);
        seekBar.setOnSeekBarChangeListener(mSeekBarChangeListener);
    }

    private final SeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int value = seekBar.getProgress();
            mMeter.setValue(value);
        }
    };

}

package com.example.tranh.pomodoro.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.settings.SettingCredentials;
import com.example.tranh.pomodoro.settings.SharedPrefs;

public class SettingActivity extends AppCompatActivity {


    private static final String TAG = "Vao roi";
    private int workTime;
    private int breakTime;
    private int longBreak;
    private int countLongBreak;
    private TextView tvWorkTime;
    private TextView tvBreakTime;
    private TextView tvLongBreak;
    private TextView tvCountLongBreak;
    private SeekBar sbWorkTime;
    private SeekBar sbBreakTime;
    private SeekBar sbLongBreak;
    private Spinner spCountLongBreak;
    private Button btnDefault;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setReference();
        if (SharedPrefs.getInstance().getSettingCredentials() == null) {
            setDefault();
        }else{
            setSavedSetting();
        }
        setProperties();
        setEvent();

    }

    private void setDefault() {
        SettingCredentials settingCredentials = new SettingCredentials(25, 5, 20, 3);
        setSetting(settingCredentials);
        saveSetting();
    }
    private void setSetting(SettingCredentials settingCredentials){
        workTime = settingCredentials.getWorkTime();
        sbWorkTime.setProgress(settingCredentials.getSpinnerValue(settingCredentials.getWorkTime(), SettingCredentials.WORKTIMEVALUES));

        breakTime = settingCredentials.getBreakTime();
        sbBreakTime.setProgress(settingCredentials.getSpinnerValue(settingCredentials.getBreakTime(), SettingCredentials.BREAKTIMEVALUES));

        longBreak = settingCredentials.getLongBreak();
        sbLongBreak.setProgress(settingCredentials.getSpinnerValue(settingCredentials.getLongBreak(), SettingCredentials.LONGTIMEVALUES));

        countLongBreak = settingCredentials.getCountToLongBreak();
        spCountLongBreak.setSelection(settingCredentials.getSpinnerValue(settingCredentials.getCountToLongBreak(), SettingCredentials.TIMETOLONGBREAKS));
    }
    private void setSavedSetting(){
        SettingCredentials settingCredentials = SharedPrefs.getInstance().getSettingCredentials();
        setSetting(settingCredentials);
    }

    private void setEvent() {
        sbWorkTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                workTime = getValue(sbWorkTime, SettingCredentials.WORKTIMEVALUES);
                tvWorkTime.setText((CharSequence) ("Work Time " + workTime + " minutes"));
                saveSetting();
            }
        });
        sbBreakTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                breakTime = getValue(sbBreakTime, SettingCredentials.BREAKTIMEVALUES);
                tvBreakTime.setText("Break " + breakTime + " minutes");
                saveSetting();
            }
        });
        sbLongBreak.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                longBreak = getValue(sbLongBreak, SettingCredentials.LONGTIMEVALUES);
                tvLongBreak.setText("Long Break " + longBreak + " minutes");
                saveSetting();
            }
        });
        spCountLongBreak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        countLongBreak = 1;
                        break;
                    case 1:
                        countLongBreak = 2;
                        break;
                    case 2:
                        countLongBreak = 3;
                        break;
                    case 3:
                        countLongBreak = 4;
                        break;
                    case 4:
                        countLongBreak = 5;
                        break;
                    default:
                        countLongBreak = 1;
                }
                saveSetting();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefault();
            }
        });
    }

    private void saveSetting() {
        SettingCredentials settingCredentials = new SettingCredentials(workTime, breakTime, longBreak, countLongBreak);
        SharedPrefs.getInstance().put(settingCredentials);
    }

    private int getValue(SeekBar sb, int[] values) {
        return values[sb.getProgress()];
    }

    private void setProperties() {
        tvWorkTime.setText((CharSequence) ("Work Time " + workTime + " minutes"));
        tvBreakTime.setText("Break " + breakTime + " minutes");
        tvLongBreak.setText("Long Break " + longBreak + " minutes");
        tvCountLongBreak.setText("Long break after: ");

    }

    private void setReference() {
        tvWorkTime = (TextView) this.findViewById(R.id.tvWorkTime);
        tvBreakTime = (TextView) this.findViewById(R.id.tvBreakTime);
        tvLongBreak = (TextView) this.findViewById(R.id.tvLongBreak);
        tvCountLongBreak = (TextView) this.findViewById(R.id.tvCountLongBreak);

        sbWorkTime = (SeekBar) this.findViewById(R.id.sbWorkTime);
        sbBreakTime = (SeekBar) this.findViewById(R.id.sbBreakTime);
        sbLongBreak = (SeekBar) this.findViewById(R.id.sbLongBreak);

        spCountLongBreak = (Spinner) this.findViewById(R.id.spCountLongBreak);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SettingCredentials.TIMETOLONGBREAKS);
        spCountLongBreak.setAdapter(adapter);
        btnDefault = (Button) this.findViewById(R.id.btnDefault);
    }


}

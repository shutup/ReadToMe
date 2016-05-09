package com.shutup.readtome;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    @InjectView(R.id.speakBtn)
    Button mSpeakBtn;
    @InjectView(R.id.speakContent)
    EditText mSpeakContent;
    private TextToSpeech textToSpeech = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        textToSpeech = new TextToSpeech(this, this);
        textToSpeech.speak("此处无声", TextToSpeech.QUEUE_ADD, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.shutdown();
        }
    }

    @OnClick(R.id.speakBtn)
    public void onClick() {
        textToSpeech.speak(mSpeakContent.getText().toString(), TextToSpeech.QUEUE_ADD, null);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.CHINESE);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED
                    || result == TextToSpeech.ERROR) {
                Toast.makeText(this, "数据丢失或语言不支持", Toast.LENGTH_SHORT).show();
            }
            if (result == TextToSpeech.LANG_AVAILABLE) {
                Toast.makeText(this, "支持该语言", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "初始化成功", Toast.LENGTH_SHORT).show();
        }
    }
}

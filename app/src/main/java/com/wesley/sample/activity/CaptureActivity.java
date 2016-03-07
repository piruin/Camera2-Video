package com.wesley.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wesley.sample.R;
import com.wesley.sample.fragment.CaptureFragment;

/**
 * Created by wesley on 2016/03/05.
 */
public class CaptureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().add(R.id.content, new CaptureFragment()).commit();
    }
}

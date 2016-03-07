package com.wesley.sample.fragment;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wesley.camera2.fragment.Camera2Fragment;
import com.wesley.sample.R;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by wesley on 2016/03/05.
 */
public class CaptureFragment extends Camera2Fragment implements Camera2Fragment.Cam2Listener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_capture, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView button = (ImageView) view.findViewById(R.id.camera_control);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCameraControlClick((ImageView)v);
            }
        });
    }

    @Override
    public Cam2Listener getCam2Listener() {
        return this;
    }

    @Override
    public int getTextureResource() {
        return R.id.camera_preview;
    }

    @Override
    public File getVideoFile(Context context) {
        File file;
        try {
            File location = context.getExternalFilesDir("video");
            file = File.createTempFile(String.valueOf(new Date().getTime()), ".mp4", location);
        } catch (IOException e) {
            file = new File(context.getExternalFilesDir("video"),String.valueOf(new Date().getTime()) + ".mp4");
        }
        return file;
    }

    public void onCameraControlClick(ImageView view) {
        if (isRecording()) {
            Log.d("TEST", "File saved: " + getCurrentFile().getName());
            view.setImageResource(R.drawable.ic_record);
            stopRecordingVideo();
        } else {
            view.setImageResource(R.drawable.ic_pause);
            startRecordingVideo();
        }
    }

    @Override
    public void onCameraException(CameraAccessException cae) {
        cae.printStackTrace();
    }

    @Override
    public void onNullPointerException(NullPointerException npe) {
        npe.printStackTrace();
    }

    @Override
    public void onInterruptedException(InterruptedException ie) {
        ie.printStackTrace();
    }

    @Override
    public void onIOException(IOException ioe) {
        ioe.printStackTrace();
    }

    @Override
    public void onConfigurationFailed() {
        Log.d("TEST", "Failed to configure camera");
    }
}

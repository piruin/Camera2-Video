package com.wesley.sample.fragment;

import android.content.Context;
import android.graphics.Paint;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wesley.camera2.fragment.Camera2Fragment;
import com.wesley.camera2.util.Camera2Listener;
import com.wesley.sample.R;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static android.hardware.camera2.CaptureRequest.*;

/**
 * Created by wesley on 2016/03/05.
 */
public class CaptureFragment extends Camera2Fragment implements Camera2Listener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRationaleMessage("Hey man, we need to use your camera please!");
    }

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
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateConfiguration();
            }
        });
    }

    boolean toggle;
    int count = 0;

    private void updateConfiguration() {
        count++;
        toggle = !toggle;
        updatePreview();
    }

    @Override
    public int getTextureResource() {
        return R.id.camera_preview;
    }

    @Override
    public File getVideoFile(Context context) {
        File file;
        try {
            File location = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
            file = File.createTempFile(String.valueOf(new Date().getTime()), ".mp4", location);
        } catch (IOException e) {
            e.printStackTrace();
            file = new File(context.getExternalFilesDir("video"),String.valueOf(new Date().getTime()) + "-io.mp4");
        }
        return file;
    }

    @Override
    protected void setUpCaptureRequestBuilder(Builder builder) {
        super.setUpCaptureRequestBuilder(builder);
        CameraCharacteristics characteristics = null;
        try {
        CameraManager cameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
            characteristics = cameraManager.getCameraCharacteristics("0");
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        //builder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_OFF);
        //builder.set(CaptureRequest.CONTROL_AWB_MODE, toggle ? CameraMetadata.CONTROL_AWB_MODE_FLUORESCENT : CameraMetadata.CONTROL_AWB_MODE_DAYLIGHT);

        //builder.set(CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_OFF);

        builder.set(CONTROL_AE_LOCK, toggle);
//        builder.set(CaptureRequest.SENSOR_FRAME_DURATION, characteristics.get(CameraCharacteristics.SENSOR_INFO_MAX_FRAME_DURATION) / 2 );
//        builder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, 5000L); //Shutter speed
        //builder.set(CaptureRequest.SENSOR_SENSITIVITY, count * 100); //ISO
//        builder.set(CaptureRequest.LENS_APERTURE, 1/6f);
    }

    public void onCameraControlClick(ImageView view) {
        if (isRecording()) {
            Log.d("TEST", "File saved: " + getCurrentFile().getAbsolutePath());
            view.setImageResource(R.drawable.ic_record);
            stopRecordingVideo();
        } else {
            view.setImageResource(R.drawable.ic_pause);
            startRecordingVideo();
        }
    }
}

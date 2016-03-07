# Camera2-Video
A simple Android wrapper for interfacing with the new Camera2 API for video capture.

## Usage
### Setup
For an example, check out the sample app included.

Firstly, create a `Fragment` and extend `com.wesley.camera2.fragment.Camera2Fragment`. You will be required to override the following methods:

1. `getTextureResource` - this needs to return an ID for an `com.wesley.camera2.widget.AutoFitTextureView` in your layout.
2. `getVideoFile` - this needs to return a `File` for the `MediaRecorder` to save to (ie: where the video will be saved to).
3. `getCam2Listener` - needs to return a `com.wesley.camera2.fragment.Camera2Fragment.Cam2Listener`, which provides error events for camera setup. This can either be an object:
```
return new Camera2Fragment.Cam2Listener({...
```

or return the fragment (assuming its implementing `Camera2Fragment.Cam2Listener`:
```
return this;
```

### Start and Stop Recording
To start recording, simply call `startRecordingVideo()` and to stop, use `stopRecordingVideo()`. Once stopped, the video is saved to disk and the `MediaRecorder` is setup again to start a new recording. To check if the app is currently recording a video or not, a method `isRecording()` is provided.

### Customisation
The new Camera2 API has greatly changed how to interface with the camera. This library doesn't provide complete customisation, but it does allow override a few setup methods:

1. `setUpCaptureRequestBuilder(CaptureRequest.Builder builder)` - the default setup only does `builder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);`, however, you can override `setUpCaptureRequestBuilder` and build the CaptureRequest yourself.
2. `protected void setUpMediaRecorder() throws IOException` - care needs to be taken when overriding this method. If you need to customise any `MediaRecorder` settings, overriding this method will allow that. Currently, the encoding frame rate is set to 30, and the bit rate to 1600

## Download
Either download using Gradle:

`compile 'com.wesleyelliott.camera2:camera2-video'`

or Maven:

```
<dependency>
  <groupId>com.wesleyelliott</groupId>
  <artifactId>camera2</artifactId>
  <version>0.0.1</version>
</dependency>
```

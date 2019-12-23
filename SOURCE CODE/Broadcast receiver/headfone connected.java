///You can use this code for checking if the headset is plugged in

AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
audioManager.isWiredHeadsetOn();

//(Don't worry about the deprecation, it's still usable for ONLY checking if the headset are plugged in.)

And you need <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />

//Available in Android 2.0 +

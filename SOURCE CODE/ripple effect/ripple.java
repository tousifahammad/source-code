android:focusable="true"
android:clickable="true"
android:foreground="?android:attr/selectableItemBackground"


========================================================================

you can also make it custom too

<ripple xmlns:android="http://schemas.android.com/apk/res/android"
    android:color="@color/colorcode">

    <item
        android:id="@android:id/mask"
        android:drawable="@color/colorcode" />
</ripple>

========================================================================

to support in older versions you can do like that

<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_pressed="true">
        <shape android:shape="rectangle">
            <solid android:color="@color/colorcode" />
        </shape>
    </item>
    <item>
        <shape android:shape="rectangle">
            <solid android:color="@android:color/colorcode" />
        </shape>
    </item>
</selector>
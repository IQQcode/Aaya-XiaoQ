
## ArcSeekBar进度条布局

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:gl="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    style="@style/parent.contentLayout"
    android:background="#00000000"
    app:layout_behavior="@string/appbar_scrolling_view_behavior"
    tools:context=".VoiceSettingActivity"
    tools:showIn="@layout/app_bar_main">

    <androidx.core.widget.NestedScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content">

                <TextView
                    style="@style/viewParent.headerText"
                    android:layout_gravity="bottom"
                    android:text="Voice \n\t\tSettings" />

                <ImageView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:adjustViewBounds="true"
                    android:src="@mipmap/hero" />
            </LinearLayout>

            <RelativeLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_marginTop="40dp">

                <RelativeLayout
                    android:id="@+id/layout_voice_setting"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content">

                    <!--语速-->
                    <ImageView
                        android:id="@+id/iv_speed"
                        android:layout_width="30dp"
                        android:layout_height="30dp"
                        android:layout_alignParentLeft="true"
                        android:layout_marginLeft="55dp"
                        android:src="@mipmap/speeds" />

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_gravity="center_vertical"
                        android:layout_toRightOf="@id/iv_speed"
                        android:paddingLeft="10dp"
                        android:text="@string/text_tts_speed"
                        android:textSize="20sp"
                        android:textStyle="bold" />

                    <!--音量-->
                    <ImageView
                        android:layout_width="30dp"
                        android:layout_height="30dp"
                        android:layout_toLeftOf="@id/tv_volume"
                        android:src="@mipmap/voice" />

                    <TextView
                        android:id="@+id/tv_volume"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_alignParentRight="true"
                        android:layout_gravity="center_vertical"
                        android:layout_marginRight="55dp"
                        android:paddingLeft="10dp"
                        android:text="@string/text_tts_volume"
                        android:textSize="20sp"
                        android:textStyle="bold" />

                </RelativeLayout>


                <LinearLayout
                    android:id="@+id/layout_arcseekbar"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_below="@id/layout_voice_setting"
                    android:orientation="horizontal">

                    <com.gcssloop.widget.ArcSeekBar
                        android:id="@+id/mArcSeekBarSpeed"
                        android:layout_width="0dp"
                        android:layout_height="280dp"
                        android:layout_weight="1"
                        app:arc_colors="@array/arc_colors_speed"
                        app:arc_max="100"
                        app:arc_open_angle="90"
                        app:arc_progress="0"
                        app:arc_rotate_angle="90"
                        app:arc_shadow_radius="10dp"
                        app:arc_thumb_color="#fff"
                        app:arc_thumb_mode="STROKE"
                        app:arc_thumb_radius="16dp"
                        app:arc_thumb_shadow_color="#000000"
                        app:arc_thumb_shadow_radius="3dp"
                        app:arc_thumb_width="3dp"
                        app:arc_width="20dp" />

                    <com.gcssloop.widget.ArcSeekBar
                        android:id="@+id/mArcSeekBarVolume"
                        android:layout_width="0dp"
                        android:layout_height="280dp"
                        android:layout_weight="1"
                        app:arc_colors="@array/arc_colors_volume"
                        app:arc_max="100"
                        app:arc_open_angle="90"
                        app:arc_progress="0"
                        app:arc_rotate_angle="90"
                        app:arc_shadow_radius="10dp"
                        app:arc_thumb_color="#fff"
                        app:arc_thumb_mode="STROKE"
                        app:arc_thumb_radius="16dp"
                        app:arc_thumb_shadow_color="#000000"
                        app:arc_thumb_shadow_radius="3dp"
                        app:arc_thumb_width="3dp"
                        app:arc_width="20dp" />

                </LinearLayout>


                <!--                <SeekBar-->
                <!--                    android:id="@+id/mVoiceSpeed"-->
                <!--                    style="@style/CustomSeekbarStyle"-->
                <!--                    android:layout_width="match_parent"-->
                <!--                    android:layout_height="wrap_content"-->
                <!--                    android:layout_margin="10dp" />-->

                <!--                <SeekBar-->
                <!--                    android:id="@+id/mVoiceVolume"-->
                <!--                    style="@style/CustomSeekbarStyle"-->
                <!--                    android:layout_width="match_parent"-->
                <!--                    android:layout_height="wrap_content"-->
                <!--                    android:layout_margin="10dp" />-->

                <androidx.recyclerview.widget.RecyclerView
                    android:id="@+id/mRecyclerView"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:layout_below="@id/layout_arcseekbar"
                    android:layout_marginTop="20dp" />

            </RelativeLayout>

        </LinearLayout>

    </androidx.core.widget.NestedScrollView>

</RelativeLayout>
```


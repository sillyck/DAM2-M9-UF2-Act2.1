package com.example.gira;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.Random;

public class Musica {
    private static final int[] canciones = new int[]{R.raw.nosotros,R.raw.fuegofuego,R.raw.cuestiondefe,R.raw.sudoresfrios,R.raw.masalchol,R.raw.alatumba,R.raw.dimequesi};
    private static MediaPlayer mp = null;
    private static int audioIndex = 0;

    static {
        mp = new MediaPlayer();
        mp.setLooping(true);
    }

    public static MediaPlayer getMp() {
        return mp;
    }

    public static void playAudio(Context context) {
        try {
            if(audioIndex >= canciones.length){
                audioIndex = 0;
            }
            int audioResourceId = canciones[audioIndex];
            AssetFileDescriptor afd = context.getResources().openRawResourceFd(audioResourceId);

            mp.reset();
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();

            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void pausaAudio() {
        if (mp != null && mp.isPlaying()) {
            mp.pause();
        }
    }

    public static void resumeAudio() {
        if (mp != null && !mp.isPlaying()) {
            mp.start();
        }
    }

    public static void releaseMediaPlayer() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }
}

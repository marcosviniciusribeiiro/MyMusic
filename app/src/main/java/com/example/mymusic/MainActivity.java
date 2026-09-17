package com.example.mymusic;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView txtMusicaAtual;

    private Button btnPlayPause;

    private ArrayList<Musica> playlist;

    private MediaPlayer mediaPlayer;

    private int musicaAtual = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listViewMusicas = findViewById(R.id.id_musicas);
        txtMusicaAtual = findViewById(R.id.id_musicaAtual);

        btnPlayPause = findViewById(R.id.id_btn_play);
        Button btnAnterior = findViewById(R.id.id_btn_anterior);
        Button btnProxima = findViewById(R.id.id_btn_proximo);

        playlist = new ArrayList<>();

        playlist.add(new Musica("Marshmello ft. Khalid \n - Silence (Video Oficial)", R.raw.silence));

        playlist.add(new Musica("Juice WRLD ft. Marshmello \n - Come & Go (Official Audio)", R.raw.come_and_go));

        playlist.add(new Musica("Imagine Dragons \n - Thunder", R.raw.thunder));
        
        playlist.add(new Musica("Shoji Meguro \n - Life Will Change", R.raw.life_will_change));

        playlist.add(new Musica("Shoji Meguro \n - Heartful Cry", R.raw.heartful_cry));

        playlist.add(new Musica("Shoji Meguro \n - Darkness (Final Boss P3R)", R.raw.darkness_persona_3_reload));

        playlist.add(new Musica("Lyn\n - Beneath the Mask", R.raw.beneath_the_mask));


        ArrayAdapter<Musica> adapter =
                new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        playlist);
        listViewMusicas.setAdapter(adapter);
        listViewMusicas.setOnItemClickListener(
                (parent, view, position, id) -> {
                    musicaAtual = position;
                    tocarMusica();
                });

        btnPlayPause.setOnClickListener(v -> {
            if(mediaPlayer != null){
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPlayPause.setText("Play");
                }else{
                    mediaPlayer.start();
                    btnPlayPause.setText("Pause");
                }
            }
        });

        btnProxima.setOnClickListener(v -> {
            musicaAtual++;
            if(musicaAtual >= playlist.size()){
                musicaAtual = 0;
            }
            tocarMusica();
        });

        btnAnterior.setOnClickListener(v -> {
            musicaAtual--;
            if(musicaAtual < 0){
                musicaAtual = playlist.size()-1;
            }
            tocarMusica();
        });

        tocarMusica();
    }

    @SuppressLint("SetTextI18n")
    private void tocarMusica(){

        if(mediaPlayer != null){
            mediaPlayer.release();
        }

        Musica musica = playlist.get(musicaAtual);
        mediaPlayer = MediaPlayer.create(this,musica.getArquivo());
        txtMusicaAtual.setText("Tocando: " +musica.getTitulo());
        mediaPlayer.start();
        btnPlayPause.setText("Pause");
        mediaPlayer.setOnCompletionListener(mp -> {
            musicaAtual++;
            if(musicaAtual >= playlist.size()){
                musicaAtual = 0;
            }
            tocarMusica();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
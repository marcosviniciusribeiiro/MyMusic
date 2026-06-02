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

        playlist.add(new Musica("Color Your Night\n· Lotus Juice · Azumi Takahashi", R.raw.color_your_night));

        playlist.add(new Musica("Heartful Cry (P3R ver.)\n· Shoji Meguro", R.raw.heartful_cry));

        playlist.add(new Musica("闇 - Darkness (Final Boss theme P3R)\n· Shoji Meguro", R.raw.darkness_persona_3_reload));

        playlist.add(new Musica("Life Will Change - Persona 5 OST\n· Shoji Meguro", R.raw.life_will_change));

        playlist.add(new Musica("When Mother was There\n· Shoji Meguro", R.raw.when_mother_was_there));

        playlist.add(new Musica("Beneath the Mask\n· Lyn", R.raw.beneath_the_mask));

        playlist.add(new Musica("KICK BACK\n· Kenshi Yonezu", R.raw.kick_back));

        playlist.add(new Musica("Hunting For Your Dreams (HxH)\n· GALNERYUS ", R.raw.hunting_for_your_dreams));

        playlist.add(new Musica("Menace\n· Playboi Carti", R.raw.menace));

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
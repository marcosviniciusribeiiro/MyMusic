import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mymusic.Musica;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listViewMusicas;
    private TextView txtMusicaAtual;

    private Button btnPlayPause;
    private Button btnAnterior;
    private Button btnProxima;

    private ArrayList<Musica> playlist;

    private MediaPlayer mediaPlayer;

    private int musicaAtual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewMusicas = findViewById(R.id.);
        txtMusicaAtual = findViewById(R.id.txtMusicaAtual);

        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnAnterior = findViewById(R.id.btnAnterior);
        btnProxima = findViewById(R.id.btnProxima);

        playlist = new ArrayList<>();

        playlist.add(new Musica("Imagine Dragons - Believer", R.raw.believer));

        playlist.add(new Musica("Coldplay - Viva La Vida", R.raw.viva_la_vida));

        playlist.add(new Musica("Coldplay - Paradise", R.raw.paradise));

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
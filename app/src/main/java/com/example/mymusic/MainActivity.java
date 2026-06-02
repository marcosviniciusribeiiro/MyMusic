import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mymusic.Musica;
import com.example.mymusic.R;

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

        listViewMusicas = findViewById(R.id.id_musicas);
        txtMusicaAtual = findViewById(R.id.id_musicaAtual);

        btnPlayPause = findViewById(R.id.id_btn_play);
        btnAnterior = findViewById(R.id.id_btn_anterior);
        btnProxima = findViewById(R.id.id_btn_proximo);

        playlist = new ArrayList<>();

        playlist.add(new Musica("KICK BACK", R.raw.kick_back));

        playlist.add(new Musica("Touhou 7 - Charming Domination", R.raw.charming_domination));

        playlist.add(new Musica("Life Will Change ( With Lyrics ) - Persona 5 OST", R.raw.life_will_change));

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
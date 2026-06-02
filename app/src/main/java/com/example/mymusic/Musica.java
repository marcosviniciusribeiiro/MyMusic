package com.example.mymusic;

public class Musica {
    private String titulo;
    private int arquivo;

    public Musica(String titulo, int arquivo) {
        this.titulo = titulo;
        this.arquivo = arquivo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getArquivo() {
        return arquivo;
    }

    public void setArquivo(int arquivo) {
        this.arquivo = arquivo;
    }

    @Override
    public String toString() {
        return "Musica{" +
                "titulo='" + titulo + '\'' +
                ", arquivo=" + arquivo +
                '}';
    }
}

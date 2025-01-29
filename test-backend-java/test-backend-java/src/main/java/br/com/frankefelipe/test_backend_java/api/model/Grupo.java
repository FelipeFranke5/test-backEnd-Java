package br.com.frankefelipe.test_backend_java.api.model;

import jakarta.persistence.*;

@Entity
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    public Grupo() {}

    public Grupo(String nome) {
        this.setNome(nome);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long novoId) {
        this.id = novoId;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

}

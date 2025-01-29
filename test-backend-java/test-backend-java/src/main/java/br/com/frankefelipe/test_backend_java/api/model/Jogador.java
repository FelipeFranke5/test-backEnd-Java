package br.com.frankefelipe.test_backend_java.api.model;

import br.com.frankefelipe.test_backend_java.api.dto.RequisicaoJogador;
import jakarta.persistence.*;

@Entity
public class Jogador {

    public Jogador() {
    }

    public Jogador(RequisicaoJogador requisicaoJogador) {
        this.setNome(requisicaoJogador.nome());
        this.setEmail(requisicaoJogador.email());
        this.setTelefone(requisicaoJogador.telefone() != null ? requisicaoJogador.telefone() : "N/A");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Column(unique = true)
    private String email;

    @Column
    private String telefone;

    @Column(unique = true)
    private String codinome;

    @OneToOne
    private Grupo grupo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCodinome() {
        return codinome;
    }

    public void setCodinome(String codinome) {
        this.codinome = codinome;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
        result = prime * result + ((codinome == null) ? 0 : codinome.hashCode());
        result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
        return result;

    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Jogador other = (Jogador) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (telefone == null) {
            if (other.telefone != null)
                return false;
        } else if (!telefone.equals(other.telefone))
            return false;
        if (codinome == null) {
            if (other.codinome != null)
                return false;
        } else if (!codinome.equals(other.codinome))
            return false;
        if (grupo == null) {
            return other.grupo == null;
        } else return grupo.equals(other.grupo);

    }

}

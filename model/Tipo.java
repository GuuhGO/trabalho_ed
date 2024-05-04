package model;

public class Tipo {
    private int id;
    private String nome;
    private String descricao;

    public Tipo() {}

    public Tipo(int id, String nome, String descricao) {
        setId(id);
        setNome(nome);
        setDescricao(descricao);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCsv() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(id).append(";").append(nome).append(";").append(descricao);
        return buffer.toString();
    }
}

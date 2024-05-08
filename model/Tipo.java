package model;

public class Tipo implements IRegistroCsv {
    private int codigo;
    private String nome;
    private String descricao;

    public Tipo() {}

    public Tipo(int codigo, String nome, String descricao) {
        setCodigo(codigo);
        setNome(nome);
        setDescricao(descricao);
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setNome(String nome) {
        if(nome.isEmpty()) {
            nome = "NULL";
        }
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setDescricao(String descricao) {
        if(descricao.isEmpty()) {
            descricao = "NULL";
        }
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String getObjCsv() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(codigo).append(";").append(nome).append(";").append(descricao);
        return buffer.toString();
    }
}

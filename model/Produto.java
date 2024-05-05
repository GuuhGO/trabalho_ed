package model;

public class Produto {
    private int codigo;
    private String nome;
    private Tipo tipo;
    private double valor;
    private int quantidadeEstoque;

    public Produto(int codigo, String nome, Tipo tipo, double valor, int quantidadeEstoque) {
        setCodigo(codigo);
        setNome(nome);
        setTipo(tipo);
        setValor(valor);
        setQuantidadeEstoque(quantidadeEstoque);
    }
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        if(valor < 0) {
            valor = 0;
        }
        this.valor = valor;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        if(quantidadeEstoque < 0) {
            quantidadeEstoque = 0;
        }
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getCsv() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(codigo);
        buffer.append(";");
        buffer.append(nome);
        buffer.append(";");
        buffer.append(valor);
        buffer.append(";");
        buffer.append(quantidadeEstoque);
        buffer.append(";");
        buffer.append(tipo.getCodigo());
        return buffer.toString();
    }
}

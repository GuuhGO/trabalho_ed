package model;

public class Produto implements ICsv {
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
        if(nome.isEmpty()) {
            nome = "NULL";
        }
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

    @Override
    public String getObjCsv() {
        return codigo + ";" +  tipo.getCodigo() + ";" + nome + ";" + valor + ";" + quantidadeEstoque;
    }

    @Override
    public String getCsvId() {
        return String.valueOf(getCodigo());
    }
	@Override
	public boolean compareAllFields(String reference) {
		return (String.valueOf(codigo).contains(reference) || nome.toUpperCase().contains(reference));
	}
}

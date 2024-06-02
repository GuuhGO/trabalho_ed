package controller.hashTables;

import datastrucures.genericList.List;
import model.ICsv;
import model.Produto;
import model.Tipo;

public class ProdutoHashTable {
    private int size; // n° de elementos na table
    @SuppressWarnings("rawtypes")
    private List[] buckets;
    private final float OCUPACAO_MAXIMA = 0.80f;
    // preenche 80% do dos itens do array antes de redimensionar
    // dobrando seu tamanho.

    public ProdutoHashTable() {
        inicializarBuckets(15);
        size = 0;
    }

    private void inicializarBuckets(int length) {
        this.buckets = new List[length];
        for (int i = 0; i < length; i++) {
            this.buckets[i] = new List<Produto>();
        }
    }

    public void put(Produto produto) throws Exception {
        Tipo tipo = produto.getTipo();
        int bucketIndex = hashFunction(tipo.getCodigo());
        @SuppressWarnings("unchecked")
        List<Produto> list = buckets[bucketIndex];

        int qtd = list.size();
        for (int i = 0; i < qtd; i++) {
            Produto listItem = list.get(i);
            if (listItem.equals(produto)) {
                return;
            }
            else if (listItem.getCodigo() == produto.getCodigo()) {
                list.remove(i);
            }
        }
        list.addLast(produto);
        size++;

        if ((float) size / buckets.length > OCUPACAO_MAXIMA) {
            reHash();
        }
    }

    public void remove(int codProduto, int codtipo) throws Exception {
        int bucketIndex = hashFunction(codtipo);
        @SuppressWarnings("unchecked")
        List<Produto> list = buckets[bucketIndex];

        int qtd = list.size();
        for (int i = 0; i < qtd; i++) {
            if (list.get(i).getCodigo() == codProduto) {
                list.remove(i);
                size--;
                break;
            }
        }
    }

    public void remove(Produto p) throws Exception {
        remove(p.getCodigo(), p.getTipo().getCodigo());
    }

    public Produto get(int codigoProduto) throws Exception {
        for(@SuppressWarnings("unchecked") List<Produto> bucket : buckets) {
            int bs = bucket.size();
            for (int i = 0; i < bs; i++) {
                Produto item = bucket.get(i);
                if(item.getCodigo() == codigoProduto) {
                    return item;
                }
            }
        }
        throw new Exception("Produto não encontrado");
    }

    public List<ICsv> getByType(int codigoTipo) throws Exception {
        int bucketIndex = hashFunction(codigoTipo);
        @SuppressWarnings("unchecked")
        List<Produto> bucket = buckets[bucketIndex];
        int bucketSize = bucket.size();
        List<ICsv> resposta = new List<>();
        for (int i = 0; i < bucketSize; i++) {
            Produto produto = bucket.get(i);
            if(produto.getTipo().getCodigo() == codigoTipo) {
                resposta.addLast(produto);
            }
        }
        return resposta;
    }

    public List<ICsv> getAllProducts() throws Exception {
        List<ICsv> resposta = new List<>();
        for(@SuppressWarnings("unchecked") List<Produto> bucket : buckets) {
            if(bucket == null) continue;
            int bucketSize = bucket.size();
            for (int i = 0; i < bucketSize; i++) {
                resposta.addLast(bucket.get(i));
            }
        }
        return resposta;
    }

    public int size() {
        return size;
    }

    public int hashFunction(Integer key) {
        return Math.abs(key) % buckets.length;
    }

    private void reHash() {
        @SuppressWarnings("unchecked")
        List<Produto>[] oldTable = buckets;
        int tamanhoAntigo = oldTable.length;
        inicializarBuckets(tamanhoAntigo * 2);
        size = 0;
        for (List<Produto> oldBucket : oldTable) {
            int sizeOldBucket = oldBucket.size();
            for (int j = 0; j < sizeOldBucket; j++) {
                try {
                    put(oldBucket.get(j));
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
}
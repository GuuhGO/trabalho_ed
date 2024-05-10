package controller.Dicionarios;

import datastrucures.genericList.List;
import model.Tipo;

public class DicionarioTipo implements IDicionario<Integer, Tipo> {
    @SuppressWarnings("rawtypes")
    private List[] buckets;
    private int size;

    public DicionarioTipo() {
        inicializarBuckets(15);
        size = 0;
    }

    private void inicializarBuckets(int size) {
        this.buckets = new List[size];
        for(int i = 0; i < size; i++) {
            this.buckets[i] = new List<Tipo>();
        }
    }

    @Override
    public void put(Tipo item) throws Exception {
        int bucketIndex = item.hashCode() % buckets.length;
        List<Tipo> list = this.buckets[bucketIndex];

        int qtd = list.size();
        for(int i = 0; i < qtd; i++) {
            if(list.get(i).equals(item)) {
                return;
            }
        }

        list.addLast(item);
        size++;
    }

    @Override
    public void remove(Integer id) throws Exception {
        int bucketIndex = Tipo.hash(id) % buckets.length;
        List<Tipo> list = this.buckets[bucketIndex];

        int qtd = list.size();
        for(int i = 0; i < qtd; i++) {
            if(list.get(i).getCodigo() == id) {
                list.remove(i);
                size--;
                return;
            }
        }
    }

    @Override
    public Tipo get(Integer id) throws Exception {
        int bucketIndex = Tipo.hash(id) % buckets.length;
        List<Tipo> list = this.buckets[bucketIndex];

        int qtd = list.size();
        for(int i = 0; i < qtd; i++) {
            Tipo elemento = list.get(i);
            if(elemento.getCodigo() == id) {
                return elemento;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }
}

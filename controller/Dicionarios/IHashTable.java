package controller.Dicionarios;

public interface IHashTable<K, V> {
    void put(V item) throws Exception;
    void remove(K id) throws Exception;
    V get(K id) throws Exception;
    int size();
}

package model;

import java.io.IOException;

public interface ICsvWriter<T extends ICsv>{
    void save(T obj) throws IOException;
    void delete(T obj) throws IOException;
    T getById(String id) throws Exception;
    T objectBuilder(String csv) throws Exception;
}

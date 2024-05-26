package controller.csv;

import datastrucures.genericList.List;
import model.ICsv;

import java.io.IOException;

public interface ICsvController<T extends ICsv> {
    void save(T obj) throws IOException;
    void delete(T obj) throws IOException;
    void delete(String id) throws IOException;
    T get(String id) throws Exception;
    List<T> get() throws IOException;
}

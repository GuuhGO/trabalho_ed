package controller.csv;

import datastrucures.genericList.List;
import model.ICsv;

import java.io.IOException;

public interface ICsvController<T extends ICsv> {
    void save(T obj) throws IOException;
    void delete(T obj) throws IOException;
    void delete(String id) throws IOException;
    ICsv get(String id) throws Exception;
    List<ICsv> get() throws Exception;
}

package model;

import java.io.IOException;

public interface ICsvWriter<T extends ICsv>{
    void save() throws IOException;
    void delete() throws IOException;
    T getById(String id) throws Exception;
    T objectBuilder(String csv) throws Exception;
}

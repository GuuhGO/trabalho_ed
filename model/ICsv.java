package model;

public interface ICsv {
    String getObjCsv();
    String getCsvId();
    boolean compareAllFields(String reference);
}

package com.nc.unc.myDao.template;

public interface SQLTemplate {
    String getInsertSQL();
    String getUpdateSQL();
    String getSelectSQL();
    String getDeleteSQL();
    String assembleVariableSelectInSql(int size);
}

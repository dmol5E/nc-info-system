package com.nc.unc.dao.template;

public interface SQLTemplate {
    String getInsertSQL();
    String getUpdateSQL();
    String getSelectSQL();
    String getSelectAllSql();
    String getDeleteSQL();
    String getExistsSql();
    String assembleVariableSelectInSql(int size);
}

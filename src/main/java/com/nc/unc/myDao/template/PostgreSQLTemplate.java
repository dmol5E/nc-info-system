package com.nc.unc.myDao.template;

import com.nc.unc.myDao.annotation.Attribute;
import com.nc.unc.myDao.annotation.PrimaryKey;
import com.nc.unc.myDao.annotation.Table;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
public class PostgreSQLTemplate implements SQLTemplate {

    private final PrimaryKey primaryKey;
    private final Map<Field, Attribute> fieldAttributeMap;
    private final Map<Field, Attribute> fieldEnumeratedMap;
    private final String attributesSql;

    private final String tableName;

    private final String primaryKeyWhere;

    private final String SELECT_IN_SQL_TEMPLATE;

    @Getter
    private final String insertSql;
    @Getter
    private final String updateSql;
    @Getter
    private final String deleteSql;
    @Getter
    private final String selectSql;


    public PostgreSQLTemplate(Class<?> entity, PrimaryKey primaryKey, Map<Field, Attribute> fieldAttributeMap, Map<Field, Attribute> fieldEnumeratedMap){
        this.primaryKey = primaryKey;

        this.fieldAttributeMap = fieldAttributeMap;
        this.fieldEnumeratedMap = fieldEnumeratedMap;
        this.tableName = entity.getAnnotation(Table.class).schema() + "." + entity.getAnnotation(Table.class).value();

        this.attributesSql = assembleAttributes();

        this.SELECT_IN_SQL_TEMPLATE = preAssembleSelectInSql();
        this.primaryKeyWhere = assemblePrimaryKeysWhere();

        this.selectSql = getSelectSQL();
        this.deleteSql = getDeleteSQL();
        this.updateSql = getUpdateSQL();
        this.insertSql = getInsertSQL();
    }

    private String assemblePrimaryKeysWhere(){
        return primaryKey.value() + " = ?";
    }

    private String preAssembleSelectInSql() {
        String SQL = "select :attributes from :table where (:in_list);";
        return SQL.replaceAll(":attributes", attributesSql)
                .replaceAll(":table", tableName);
    }

    private String assembleAttributes(){
        StringBuilder sb = new StringBuilder();
        sb.append(primaryKey.value()).append(", ");
        for (Attribute attribute: this.fieldEnumeratedMap.values())
            sb.append(attribute.value()).append(", ");
        for (Attribute attribute: this.fieldAttributeMap.values())
            sb.append(attribute.value()).append(", ");
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    @Override
    public String getInsertSQL() {
        String SQL = "insert into :table_name (:columns) values :values";

        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < this.fieldAttributeMap.size(); i++){
            sb.append("?, ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(")");
        return SQL.replaceAll(":table_name", tableName)
                .replaceAll(":columns",  this.fieldAttributeMap.values().stream()
                        .map(Attribute::value)
                        .collect(Collectors.joining(" , ")))
                .replaceAll(":values",sb.toString());
    }

    @Override
    public String getUpdateSQL() {
        String SQL = "update :table_name set :update_attributes where :primary_key ";
        String updateAttributesSQL = this.fieldAttributeMap.values().stream()
                .map(Attribute::value)
                .map(s -> s + " = ?, ")
                .collect(Collectors.joining());
        updateAttributesSQL = updateAttributesSQL.substring(0, updateAttributesSQL.length() - 2);
        return SQL.replaceAll(":table_name", tableName)
                .replaceAll(":update_attributes",updateAttributesSQL)
                .replaceAll(":primary_key", primaryKeyWhere);
    }

    @Override
    public String getSelectSQL() {
        String SQL = "select :attributes FROM :table_name WHERE :primary_key";
        return SQL.replaceAll(":attributes", attributesSql)
                .replaceAll(":table_name", tableName)
                .replaceAll(":primary_key", primaryKeyWhere);
    }

    @Override
    public String getSelectAllSql() {
        String SQL = "select :attributes FROM :table_name";
        return SQL.replaceAll(":attributes", attributesSql)
                .replaceAll(":table_name", tableName);
    }

    @Override
    public String getDeleteSQL() {
        String SQL = "delete from :table_name WHERE :primary_key";
        return SQL.replaceAll(":table_name", tableName)
                .replaceAll(":primary_key", primaryKeyWhere);
    }

    @Override
    public String getExistsSql() {
        String EXISTS_SQL = "select count(*) from (select :pk from :table_name where :primary_key limit 1) sub";
        return EXISTS_SQL.replaceAll(":pk", primaryKey.value())
                .replaceAll(":table_name", tableName)
                .replaceAll(":primary_key", primaryKeyWhere);
    }

    @Override
    public String assembleVariableSelectInSql(int size) {
        if(size < 1)
            throw new IllegalArgumentException();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++)
            sb.append("?, ");
        sb.delete(sb.length() - 2, sb.length());
        return SELECT_IN_SQL_TEMPLATE.replaceAll(":in_list", sb.toString());
    }

}

package com.dstresdf.common.util;

import java.io.Serializable;

public class StudyGroupCriteria implements Serializable {
    private String field;
    private String operator;
    private String value;
    private String sortField;
    private String sortOrder;

    public StudyGroupCriteria(String field, String operator, String value, String sortField, String sortOrder) {
        this.field = field;
        this.operator = operator;
        this.value = value;
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getSortField() {
        return sortField;
    }
    public void setSortField(String sortField) {
        this.sortField = sortField;
    }
    public String getSortOrder() {
        return sortOrder;
    }
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}

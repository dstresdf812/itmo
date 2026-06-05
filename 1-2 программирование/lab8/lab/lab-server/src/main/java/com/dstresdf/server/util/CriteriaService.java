package com.dstresdf.server.util;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.util.StudyGroupCriteria;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class CriteriaService {
    public static List<StudyGroup> applyCriteria(List<StudyGroup> groupList, StudyGroupCriteria criteria) {
        Stream<StudyGroup> stream = groupList.stream();

        if (hasText(criteria.getField()) && hasText(criteria.getValue())) {
            stream = stream.filter(group -> matchesCriteria(group, criteria));
        }

        if (hasText(criteria.getSortField())) {
            Comparator<StudyGroup> comparator = Comparator.comparing(
                    group -> getFieldValue(group, criteria.getSortField()),
                    Comparator.nullsLast(Comparator.comparingDouble(Number::doubleValue))
            );
            if ("desc".equalsIgnoreCase(criteria.getSortOrder())) {
                comparator = comparator.reversed();
            }
            stream = stream.sorted(comparator);
        }

        return stream.toList();
    }

    private static boolean matchesCriteria(StudyGroup group, StudyGroupCriteria criteria) {
        Number fieldValue = getFieldValue(group, criteria.getField());
        if (fieldValue == null) {
            return false;
        }
        String operator = criteria.getOperator();
        String value  = criteria.getValue();
        return compare(fieldValue, operator, value);
    }

    private static boolean compare(Number fieldValue, String operator, Object value) {
        if (operator == null || value == null) {
            return false;
        }
        Double leftFieldValue = fieldValue.doubleValue();
        Double rightFieldValue;
        try {
            rightFieldValue = Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            return false;
        }

        if (operator.equals("=") || operator.equals("==")) {
            return leftFieldValue.equals(rightFieldValue);
        }
        if (operator.equals("!=")) {
            return !leftFieldValue.equals(rightFieldValue);
        }
        if (operator.equals(">")) {
            return leftFieldValue > rightFieldValue;
        }
        if (operator.equals("<")) {
            return leftFieldValue < rightFieldValue;
        }
        if (operator.equals(">=")) {
            return leftFieldValue >= rightFieldValue;
        }
        if (operator.equals("<=")) {
            return leftFieldValue <= rightFieldValue;
        }
        return false;
    }
    private static Number getFieldValue(StudyGroup group, String fieldName) {
        if (group == null || fieldName == null) {
            return null;
        }
        if (fieldName.equals("id")) {
            return group.getId();
        }
        if (fieldName.equals("x")) {
            return group.getCoordinates() == null ? null : group.getCoordinates().getX();
        }
        if (fieldName.equals("y")) {
            return group.getCoordinates() == null ? null : group.getCoordinates().getY();
        }
        if (fieldName.equals("studentsCount")) {
            return group.getStudentsCount();
        }
        if (fieldName.equals("expelledStudents")) {
            return group.getExpelledStudents();
        }
        if (fieldName.equals("shouldBeExpelled")) {
            return group.getShouldBeExpelled();
        }
        if (fieldName.equals("groupAdmin.weight")) {
            return group.getGroupAdmin() == null ? null : group.getGroupAdmin().getWeight();
        }
        if (fieldName.equals("price")) {
            return group.getPrice();
        }
        return null;
    }

    private static boolean hasText(String value) {
        return value != null && !value.isEmpty();
    }
}

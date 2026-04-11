package com.dstresdf.server.util;

import com.dstresdf.common.model.StudyGroup;

import java.util.Comparator;

public class StudyGroupComparator implements Comparator<StudyGroup> {
    public int compare(StudyGroup o1, StudyGroup o2) {
        return o1.getStudentsCount() - o2.getStudentsCount();
    }
}

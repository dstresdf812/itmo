package com.dstresdf.server.db;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudyGroupService {
    private StudyGroupRepository studyGroupRepository;

    public StudyGroupService(StudyGroupRepository studyGroupRepository) {
        this.studyGroupRepository = studyGroupRepository;
    }

    public List<StudyGroup> getCollection() throws SQLException {
        return studyGroupRepository.getCollection();
    };

    public int insertStudyGroup(StudyGroup studyGroup) throws SQLException {
        return studyGroupRepository.insertStudyGroup(studyGroup);
    }

    public boolean updateStudyGroup(StudyGroup studyGroup) throws SQLException {
        return  studyGroupRepository.updateStudyGroup(studyGroup);
    }

    public boolean removeStudyGroup(int key, String ownerLogin) throws SQLException {
        return  studyGroupRepository.removeStudyGroup(key, ownerLogin);
    }
}

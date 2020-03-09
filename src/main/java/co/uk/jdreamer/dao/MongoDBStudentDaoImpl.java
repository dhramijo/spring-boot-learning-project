package co.uk.jdreamer.dao;

import co.uk.jdreamer.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("mongoDbDao")
public class MongoDBStudentDaoImpl implements StudentDao {
    @Override
    public int insertNewStudent(UUID studentId, Student student) {
        return 0;
    }

    @Override
    public Student selectStudentById(UUID studentId) {
        return null;
    }

    @Override
    public List<Student> selectAllStudents() {
        return null;
    }

    @Override
    public int updateStudentById(UUID studentId, Student studentUpdate) {
        return 0;
    }

    @Override
    public int deleteStudentById(UUID studentId) {
        return 0;
    }
}

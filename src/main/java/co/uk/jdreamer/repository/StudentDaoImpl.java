package co.uk.jdreamer.repository;

import co.uk.jdreamer.model.Student;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("studentDao")
public class StudentDaoImpl implements StudentDao {

    private final Map<UUID, Student> database;

    public StudentDaoImpl() {
        database = new HashMap<UUID, Student>();
        UUID studentId = UUID.randomUUID();
        database.put(
                studentId,
                new Student(studentId,22,"Jo","Dreamer", "Computer Science")
        );
    }

    @Override
    public List<Student> selectAllStudents() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Student selectStudentById(UUID studentId) {
        return database.get(studentId);
    }

    @Override
    public int insertNewStudent(UUID studentId, Student student) {
        database.put(studentId,student);
        return 1;
    }

    @Override
    public int updateStudentById(UUID studentId, Student studentUpdate) {
        database.put(studentId,studentUpdate);
        return 1;
    }

    @Override
    public int deleteStudentById(UUID studentId) {
        database.remove(studentId);
        return 1;
    }
}

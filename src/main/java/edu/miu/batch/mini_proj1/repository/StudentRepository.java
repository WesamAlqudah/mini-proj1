package edu.miu.batch.mini_proj1.repository;

import edu.miu.batch.mini_proj1.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
}

package edu.miu.batch.mini_proj1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

public class StudentItemProcessor implements ItemProcessor<Student, Student> {

    private static final Logger log = LoggerFactory.getLogger(StudentItemProcessor.class);

    @Override
    public Student process(Student student) throws Exception {
        final int age = student.getAge();
        int currentYear = LocalDate.now().getYear();
        final int yearBorn = currentYear - age;
        final LocalDate dob = LocalDate.of(yearBorn, 1, 1);
        final Student transformedStudent = new Student(student.getFirstname(), student.getLastname(), student.getGpa(), dob, student.getAge());

//        final Student transformedStudent = student;
//        transformedStudent.setDob(dob);

        log.info("Converting (" + student + ") into (" + transformedStudent + ")");

        return transformedStudent;
    }
}

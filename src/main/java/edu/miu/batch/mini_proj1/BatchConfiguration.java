package edu.miu.batch.mini_proj1;

import edu.miu.batch.mini_proj1.repository.StudentRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Lazy
    private StudentRepository studentRepository;

    @Bean
    public FlatFileItemReader<Student> reader(){
        return new FlatFileItemReaderBuilder<Student>()
                .name("studentItemReader")
                .resource(new ClassPathResource("student-data.csv"))
                .delimited()
                .names(new String[] {"firstname", "lastname", "gpa", "age"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Student>(){{
                    setTargetType(Student.class);
                }})
                .build();
    }

    @Bean
    public StudentItemProcessor processor(){
        return new StudentItemProcessor();
    }

    @Bean
    public RepositoryItemWriter<Student> writer(){
        RepositoryItemWriter<Student> writer = new RepositoryItemWriter<>();
        writer.setRepository(studentRepository);
        writer.setMethodName("save");
        return writer;
    }

//    @Bean
//    public JdbcBatchItemWriter<Student> writer(DataSource dataSource){
//        return new JdbcBatchItemWriterBuilder<Student>()
//                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//                .sql("INSERT INTO student (firstname, lastname, gpa, dob) VALUES (:firstname, :lastname, :gpa, :dob)")
//                .dataSource(dataSource)
//                .build();
//    }

    @Bean
    public Job readCSVFileJob(JobCompletionNotificationListener listener, Step step1){
        return jobBuilderFactory.get("readCSVFileJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(ItemReader<Student> reader, ItemWriter<Student> writer) throws Exception{
        return stepBuilderFactory.get("step1")
                .<Student, Student> chunk(10)
                .reader(reader)
                .processor(processor())
                .writer(writer)
                .build();
    }

//    @Bean
//    public DataSource dataSource(){
//        EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
//        return embeddedDatabaseBuilder
//                .build();
//    }


}

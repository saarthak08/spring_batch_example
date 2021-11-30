package com.example.sprintbatchexample.batch.config

import com.example.sprintbatchexample.batch.utils.PersonItemProcessor
import com.example.sprintbatchexample.batch.utils.PersonJobCompletionNotificationListener
import com.example.sprintbatchexample.model.Person
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.file.FlatFileHeaderCallback
import org.springframework.batch.item.file.FlatFileItemWriter
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor
import org.springframework.batch.item.file.transform.DelimitedLineAggregator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource
import org.springframework.jdbc.core.BeanPropertyRowMapper
import javax.sql.DataSource

@Configuration
@EnableBatchProcessing
class PersonBatchConfiguration(
  val jobBuilderFactory: JobBuilderFactory,
  val stepBuilderFactory: StepBuilderFactory
) {

  val outputResource = FileSystemResource("output/output.csv")

  // Reader reads data from database.
  @Bean fun reader(dataSource: DataSource): JdbcCursorItemReader<Person> {
    var itemReader = JdbcCursorItemReader<Person>()
    itemReader = itemReader.apply {
      sql = "SELECT * FROM PERSON"
      setRowMapper(BeanPropertyRowMapper<Person>(Person::class.java))
      setName("personJdbcCursorItemReader")
      setDataSource(dataSource)
    }
    return itemReader
  }

  // Processor processes the data
  @Bean fun processor(): PersonItemProcessor {
    return PersonItemProcessor()
  }

  @Bean(name = ["personJob"])
  fun importUserJob(listener: PersonJobCompletionNotificationListener, step1: Step): Job {
    return jobBuilderFactory["importUserJob"]
      .incrementer(RunIdIncrementer())
      .listener(listener)
      .flow(step1)
      .end()
      .build()
  }

  @Bean fun step1(reader: JdbcCursorItemReader<Person>): Step {
    return stepBuilderFactory["step1"]
      .chunk<Person, Person>(10)
      .reader(reader)
      .processor(processor())
      .writer(writer())
      .build()
  }

  // Writer writes the data to CSV file.
  @Bean fun writer(): FlatFileItemWriter<Person> {

    val writer: FlatFileItemWriter<Person> = FlatFileItemWriter<Person>()

    writer.setResource(outputResource)

    // writer.setAppendAllowed(true)

    writer.setHeaderCallback(
      FlatFileHeaderCallback {
        it.write("id,first_name,last_name")
      }
    )

    writer.setLineAggregator(object : DelimitedLineAggregator<Person>() {
      init {
        setDelimiter(",")
        setFieldExtractor(object : BeanWrapperFieldExtractor<Person>() {
          init {
            setNames(arrayOf("id", "firstName", "lastName"))
          }
        })
      }
    })
    return writer
  }
}

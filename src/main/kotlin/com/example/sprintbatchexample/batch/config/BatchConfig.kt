package com.example.sprintbatchexample.batch.config

import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.SimpleAsyncTaskExecutor

@Configuration
class BatchConfig(val jobRepository: JobRepository) {
  @Bean(name = ["myJobLauncher"]) @Throws(Exception::class) fun simpleJobLauncher(): JobLauncher {
    val jobLauncher = SimpleJobLauncher()
    jobLauncher.setJobRepository(jobRepository)
    jobLauncher.setTaskExecutor(SimpleAsyncTaskExecutor())
    jobLauncher.afterPropertiesSet()
    return jobLauncher
  }
}

package com.example.sprintbatchexample.batch.config

import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
class BatchConfig(val jobRepository: JobRepository) {

  @Bean fun threadPoolTaskExecutor(): TaskExecutor {
    val executor = ThreadPoolTaskExecutor()
    executor.maxPoolSize = 8
    executor.corePoolSize = 4
    executor.setQueueCapacity(100)
    return executor
  }

  @Bean(name = ["asyncJobLauncher"]) fun asyncJobLauncher(): JobLauncher {
    val jobLauncher = SimpleJobLauncher()
    jobLauncher.setJobRepository(jobRepository)
    jobLauncher.setTaskExecutor(threadPoolTaskExecutor())
    return jobLauncher
  }
}

package com.example.sprintbatchexample.batch.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.listener.JobExecutionListenerSupport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PersonJobCompletionNotificationListener @Autowired constructor() :
  JobExecutionListenerSupport() {

  private val log: Logger = LoggerFactory.getLogger(PersonJobCompletionNotificationListener::class.java)

  override fun afterJob(jobExecution: JobExecution) {
    if (jobExecution.status === BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED!")
    }
  }
}

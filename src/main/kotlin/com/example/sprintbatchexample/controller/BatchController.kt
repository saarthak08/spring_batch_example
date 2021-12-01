package com.example.sprintbatchexample.controller

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/batch")
@RestController
class BatchController(
  @Qualifier("asyncJobLauncher") val jobLauncher: JobLauncher,
  @Qualifier("personJob") val job: Job
) {

  @GetMapping("/exec")
  fun startJob(): ResponseEntity<String> {
    jobLauncher.run(job, JobParameters())
    return ResponseEntity("OK", HttpStatus.OK)
  }
}

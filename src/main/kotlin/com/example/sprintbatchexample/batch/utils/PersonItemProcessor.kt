package com.example.sprintbatchexample.batch.utils

import com.example.sprintbatchexample.model.Person
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor
import java.util.Locale

class PersonItemProcessor : ItemProcessor<Person, Person> {
  private val logger: Logger = LoggerFactory.getLogger(PersonItemProcessor::class.java)
  override fun process(p0: Person): Person? {
    val transformedPerson = p0.apply {
      firstName = p0.firstName.uppercase(Locale.getDefault())
      lastName = p0.lastName.uppercase(Locale.getDefault())
    }
    logger.info("Converting ($p0) into ($transformedPerson)")
    return transformedPerson
  }
}

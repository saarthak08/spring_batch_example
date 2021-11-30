package com.example.sprintbatchexample.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Person")
class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  val id: Long = 0

  @Column(name = "first_name")
  var firstName = ""

  @Column(name = "last_name")
  var lastName = ""

  override fun toString(): String {
    return "firstName: $firstName\nlastName: $lastName"
  }
}

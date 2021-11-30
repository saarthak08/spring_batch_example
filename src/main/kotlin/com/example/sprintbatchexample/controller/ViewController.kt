package com.example.sprintbatchexample.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class ViewController {

  @RequestMapping("/")
  fun index(): String {
    return "index"
  }
}

package com.developer.test.developertestnatan.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Can't find the route")
class LandPathNotFoundException(msg: String) : RuntimeException(msg)
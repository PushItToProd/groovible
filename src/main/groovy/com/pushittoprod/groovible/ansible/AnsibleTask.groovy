package com.pushittoprod.groovible.ansible

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class AnsibleTask {
    @JsonProperty("name")
    String name = null
    String module = null
    Map<String, Object> args = [:]
    List<String> handlers = []
}

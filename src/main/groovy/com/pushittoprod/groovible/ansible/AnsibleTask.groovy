package com.pushittoprod.groovible.ansible

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class AnsibleTask {
    @JsonProperty("name")
    String name = null

    @JsonIgnore
    String module = null

    @JsonIgnore
    Map<String, Object> args = [:]

    @JsonProperty("notify")
    List<String> handlers = []

    @JsonAnyGetter
    Map<String, Object> any() {
        [(module): args]
    }
}

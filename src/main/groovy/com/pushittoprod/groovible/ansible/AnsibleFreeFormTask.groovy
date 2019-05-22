package com.pushittoprod.groovible.ansible

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.Canonical
import groovy.transform.TupleConstructor

@Canonical
@TupleConstructor(includeSuperProperties = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class AnsibleFreeFormTask extends AnsibleTask {
    @JsonIgnore
    String freeformParameter

    @JsonAnyGetter
    @Override
    Map<String, Object> any() {
        def map = [(module): freeformParameter]
        if (args) {
            map.args = args
        }
        map
    }
}

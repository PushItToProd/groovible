package com.pushittoprod.groovible.ansible

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.Canonical
import groovy.transform.TupleConstructor

@Canonical
@TupleConstructor(includeSuperProperties = true)
class AnsibleFreeFormTask extends AnsibleTask {
    @JsonIgnore
    String freeformParameter
}

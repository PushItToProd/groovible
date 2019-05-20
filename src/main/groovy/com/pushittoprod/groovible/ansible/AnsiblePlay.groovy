package com.pushittoprod.groovible.ansible

import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.Canonical

@Canonical
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class AnsiblePlay {
    String name = null
    String hosts = null
    Map<String, Object> vars = [:]
    String remote_user = null
    List<AnsibleTask> tasks = []
    List<AnsibleTask> handlers = []
}

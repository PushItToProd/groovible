package com.pushittoprod.groovible.ansible

import groovy.transform.Canonical

@Canonical
class AnsiblePlay {
    String name = null
    String hosts = null
    Map<String, Object> vars = [:]
    String remote_user = null
    List<AnsibleTask> tasks = []
    List<AnsibleTask> handlers = []
}

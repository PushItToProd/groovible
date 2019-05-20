package com.pushittoprod.groovible.dsl

import com.pushittoprod.groovible.ansible.AnsibleTask

class AnsibleTaskBuilder {
    AnsibleTask task

    AnsibleTaskBuilder(AnsibleTask task) {
        this.task = task
    }

    def build(@DelegatesTo(value = AnsibleTaskBuilder, strategy = Closure.DELEGATE_ONLY) Closure cl) {
        with(cl)
    }

    def propertyMissing(String name) {
        task.args[name]
    }

    def propertyMissing(String name, value) {
        task.args[name] = value
    }

    def notify(String... name) {
        task.handlers.addAll(name)
    }
}

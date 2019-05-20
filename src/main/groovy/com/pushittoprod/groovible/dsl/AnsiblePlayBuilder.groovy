package com.pushittoprod.groovible.dsl

import com.pushittoprod.groovible.ansible.AnsiblePlay

class AnsiblePlayBuilder {
    @Delegate AnsiblePlay ansiblePlay

    AnsiblePlayBuilder(AnsiblePlay ansiblePlay) {
        this.ansiblePlay = ansiblePlay
    }

    def build(@DelegatesTo(AnsiblePlayBuilder) Closure cl) {
        with(cl)
    }

    void vars(@DelegatesTo(Map.class) Closure cl) {
        ansiblePlay.vars.with(cl)
    }

    void tasks(@DelegatesTo(DslTasksBlock) Closure cl) {
        new DslTasksBlock(ansiblePlay.tasks).build(cl)
    }

    void handlers(@DelegatesTo(DslTasksBlock) Closure cl) {
        new DslTasksBlock(ansiblePlay.handlers).build(cl)
    }
}

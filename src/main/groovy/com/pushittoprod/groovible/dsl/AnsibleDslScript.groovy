package com.pushittoprod.groovible.dsl

import com.pushittoprod.groovible.ansible.AnsiblePlaybook

abstract class AnsibleDslScript extends Script {
    AnsiblePlaybook playbook(
            @DelegatesTo(value = AnsiblePlaybookBuilder, strategy = Closure.DELEGATE_ONLY) Closure cl) {
        AnsiblePlaybookBuilder.playbook(cl)
    }
}

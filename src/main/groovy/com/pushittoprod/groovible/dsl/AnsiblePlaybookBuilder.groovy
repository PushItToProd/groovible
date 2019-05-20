package com.pushittoprod.groovible.dsl

import com.pushittoprod.groovible.ansible.AnsiblePlay
import com.pushittoprod.groovible.ansible.AnsiblePlaybook

class AnsiblePlaybookBuilder {
    AnsiblePlaybook ansiblePlaybook

    AnsiblePlaybookBuilder(AnsiblePlaybook ansiblePlaybook) {
        this.ansiblePlaybook = ansiblePlaybook
    }

    void build(@DelegatesTo(value = AnsiblePlaybookBuilder, strategy = Closure.DELEGATE_ONLY) Closure cl) {
        with(cl)
    }

    void play(String playName,
              @DelegatesTo(value = AnsiblePlayBuilder, strategy = Closure.DELEGATE_ONLY) Closure cl) {
        def play = new AnsiblePlay(name: playName)
        def builder = new AnsiblePlayBuilder(play)
        builder.build(cl)
        ansiblePlaybook.add(play)
    }

    void play(@DelegatesTo(value = AnsiblePlayBuilder, strategy = Closure.DELEGATE_ONLY) Closure cl) {
        play(null, cl)
    }

    static AnsiblePlaybook playbook(
            @DelegatesTo(value = AnsiblePlaybookBuilder, strategy = Closure.DELEGATE_ONLY) Closure cl) {
        def playbook = new AnsiblePlaybook()
        def builder = new AnsiblePlaybookBuilder(playbook)
        builder.build(cl)
        return playbook
    }
}

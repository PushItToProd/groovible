package com.pushittoprod.groovible.compiler

import com.pushittoprod.groovible.ansible.AnsiblePlay
import com.pushittoprod.groovible.ansible.AnsiblePlaybook
import com.pushittoprod.groovible.dsl.AnsiblePlayBuilder
import com.pushittoprod.groovible.serialization.Serialization

class DslCompiler {
    DslCompiler() {
    }

    def play = { playbook, name_, cl_ = null ->
        assert name_ != null
        String name = null
        Closure cl
        if (cl_ != null) {
            name = name_ as String
            cl = cl_ as Closure
        } else {
            cl = name_ as Closure
        }
        assert cl != null, "play requires a non-null closure argument"
        def play = new AnsiblePlay(name)
        new AnsiblePlayBuilder(play).build(cl)
        playbook.add(play)
    }

    AnsiblePlaybook compileToPlaybook(String groovy) {
        def binding = new Binding()
        def shell = new GroovyShell(binding)
        AnsiblePlaybook playbook = new AnsiblePlaybook()
        def play = play.curry(playbook)
        binding.setProperty('play', play)
        shell.evaluate(groovy)
        return playbook
    }

    String compile(String groovy) {
        def playbook = compileToPlaybook(groovy)
        def string = Serialization.serializeYaml(playbook)
        return string
    }
}

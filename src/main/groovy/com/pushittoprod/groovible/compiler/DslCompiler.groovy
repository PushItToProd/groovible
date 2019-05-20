package com.pushittoprod.groovible.compiler

import com.pushittoprod.groovible.ansible.AnsiblePlaybook
import com.pushittoprod.groovible.dsl.AnsiblePlaybookBuilder
import com.pushittoprod.groovible.serialization.Serialization

class DslCompiler {
    DslCompiler() {
    }

    AnsiblePlaybook compileToPlaybook(String groovy) {
        def binding = new Binding()
        def shell = new GroovyShell(binding)
        def playbookCl = { Closure cl ->
            AnsiblePlaybookBuilder.playbook(cl)
        }
        binding.setProperty('playbook', playbookCl)
        AnsiblePlaybook result = shell.evaluate(groovy) as AnsiblePlaybook
        return result
    }

    String compile(String groovy) {
        def playbook = compileToPlaybook(groovy)
        def string = Serialization.serializeYaml(playbook)
        return string
    }
}

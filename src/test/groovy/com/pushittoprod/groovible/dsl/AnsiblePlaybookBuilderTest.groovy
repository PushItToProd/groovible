package com.pushittoprod.groovible.dsl

import com.pushittoprod.groovible.ansible.AnsiblePlaybook

class AnsiblePlaybookBuilderTest extends GroovyTestCase {
    void testPlaybookAddsNamedPlays() {
        def playbook = new AnsiblePlaybook()
        def builder = new AnsiblePlaybookBuilder(playbook)
        builder.build {
            play("example play") {}
        }
        assert playbook.size() == 1
        assert playbook[0].name == "example play"
    }

    void testPlaybookAddsUnnamedPlays() {
        def playbook = new AnsiblePlaybook()
        def builder = new AnsiblePlaybookBuilder(playbook)
        builder.build {
            play {}
        }
        assert playbook.size() == 1
        assert playbook[0].name == null
    }
}

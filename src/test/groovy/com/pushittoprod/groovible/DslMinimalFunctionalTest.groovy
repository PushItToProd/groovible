package com.pushittoprod.groovible
import org.junit.Test

import static com.pushittoprod.groovible.AnsibleDsl.*


class DslMinimalFunctionalTest {
    def createPlaybook = {
        playbook {
            play {
                hosts = "hosts"
                vars {
                    foo = 'bar'
                }

                remote_user = "root"
            }
        }
    }

    def expected_yml = '''
        ---
        - hosts: "hosts"
          vars:
            foo: "bar"
    '''.stripIndent().trim()

    @Test void playbookCreatesAPlay() {
        AnsiblePlaybook playbook = createPlaybook()
        assert playbook.plays.size() == 1
    }

    @Test void playbookSetsHosts() {
        AnsiblePlaybook playbook = createPlaybook()
        assert playbook.plays[0].hosts == "hosts"
    }

    @Test void playbookSetsVars() {
        AnsiblePlaybook playbook = createPlaybook()
        assert playbook.plays[0].vars.foo == "bar"
    }
}

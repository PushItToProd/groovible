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

                tasks {
                    yum("ensure apache is at the latest version") {
                        name = "httpd"
                        state = "latest"

                        notify "example handler"
                    }
                }
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

    @Test void createsTask() {
        AnsiblePlaybook playbook = createPlaybook()
        assert playbook.plays[0].tasks.size() == 1
    }

    @Test void setsNotify() {
        AnsiblePlaybook playbook = createPlaybook()
        assert playbook.plays[0].tasks[0].handlers == ["example handler"]
    }
}

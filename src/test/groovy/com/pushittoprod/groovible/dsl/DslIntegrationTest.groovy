package com.pushittoprod.groovible.dsl

import com.pushittoprod.groovible.ansible.AnsiblePlaybook

class DslIntegrationTest extends GroovyTestCase {
    def createPlaybook = {
        def playbook = new AnsiblePlaybook()
        def builder = new AnsiblePlaybookBuilder(playbook)
        builder.build {
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

                handlers {
                    service("example handler") {
                        name = "httpd"
                        state = "restarted"
                    }
                }
            }
        }
        playbook
    }

    void testPlaybookCreatesAPlay() {
        AnsiblePlaybook playbook = createPlaybook()
        assert playbook.size() == 1
    }

    void testCreatesTask() {
        AnsiblePlaybook playbook = createPlaybook()
        assert playbook[0].tasks.size() == 1
    }

    void testSetsNotify() {
        AnsiblePlaybook playbook = createPlaybook()
        assert playbook[0].tasks[0].handlers == ["example handler"]
    }

    void testCreatesHandler() {
        AnsiblePlaybook playbook = createPlaybook()
        assert playbook[0].handlers.size() == 1
        assert playbook[0].handlers[0].name == "example handler"
    }
}

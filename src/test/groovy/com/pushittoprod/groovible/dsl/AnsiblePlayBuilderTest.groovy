package com.pushittoprod.groovible.dsl

import com.pushittoprod.groovible.ansible.AnsiblePlay

class AnsiblePlayBuilderTest extends GroovyTestCase {
    void testApply() {
        def ansiblePlay = new AnsiblePlay()
        def builder = new AnsiblePlayBuilder(ansiblePlay)
        builder.build {
            hosts = "hosts"
            vars {
                foo = 1
            }
        }
        assertEquals("hosts", ansiblePlay.hosts)
        assertEquals([foo: 1], ansiblePlay.vars)
    }

    void testTasksGetCreated() {
        def ansiblePlay = new AnsiblePlay()
        def builder = new AnsiblePlayBuilder(ansiblePlay)
        builder.build {
            tasks {
                service("example service") {}
            }
        }
        assertEquals("example service", ansiblePlay.tasks[0].name)
        assertEquals(1, ansiblePlay.tasks.size())
    }

    void testHandlersGetCreated() {
        def ansiblePlay = new AnsiblePlay()
        def builder = new AnsiblePlayBuilder(ansiblePlay)
        builder.build {
            handlers {
                service("example handler") {}
            }
        }
        assertEquals("example handler", ansiblePlay.handlers[0].name)
        assertEquals(1, ansiblePlay.handlers.size())
    }
}

package com.pushittoprod.groovible

import com.pushittoprod.groovible.ansible.AnsiblePlay
import com.pushittoprod.groovible.dsl.AnsiblePlayBuilder

class AnsiblePlayTest extends GroovyTestCase {
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

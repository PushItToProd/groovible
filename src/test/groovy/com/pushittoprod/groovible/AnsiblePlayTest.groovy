package com.pushittoprod.groovible

class AnsiblePlayTest extends GroovyTestCase {
    void testApply() {
        AnsiblePlay ansiblePlay = new AnsiblePlay()
        ansiblePlay.apply {
            hosts = "hosts"
            vars {
                foo = 1
            }
        }
        assertEquals("hosts", ansiblePlay.hosts)
        assertEquals([foo: 1], ansiblePlay.vars)
    }
}

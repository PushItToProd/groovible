package com.pushittoprod.groovible

class AnsiblePlayTest extends GroovyTestCase {
    void testVars() {
        AnsiblePlay ansiblePlay = new AnsiblePlay()
        ansiblePlay.vars {
            foo = 1
        }
        assertEquals([foo: 1], ansiblePlay.vars)
    }
}

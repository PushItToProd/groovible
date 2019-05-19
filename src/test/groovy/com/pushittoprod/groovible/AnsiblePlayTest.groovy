package com.pushittoprod.groovible

class AnsiblePlayTest extends GroovyTestCase {
    void testVars() {
        AnsiblePlay ansiblePlay = new AnsiblePlay()
        ansiblePlay.apply {
            vars {
                foo = 1
            }
        }
        assertEquals([foo: 1], ansiblePlay.vars)
    }
}

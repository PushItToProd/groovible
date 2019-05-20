package com.pushittoprod.groovible

import com.pushittoprod.groovible.dsl.AnsibleTaskBuilder

class AnsibleTaskTest extends GroovyTestCase {
    void testNotify() {
        def task = new com.pushittoprod.groovible.ansible.AnsibleTask()
        def builder = new AnsibleTaskBuilder(task)
        builder.build {
            notify 'example handler'
        }
        assert task.handlers == ['example handler']
    }
}

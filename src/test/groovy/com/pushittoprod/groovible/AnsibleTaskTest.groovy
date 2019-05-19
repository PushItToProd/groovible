package com.pushittoprod.groovible

class AnsibleTaskTest extends GroovyTestCase {
    void testNotify() {
        def task = new AnsibleTask()
        task.apply {
            notify 'example handler'
        }
        assert task.handlers == ['example handler']
    }
}

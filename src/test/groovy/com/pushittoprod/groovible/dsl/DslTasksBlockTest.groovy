package com.pushittoprod.groovible.dsl

import com.pushittoprod.groovible.ansible.AnsibleTask

class DslTasksBlockTest extends GroovyTestCase {
    void testNamedTasksGetCreated() {
        List<AnsibleTask> tasks = []
        new DslTasksBlock(tasks).build {
            yum("example with name") {
                name = "httpd"
                state = "latest"
            }
        }
        assert tasks.size() == 1
        def task = tasks[0]
        assert task.module == "yum"
        assert task.name == "example with name"
        assert task.args.name == "httpd"
        assert task.args.state == "latest"
    }

    void testUnnamedTasksGetCreated() {
        List<AnsibleTask> tasks = []
        new DslTasksBlock(tasks).build {
            yum {
                name = "httpd"
                state = "latest"
            }
        }
        assert tasks.size() == 1
        def task = tasks[0]
        assert task.module == "yum"
        assert task.name == null
        assert task.args.name == "httpd"
        assert task.args.state == "latest"
    }
}

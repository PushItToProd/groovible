package com.pushittoprod.groovible.dsl

import com.pushittoprod.groovible.ansible.AnsibleTask

class AnsibleTaskBuilderTest extends GroovyTestCase {
    void testBuilderBasicArgs() {
        def task = new AnsibleTask()
        def builder = new AnsibleTaskBuilder(task)
        builder.build {
            name = "httpd"
            state = "latest"
        }

        assert task.args.name == "httpd"
        assert task.args.state == "latest"
    }

    void testBuilderWithNotify() {
        def task = new AnsibleTask()
        def builder = new AnsibleTaskBuilder(task)
        builder.build {
            name = "httpd"
            state = "latest"

            notify "restart apache"
        }

        assert task.args.name == "httpd"
        assert task.args.state == "latest"

        assert task.handlers == ["restart apache"]
    }

    void testBuilderWithNameAndModule() {
        def task = new AnsibleTask(name: "ensure apache is at the latest version", module: "yum")
        def builder = new AnsibleTaskBuilder(task)
        builder.build {
            name = "httpd"
            state = "latest"
        }

        assert task.name == "ensure apache is at the latest version"
        assert task.module == "yum"
    }
}

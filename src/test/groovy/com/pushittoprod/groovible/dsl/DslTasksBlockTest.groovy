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

    def testShellBlockArgs() {
        List<AnsibleTask> tasks = []
        new DslTasksBlock(tasks).build {
            shell('example shell command') {
                shell = "somescript.sh >> somelog.txt"
                chdir = "somedir/"
                creates = "somelog.txt"
                executable = "/bin/bash"
            }
        }
        assert tasks.size() == 1
        assert task == tasks[0]
        assert task.module == "shell"
        assert task.name == "example shell command"
        assert task.args.shell == "somescript.sh >> somelog.txt"
        assert task.args.chdir == "somedir/"
        assert task.args.creates == "somelog.txt"
        assert task.args.executable == "/bin/bash"
    }
}

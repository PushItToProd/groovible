package com.pushittoprod.groovible

import com.pushittoprod.groovible.ansible.AnsibleTask
import com.pushittoprod.groovible.dsl.DslTasksBlock

import org.junit.Test

class DslTasksBlockTest {
    @Test void tasksGetCreatedInBlock() {
        List<AnsibleTask> tasks = []
        new DslTasksBlock(tasks).build {
            foo_module("task name") {
                bar = 1
                baz = "hello"
            }
        }
        assert tasks.size() == 1
        def task = tasks[0]
        assert task.module == "foo_module"
        assert task.name == "task name"
        assert task.args.bar == 1
        assert task.args.baz == "hello"
    }

    @Test void tasksGetCreatedByMissingMethod() {
        List<AnsibleTask> tasks = []
        new DslTasksBlock(tasks).build {
            foo_module {}
        }
        assert tasks.size() == 1
        assert tasks[0].module == "foo_module"
    }

    @Test void notifyWorks() {
        List<AnsibleTask> tasks = []
        new DslTasksBlock(tasks).build {
            yum {
                notify "example handler"
            }
        }
        assert tasks[0].handlers == ["example handler"]
    }

    @Test void unnamedTasksWork() {
        List<AnsibleTask> tasks = []
        new DslTasksBlock(tasks).build {
            foo_module {
                bar = 1
            }
        }
        assert tasks.size() == 1
        def task = tasks[0]
        assert task.module == "foo_module"
        assert task.name == null
        assert task.args.bar == 1
    }
}

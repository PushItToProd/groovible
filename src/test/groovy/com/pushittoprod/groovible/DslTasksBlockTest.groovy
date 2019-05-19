package com.pushittoprod.groovible

import org.junit.Ignore
import org.junit.Test

class DslTasksBlockTest {
    @Test void tasksGetCreatedInBlock() {
        List<AnsibleTask> tasks = []
        new DslTasksBlock(tasks).apply {
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
        new DslTasksBlock(tasks).apply {
            foo_module(foo: 1)
        }
        assert tasks.size() == 1
        assert tasks[0].module == "foo_module"
    }

    @Test void tasksGetCreatedByAddTask() {
        List<AnsibleTask> tasks = []
        new DslTasksBlock(tasks).addTask("debug", [msg: "hello"])
        assert tasks[0].module == "debug"
        assert tasks[0].args == [msg: "hello"]
    }

    @Test void namedTasksGetCreatedByAddTask() {
        List<AnsibleTask> tasks = []
        new DslTasksBlock(tasks).addTask( "print debug", "debug", [msg: "hello"])
        assert tasks[0].name == "print debug"
    }
}

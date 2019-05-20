package com.pushittoprod.groovible.dsl

import com.pushittoprod.groovible.ansible.AnsibleTask

class DslTasksBlock {
    List<AnsibleTask> tasks

    DslTasksBlock(List<AnsibleTask> tasks) {
        this.tasks = tasks
    }

    void build(@DelegatesTo(DslTasksBlock) Closure cl) {
        with(cl)
    }

    def methodMissing(String name, def args) {
        String moduleName = name
        String taskName = null
        Closure closure
        def argsList = args as Object[]
        switch (argsList.size()) {
            case 1:
                // signature 1: $moduleName(Closure body)
                closure = argsList[0] as Closure
                break
            case 2:
                // signature 2: $moduleName(String name, Closure body)
                taskName = argsList[0] as String
                closure = argsList[1] as Closure
                break
            default:
                throw new MissingMethodException(name, DslTasksBlock.class, args)
        }

        def task = new AnsibleTask(name: taskName, module: moduleName)
        def builder = new AnsibleTaskBuilder(task)
        builder.build(closure)

        tasks.add(task)
    }
}

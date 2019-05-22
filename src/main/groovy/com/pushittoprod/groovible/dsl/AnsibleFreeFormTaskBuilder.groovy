package com.pushittoprod.groovible.dsl

import com.pushittoprod.groovible.ansible.AnsibleFreeFormTask

class AnsibleFreeFormTaskBuilder extends AnsibleTaskBuilder {
    AnsibleFreeFormTaskBuilder(AnsibleFreeFormTask task) {
        super(task)
    }

    @Override
    def build(@DelegatesTo(value = AnsibleTaskBuilder, strategy = Closure.DELEGATE_ONLY) Closure cl) {
        super.build(cl)
        AnsibleFreeFormTask task = this.task
        task.freeformParameter = task.args[task.module]
        task.args.remove(task.module)
    }
}

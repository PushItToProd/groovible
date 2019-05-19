package com.pushittoprod.groovible

class AnsibleDsl {
    static AnsiblePlaybook playbook(Closure cl) {
        def playbook = new AnsiblePlaybook()
        playbook.apply(cl)
        return playbook
    }
}

class AnsiblePlaybook implements Applicable {
    List<AnsiblePlay> plays = []

    void play(Closure f) {
        def play = new AnsiblePlay()
        play.apply(f)
        plays.add(play)
    }
}

class AnsiblePlay implements Applicable {
    String hosts = null
    Map<String, Object> vars = [:]
    String remote_user = null
    List<AnsibleTask> tasks = []
    List<AnsibleTask> handlers = []

    def vars(Closure f) {
        // TODO: refactor to use apply()
        def code = f.rehydrate(vars, this, this)
        code.resolveStrategy = Closure.DELEGATE_FIRST
        code()
    }

    def tasks(Closure f) {
        // TODO: refactor to use apply()
        DslTasksBlock tasksBlock = new DslTasksBlock(tasks)
        def code = f.rehydrate(tasksBlock, this, this)
        code.resolveStrategy = Closure.DELEGATE_FIRST
        code()
    }

    void handlers(Closure f) {
        DslTasksBlock tasksBlock = new DslTasksBlock(handlers)
        tasksBlock.apply(f)
    }
}

class DslTasksBlock implements Applicable {
    // TODO: support handlers(), notify(), etc.
    List<AnsibleTask> tasks

    DslTasksBlock(List<AnsibleTask> tasks) {
        this.tasks = tasks
    }

    // methodMissing dynamically dispatches undefined methods to Ansible task declarations
    def methodMissing(String name, args) {
        // TODO: validate Ansible module names and args
        assert args instanceof Object[]
        // check whether the task has a name
        String taskName = null
        String moduleName = name
        def argsObject

        switch (args[0]) {  // TODO: switch based on number of arguments
            case String:
                taskName = args[0]
                argsObject = args[1]
                break
            case Closure:
            case Map:
                argsObject = args[0]
                break
            default:
                throw new MissingMethodException(name, this.class, args)
        }

        switch (argsObject) {
            case Closure:
                tasks.add(new AnsibleTask(taskName, moduleName, argsObject as Closure))
                break
            case Map:
                tasks.add(new AnsibleTask(taskName, moduleName, argsObject as Map))
                break
            default:
                throw new MissingMethodException(name, this.class, args)
        }
    }
}

class AnsibleTask implements Applicable {
    String taskName = null
    String module
    Map<String, Object> args = [:]
    List<String> handlers = []

    AnsibleTask() {}

    AnsibleTask(String taskName, String module, Map<String, Object> args) {
        this.taskName = taskName
        this.module = module
        this.args = args
    }

    AnsibleTask(String taskName, String module, Closure cl) {
        this.taskName = taskName
        this.module = module
        this.apply(cl)
    }

    def propertyMissing(String name) {
        args[name]
    }

    def propertyMissing(String name, value) {
        args[name] = value
    }

    def notify(String... handlers) {
        this.handlers.addAll(handlers)
    }
}


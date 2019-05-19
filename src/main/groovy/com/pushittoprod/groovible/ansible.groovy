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

    def play(Closure f) {
        throw new Exception('TODO')
    }
}

class AnsiblePlay {
    String hosts = null
    Map<String, Object> vars = [:]
    String remote_user = null
    List<AnsibleTask> tasks = []
    List<AnsibleTask> handlers = []

    def vars(Closure f) {
        def code = f.rehydrate(vars, this, this)
        code.resolveStrategy = Closure.DELEGATE_FIRST
        code()
    }

    def tasks(Closure f) {
        DslTasksBlock tasksBlock = new DslTasksBlock(tasks)
        def code = f.rehydrate(tasksBlock, this, this)
        code.resolveStrategy = Closure.DELEGATE_FIRST
        code()
    }
}

class DslTasksBlock {
    // TODO: support handlers(), notify(), etc.
    List<AnsibleTask> tasks

    DslTasksBlock(List<AnsibleTask> tasks) {
        this.tasks = tasks
    }

    def addTask(String taskName, String moduleName, Map args) {
        AnsibleTask task = new AnsibleTask(name: taskName, module: moduleName, args: args)
        tasks.add(task)
    }

    def addTask(String moduleName, Map args) {
        addTask(null, moduleName, args)
    }

    def addTask(String moduleName, Closure cl) {
        addTask(null, moduleName, cl)
    }

    def addTask(String taskName, String moduleName, Closure cl) {
        Map args = [:]
        Metaprogramming.apply(args, cl)
        addTask(taskName, moduleName, args)
    }

    // methodMissing dynamically dispatches undefined methods to Ansible task declarations
    def methodMissing(String name, args) {
        // TODO: validate Ansible module names and args
        assert args instanceof Object[]
        // check whether the task has a name
        String taskName = null
        String mapName = name
        def argsObject = args[0]

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
                return addTask(taskName, name, argsObject as Closure)
                break
            case Map:
                return addTask(taskName, name, argsObject as Map)
                break
            default:
                throw new MissingMethodException(name, this.class, args)
        }
    }

    def apply(Closure f) {
        def code = f.rehydrate(this, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        return tasks
    }
}

class AnsibleTask {
    String name
    String module
    Map<String, Object> args
}


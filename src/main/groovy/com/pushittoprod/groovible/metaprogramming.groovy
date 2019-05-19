package com.pushittoprod.groovible

class Metaprogramming {
    static apply(obj, Closure cl, int resolveStrategy = Closure.DELEGATE_ONLY) {
        def code = cl.rehydrate(obj, this, this)
        code.resolveStrategy = resolveStrategy
        return code()
    }
}

trait Applicable {
   def apply(Closure cl, int resolveStrategy = Closure.DELEGATE_ONLY) {
       return Metaprogramming.apply(this, cl, resolveStrategy)
   }
}
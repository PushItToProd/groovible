package com.pushittoprod.groovible
import static com.pushittoprod.groovible.Metaprogramming.*

import org.junit.Test


class TestMetaprogramming {
    class A {
        def val
    }

    class MixedIn extends A implements Applicable {
    }

    @Test void applyWorks() {
        def a = new A()
        apply(a) {
            val = 9999
        }
        assert a.val == 9999
    }

    @Test void applicableWorks() {
        def a = new MixedIn()
        a.apply {
            val = 9999
        }
        assert a.val == 9999
    }
}

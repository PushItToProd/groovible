package com.pushittoprod.groovible

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.junit.Test

@ToString @EqualsAndHashCode
class A {
    int foo
}

class TestSerialization {
    @Test void serializationWorks() {
        def a = new A(foo: 1)
        def serialized = Serialization.serializeYaml(a)
        def deserialized = Serialization.deserializeYaml(serialized, A.class)
        assert a == deserialized
        assert a.foo == deserialized.foo
    }
}


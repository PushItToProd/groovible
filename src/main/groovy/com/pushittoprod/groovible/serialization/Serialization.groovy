package com.pushittoprod.groovible.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.pushittoprod.groovible.ansible.AnsibleTask

class Serialization {
    static ObjectMapper mapper = getMapper()

    static ObjectMapper getMapper() {
        def module = new SimpleModule()
        module.addSerializer(AnsibleTask.class, new AnsibleTaskSerializer())

        def yf = new YAMLFactory()
        yf.configure(YAMLGenerator.Feature.MINIMIZE_QUOTES, true)

        def mapper = new ObjectMapper(yf)
        mapper.registerModule(module)
        return mapper
    }

    static String serializeYaml(obj) {
        return mapper.writeValueAsString(obj)
    }

    static <T> T deserializeYaml(String yaml, Class<T> type) {
        return mapper.readValue(yaml, type)
    }
}

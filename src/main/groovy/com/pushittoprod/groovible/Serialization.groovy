package com.pushittoprod.groovible

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

class Serialization {
    static ObjectMapper mapper = getMapper()

    static ObjectMapper getMapper() {
        return new ObjectMapper(new YAMLFactory())
    }

    static String serializeYaml(obj) {
        return mapper.writeValueAsString(obj)
    }

    static <T> T deserializeYaml(String yaml, Class<T> type) {
        return mapper.readValue(yaml, type)
    }
}

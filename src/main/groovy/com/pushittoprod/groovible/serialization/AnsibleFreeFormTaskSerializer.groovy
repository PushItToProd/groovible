package com.pushittoprod.groovible.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.pushittoprod.groovible.ansible.AnsibleFreeFormTask

class AnsibleFreeFormTaskSerializer extends StdSerializer<AnsibleFreeFormTask> {
    AnsibleFreeFormTaskSerializer() {
        this(null)
    }

    AnsibleFreeFormTaskSerializer(Class<AnsibleFreeFormTask> t) {
        super(t)
    }

    @Override
    void serialize(AnsibleFreeFormTask value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject()

        // task name
        if (value.name != null) {
            gen.writeStringField("name", value.name)
        }

        // freeform field
        gen.writeStringField(value.module, value.freeformParameter)

        if (value.args) {
            // module and params
            gen.writeObjectField("args", value.args)
        }

        // handlers
        if (value.handlers != null && value.handlers.size() > 0) {
            gen.writeArrayFieldStart("notify")

            for (handler in value.handlers) {
                gen.writeString(handler)
            }

            gen.writeEndArray()
        }

        gen.writeEndObject()
    }
}

package com.pushittoprod.groovible.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.pushittoprod.groovible.ansible.AnsibleTask

class AnsibleTaskSerializer extends StdSerializer<AnsibleTask> {
    AnsibleTaskSerializer() {
        this(null)
    }

    AnsibleTaskSerializer(Class<AnsibleTask> t) {
        super(t)
    }

    @Override
    void serialize(AnsibleTask value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        // TODO: I feel like there has to be a cleaner way to do this
        gen.writeStartObject()

        // task name
        if (value.name != null) {
            gen.writeStringField("name", value.name)
        }

        // module and params
        gen.writeObjectField(value.module, value.args)

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

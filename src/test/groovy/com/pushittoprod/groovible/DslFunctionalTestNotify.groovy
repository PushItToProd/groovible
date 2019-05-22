package com.pushittoprod.groovible

import com.pushittoprod.groovible.serialization.Serialization

import static com.pushittoprod.groovible.dsl.AnsiblePlaybookBuilder.playbook

import org.junit.Test

// created to check issue where notify isn't being rendered in the output
class DslFunctionalTestNotify {
    def input = playbook {
        play {
            tasks {
                template {
                    src = "/srv/httpd.j2"
                    dest = "/etc/httpd.conf"

                    notify "restart apache"
                }
            }
        }
    }

    def expected_output = '''
    ---
    - tasks:
      - template:
          src: /srv/httpd.j2
          dest: /etc/httpd.conf
        notify:
        - restart apache
    '''.stripIndent().trim()

    @Test void testExample() {
        def output = Serialization.serializeYaml(input).stripIndent().trim()
        def outputMap = Serialization.deserializeYaml(output, ArrayList.class)
        def expectedOutputMap = Serialization.deserializeYaml(expected_output, ArrayList.class)
        assert expectedOutputMap == outputMap
    }
}


class DslFunctionalTestNotifyEmpty {
    def input = playbook {
        play {
            tasks {
                template {
                    src = "/srv/httpd.j2"
                    dest = "/etc/httpd.conf"
                }
            }
        }
    }

    def expected_output = '''
    ---
    - tasks:
      - template:
          src: /srv/httpd.j2
          dest: /etc/httpd.conf
    '''.stripIndent().trim()

    @Test void testExample() {
        def output = Serialization.serializeYaml(input).stripIndent().trim()
        def outputMap = Serialization.deserializeYaml(output, ArrayList.class)
        def expectedOutputMap = Serialization.deserializeYaml(expected_output, ArrayList.class)
        assert expectedOutputMap == outputMap
    }
}

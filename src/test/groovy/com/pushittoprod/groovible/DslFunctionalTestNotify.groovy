package com.pushittoprod.groovible

import static com.pushittoprod.groovible.AnsibleDsl.*

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
        def output = input.compile().stripIndent().trim()
        assert expected_output == output
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
        def output = input.compile().stripIndent().trim()
        assert expected_output == output
    }
}

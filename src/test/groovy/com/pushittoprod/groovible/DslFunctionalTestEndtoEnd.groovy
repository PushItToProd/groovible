package com.pushittoprod.groovible

import org.junit.Ignore
import org.junit.Test

import static com.pushittoprod.groovible.AnsibleDsl.*

// functional test for overall dsl behavior
class DslFunctionalTestEndToEnd {
    def input =
            playbook {
                // a play with no name
                play {
                    hosts = "webservers"
                    vars {
                        http_port = 80
                        max_clients = 200
                    }
                    remote_user = "root"

                    tasks {
                        yum("ensure apache is at the latest version") {
                            name = "httpd"
                            state = "latest"
                        }

                        template("write the apache config file") {
                            src = "/srv/httpd.j2"
                            dest = "/etc/httpd.conf"

                            notify "restart apache"
                        }

                        service("ensure apache is running") {
                            name = "httpd"
                            state = "started"
                        }
                    }

                    handlers {
                        service("restart apache") {
                            name = "httpd"
                            state = "restarted"
                        }
                    }
                }
            }

    def expected_output = '''
    ---
    - hosts: webservers
      vars:
        http_port: 80
        max_clients: 200
      remote_user: root
      tasks:
      - name: ensure apache is at the latest version
        yum:
          name: httpd
          state: latest
      - name: write the apache config file
        template:
          src: /srv/httpd.j2
          dest: /etc/httpd.conf
        notify:
        - restart apache
      - name: ensure apache is running
        service:
          name: httpd
          state: started
      handlers:
        - name: restart apache
          service:
            name: httpd
            state: restarted
    '''.stripIndent().trim()

    @Test void testExample() {
        def output = input.compile()
        assert expected_output == output
    }
}

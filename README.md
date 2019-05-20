# Groovible

Groovible is a proof of concept DSL that compiles to a very limited subset of Ansible. The Example section below
demonstrates the entirety of supported language features.


## Motivation

I'm tired of writing YAML and I wanted to see if a simple DSL could do the job any better.

## Example

This example is taken from the 
[Ansible Docs](https://docs.ansible.com/ansible/latest/user_guide/playbooks_intro.html#playbook-language-example) and is
the code used to validate the compiler in the acceptance test.

Write this DSL:

```groovy
playbook {
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
```

Get this YAML:

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

## Technologies used

* [Groovy](http://groovy-lang.org/): DSL definition
* [Jackson](https://github.com/FasterXML/jackson): Serialization
* [BATS](https://github.com/bats-core/bats-core): Acceptance testing
* [Gradle](https://gradle.org/): Build

## Test

You can run unit, integration, and functional tests with `./gradlew test`. The acceptance test can be run with 
`./gradlew acceptanceTest`. The acceptance test requires you to have BATS installed.

## Build and Run

To use the compiler directly, build with `./gradlew fullJar` and invoke the JAR under 
`build/libs/groovible-1.0-SNAPSHOT`. The CLI takes up to two arguments: the first required argument is the name of the
DSL script to execute, the second optional argument is the name of the YAML file to write out to. If the second
argument is not provided, the compiled YAML will be written to stdout.

For example:

```
$ java -jar build/libs/groovible-1.0-SNAPSHOT.jar acceptance-tests/example1_in.groovy output.yml
```

## License

Provided under the GNU Affero General Public License, Version 3.

## References

* Groovy
    * [Runtime and compile-time metaprogramming](http://groovy-lang.org/metaprogramming.html): 
      Covers propertyMissing, methodMissing, used to implement dynamic task blocks.
    * [Domain-Specific Languages](http://docs.groovy-lang.org/docs/latest/html/documentation/core-domain-specific-languages.html):
      General overview of Groovy DSL implementation.
    * [Integrating Groovy in a Java application](http://docs.groovy-lang.org/latest/html/documentation/guide-integrating.html):
      Describes how to compile Groovy code on demand, used to implement the compiler.
    * [Compile-time metaprogramming](http://docs.groovy-lang.org/latest/html/documentation/index.html#_compile_time_metaprogramming):
      Not yet used. Covers AST transformations.
* Ansible
    * [Intro to Playbooks](https://docs.ansible.com/ansible/latest/user_guide/playbooks_intro.html):
      Contains the example used for acceptance testing.
* Gradle
    * [Create a Groovy executable JAR with Gradle](https://stackoverflow.com/a/9749869) (StackOverflow)
* README
    * [A Beginners Guide to writing a Kickass README](https://medium.com/@meakaakka/a-beginners-guide-to-writing-a-kickass-readme-7ac01da88ab3) (Akash Nimare, Medium)
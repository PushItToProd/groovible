package com.pushittoprod.groovible.compiler

import org.junit.Test

class DslCompilerShellModuleTest {
    def input = '''
    play {
        tasks {
            shell {
                shell = "somescript.sh >> somelog.txt"
            }
        }
    }
    '''

    def expected_output = '''
    ---
    - tasks:
      - shell: somescript.sh >> somelog.txt
    '''.stripIndent().trim()

    @Test void shellCommandCompilesCorrectly() {
        def compiler = new DslCompiler()
        String yaml = compiler.compile(input)
        assert expected_output == yaml.stripIndent().trim()
    }
}

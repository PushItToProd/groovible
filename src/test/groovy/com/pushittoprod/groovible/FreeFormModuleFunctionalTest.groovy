package com.pushittoprod.groovible

import com.pushittoprod.groovible.compiler.DslCompiler
import org.junit.Test

class FreeFormModuleFunctionalTest {
    def input = '''
    play {
        tasks {
            shell {
                shell = "somescript.sh >> somelog.txt"
            }
            command {
                command = "cat /etc/motd"
            }
            raw {
                raw = "cat < /tmp/*.txt"
            }
            script {
                script = "/some/local/script.sh --some-argument 1234"
            }
            win_shell {
                win_shell = /C:\\somescript.ps1 >> C:\\somelog.txt/
            }
        }
    }
    '''

    def expected_output = '''
    ---
    - tasks:
      - shell: somescript.sh >> somelog.txt
      - command: cat /etc/motd
      - raw: cat < /tmp/*.txt
      - script: /some/local/script.sh --some-argument 1234
      - win_shell: C:\\somescript.ps1 >> C:\\somelog.txt
    '''.stripIndent().trim()

    @Test void shellCommandCompilesCorrectly() {
        def compiler = new DslCompiler()
        String yaml = compiler.compile(input)
        assert expected_output == yaml.stripIndent().trim()
    }
}

package com.pushittoprod.groovible.cli

import com.pushittoprod.groovible.compiler.DslCompiler

class CliMain {
    static void main(String[] args) {
        String inFile
        String outFile = null
        switch (args.length) {
            case 1:
                inFile = args[0]
                break
            case 2:
                inFile = args[0]
                outFile = args[1]
                break
            default:
                throw new Exception("You must pass one argument (the script to compile, output will be on stdout) " +
                        "or two arguments (the script to compile and the file to output to)")
        }
        def source = new File(inFile).text
        def compiler = new DslCompiler()
        def output = compiler.compile(source)
        if (outFile) {
            new File(outFile) << output
        } else {
            println(output.trim())
        }
    }
}

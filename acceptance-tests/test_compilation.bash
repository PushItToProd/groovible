#!/usr/bin/env bash

. acceptance-tests/common.bash

test test_example1 "Example 1 compiles correctly"
test_example1() {
  java -jar "$JAR_PATH" "$TEST_PATH/example1_in.groovy" 2>/dev/null \
    | diff - "$TEST_PATH/example1_expected.yml"
}

run_tests

#!/usr/bin/env bash

readonly JAR_PATH="build/libs/groovible-1.0-SNAPSHOT.jar"
readonly TEST_PATH="acceptance-tests"

java -jar "$JAR_PATH" "$TEST_PATH/example1_in.groovy" \
  | diff - "$TEST_PATH/example1_expected.yml"
if [ $? -ne 0 ]; then
  echo "test failed: compiled output does not match expected output" >&2
  exit 1
fi

echo "all tests passed!"

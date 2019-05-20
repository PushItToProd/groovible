#!/usr/bin/env bats

@test "compilation works" {
  load common
  java -jar "$JAR_PATH" "$TEST_PATH/example1_in.groovy" \
    | diff - "$TEST_PATH/example1_expected.yml"
  [ $? -eq 0 ]
}

#!/usr/bin/env bash
# Simple test runner that emits test results in JUnit compatible XML format.
# Usage: Write each test as a function returning success or failure. Invoke
# `test` with the name of the test and, optionally, a failure message.

readonly JAR_PATH="build/libs/groovible-1.0-SNAPSHOT.jar"
readonly TEST_PATH="acceptance-tests"
readonly TEST_RESULTS=build/test-results/acceptance-tests/TEST-acceptancetests.xml
readonly CLASSNAME="BashAcceptanceTests"

declare -A TESTS=()
declare -a FAILED_TESTS=()

test() {
  TESTS["$1"]="$2"
}

run_tests() {
  local msg
  mkdir -p "$(dirname "$TEST_RESULTS")"
  echo "<testsuite tests=\"${#TESTS[@]}\">" > "$TEST_RESULTS"
  for test in "${!TESTS[@]}"; do
    msg="${TESTS["$test"]}"
    if eval "$test"; then
      _log_success "$test"
    else
      _log_failure "$test" "$msg"
    fi
  done
  echo "</testsuite>" >> "$TEST_RESULTS"

  echo

  if [[ "${#FAILED_TESTS[@]}" -gt 0 ]]; then
    echo "[x] ${#FAILED_TESTS[@]} failed tests!"
    for test in FAILED_TESTS; do
      echo "* $test"
    done
  else
    echo "All ${#TESTS[@]} tests passed"
  fi
}

_log_success() {
  printf "."
  echo "  <testcase classname=\"$CLASSNAME\" name=\"$1\"/>" >>"$TEST_RESULTS"
}

_log_failure() {
  printf "x"
  if [[ -n "$2" ]]; then
    FAILED_TESTS+=("$1: $2")
  else
    FAILED_TESTS+=("$1")
  fi
  echo "  <testcase classname=\"$CLASSNAME\" name=\"$1\">" >>"$TEST_RESULTS"
  echo "    <failure type=\"TestFailure\">$2</failure>" >>"$TEST_RESULTS"
  echo "  </testcase>" >>"$TEST_RESULTS"
}

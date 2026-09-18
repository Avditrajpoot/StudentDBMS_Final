#!/usr/bin/env bash
set -e
mvn clean test
mvn exec:java

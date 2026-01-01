#!/bin/bash
# Build and run the CLI client for the Car Management & Fuel Tracking System
# Usage: ./run-cli.sh [arguments]

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CLI_DIR="$SCRIPT_DIR/cli"
JAR_NAME="car-management-cli-1.0-SNAPSHOT-jar-with-dependencies.jar"
JAR_PATH="$CLI_DIR/target/$JAR_NAME"

cd "$CLI_DIR"

# Build the CLI project if the jar does not exist or sources have changed
if [[ ! -f "$JAR_PATH" ]]; then
	echo "[INFO] Building CLI project..."
	mvn clean package -DskipTests
fi

# Run the CLI jar with any provided arguments
if [[ -f "$JAR_PATH" ]]; then
	echo "[INFO] Running CLI client..."
	java -jar "$JAR_PATH" "$@"
else
	echo "[ERROR] CLI jar not found after build. Check for build errors."
	exit 1
fi

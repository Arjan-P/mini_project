#!/bin/bash

# DBMS Mini Project - Compile & Run

cd "$(dirname "$0")"
cd src

echo "Compiling..."
javac -cp "../lib/mysql-connector-j.jar" *.java database/*.java service/*.java ui/*.java

if [ $? -eq 0 ]; then
    echo "✓ Running application..."
    java -cp "../lib/mysql-connector-j.jar:." Main
else
    echo "✗ Compilation failed"
    exit 1
fi

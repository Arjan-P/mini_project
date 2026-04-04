#!/bin/bash

echo "Compiling..."
javac -cp "lib/mysql-connector-j.jar" $(find src -name "*.java")

if [ $? -eq 0 ]; then
    echo "Running application..."
    java -cp "lib/mysql-connector-j.jar:src" Main
else
    echo "Compilation failed"
    exit 1
fi
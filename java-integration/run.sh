#!/bin/bash

echo "Compiling..."
javac -cp "lib/*" $(find src -name "*.java")

if [ $? -eq 0 ]; then
    echo "Running application..."
    java -cp "lib/*:src" Main
else
    echo "Compilation failed"
    exit 1
fi
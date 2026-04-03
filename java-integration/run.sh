#!/bin/bash

# ACADEMIC MANAGEMENT SYSTEM - QUICK START GUIDE

echo "=========================================="
echo "DBMS Mini Project - Quick Compile & Run"
echo "=========================================="

# Navigate to project directory
cd "$(dirname "$0")"

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Step 1: Compile
echo -e "${BLUE}[1/2] Compiling Java files...${NC}"
cd src

javac -cp "../lib/mysql-connector-j.jar" \
  *.java \
  database/*.java \
  service/*.java \
  ui/*.java

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Compilation successful!${NC}"
else
    echo -e "${RED}✗ Compilation failed!${NC}"
    exit 1
fi

# Step 2: Run
echo -e "${BLUE}[2/2] Starting application...${NC}"
java -cp "../lib/mysql-connector-j.jar:." Main

echo -e "${GREEN}✓ Application exited${NC}"

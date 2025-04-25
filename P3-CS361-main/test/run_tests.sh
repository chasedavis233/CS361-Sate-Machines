#!/bin/bash

for f in test/file0.txt test/file2.txt test/file5.txt; do
    echo "Running $f"
    java tm.TMSimulator "$f"
    echo ""
done

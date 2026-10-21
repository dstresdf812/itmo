#!/bin/bash

set -e

kotlinc src/*.kt \
  -cp lib/fastcgi-lib.jar \
  -include-runtime \
  -d lab2.jar

echo "Build successful"
#!/bin/bash

set -e

java \
  -DFCGI_PORT=24001 \
  -cp "lab2.jar:lib/fastcgi-lib.jar" \
  com.dstresdf.MainKt
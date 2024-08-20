#!/bin/bash
set -e
./package.sh
eb deploy switchredux-prod
rm app.zip

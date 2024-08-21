#!/bin/bash
set -e
./package.sh
eb deploy switchtwo-prod
rm app.zip

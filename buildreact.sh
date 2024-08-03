#!/bin/bash
set -e
#sed -i '' 's|address: "http://localhost:8080"|address: ""|g' moose-react-app/src/ServerConfig.ts
cd switchreact/
npm run build
cd ..
#sed -i '' 's|address: ""|address: "http://localhost:8080"|g' moose-react-app/src/ServerConfig.ts
rm -rf src/main/resources/static
cp -R switchreact/build src/main/resources/static


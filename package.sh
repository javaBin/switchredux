#!/usr/bin/env bash
set -e

./buildreact.sh
mvn clean install

BASEDIR=$(cd -P -- "$(dirname -- "$0")" && pwd -P)

# resolve symlinks
while [ -h "$BASEDIR/$0" ]; do
    DIR=$(dirname -- "$BASEDIR/$0")
    SYM=$(readlink $BASEDIR/$0)
    BASEDIR=$(cd $DIR && cd $(dirname -- "$SYM") && pwd)
done
cd ${BASEDIR}


echo "> Assembling files"
secret_properties_file="config.properties"

ansible-vault decrypt "conf/prod.properties.encrypted" --output=${secret_properties_file}
if [ ! -f ${secret_properties_file} ]; then
    echo "Something went wrong with decrypting secret properties. File ${secret_properties_file} is missing. Can't deploy..."
    exit 1
fi

cp ./target/switch-ktor-0.0.1-jar-with-dependencies* ./app.jar

echo "> Packaging app"
chmod 644 ${secret_properties_file}
zip -r app.zip app.jar .ebextensions Procfile ${secret_properties_file}
if [ $? -ne 0 ]; then
  rm -f app.jar ${secret_properties_file}
  echo "> Package failed!"
  exit 1
fi
rm -f app.jar ${secret_properties_file}
echo "> Done packaging app"

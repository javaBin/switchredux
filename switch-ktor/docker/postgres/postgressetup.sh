#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    create user localdevuser with login password 'localdevuser';
    CREATE DATABASE switcharoolocal with owner localdevuser;
    CREATE DATABASE switcharoounit with owner localdevuser;
EOSQL
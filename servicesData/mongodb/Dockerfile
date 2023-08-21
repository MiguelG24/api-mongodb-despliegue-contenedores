FROM mongo:latest

COPY base-datos.sh /docker-entrypoint-initdb.d/

RUN chmod +x /docker-entrypoint-initdb.d/*.sh

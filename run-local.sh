
cd api-personas-mongodb
gradle clean buildImage

docker compose up --build -d

docker compose push

#docker compose stop
#docker compose kill
#docker compose rm -f

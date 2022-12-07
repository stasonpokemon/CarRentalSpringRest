docker stop postgres_car_rental_spring_rest
docker rm postgres_car_rental_spring_rest
docker run -d --name postgres_car_rental_spring_rest -e POSTGRES_USERNAME=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=car_rental_spring_rest -p 5432:5432 postgres

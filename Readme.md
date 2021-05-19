# Boopr

[Find Live Site here!](https://boopr.dog)

Boopr is a post-human social media platform that allows dog owners to consume adorable pet content in a social media  
landscape mired with human stress. A full stack web application that uses SpringBoot, Thymeleaf, Mapbox API  
integration, and Bootstrap, Boopr creates a social media experience for users to share pictures of their pet canines.  
Users can upload profiles for their dogs as well as discover other dogs thru geo-locational proximity. Additionally, users  
may upload their favorite pictures of their pups and 'boop'/like pictures of other charming furry friends.

## Deployment

Our project uses docker with environment variables for a quick an easy setup. 

Use the following to pull the image from the dockerhub repo

    docker pull jacobgonzalez0/boopr
and then setup a file for the environment variables

    $ touch .env
    $ nano .env
Inside the environment variable file paste using the following template and fill out the login for an MySQL/MariaDB server and the [Mapbox API key](https://docs.mapbox.com/mapbox-gl-js/api/)
You can also pull one from a docker image as well.
	    
    SPRING_DATASOURCE_URL=jdbc:mysql://<ServerAddress>/<TableName>?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    SPRING_DATASOURCE_USERNAME=<MySQL Username>
    SPRING_DATASOURCE_PASSWORD=<MySQL Password>
    image-upload-path=images/
    mapbox-key=<MapBox Key>


Afterwards just run the docker image with this command

    docker run -d --env-file=".env" -p 88:8080 jacobgonzalez0/boopr

### Persistent storage

An Optional step is to create a volume for the app using volumes

```
docker volume create boopr
```
and Run using 

    docker run -d -v boopr:/images --env-file=".env" -p 88:8080 jacobgonzalez0/boopr


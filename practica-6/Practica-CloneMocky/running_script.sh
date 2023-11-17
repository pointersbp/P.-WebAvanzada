#!/bin/bash
command="java -jar /app/app.jar --server.port=${APPLICATION_PORT}"

if [ -z "$APP_DB_URL" ]
then
	echo "APP_DB_URL is not set. Using default application properties."
else
	if [ -z "$APP_DB_DRIVER_CLASSNAME" ] || [ -z "$APP_DB_PLATFORM" ]
	then
		echo "Error: Missing APP_DB_DRIVER_CLASSNAME and/or APP_DB_PLATFORM."
		exit 1
	else
		if [ -z "$APP_DB_USERNAME" ]
		then
			echo "Error: APP_DB_USERNAME is missing."
			exit 2
		else
			command="${command} --spring.datasource.url=${APP_DB_URL} --spring.datasource.driverClassName=${APP_DB_DRIVER_CLASSNAME} --spring.jpa.database-platform=${APP_DB_PLATFORM} --spring.datasource.username=${APP_DB_USERNAME}"
			if ! [ -z "$APP_DB_PASSWORD" ]
			then
				command="${command} --spring.datasource.password=${APP_DB_PASSWORD}"
			fi
		fi
	fi
fi

if [ -z "$REDIS_HOST" ]
then
	echo "Not redist host defined."
else
	command="${command} --spring.session.store-type=redis --spring.session.redis.flush-mode=on_save --spring.redis.host=${REDIS_HOST} --spring.redis.port=${REDIS_PORT}"
	if [ -z "$REDIS_PASS"]
	then
		echo "Redis not password."
	else
		command="${command} --spring.redis.password=${REDIS_PASS}"
	fi
fi
echo $command
exec $command

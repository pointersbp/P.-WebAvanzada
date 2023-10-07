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

exec $command
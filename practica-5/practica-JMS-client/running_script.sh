#!/bin/bash
command="java -jar /app/app.jar --server.port=${APPLICATION_PORT}"
exec $command
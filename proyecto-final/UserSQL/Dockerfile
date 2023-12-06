FROM mysql:latest
ADD ./Usuario.sql /docker-entrypoint-initdb.d/Usuario.sql
RUN chown -R mysql:mysql /docker-entrypoint-initdb.d/
CMD ["mysqld"]

FROM mysql:latest
ADD ./Ventas.sql /docker-entrypoint-initdb.d/Ventas.sql
RUN chown -R mysql:mysql /docker-entrypoint-initdb.d/
CMD ["mysqld"]

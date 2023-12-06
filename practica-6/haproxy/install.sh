#!/bin/bash

# Instalar Certbot
snap install --classic certbot
ln -s /snap/bin/certbot /usr/bin/certbot

# Ejecutar Certbot para obtener credenciales
sudo certbot certonly --standalone -d app1.donaldsg.me -d app2.donaldsg.me -d app3.donaldsg.me -m tu_correo@gmail.com

# Las credenciales se guardan en /etc/letsencrypt/live/<URI>
# Unir credenciales dentro de un solo archivo para ser usadas por HAProxy
mkdir -p /etc/haproxy/certs

cat /etc/letsencrypt/live/app1.donaldsg.me/fullchain.pem /etc/letsencrypt/live/app1.donaldsg.me/privkey.pem > /etc/haproxy/certs/app1.donaldsg.me.pem
cat /etc/letsencrypt/live/app2.donaldsg.me/fullchain.pem /etc/letsencrypt/live/app2.donaldsg.me/privkey.pem > /etc/haproxy/certs/app2.donaldsg.me.pem
cat /etc/letsencrypt/live/app3.donaldsg.me/fullchain.pem /etc/letsencrypt/live/app3.donaldsg.me/privkey.pem > /etc/haproxy/certs/app3.donaldsg.me.pem

chmod -R go-rwx /etc/haproxy/certs

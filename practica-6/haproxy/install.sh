#Instalar snap
apt update
apt install snapd -y

#Instalar Certbot
snap install --classic certbot
ln -s /snap/bin/certbot /usr/bin/certbot

#Ejecutar certbot para obtener credenciales
sudo certbot certonly --standalone -d servidorapp2.newdeveloperjsh.software  -m luisdonaldosalguero7@gmail.com

#Las credenciales se guardan en /etc/letsencrypt/live/<URI>
#Unir credenciales dentro de un solo archivo para ser usadas por haproxy
mkdir -p /etc/haproxy/certs
DOMAIN='servidorapp2.newdeveloperjsh.software'
cat /etc/letsencrypt/live/$DOMAIN/fullchain.pem /etc/letsencrypt/live/$DOMAIN/privkey.pem > /etc/haproxy/certs/$DOMAIN.pem
cat /etc/letsencrypt/live/servidorapp2.newdeveloperjsh.software/fullchain.pem /etc/letsencrypt/live/servidorapp2.newdeveloperjsh.software/privkey.pem > /etc/haproxy/certs/servidorapp2.newdeveloperjsh.software.pem
chmod -R go-rwx /etc/haproxy/certs
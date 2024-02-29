set MYSQL_ROOT_PASSWORD="iA2YKJH;zED8n@e"
docker cp dump.sql mdchat_mysql:/opt
docker exec mdchat_mysql /bin/sh -c "/bin/cat /opt/dump.sql | /bin/mysql -uroot --password=""%MYSQL_ROOT_PASSWORD%"" mdchat"


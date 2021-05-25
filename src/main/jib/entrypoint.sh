#!/bin/sh

echo "The application will start in ${KEY_VALUE_STORE_SLEEP}s..." && sleep ${KEY_VALUE_STORE_SLEEP}
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Duser.timezone=Asia/Dhaka -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "bd.ac.buet.KeyValueStore.KeyValueStoreApplication"  "$@"

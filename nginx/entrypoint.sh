#!/bin/sh

# Verifying the environment variables were found
if [ -z "$BASIC_AUTH_USER" ] || [ -z "$BASIC_AUTH_PASS" ]; then
    echo "Error: User or password where not defined! Use the environment variables BASIC_AUTH_USER e BASIC_AUTH_PASS."
    exit 1
fi

# Creating credentials passing through the variables
htpasswd -bc /etc/nginx/.htpasswd "$BASIC_AUTH_USER" "$BASIC_AUTH_PASS"

# Starting the NGINX image
exec nginx -g "daemon off;"

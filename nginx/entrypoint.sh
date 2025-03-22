#!/bin/sh

# Verifying the environment variables were found
if [ -z "$BASIC_AUTH_USER" ] || [ -z "$BASIC_AUTH_PASS" ]; then
    echo "Error: User or password where not defined! Use the environment variables BASIC_AUTH_USER e BASIC_AUTH_PASS."
    exit 1
fi

# Verifying the environment variables were found
if [ -z "$ROOT_AUTH_USER" ] || [ -z "$ROOT_AUTH_PASS" ]; then
    echo "Error: User or password where not defined! Use the environment variables ROOT_AUTH_USER e ROOT_AUTH_PASS."
    exit 1
fi

# Creating credentials passing through the variables
# The first commando rewrite the file, the second one add new user though
htpasswd -bc /etc/nginx/.htpasswd "$BASIC_AUTH_USER" "$BASIC_AUTH_PASS"
htpasswd -b /etc/nginx/.htpasswd "$ROOT_AUTH_USER" "$ROOT_AUTH_PASS"

# Starting the NGINX image
exec nginx -g "daemon off;"
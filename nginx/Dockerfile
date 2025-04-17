FROM nginx:1.27.4-alpine

# Deleting the default configuration folder
RUN rm /etc/nginx/conf.d/default.conf

# Coping the files into the image
COPY nginx.conf /etc/nginx/conf.d
COPY entrypoint.sh /entrypoint.sh
COPY certificate.crt /etc/nginx/ssl/certificate.crt
COPY private.key /etc/nginx/ssl/private.key

# Installing the apache2-utils to use the command `htpasswd`
RUN apk update && apk add --no-cache apache2-utils

# Making the entrypoint file executable
RUN chmod +x /entrypoint.sh

# Defining the entry point to the container
ENTRYPOINT ["/entrypoint.sh"]
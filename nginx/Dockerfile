FROM nginx:1.27.4-alpine

# Delete the configuration default folder
RUN rm /etc/nginx/conf.d/default.conf

# Copy the new configuration file into the conf image folder
COPY nginx.conf /etc/nginx/conf.d

# Create a html folder and the empty js file in it
RUN mkdir /var/www/html -p && touch /var/www/html/index.js
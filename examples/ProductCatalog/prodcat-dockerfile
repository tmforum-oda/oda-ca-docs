FROM node:10.19
COPY productCatalogMicroservice/implementation/package*.json /src/
WORKDIR /src
RUN npm install
COPY productCatalogMicroservice/implementation ./
EXPOSE 8080
CMD ["node", "index.js"]

FROM node:16
WORKDIR /src
COPY package*.json ./

RUN npm install
COPY . .
CMD ["node", "initialization.js"]

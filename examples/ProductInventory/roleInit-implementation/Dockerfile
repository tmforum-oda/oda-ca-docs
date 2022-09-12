FROM node:12
WORKDIR /src
COPY package*.json ./

RUN npm install
COPY . .
CMD ["node", "initialization.js"]

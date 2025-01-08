# Folosește imaginea Node.js ca bază
FROM node:16

# Setează directorul de lucru în container
WORKDIR /usr/src/app

# Copiază package.json și package-lock.json
COPY package*.json ./

# Instalează dependențele
RUN npm install

# Copiază restul fișierelor aplicației în container
COPY . .

# Expune portul 3000
EXPOSE 3000

# Comanda de rulare
CMD ["npm", "start"]


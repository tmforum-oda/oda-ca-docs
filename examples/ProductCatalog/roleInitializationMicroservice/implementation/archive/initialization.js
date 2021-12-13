const mongoUtils = require('./mongoUtils.js');


var initialPartyRole = {
  name: "Admin",
  id: "ffe4111d-2bea-4a48-9ee9-d8dcbf3ebf5d",
  href: "http://localhost:8080/productcatalog/tmf-api/partyRoleManagement/v4/partyrole/ffe4111d-2bea-4a48-9ee9-d8dcbf3ebf5d",
  "@schemaLocation": "http://localhost:8080/productcatalog/tmf-api/partyRoleManagement/v4/docs/#/",
  "@type": "PartyRole",
  "@baseType": "PartyRole",
}


mongoUtils.connect().then(db => {
    db.collection('PartyRole')
        .insertOne(initialPartyRole)
        .then(() => {
          console.log("initialize createPartyRole complete");
          process.exit(0)
        })
        .catch((error) => {
        console.log("initialize createPartyRole: error=" + error);
        process.exit(1)
        })
    })
    .catch((error) => {
    console.log("createPartyRole: error=" + error);
    process.exit(1)
  })
const axios = require('axios');

const initialPartyRole = {
  name: "Admin"
}

const delay = ms => new Promise(res => setTimeout(res, ms));

var releaseName = process.env.RELEASE_NAME; 
var componentName = process.env.COMPONENT_NAME; 

const createPartyRole = async () => {
  var complete = false;

  while (complete == false) {
    try {
        await delay(5000);  // retry every 5 seconds
        const url = 'http://' + releaseName + '-partyroleapi:8080/' + componentName + '/tmf-api/partyRoleManagement/v4/partyRole';
        console.log('POSTing partyRole to: ', url);
        const res = await axios.post(
          url, 
          initialPartyRole,
          {timeout: 10000});
        console.log(`Status: ${res.status}`);
        console.log('Body: ', res.data);
        complete = true;
        
        console.log('Telling Istio were finished');
        const res2 = await axios.post(
          'http://127.0.0.1:15020/quitquitquit', 
          {},
          {timeout: 10000});

        process.exit(0);
    } catch (err) {
      console.log('Initialization failed - retrying in 5 seconds');
    }
  }
};

createPartyRole();


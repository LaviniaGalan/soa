/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

const {onRequest} = require("firebase-functions/v2/https");
const logger = require("firebase-functions/logger");
const cors = require('cors')({ origin: true });
// Create and deploy your first functions
// https://firebase.google.com/docs/functions/get-started

// exports.helloWorld = onRequest((request, response) => {
//   logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });

exports.topDestination = onRequest((request, response) => {
    cors(request, response, () =>
    {
        const destinationList = ['Maldive', 'Rome', 'Paris', 'Berlin', 'Saint Tropez', 'Sicily', 'Venice', 'Florence',
            'Milan', 'Barcelona', 'Madrid', 'Stockholm', 'Oslo', 'Bucharest', 'London', 'New York', 'Zanzibar', 'Tenerife'];
        const number = Math.round(Math.random() * destinationList.length);
        const destination = destinationList[number];

        // Set the CORS headers
        response.set('Access-Control-Allow-Origin', '*')
        response.set('Access-Control-Allow-Methods', 'GET,POST,OPTIONS,DELETE,PUT')
        response.set('Access-Control-Allow-Headers', 'Content-Type')


        response.status(200).json({destination});
    })
});

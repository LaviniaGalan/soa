import React from "react";
import axios from 'axios'

const Button = () => ( <button onClick={() => {
    axios({
        method: 'get',
        url: 'https://topdestination-gamyl2zocq-uc.a.run.app'
    })
        .then((response) => {
            alert('Top destination: ' + response.data.destination);
        })
        .catch((error) => {
            alert('Error :( ');
            console.log(error);
        });
    }}>GET</button> )

export default Button;

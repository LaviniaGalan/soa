import axios from 'axios';

let API_URL = 'http://localhost:8765/api/trip/service/';

class TripService {

  makeBooking(booking, token){
    const g = JSON.stringify(booking);
    console.log(g);
    return axios.post(API_URL + 'private/makeBooking', JSON.stringify(booking),
  {headers: {'Content-Type':'application/json; charset=UTF-8', 'Authorization':'Bearer ' + token}});
  }

  filterBookings(userId, token){
    return axios.get(API_URL + '/private/user/'+ userId,
  {headers: {'Content-Type':'application/json; charset=UTF-8', 'Authorization':'Bearer ' + token}});
  }

  filterClients(tripId){
    return axios.get(API_URL + 'public/tripClients/' + tripId,
  {headers: {'Content-Type':'application/json; charset=UTF-8'}});
  }

  findTripById(tripId){
    return axios.get(API_URL + 'public/' + tripId,
        {headers: {'Content-Type':'application/json; charset=UTF-8'}});
  }

  findAllTrips(){
    return axios.get(API_URL + 'public/allTrips',
    {headers: {'Content-Type':'application/json; charset=UTF-8'}});
  }
}
export default new TripService();

import axios, { AxiosRequestConfig } from 'axios'
import {BehaviorSubject} from 'rxjs';

const USER_API_URL = 'http://localhost:8765/api/user/service/';
const AUTH_API_URL = 'http://localhost:8765/api/auth/service/';

const currentUserSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('currentUser')));
class UserService {
  get currentUserValue(){
    return currentUserSubject.value;
  }

  get currentUser(){
    return currentUserSubject.asObservable();
  }

  login(user){

    const config = {
      'authorization':'Basic ' + btoa(user.email + ':' + user.password),
      'content-type': 'application/json',
    };
    const body = `{"email":"${user.email}", "password":"${user.password}"}`;
    console.log(body);
    return axios.post(AUTH_API_URL + 'login', body, {headers:  { 'Content-Type': 'application/json' } }).then(response => {
      localStorage.setItem('currentUser', JSON.stringify(response.data));
      currentUserSubject.next(response.data);
    });
  }

  logOut(user){
    return axios.post(AUTH_API_URL + 'logout/' + user.email, "", {headers:  { 'Content-Type': 'application/json' } }).then(response => {
      localStorage.removeItem('currentUser');
      currentUserSubject.next(null);
    })
  }

  register(user){
    return axios.post(USER_API_URL + 'signup', JSON.stringify(user),
  {headers: {'Content-Type':'application/json; charset=UTF-8'}});
  }

}

export default new UserService();

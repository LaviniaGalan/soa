import React from 'react';
import UserService from '../../services/user.service';
import {User} from '../../models/user';
import TripService from "../../services/trip.service";

const RemoteButton = React.lazy(() => import("app2/Button"));

export default class HomePage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            trips: [],
            errorMessage: '',
            infoMessage: '',
            currentUser: new User()
        };
    }

    componentDidMount() {
        UserService.currentUser.subscribe(data => {
            this.setState({
                currentUser: data
            });
        });

        this.getAllTrips();
    }

    getAllTrips() {
        this.setState({
            trips: {loading: true}
        });

        TripService.findAllTrips().then(tripsData => {
            this.setState({trips: tripsData.data});
        });
    }

    makeBooking(trip) {
        if (!this.state.currentUser) {
            this.setState({errorMessage: 'Please log in for booking a trip.'});
            return;
        }

        localStorage.setItem('currentTrip', JSON.stringify(trip));
        this.props.history.push('/payment/' + trip.id);
    }

    detail(trip) {
        localStorage.setItem('currentTrip', JSON.stringify(trip));
        this.props.history.push('/detail/' + trip.id);
    }

    render() {
        const {trips, infoMessage, errorMessage} = this.state;
        return (
            <div className="col-md-12">
                {infoMessage &&
                    <div className="alert alert-success">
                        <strong>Successfull! </strong>{infoMessage}
                        <button type="button" className="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                }
                {errorMessage &&
                    <div className="alert alert-danger">
                        <strong>Error! </strong>{errorMessage}
                        <button type="button" className="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                }
                {trips.loading && <em> Loading trips...</em>}
                {trips.length &&
                    <div style={{marginTop: 50}}>
                        <h4 style={{marginBottom: 30}}>Available Trips: </h4>

                        <table className="table table-bordered table-secondary">
                            <thead>
                            <tr>
                                <th scope="col">Id</th>
                                <th scope="col">Destination</th>
                                <th scope="col">Description</th>
                                <th scope="col">Price</th>
                                <th scope="col">Duration</th>
                                <th scope="col">Details</th>
                                <th scope="col">Book</th>
                            </tr>
                            </thead>
                            <tbody>
                            {trips.map((trip, index) =>
                                <tr key={trip.id}>
                                    <th scope="row">{index + 1}</th>
                                    <td>{trip.destination}</td>
                                    <td>{trip.description}</td>
                                    <td>${trip.price}</td>
                                    <td>{trip.duration} days</td>
                                    <td>
                                        <button className="btn btn-outline-dark btn-group-sm" onClick={() => this.detail(trip)}>Detail
                                        </button>
                                    </td>
                                    <td>
                                        <button className="btn btn-outline-dark btn-group-sm" onClick={() => this.makeBooking(trip)}>Book
                                        </button>
                                    </td>
                                </tr>
                            )}
                            </tbody>
                        </table>
                    </div>
                }
                <div>
                    <h2 style={{marginTop: 20, marginBottom: 5}}>Top Destination</h2>
                    <React.Suspense fallback="Loading Button">
                        <RemoteButton />
                    </React.Suspense>
                </div>
            </div>
        );
    }

}

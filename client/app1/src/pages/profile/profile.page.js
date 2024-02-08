import React from 'react';
import UserService from '../../services/user.service';
import TripService from '../../services/trip.service';

export default class ProfilePage extends React.Component {

    constructor(props) {
        super(props);

        if (!UserService.currentUserValue) {
            this.props.history.push('/');
            return;
        }

        this.state = {
            user: UserService.currentUserValue,
            bookings: []
        };
    }

    componentDidMount() {
        this.setState({
            bookings: {loading: true}
        });
        const user = this.state.user;
        TripService.filterBookings(user.id, user.token).then(bookings => {
            this.setState({bookings: bookings.data});
        });
    }

    render() {
        const {bookings} = this.state;
        return (
            <div className="col-md-12">
                <div className="jumbotron">
                    <h1 className="display-4">Hello, {this.state.user.name}</h1>
                </div>
                {bookings.loading && <em>Loading bookings...</em>}
                {bookings.length > 0 &&
                    <table className="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Destination</th>
                            <th scope="col">Description</th>
                            <th scope="col">Price</th>
                            <th scope="col">Booking Date</th>
                        </tr>
                        </thead>
                        <tbody>
                        {bookings.map((booking, index) =>
                            <tr key={booking.id}>
                                <th scope="row">{index + 1}</th>
                                <td>{booking.trip.destination}</td>
                                <td>{booking.trip.description}</td>
                                <td>{booking.trip.price}</td>
                                <td>{booking.bookingDate}</td>
                            </tr>
                        )
                        }
                        </tbody>
                    </table>
                }
                {bookings.length === 0 &&
                    <div>No bookings so far</div>
                }
            </div>
        );
    }

}

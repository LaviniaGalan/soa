import React from 'react';
import TripService from '../../services/trip.service';

export default class DefaultPage extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            id: this.props.match.params.id,
            trip: JSON.parse(localStorage.getItem('currentTrip')),
            clients: [],
        };
    }

    componentDidMount() {
        this.findClientsOfTrip();
    }

    findClientsOfTrip() {
        TripService.filterClients(this.state.id).then(clients => {
            this.setState({clients: clients.data});
        });
    }

    render() {
        const {clients} = this.state;
        return (
            <div className="col-md-12">
                <div className="card bg-light">
                    <div className="card-body">
                        <h3 className="card-title" style={{marginBottom: 30}}>Trip to {this.state.trip.destination}</h3>
                        <h4>Trip description: {this.state.trip.description}</h4>
                        <h4>Trip price: {this.state.trip.price} $</h4>
                        <h4>Trip duration: {this.state.trip.duration} days</h4>
                    </div>

                </div>
                <div><h5>Clients that booked this trip so far:</h5></div>
                {clients.length > 0 &&
                    <table className="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Client Name</th>
                        </tr>
                        </thead>
                        <tbody>
                        {clients.map((client, index) =>
                            <tr key={client}>
                                <th scope="row">{index + 1}</th>
                                <td>{client}</td>
                            </tr>
                        )}
                        </tbody>
                    </table>
                }
                {clients.length === 0 &&
                    <div>No user has booked this trip so far</div>
                }
            </div>
        );
    }

}

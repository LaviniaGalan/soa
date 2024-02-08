import React from 'react'
import { render } from 'react-dom'
import Card from 'react-credit-cards'
import './card-payment.page.css'
import { useParams } from "react-router-dom"

import {
    formatCreditCardNumber,
    formatCVC,
    formatExpirationDate
} from './utils'

import 'react-credit-cards/es/styles-compiled.css'
import {Booking} from "../../models/booking";
import TripService from "../../services/trip.service";
import UserService from "../../services/user.service";

export default class CardPaymentPage extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            number: '',
            name: '',
            expiry: '',
            cvc: '',
            issuer: '',
            focused: '',
            formData: null,
            props: props
        }
    }

    handleCallback = ({ issuer }, isValid) => {
        if (isValid) {
            this.setState({ issuer })
        }
    }

    handleInputFocus = ({ target }) => {
        this.setState({
            focused: target.name
        })
    }

    handleInputChange = ({ target }) => {
        if (target.name === 'number') {
            target.value = formatCreditCardNumber(target.value)
        } else if (target.name === 'expiry') {
            target.value = formatExpirationDate(target.value)
        } else if (target.name === 'cvc') {
            target.value = formatCVC(target.value)
        }

        this.setState({ [target.name]: target.value })
    }

    handleSubmit = e => {
        e.preventDefault()
        alert('You have finished payment!')
        this.form.reset()
        const id = this.state.props.history.location.pathname.substr(9,1)
        TripService.findTripById(id).then(trip =>{
            var booking = new Booking(UserService.currentUserValue.id, trip.data);
            TripService.makeBooking(booking, UserService.currentUserValue.token).then(data => {
                this.setState({infoMessage: 'You booked the trip successfully.'});
                this.props.history.push('/profile');
            }, error => {
                this.setState({errorMessage: 'Unexpected error occurred.'});
            });
        })
    }

    render () {
        const { name, number, expiry, cvc, focused, issuer } = this.state

        return (
            <div key='Payment'>
                <div className='App-payment'>
                    <h4>Payment details</h4>
                    <Card
                        number={number}
                        name={name}
                        expiry={expiry}
                        cvc={cvc}
                        focused={focused}
                        callback={this.handleCallback}
                    />
                    <form ref={c => (this.form = c)} onSubmit={this.handleSubmit}>
                        <div className='form-group'>
                            <small>Name on card:</small>

                            <input
                                type='text'
                                name='name'
                                className='form-control'
                                placeholder='Name'
                                pattern='[a-z A-Z-]+'
                                required
                                onChange={this.handleInputChange}
                                onFocus={this.handleInputFocus}
                            />
                        </div>
                        <div className='form-group'>
                            <small>Card Number:</small>

                            <input
                                type='tel'
                                name='number'
                                className='form-control'
                                placeholder='Card Number'
                                pattern='[\d| ]{16,22}'
                                maxLength='19'
                                required
                                onChange={this.handleInputChange}
                                onFocus={this.handleInputFocus}
                            />
                        </div>

                        <div className='form-group'>
                            <small>Expiration Date:</small>

                            <input
                                type='tel'
                                name='expiry'
                                className='form-control'
                                placeholder='Valid Until'
                                pattern='\d\d/\d\d'
                                required
                                onChange={this.handleInputChange}
                                onFocus={this.handleInputFocus}
                            />
                        </div>
                        <div className='form-group'>
                            <small>CVC:</small>

                            <input
                                type='tel'
                                name='cvc'
                                className='form-control'
                                placeholder='CVC'
                                pattern='\d{3}'
                                required
                                onChange={this.handleInputChange}
                                onFocus={this.handleInputFocus}
                            />
                        </div>
                        <input type='hidden' name='issuer' value={issuer} />
                        <div className='form-actions'>
                            <button>Submit</button>
                        </div>
                    </form>
                </div>
            </div>
        )
    }
}

render(<CardPaymentPage />, document.getElementById('root'))

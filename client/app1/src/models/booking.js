export class Booking {
  constructor(userId, trip, bookingDate, id){
    this.id = id;
    this.userId = userId;
    this.trip = trip;
    this.bookingDate = bookingDate;
  }
}

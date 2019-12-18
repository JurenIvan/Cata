import {TripPlan} from "./trip-plan";

export class Trip {

  constructor(
    public id: number,
    public startDateTime: Date,
    public endDateTime: Date,
    public price: number,
    public passengerCount: number,
    public tripPlanDto: TripPlan
  ){}
}

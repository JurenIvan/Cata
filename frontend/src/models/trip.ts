import {TripPlan} from "./trip-plan";

export class Trip {

  constructor(
    public id: number,
    public startDateTime: string,
    public endDateTime: string,
    public price: number,
    public passengerCount: number,
    public tripPlanDto: TripPlan
  ){}
}

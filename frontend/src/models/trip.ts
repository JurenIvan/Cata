import {TripPlan} from "./trip-plan";
import {User} from "./user";

export class Trip {

  constructor(
    public id: number,
    public startDateTime: Date,
    public endDateTime: Date,
    public price: number,
    public passengers: User[],
    public tripPlanid: number,
    public tripPlanDto: TripPlan
  ){}
}

import {Location} from "./location";

export class TripPlan {

  constructor(
    public id: number,
    public description: string,
    public locationList: Location[],
    public minNumberOfPassengers: number,
    public pictureUrl: string
  ){}
}

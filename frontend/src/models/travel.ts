export class Travel {

  constructor(
    public id: number,
    public title: string,
    public startDateTime: string,
    public endDateTime: string,
    public price: number,
    public passengerCount: number,
    public description: string,
    public pictureUrl: string
  ){}
}

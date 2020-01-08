import {Role} from "./role.enum";

export class User {

  constructor(
    public id: number,
    public email: string,
    public username: string,
    public passwordHash: string,
    public yearOfBirth: number,
    public roles: Role[]
  ) {}

}

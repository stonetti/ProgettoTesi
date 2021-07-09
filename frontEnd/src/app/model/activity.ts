import {User} from "./user";

export interface Activity{
   id : string;
   name : string;
   description : string;
   users : User[];
   expiringDate : Date;
   sub_activities : Activity[];
}

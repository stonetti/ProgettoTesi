import {User} from "./user";
import {Activity} from "./activity";

export interface Macro {
  id: string;
  name: string;
  description: string;
  dateOfCreation: Date;
  expiringDate: Date;
  activities: Activity[];
  pm: User[];
  assignedUsers: User[];
  subAssignedUsers: User[];
}

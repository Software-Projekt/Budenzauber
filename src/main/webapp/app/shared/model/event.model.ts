import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface IEvent {
    id?: number;
    archivierungsDatum?: Moment;
    erstellungsDatum?: Moment;
    freigegeben?: boolean;
    grusswort?: any;
    name?: string;
    user?: IUser;
}

export class Event implements IEvent {
    constructor(
        public id?: number,
        public archivierungsDatum?: Moment,
        public erstellungsDatum?: Moment,
        public freigegeben?: boolean,
        public grusswort?: any,
        public name?: string,
        public user?: IUser
    ) {
        this.freigegeben = this.freigegeben || false;
    }
}

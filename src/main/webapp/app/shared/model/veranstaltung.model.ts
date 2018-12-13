import { Moment } from 'moment';
import { IBild } from 'app/shared/model//bild.model';

export interface IVeranstaltung {
    id?: number;
    archivierungsDatum?: Moment;
    erstellungsDatum?: Moment;
    freigegeben?: boolean;
    grusswort?: string;
    name?: string;
    bilds?: IBild[];
}

export class Veranstaltung implements IVeranstaltung {
    constructor(
        public id?: number,
        public archivierungsDatum?: Moment,
        public erstellungsDatum?: Moment,
        public freigegeben?: boolean,
        public grusswort?: string,
        public name?: string,
        public bilds?: IBild[]
    ) {
        this.freigegeben = this.freigegeben || false;
    }
}
